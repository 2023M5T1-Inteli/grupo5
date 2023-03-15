//Import libraries
import React, { useEffect, useState } from "react";
import data from "../data/local";
import styled from "styled-components";
import * as d3 from "d3";
import { SimulationNodeDatum } from 'd3';
import "./test.css"

//Create a container component to render the map
const Container = styled.div`
    display:flex;
    flex-direction:column;
    width: 95vw;
    height: 70vh;
    align-items: center;
    margin-top: 2.5rem;
`


// Return the components to export for HTML
const Test = () => {
  interface FlightPathNode {
    id: number;
    latitude: number;
    longitude: number;
    elevation: number;
    paths: {
      id: number;
      distance: number;
      elevation: number;
      sourceId: number;
      targetId: number;
    }[];
  }
  interface Node extends SimulationNodeDatum{
      id: number;
      latitude: number;
      longitude: number;
      elevation: number;
  }
  interface Edge{
      id: number;
      source:number;
      target:number;
  }
  interface FeatureProperties {
    id: string;
    name: string;
    description: string;
  }
  
  interface FeatureGeometry {
    type: string;
    coordinates: number[][][];
  }
  
  interface Feature {
    type: string;
    properties: FeatureProperties;
    geometry: FeatureGeometry;
  }
  
  interface FeatureCollection {
    type: string;
    features: Feature[];
  }

  const [jsonData, setJsonData] = useState([]);
  const nodes: Node[] = [];
  const edges: Edge[] = [];

  // Use states function to make a fetch and save in a const
  useState(() => {
      // Request data
      const getData = async () => {
          //Fetch datas from the path
          const response = await fetch("http://localhost:8080/flight-path/nodes");
          //Recieve a json as response
          const data = await response.json();
          //Create a loop to put all json data into lists created above
          data.map(((node: any) => {
              nodes.push({id: node.id, latitude: node.latitude, longitude: node.longitude, elevation: node.elevation})
              node.paths.map((path:any) => {
                  const { id: pathId, sourceId, targetId } = path;
                  edges.push({id: pathId ,source: sourceId, target: targetId});
                });
         }))
         //Return the data on jsonData
          return setJsonData(data);
      }

     getData();
     
  })

  useState(()=> {
  })

  useEffect(()=> {
    let projection = d3.geoMercator()
      .scale(3000)
      .translate([2900, -1000]);

    let geoGenerator = d3.geoPath()
      .projection(projection);

    function update(geojson: any) {
        let u = d3.select('#content g.map')
          .selectAll('path')
          .data(geojson.features);

    u.enter()
      .append('path')
      .attr('d', (d: any) => geoGenerator(d.geometry));
    }

    update(data);
  })
  return (
    <div id="content">
      <svg width="800px" height="400px">
        <g className="map"></g>
      </svg>
    </div>
  )
}

//Export component map
export default Test;


