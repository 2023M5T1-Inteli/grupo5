package br.edu.inteli.cc.m5.maverick.services;

import br.edu.inteli.cc.m5.maverick.models.FlightNodeEntity;
import br.edu.inteli.cc.m5.maverick.models.Path;
import br.edu.inteli.cc.m5.maverick.repositories.FlightNodeRepository;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
* Implements basic operations for accessing geographic data stored in DTED format files using the GDAL library.
*/

@Service
public class DTEDDatabaseService {

    /*
    * List of datasets loaded from the DTED files in the resources folder.
    */
    private final List<Dataset> m_DatabaseDtedDatasets;

    /**
    * Repository for FlightNodeEntity objects.
    */
    private final FlightNodeRepository flightNodeRepository;

    /**
    * Hashmap of FlightNodeEntity objects indexed by UUID.
    */
    private HashMap<UUID, FlightNodeEntity> nodeSet;


    /**
    * Constructs a DTEDDatabaseService object by loading the DTED files from the given resources directory
    * and initializing the FlightNodeRepository.
    * 
    @param resourcesDirectory The path of the resources directory containing the DTED files.
    @param flightNodeRepository The repository for FlightNodeEntity objects.
    @throws Exception If the resources directory cannot be found or no .dt2 files are found in the directory.
    */
    public DTEDDatabaseService(@Value("dted/Rio") String resourcesDirectory, FlightNodeRepository flightNodeRepository) throws Exception {
        this.flightNodeRepository = flightNodeRepository;

        // Get version (for debug purposes only)
        String s = gdal.VersionInfo();

        // Register GDAL
        gdal.AllRegister();

        m_DatabaseDtedDatasets = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(resourcesDirectory);

        if (resource == null) {
            throw new Exception("Unable to load resources directory: " + resourcesDirectory);
        }

        // Get the first ".dt2" file in the folder
        Optional<File> firstDtedFile = Files.walk(Paths.get(resource.toURI()))
                .filter(Files::isRegularFile)
                .map(x -> x.toFile())
                .filter(file -> file.getName().endsWith(".dt2"))
                .findFirst();

        if (firstDtedFile.isPresent()) {
            Dataset d = gdal.Open(firstDtedFile.get().getAbsolutePath());
            if (d != null) {
                m_DatabaseDtedDatasets.add(d);
            } else {
                throw new Exception("Unable to open file: " + firstDtedFile.get().getAbsolutePath());
            }
        } else {
            throw new Exception("No '.dt2' files found in directory: " + resourcesDirectory);
        }
    }

    /**
    * Queries the DTED datasets for the elevation at the given longitude and latitude.
    * 
    @param queryLon The longitude to query.
    @param queryLat The latitude to query.
    @return An Optional containing the elevation at the given coordinate, or empty if the coordinate is outside the dataset bounds.
    */
    public Optional<Integer> QueryLonLatElevation(Double queryLon, Double queryLat) {
        Optional<Integer> ret = null;

        for (Dataset d : m_DatabaseDtedDatasets) {
            if (isCoordinateInsideDataset(d, queryLon, queryLat)) {
                ret = Optional.of(queryDataset(d, queryLon, queryLat));
                break;
            }
        }
        return ret;
    }

    /**
    * Returns the extension of the given filename.
    * 
    @param filename The filename.
    @return An Optional containing the extension of the filename, or empty if the filename has no extension.
    */
    private static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    /**
    * Check if a coordinate is inside a given dataset
    * 
    @param d the dataset to check
    @param lon the longitude of the coordinate
    @param lat the latitude of the coordinate
    @return true if the coordinate is inside the dataset, false otherwise
    */
    private static boolean isCoordinateInsideDataset(Dataset d, Double lon, Double lat) {
        boolean retCode = false;

        int xsize = d.getRasterXSize();
        int ysize = d.getRasterYSize();

        double[] geoTransform = d.GetGeoTransform();

        double minLon = geoTransform[0];
        double maxLon = minLon + (xsize - 1) * geoTransform[1];

        double maxLat = geoTransform[3];
        double minLat = maxLat + (ysize - 1) * geoTransform[5];

        retCode = (maxLat > lat) && (minLat < lat) &&
                (maxLon > lon) && (minLon < lon);

        return retCode;
    }

    /**
    * Query a dataset for a given coordinate and return its elevation value
    * 
    @param d the dataset to query
    @param lon the longitude of the coordinate
    @param lat the latitude of the coordinate
    @return the elevation value at the given coordinate
    */
    private static int queryDataset(Dataset d, Double lon, Double lat) {
        int xsize = d.getRasterXSize();
        int ysize = d.getRasterYSize();

        double[] geoTransform = d.GetGeoTransform();

        double minLon = geoTransform[0];
        double maxLon = minLon + (xsize - 1) * geoTransform[1];

        double maxLat = geoTransform[3];
        double minLat = maxLat + (ysize - 1) * geoTransform[5];

        Double queryOffsetXDouble = ((lon - minLon)) * (xsize - 1);
        Double queryOffsetYDouble = ((maxLat - lat)) * (ysize - 1);

        int queryOffsetX = queryOffsetXDouble.intValue();
        int queryOffsetY = queryOffsetYDouble.intValue();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        byteBuffer.order(ByteOrder.nativeOrder());
        d.GetRasterBand(1).ReadRaster_Direct(queryOffsetX, queryOffsetY, 1, 1, 1, 1, gdalconst.GDT_Int32, byteBuffer);
        int queryResult = byteBuffer.getInt(0);

        return queryResult;
    }

