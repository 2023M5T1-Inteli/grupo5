import { SimulationNodeDatum } from 'd3';

export interface Node extends SimulationNodeDatum{
    id: string;
    latitude: number;
    longitude: number;
    elevation: number;
}

export interface Edge{
    id: string;
    source:string;
    target:string;
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