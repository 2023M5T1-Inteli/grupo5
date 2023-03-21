package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Node representation
@Node("FlightNode")
public class FlightNodeEntity {
    // Node properties

    // Auto generated id
    @Id
    @GeneratedValue
    private Long idNeo;

    private UUID id;

    // Manually set properties
    private Double latitude;
    private Double longitude;
    private Double elevation;


    // Relationship between edges
    @Relationship(type = "PATH")
    private List<Path> paths = new ArrayList<>();

    // Constructors
    public FlightNodeEntity() {
        this.id = UUID.randomUUID();
    }

    public FlightNodeEntity(Double latitude, Double longitude, Double elevation) {
        this.id = UUID.randomUUID();
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    // Getters and setters

    public Long getIdNeo() {
        return idNeo;
    }

    public void setIdNeo(Long idNeo) {
        this.idNeo = idNeo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public void clearAllPaths() {
        this.paths.clear();
    }

    public void addPath(Path path) {
        this.paths.add(path);
    }
}