    /**
    * Read a DTED file and create a list of FlightNodeEntity objects
    * 
    @param path the path to the DTED file
    @return a list of FlightNodeEntity objects
    */
    public List<FlightNodeEntity> readPointsFromFile(String path) {
        List<FlightNodeEntity> nodes = new ArrayList<>();

        try {
            Dataset d = gdal.Open(path);
            if (d != null) {
                int xsize = d.getRasterXSize();
                int ysize = d.getRasterYSize();

                double[] geoTransform = d.GetGeoTransform();

                for (int i = 0; i < xsize; i++) {
                    for (int j = 0; j < ysize; j++) {
                        double lon = geoTransform[0] + i * geoTransform[1];
                        double lat = geoTransform[3] + j * geoTransform[5];
                        int elevation = QueryLonLatElevation(lon, lat).get();

                        FlightNodeEntity node = new FlightNodeEntity();
                        node.setLatitude(lat);
                        node.setLongitude(lon);
                        node.setElevation((double) elevation);

                        nodes.add(node);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return nodes;
    }

    /**
    * Reads 500 points from a DTED file, creates FlightNodeEntity objects for each point, and saves them to the db repository.
    * 
    @return a HashMap of FlightNodeEntity objects, with UUID keys.
    */
    public HashMap<UUID, FlightNodeEntity> readPointsFromDataset(int elevationWeight, int distanceWeight) {
        this.nodeSet = new HashMap<>();
        for (Dataset d : m_DatabaseDtedDatasets) {
            int xsize = d.getRasterXSize();
            int ysize = d.getRasterYSize();

            double[] geoTransform = d.GetGeoTransform();

            int xStep = 500;
            int yStep = 500;

            // Add flight nodes
            int xSlots = (xsize / xStep) + 1;

            // Create top nodes list
            List<FlightNodeEntity> topNodes = new ArrayList<>(xSlots);
            for (int k = 0; k < xSlots; k++) {
                topNodes.add(null);
            }

            // create left node
            FlightNodeEntity leftNode = null;
            for (int y = 0; y < ysize; y += yStep) {
                for (int x = 0; x < xsize; x += xStep) {
                    double lat = geoTransform[3] + y * geoTransform[5];
                    double lon = geoTransform[0] + x * geoTransform[1];

                    FlightNodeEntity node = new FlightNodeEntity();
                    node.setLongitude(lon);
                    node.setLatitude(lat);

                    // Set Elevation
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
                    byteBuffer.order(ByteOrder.nativeOrder());
                    d.GetRasterBand(1).ReadRaster_Direct(x, y, 1, 1, 1, 1, gdalconst.GDT_Int32, byteBuffer);
                    int elevation = byteBuffer.getInt(0);
                    node.setElevation((double) elevation);

                    // Create relationships with current dataset
                    if (leftNode != null) {
                        Path outgoingLeftPath = new Path(leftNode, node.getLatitude(), node.getLongitude(), node.getElevation(), node.getId());
                        outgoingLeftPath.setWeight(distanceWeight, elevationWeight);
                        node.getPaths().add(outgoingLeftPath);

                        Path incomingLeftPath = new Path(node, leftNode.getLatitude(), leftNode.getLongitude(), leftNode.getElevation(), leftNode.getId());
                        incomingLeftPath.setWeight(distanceWeight, elevationWeight);
                        leftNode.getPaths().add(incomingLeftPath);
                    }

                    FlightNodeEntity topNode = topNodes.get(x / xStep);
                    if (topNode != null) {
                        Path outgoingTopPath = new Path(topNode, node.getLatitude(), node.getLongitude(), node.getElevation(), node.getId());
                        outgoingTopPath.setWeight(distanceWeight, elevationWeight);
                        node.getPaths().add(outgoingTopPath);

                        Path incomingTopPath = new Path(node, topNode.getLatitude(), topNode.getLongitude(), topNode.getElevation(), topNode.getId());
                        incomingTopPath.setWeight(distanceWeight, elevationWeight);
                        topNode.getPaths().add(incomingTopPath);
                    }

                    // update previous left node
                    leftNode = (x + xStep >= xsize) ? null : node;

                    // update previous top node
                    topNodes.set(x / xStep, node);

                    // Save the node to the repository
                    nodeSet.put(node.getId(), node);
                }
            }
        }
        return this.nodeSet;
    }
}