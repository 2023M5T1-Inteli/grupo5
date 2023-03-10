//Import libraries
import React, { useEffect, useRef } from "react";
import { Feature, FeatureCollection, GeoJsonProperties, Geometry } from 'geojson';
import styled from 'styled-components';
import * as d3 from "d3";

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
const Map = () => {
    const svgRef = useRef<SVGSVGElement>(null); 

    //Define svg size
    const width = 940;
    const height = 600;

    //Define Geo mercator projection
    const projection = d3.geoMercator().center([-46.377626304,-23.5159233375]).translate([width/2, height/2]).scale(4000);
    const path = d3.geoPath().projection(projection);

    //Create d3 map and graphs after render.
    useEffect(() => {
        //Create svg on div with id selected
        const svg = d3.select("#svgReference").append("svg").attr("width", width).attr("height", height);
        //Import GEOJSON from github
        const data = d3.json<FeatureCollection>("https://raw.githubusercontent.com/andlljr/geojson-sp-rj/main/sp-rio.json").then(function(json){
            //Case errors that cant be null, return error.
            if (!json) {
                console.error("Error loading JSON data");
                return;
            }

            if (!svg) {
                console.error("Error selecting SVG element");
                return;
            }
            
            //Create the map
            svg.selectAll('path')
                .data(json.features)
                .enter()
                .append("path")
                .attr("d", path)
                .attr("stroke", "dimgray")
                .attr("fill", "#F2CA52");
            
            //Define nodes
            const nodes: { name: string, coordinates: [number, number] }[] = [
                { name: "São Paulo", coordinates: [-46.5372287017,-23.3691818313] },
                { name: "Angra dos Reis", coordinates: [-44.1955721491,-23.0983083542] },
                { name: "Votorantim", coordinates: [-47.3726760637,-23.5178348439] }
            ]

            //Create node on the map
            svg.selectAll("circle")
                .data(nodes)
                .enter()
                .append("circle")
                .attr("cx", (d) => {
                    const projected = projection(d.coordinates);
                    return projected ? projected[0] : 0;
                })
                .attr("cy", (d) => {
                    const projected = projection(d.coordinates);
                    return projected ? projected[1] : 0;
                })
                .attr("r", 5)
                .attr("fill", "red");
            
            //Define edges
            const edges = [
                { source: "Votorantim", target: "São Paulo" },
                { source: "São Paulo", target: "Angra dos Reis" },
                { source: "Votorantim", target: "São Paulo" }
            ];
            
            //Put direction on edges
            svg.append("defs").append("marker")
                .attr("id", "arrowhead")
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 10)
                .attr("refY", 0)
                .attr("markerWidth", 8)
                .attr("markerHeight", 8)
                .attr("orient", "auto")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5")
                .attr("fill", "blue");

            // Create edge
            svg.selectAll("line")
                .data(edges)
                .enter()
                .append("line")
                .attr("x1", (d) => {
                    const sourceNode = nodes.find((node) => node.name === d.source);
                    const projected = sourceNode ? projection(sourceNode.coordinates) : null;
                    return projected ? projected[0] : 0;
                })
                .attr("y1", (d) => {
                    const sourceNode = nodes.find((node) => node.name === d.source);
                    const projected = sourceNode ? projection(sourceNode.coordinates) : null;
                    return projected ? projected[1] : 0;
                })
                .attr("x2", (d) => {
                    const targetNode = nodes.find((node) => node.name === d.target);
                    const projected = targetNode ? projection(targetNode.coordinates) : null;
                    return projected ? projected[0] : 0;
                })
                .attr("y2", (d) => {
                    const targetNode = nodes.find((node) => node.name === d.target);
                    const projected = targetNode ? projection(targetNode.coordinates) : null;
                    return projected ? projected[1] : 0;
                })
                .attr("stroke", "blue")
                .attr("marker-end", "url(#arrowhead)");

        })
        // This return is necessary to not append multiples svg on div
        return () => {
            svg.remove();
        }
    })
    //Add the div with id select in D3
   return (
        <Container id="svgReference"></Container>
   )
}

//Export component map
export default Map;


