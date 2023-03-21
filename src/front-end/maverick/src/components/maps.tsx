import React, { useEffect, useState } from "react";
import { Node, Edge, FeatureCollection, Feature } from "../types";
import * as d3 from "d3";
import styled from "styled-components";
import geojsonFile from "../data/local";

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
  const [mergedData, setMergedData] = useState<FeatureCollection | null>(null);
  const [nodes, setNodes] = useState<Node[]>([]);
  const [edges, setEdges] = useState<Edge[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch("http://localhost:8080/flight-path/nodes");
      const data = await response.json();
      const newNodes: Node[] = [];
      const newEdges: Edge[] = [];
      const newCoord: Feature[] = [];

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
          id: node.id,
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
        features: [...geojsonFile.features, ...coordinates.features],
      });
    };

    fetchData();
  }, []);

  useEffect(() => {
    let projection = d3.geoMercator().scale(3000).translate([2900, -1000]);

    let geoGenerator = d3.geoPath().projection(projection).pointRadius((d) => 0);

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

    function createNode(nodes: Node[]){
      let node = d3.select("#content g.map")
        .selectAll("g")
        .data(nodes)
        .enter()
        .append("g")
        .attr("transform", (d: any) => {
          let coords = projection([d.longitude, d.latitude]);
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-expect-error
          return `translate(${coords[0]}, ${coords[1]})`;
        });

      node.append("circle")
        .attr("r", 1.5)
        .style("fill", (d, i) => (i === 1 ? "blue" : "red"));
    }

    function createEdge(edges: Edge[]) {
      const edgeGroup = d3.select("#content g.map")
        .selectAll("g")
        .data(edges)
        .enter()
        .append("g");
    
      const projection = d3.geoMercator().scale(3000).translate([2900, -1000]);
    
      const lineGenerator = d3.line()
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        .x((d: any) => projection([d.longitude, d.latitude])[0])
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        .y((d: any) => projection([d.longitude, d.latitude])[1]);
    
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
        .attr("stroke", "black")
        .attr("stroke-width", 0.1)
        .attr("fill", "none");
    }

    if (mergedData) {
      initZoom();
      update(mergedData);
      createNode(nodes);
      createEdge(edges);
    }

  }, [mergedData]);
  console.log(mergedData);
  return (
    <Container id="content">
      <svg id="svgReference" width="800px" height="400px">
        <g className="map"></g>
      </svg>
    </Container>
  );
};

export default Map;