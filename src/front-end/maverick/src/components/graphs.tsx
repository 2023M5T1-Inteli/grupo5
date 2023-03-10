//Import libraries
import React, { useEffect, useRef, useState } from "react";
import { SimulationNodeDatum } from 'd3';
import * as d3 from "d3";
import styled from 'styled-components';

//Create a container component to render the map
const Container = styled.div`
    display:flex;
    flex-direction:column;
    width: 95vw;
    height: auto;
    align-items: center;
    margin-top: 2.5rem;
`

const SVG = styled.svg`
    display:flex;
    justify-content: center;
    width: 95vw;
    height: 100vh;
`

// Return the components to export for HTML
const Graph = () => {

    // Create interfaces to set a type on json
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

    // Create list to save JSON
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
    
    useState(()=>{
        //Use to return data from the path(d3.json is useful when you going to add maps)
        d3.json<FlightPathNode>("http://localhost:8080/flight-path/nodes").then(((jsonFile) => {

            //Select the svg on body
            const svg = d3.select("#svgReference");

            //Save svg size
            const svgWidth = parseInt(svg.style("width"));
            const svgHeight = parseInt(svg.style("height"));
            
            // Set forces on the nodes.
            const simulation = d3.forceSimulation(nodes)
                .force("link", d3.forceLink(edges).id((d: any) => d.id))
                .force("charge", d3.forceManyBody())
                .force("center", d3.forceCenter(svgWidth / 2, svgHeight / 2));
            
            // Create arrows
            svg.append("defs").append("marker")
                .attr("id", "arrowhead")
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 15)
                .attr("refY", 0)
                .attr("markerWidth", 10)
                .attr("markerHeight", 10)
                .attr("orient", "auto")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5")
                .attr("fill", "red");
            
            // Create edges
            const link = svg.append("g")
                .attr("class", "links")
                .selectAll("line")
                .data(edges)
                .enter().append("line")
                .attr("stroke", "black")
                .attr("marker-end", "url(#arrowhead)");
            
            // Create a g with the node
            const nodeGroup = svg.append("g")
                .attr("class", "node");
            
            // Create node
            const node = nodeGroup.selectAll("circle")
                .data(nodes)
                .enter().append("circle")
                .attr("r", 10)
                .attr("fill", "blue")
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-expect-error
                .call(drag(simulation))
            
            // Add description on nodes
            node.append("title")
                .text((d: any) => "id: " + d.id + ", latitude: " + d.latitude + ", longitude: " + d.longitude);
            
            //Updates the positions of the nodes and links on every tick of the simulation
            simulation.on("tick", () => {
                link.attr("x1", (d: any) => d.source.x)
                    .attr("y1", (d: any) => d.source.y)
                    .attr("x2", (d: any) => d.target.x)
                    .attr("y2", (d: any) => d.target.y);

                node.attr("cx", (d: any) => d.x)
                    .attr("cy", (d: any) => d.y);
            });

            // With the drag fuction the use are able to select a node and chande his position
            function drag(simulation: any) {
                function dragstarted(event: any, d: any) {
                    if (!event.active) simulation.alphaTarget(0.3).restart();
                    d.fx = d.x;
                    d.fy = d.y;
                }

                function dragged(event: any, d: any) {
                    d.fx = event.x;
                    d.fy = event.y;
                }

                function dragended(event: any, d: any) {
                    if (!event.active) simulation.alphaTarget(0);
                    d.fx = null;
                    d.fy = null;
                }

                return d3.drag()
                    .on("start", dragstarted)
                    .on("drag", dragged)
                    .on("end", dragended);
            }
            
        }))
    })
    
    
    //Add the div with id select in D3
   return (
        <Container>
            <SVG id="svgReference"></SVG>
        </Container>
   )
}

//Export component map
export default Graph;


