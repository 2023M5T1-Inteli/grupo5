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
    private Double haversine (Double lat, Double lon) {
        return (lat + lon) - (target.getLatitude() + target.getLongitude());
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
