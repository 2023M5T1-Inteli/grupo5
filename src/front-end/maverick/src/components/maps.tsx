import React, { useEffect } from "react";
import { MapNode } from "../types";
import mapboxgl from "mapbox-gl";

const Map = () => {
  useEffect(() => {
    // TO MAKE THE MAP APPEAR YOU MUST
    // ADD YOUR ACCESS TOKEN FROM
    // https://account.mapbox.com
    mapboxgl.accessToken = 'pk.eyJ1Ijoic3RidXRvcmkiLCJhIjoiY2xmbzl5MzV2MGQxMDQzdDI5OWt5YTNpYyJ9.lOTKaHXhio-K0_chix_vmA';

    const map = new mapboxgl.Map({
      container: 'map',
      // Choose from Mapbox's core styles, or make your own style with Mapbox Studio
      style: 'mapbox://styles/mapbox/streets-v12',
      center: [-42.415941152776876, -22.21163076428416],
      zoom: 12
    });

    map.addControl(new mapboxgl.NavigationControl());

    map.on('load', async () => {
      // Fetch the GeoJSON data and add it to the map
      const mapCoords = await fetch('http://localhost:8080/flight-path/nodes')
        .then(response => response.json())
        .then(data => data.map(({ latitude, longitude }:MapNode) => [longitude, latitude]));


      console.log(mapCoords);

      map.addSource('route', {
        'type': 'geojson',
        'data': {
          'type': 'Feature',
          'properties': {},
          'geometry': {
            'type': 'LineString',
            'coordinates': mapCoords
          }
        }
      });
      map.addLayer({
        'id': 'route',
        'type': 'line',
        'source': 'route',
        'layout': {
          'line-join': 'round',
          'line-cap': 'round'
        },
        'paint': {
          'line-color': '#888',
          'line-width': 8
        }
      });
    });
  }, []);

  return <div id="map" style={{ position: "absolute", top: 0, bottom: 0, width: "70%", height:"100%"}} />;
};

export default Map;