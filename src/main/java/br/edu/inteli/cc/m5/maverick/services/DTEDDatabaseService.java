package br.edu.inteli.cc.m5.maverick.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.edu.inteli.cc.m5.maverick.repositories.FlightPathNodeRepository;
import org.gdal.gdal.gdal;
import org.gdal.gdal.Dataset;
import org.gdal.gdalconst.gdalconst;

import br.edu.inteli.cc.m5.maverick.models.FlightPathNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Implementa operações básicas de acesso a dados geográficos armazenados em arquivos
 * no formato DTED utilizando a biblioteca GDAL.
 */

@Service
public class DTEDDatabaseService {
    private final List<Dataset> m_DatabaseDtedDatasets;
    private final FlightPathNodeRepository flightPathNodeRepository;

    public DTEDDatabaseService(@Value("dted/Rio") String resourcesDirectory, FlightPathNodeRepository flightPathNodeRepository) throws Exception {
        this.flightPathNodeRepository = flightPathNodeRepository;

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

        List<File> dtedFiles = Files.walk(Paths.get(resource.toURI()))
                .filter(Files::isRegularFile)
                .map(x -> x.toFile())
                .collect(Collectors.toList());

        // Loop over all ".dt2" files to get their datasets
        for (File f : dtedFiles) {
            Dataset d = gdal.Open(f.getAbsolutePath());
            if (d != null)
                m_DatabaseDtedDatasets.add(d);
        }
    }

    public Optional<Integer> QueryLonLatElevation(Double queryLon, Double queryLat)
    {
        Optional<Integer> ret = null;

        for (Dataset d : m_DatabaseDtedDatasets)
        {
            if(isCoordinateInsideDataset(d, queryLon, queryLat))
            {
                ret = Optional.of(queryDataset(d, queryLon, queryLat));
                break;
            }
        }
        return ret;
    }

    private static Optional<String> getExtensionByStringHandling(String filename)
    {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private static boolean isCoordinateInsideDataset(Dataset d, Double lon, Double lat)
    {
        boolean retCode = false;

        int xsize = d.getRasterXSize();
        int ysize = d.getRasterYSize();

        double[] geoTransform = d.GetGeoTransform();

        double minLon = geoTransform[0];
        double maxLon = minLon  + (xsize-1)*geoTransform[1];

        double maxLat = geoTransform[3];
        double minLat = maxLat + (ysize-1)*geoTransform[5];

        retCode = (maxLat > lat) && (minLat < lat) &&
                (maxLon > lon) && (minLon < lon);

        return retCode;
    }

    private static int queryDataset(Dataset d, Double lon, Double lat)
    {
        int xsize = d.getRasterXSize();
        int ysize = d.getRasterYSize();

        double[] geoTransform = d.GetGeoTransform();

        double minLon = geoTransform[0];
        double maxLon = minLon  + (xsize-1)*geoTransform[1];

        double maxLat = geoTransform[3];
        double minLat = maxLat + (ysize-1)*geoTransform[5];

        Double queryOffsetXDouble = ((lon - minLon))*(xsize-1);
        Double queryOffsetYDouble = ((maxLat - lat))*(ysize-1);

        int queryOffsetX = queryOffsetXDouble.intValue();
        int queryOffsetY = queryOffsetYDouble.intValue();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 );
        byteBuffer.order(ByteOrder.nativeOrder());
        d.GetRasterBand(1).ReadRaster_Direct(queryOffsetX, queryOffsetY, 1, 1,1,1, gdalconst.GDT_Int32, byteBuffer );
        int queryResult = byteBuffer.getInt(0);

        return queryResult;
    }

    public List<FlightPathNode> readPointsFromFile(String path) {
        List<FlightPathNode> nodes = new ArrayList<>();

        try {
            Dataset d = gdal.Open(path);
            if(d != null) {
                int xsize = d.getRasterXSize();
                int ysize = d.getRasterYSize();

                double[] geoTransform = d.GetGeoTransform();

                for(int i = 0; i < xsize; i++) {
                    for(int j = 0; j < ysize; j++) {
                        double lon = geoTransform[0] + i * geoTransform[1];
                        double lat = geoTransform[3] + j * geoTransform[5];
                        int elevation = QueryLonLatElevation(lon, lat).get();

                        FlightPathNode node = new FlightPathNode();
                        node.setLatitude(lat);
                        node.setLongitude(lon);
                        node.setElevation((double) elevation);

                        nodes.add(node);
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return nodes;
    }


    public void readPointsFromDataset() {
        for (Dataset d : m_DatabaseDtedDatasets) {
            int xsize = d.getRasterXSize();
            System.out.println("xsize: " + xsize);
            int ysize = d.getRasterYSize();
            System.out.println("ysize: " + ysize);

            double[] geoTransform = d.GetGeoTransform();

            for(int i = 0; i < xsize; i++) {
                for(int j = 0; j < ysize; j++) {
                    double lon = geoTransform[0] + i * geoTransform[1];
                    double lat = geoTransform[3] + j * geoTransform[5];
                    Optional<Integer> elevationQuery = QueryLonLatElevation(lon, lat);

                    FlightPathNode node = new FlightPathNode();
                    node.setLatitude(lat);
                    node.setLongitude(lon);
                    if (elevationQuery != null) {
                        double elevation = QueryLonLatElevation(lon, lat).get();
                        node.setElevation(elevation);
                    }

                    flightPathNodeRepository.save(node);
                }
            }
        }

//        return nodes;
    }
}
