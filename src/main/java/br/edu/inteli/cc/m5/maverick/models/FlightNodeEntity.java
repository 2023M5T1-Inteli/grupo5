/**
* The FlightNodeEntity class represents a node in the flight graph database.
* It uses the Spring Data Neo4j framework for object mapping and persistence.
*/
package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Node("FlightNode")
public class FlightNodeEntity {
    // Node properties

    /**
     * The auto-generated id of this node in Neo4j
     */
    @Id
    @GeneratedValue
    private Long idNeo;

    /**
     * The unique id of this node
     */
    private UUID id;

    /**
     * The latitude of this node
     */
    private Double latitude;

    /**
     * The longitude of this node
     */
    private Double longitude;

    /**
     * The elevation of this node
     */
    private Double elevation;

    /**
     * The list of paths from this node to other nodes, relationship between edges
     */
    @Relationship(type = "PATH")
    private List<Path> paths = new ArrayList<>();

    /**
     * Creates a new FlightNodeEntity instance with a randomly generated UUID.
     */
    public FlightNodeEntity() {
        this.id = UUID.randomUUID();
    }

    /**
     * Creates a new FlightNodeEntity instance with the given latitude, longitude, and elevation.
     * A random UUID is generated for the node.
     * 
     * @param latitude the node's latitude.
     * @param longitude the node's longitude.
     * @param elevation the node's elevation.
     */
    public FlightNodeEntity(Double latitude, Double longitude, Double elevation) {
        this.id = UUID.randomUUID();
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    // Getters and setters

    /**
     * Getter method for the Neo4j generated id of this node
     * 
     * @return the Neo4j generated id of this node
     */
    public Long getIdNeo() {
        return idNeo;
    }

    /**
     * Setter method for the Neo4j generated id of this node
     * 
     * @param idNeo the new Neo4j generated id of this node
     */
    public void setIdNeo(Long idNeo) {
        this.idNeo = idNeo;
    }

    /**
     * Getter method for the unique id of this node
     * 
     * @return the unique id of this node
     */
    public UUID getId() {
        return id;
    }

    /**
     * Setter method for the unique id of this node
     * 
     * @param id the new unique id of this node
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Getter method for the latitude of this node
     * 
     * @return the latitude of this node
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Setter method for the latitude of this node
     * 
     * @param latitude the new latitude of this node
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter method for the longitude of this node
     * 
     * @return the longitude of this node
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Setter method for the longitude of this node
     * 
     * @param longitude the new longitude of this node
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter method for the elevation of this node
     * 
     * @return the elevation of this node
     */
    public Double getElevation() {
        return elevation;
    }

    /**
     * Setter method for the elevation of this node
     * 
     * @param elevation the new elevation of this node
     */
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    /**
    Returns a list of {@link Path} objects associated with this {@code FlightNodeEntity}.

    @return a list of {@link Path} objects associated with this {@code FlightNodeEntity}.
    */
    public List<Path> getPaths() {
        return paths;
    }

    /**
    Sets the list of {@link Path} objects associated with this {@code FlightNodeEntity}.

    @param paths a list of {@link Path} objects to be associated with this {@code FlightNodeEntity}.
    */
    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    /**
     * Removes all paths from the node's path list.
     */
    public void clearAllPaths() {
        this.paths.clear();
    }

    /**
     * Adds a path to the node's path list.
     * 
     * @param path the path to add.
     */
    public void addPath(Path path) {
        this.paths.add(path);
    }
}