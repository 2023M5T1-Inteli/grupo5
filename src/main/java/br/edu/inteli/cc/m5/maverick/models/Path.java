package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Path {

    @RelationshipId
    private Long id;

    @Property
    private double distance;
    @Property("elevationChange")
    private double elevation;

    @Property
    private Long sourceId;

    @Property
    private Long targetId;

    private double sourceLon;

    private double sourceLat;

    private double sourceElevation;




    @TargetNode
    private final FlightNodeEntity target;

    public Path(FlightNodeEntity target, double sourceLon, double sourceLat, double sourceElevation, Long sourceId) {
        this.target = target;
        this.targetId = target.getId();
        this.sourceId = sourceId;
        this.sourceLon = sourceLon;
        this.sourceLat = sourceLat;
        this.sourceElevation = sourceElevation;
        this.elevation = target.getElevation() - this.sourceElevation;
        this.distance = haversine(this.sourceLat, this.sourceLon);
    }

    public Long getId() {
        return id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public double getElevation() {
        return elevation;
    }

    public double getDistance() {
        return distance;
    }

    public double getSourceLon() {
        return sourceLon;
    }

    public double getSourceLat() {
        return sourceLat;
    }

    public double getSourceElevation() {
        return sourceElevation;
    }

    //Haversine algorithm
    private Double haversine (Double lat1, Double lon1) {
        double R = 6372.8; // Earth radius in kilometers

        // target coordinates
        double lat2 = target.getLatitude();
        double lon2 = target.getLongitude();

        // difference in radians
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // lats in radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return R * c * 1000; // Multiply by 1000 to convert km to meters
    }
}

