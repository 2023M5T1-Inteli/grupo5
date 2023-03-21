import { SimulationNodeDatum } from 'd3';

export interface Node extends SimulationNodeDatum{
    id: number;
    latitude: number;
    longitude: number;
    elevation: number;
}

export interface Edge{
    id: number;
    source:number;
    target:number;
}

export interface FeatureProperties {
    id: string;
    name: string;
    description: string;
}
  
export interface FeatureGeometry {
    type: string;
    coordinates: any;
}
  
export interface Feature {
    type: string;
    properties: FeatureProperties;
    geometry: FeatureGeometry;
}
  
export interface FeatureCollection {
    type: string;
    features: Feature[];
}