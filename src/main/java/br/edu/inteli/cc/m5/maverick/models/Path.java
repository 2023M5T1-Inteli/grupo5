package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class Path {

    // Auto generated id
    @RelationshipId
    @GeneratedValue
    private Long id;

    @Property("distance")
    private Double distance;

    @Property("elevation")
    private Double elevation;

    @TargetNode
    private FlightPathNode target;

    //Fictional algo
    private Double haversine (Double lat1, Double lon1) {
        double R = 6372.8; // In kilometers
        double lat2 = target.getLatitude();
        double lon2 = target.getLongitude();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public Double getDistance() {return distance;}

    public void setDistance(Double lat, Double lon) {
        this.distance = haversine(lat, lon);
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = target.getElevation() - elevation;
    }

    public Long getId() {
        return id;
    }

    public FlightPathNode getTarget() {
        return target;
    }

    public void setTarget(FlightPathNode target) {
        this.target = target;
    }
}
