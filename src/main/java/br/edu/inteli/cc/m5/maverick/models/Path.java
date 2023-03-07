package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class Path {

    // Auto generated id
    @GeneratedValue
    @RelationshipId
    private long id;

    @Property("distance")
    private Double distance;

    @Property("elevation")
    private Double elevation;

    @TargetNode
    private FlightPathNode target;

    public Path(FlightPathNode target, Double sourceElevation, Double sourceLat, Double sourceLon) {
        this.target = target;
        this.elevation = target.getElevation() - sourceElevation;
        this.distance = haversine(sourceLat, sourceLon);
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

    public Double getDistance() {return distance;}

//    public void setDistance(Double lat, Double lon) {
//        this.distance = haversine(lat, lon);
//    }

    public Double getElevation() {
        return elevation;
    }

// public void setElevation(Double elevation) {
//        this.elevation = target.getElevation() - elevation;
//    }

    public Long getId() {
        return id;
    }

    public FlightPathNode getTarget() {
        return target;
    }

//    public void setTarget(FlightPathNode target) {
//        this.target = target;
//    }
}
