/**
* Represents the relationship between two nodes in a graph database.
* Each instance of this class represents a directed edge from one node to another.
*/
package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.UUID;

@RelationshipProperties
public class Path {

    /**
    * The unique identifier of this edge.
    */
    @RelationshipId
    private Long id;

    /**
    * The distance between the source and target nodes of this edge.
    */
    @Property
    private double distance;

    /**
     * The elevation change between the source and target nodes of this path.
     */
    @Property("elevationChange")
    private double elevation;

    /**
    * The UUID of the source node of this edge.
    */
    @Property
    private UUID sourceId;

    /**
    * The UUID of the target node of this edge.
    */
    @Property
    private UUID targetId;

    @Property
    private double distance;

    @Property("elevationChange")
    private double elevation;

    private double weight;
    private double sourceLat;
    private double sourceLon;
    private double sourceElevation;

    /**
     * The target node of this path.
     */
    @TargetNode
    private final FlightNodeEntity target;

    /**
     * The source node of this path.
     */
    private FlightNodeEntity source;

    /**
     * Constructs a new Path instance.
     * @param target the target node of this path
     * @param sourceLat the latitude of the source node of this path
     * @param sourceLon the longitude of the source node of this path
     * @param sourceElevation the elevation of the source node of this path
     * @param sourceId the UUID of the source node of this path
     */
    public Path(FlightNodeEntity target, double sourceLat, double sourceLon, double sourceElevation, UUID sourceId) {
        this.target = target;
        this.targetId = this.target.getId();
        this.sourceId = sourceId;
        this.elevation = target.getElevation() - sourceElevation;
        this.distance = haversine(sourceLat, sourceLon);
    }

    /**
     * Returns the ID of this path.
     * 
     * @return the ID of this path
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of this path.
     * 
     * @param id the ID of this path
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the UUID of the source node of this path.
     * 
     * @return the UUID of the source node of this path
     */
    public UUID getSourceId() {
        return sourceId;
    }

    /**
     * Returns the UUID of the target node of this path.
     * 
     * @return the UUID of the target node of this path
     */
    public UUID getTargetId() {
        return targetId;
    }

    /**
     * Returns the elevation change between the source and target nodes of this path.
     * 
     * @return the elevation change between the source and target nodes of this path
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Returns the distance between the source and target nodes of this path.
     * 
     * @return the distance between the source and target nodes of this path
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Calculates the great-circle distance between two points on a sphere using the Haversine formula.
     * 
     * @param lat1 the latitude of the first point in degrees
     * @param lon1 the longitude of the first point in degrees
     * @return the distance between the two points in meters
     */

    public double getWeight() {
        return weight;
    }

    public void setWeight(int distanceWeight, int elevationWeight) {
        // make some adjustments to elevation (invert signal if negative, weight it more it if positive)
        double adjustedElevation;
        if (this.elevation < 0) {
            adjustedElevation = this.elevation * -1;
        } else {
            adjustedElevation = this.elevation * 3;
        }
        // compute weighted average of distance and elevation
        this.weight = (distanceWeight * this.distance + elevationWeight * adjustedElevation) / (distanceWeight + elevationWeight);
    }

    //Haversine algorithm
    private Double haversine(Double lat1, Double lon1) {
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
