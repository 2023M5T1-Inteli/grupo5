package br.edu.inteli.cc.m5.maverick.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

// Node representation
@Node
public class FlightPathNode {
    // Node properties

    // Auto generated id
    @Id
    @GeneratedValue
    private Long id;

    // Manually set properties
    private Double latitude;
    private Double longitude;
    private Double elevation;


    // Relationship a --> b
    @Relationship(type = "PATH", direction = Relationship.Direction.OUTGOING)
    public Set<FlightPathNode> goesTo;

    // Relationship a <-- b
    @Relationship(type = "PATH", direction = Relationship.Direction.INCOMING)
    public Set<FlightPathNode> goesInto;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
