//Import libraries and map file
import React, { useEffect, useState } from "react";
import { Node, Edge, FeatureCollection, Feature } from "../types";
import * as d3 from "d3";
import styled from "styled-components";
import geojsonFile from "../data/local";

//Create a component to render the map
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 95vw;
  height: 70vh;
  align-items: center;
  justify-content: center;
  margin-top: 2.5rem;
`;

const Map = () => {
  //Create use states to save data
  const [mergedData, setMergedData] = useState<FeatureCollection | null>(null);
  const [nodes, setNodes] = useState<Node[]>([]);
  const [edges, setEdges] = useState<Edge[]>([]);

  useEffect(() => {
    //Fetch data from API
    const fetchData = async () => {
      const response = await fetch("http://localhost:8080/flight-path/nodes");
      const data = await response.json();
      const newNodes: Node[] = [];
      const newEdges: Edge[] = [];
      const newCoord: Feature[] = [];
      
      //Separe the data into nodes, edges and coordinates(add in the map)
      data.forEach((node: any) => {
        newCoord.push({
          type: "Feature",
          properties: {
            id: String(node.id),
            name: String(node.id),
            description: String(node.id),
          },
          geometry: {
            type: "Point",
            coordinates: [node.longitude, node.latitude],
          },
        });

        newNodes.push({
          id: String(node.id),
          latitude: node.latitude,
          longitude: node.longitude,
          elevation: node.elevation,
        });

        node.paths.forEach((path: any) => {
          const { id: pathId, sourceId, targetId } = path;
          newEdges.push({ id: pathId, source: sourceId, target: targetId });
        });
      });

      const coordinates: FeatureCollection = {
        type: "FeatureCollection",
        features: newCoord,
      };

      setNodes(newNodes);
      setEdges(newEdges);
      setMergedData({
        type: "FeatureCollection",
        //Merge the json maps with the new coordinates to render
        features: [...geojsonFile.features, ...coordinates.features],
      });
    };

    fetchData();
  }, []);

  useEffect(() => {
    // Center the map
    let projection = d3.geoMercator().scale(3000).translate([2900, -1000]);

    // Add projection to the map
    let geoGenerator = d3.geoPath().projection(projection).pointRadius((d) => 0);

    // Add zoom to interact with the map
    let zoom = d3.zoom()
      .on('zoom', handleZoom);
    
    function handleZoom(e: any){
      d3.select('svg g')
        .attr('transform', e.transform);
    }

    function initZoom(){
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-expect-error
      d3.select('svg').call(zoom);
    }

    // Function to render the map
    function update(geojson: FeatureCollection) {
      let map = d3.select("#content g.map")
        .selectAll("path")
        .data(geojson.features);
    
      map.enter()
        .append("path")
        .attr("d", (d: any) => geoGenerator(d.geometry))
        .style("fill", (d: any) => d.geometry.type === "Point" ? "none" : "#d4d4d4")
        .attr("stroke", "#aaa");
    }

    // Function to create nodes
    function createNode(nodes: Node[]){
      let node = d3.select("#content g.map")
        .selectAll("g")
        .data(nodes)
        .enter()
        .append("g")
        .attr("transform", (d: any) => {
          // Nodes will be created according to latitude and longitude
          let coords = projection([d.longitude, d.latitude]);
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-expect-error
          return `translate(${coords[0]}, ${coords[1]})`;
        });

      // Add circles (Nodes)
      node.append("circle")
        .attr("r", 1.5)
        // Change the color of the first node
        .style("fill", (d, i) => (i === 0 ? "blue" : "red"));
      
      // Add title to nodes
      node.append("title")
        .text(function(d) { return d.latitude + ", " + d.longitude + ", " + d.elevation });

    }

    // Function to create edges
    function createEdge(edges: Edge[]) {
      const edgeGroup = d3.select("#content g.map")
        .selectAll("g.edge-group")
        .data(edges)
        .enter()
        .append("g")
        .attr("class", "edge-group");

      // Add projection to the edges
      const projection = d3.geoMercator().scale(3000).translate([2900, -1000]);

      // Create line according to projection, latitude and longitude
      const lineGenerator = d3.line()
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        .x((d: any) => projection([d.longitude, d.latitude])[0])
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        .y((d: any) => projection([d.longitude, d.latitude])[1]);

      // Size of the arrow marker
      const markerSize = 5;

      // Add arrow marker definition to the SVG
      const defs = edgeGroup.append("defs");
      defs.append("marker")
        .attr("id", "arrowhead")
        .attr("viewBox", `0 0 ${markerSize} ${markerSize}`)
        // Position the arrowhead at the middle of the marker
        .attr("refX", markerSize / 2) 
        .attr("refY", markerSize / 2)
        .attr("markerWidth", markerSize)
        .attr("markerHeight", markerSize)
        .attr("orient", "auto")
        .append("path")
        .attr("d", `M0,0 L0,${markerSize} L${markerSize},${markerSize / 2} Z`)
        // Color of the arrow marker
        .attr("fill", "#000") 
        // Define the shape of the arrowhead
        .attr("d", "M 0 0 L 100 50 L 0 100 z") 
        // Set the color of the arrowhead
        .attr("fill", "#000"); 

      // Add path for each edge with arrow marker at the end
      edgeGroup.append("path")
        .attr("d", (d: any) => {
          const source = nodes.find((node) => node.id === d.source);
          const target = nodes.find((node) => node.id === d.target);
          const lineData = [
            { longitude: source?.longitude, latitude: source?.latitude },
            { longitude: target?.longitude, latitude: target?.latitude },
          ];
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-expect-error
          return lineGenerator(lineData);
        })
        .attr("stroke", "#000")
        .attr("marker-end", "url(#arrowhead)")
        .attr("stroke-width", 0.1)
        .attr("fill", "none");
        }
    
    // If mergedData is not null, create all components
    if (mergedData) {
      initZoom();
      update(mergedData);
      createNode(nodes);
      createEdge(edges);
      console.log(edges);
    }

  }, [mergedData]);
  return (
    <Container id="content">
      <svg id="svgReference" width="800px" height="400px">
        <g className="map"></g>
      </svg>
    </Container>
  );
};

export default Map;