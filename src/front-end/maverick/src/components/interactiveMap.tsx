import { useEffect } from 'react';
import mapboxgl from 'mapbox-gl';
import * as turf from '@turf/turf';

const MapAnimation = () => {
  useEffect(() => {
    const fetchData = async () => {
      const targetCoords = await fetch('http://localhost:8080/flight-path/nodes')
        .then((response) => response.json())
        .then((data) => data.map(({ latitude, longitude }: any) => [longitude, latitude]));

      const cameraCoords = targetCoords.map((coords: [number, number], index: number) => {
        if (index === 0) {
          return coords;
        }
        return [coords[0] + 0.0001, coords[1] + 0.0001];
      });

      const routes = {
        target: targetCoords,
        camera: cameraCoords,
      };

      mapboxgl.accessToken = 'pk.eyJ1Ijoic3RidXRvcmkiLCJhIjoiY2xmbzl5MzV2MGQxMDQzdDI5OWt5YTNpYyJ9.lOTKaHXhio-K0_chix_vmA';
      const map = new mapboxgl.Map({
        container: 'map',
        zoom: 11.53,
        center: [-42.415941152776876, -22.21163076428416],
        pitch: 65,
        bearing: -180,
        style: 'mapbox://styles/mapbox/outdoors-v12',
        interactive: false,
      });

      const targetRoute = routes.target;
      const cameraRoute = routes.camera;

      map.on('style.load', () => {
        map.addSource('mapbox-dem', {
          'type': 'raster-dem',
          'url': 'mapbox://mapbox.mapbox-terrain-dem-v1',
          'tileSize': 512,
          'maxzoom': 14,
        });
        map.setTerrain({ 'source': 'mapbox-dem', 'exaggeration': 1.5 });

        map.addSource('trace', {
          type: 'geojson',
          data: {
            type: 'Feature',
            properties: {},
            geometry: {
              type: 'LineString',
              coordinates: targetRoute,
            },
          },
        });

        map.addLayer({
          type: 'line',
          source: 'trace',
          id: 'line',
          paint: {
            'line-color': 'black',
            'line-width': 5,
          },
          layout: {
            'line-cap': 'round',
            'line-join': 'round',
          },
        });

        fetch('airplane.svg')
          .then((response) => response.blob())
          .then((blob) => {
            const image = new Image();
            image.src = URL.createObjectURL(blob);
            image.onload = () => {
              map.addImage('airplane-icon', image);
            };
          });

        map.addSource('airplane', {
          type: 'geojson',
          // @ts-expect-error
          data: {
            type: 'Feature',
            geometry: {
              type: 'Point',
              coordinates: targetRoute[0],
            },
          },
        });

        map.addLayer({
          id: 'airplane',
          type: 'symbol',
          source: 'airplane',
          layout: {
            'icon-image': 'airplane-icon',
            'icon-size': 1.5,
            'icon-rotate': 0,
          },
        });

        map.on('load', () => {
          const animationDuration = 80000;
          const cameraAltitude = 4000;
          // get the overall distance of each route so we can interpolate along them
          const routeDistance = turf.lineDistance(turf.lineString(targetRoute));
          const cameraRouteDistance = turf.lineDistance(
            turf.lineString(cameraRoute)
          );
          let start:any;
          function frame(time:any) {
            if (!start) start = time;
            // phase determines how far through the animation we are
            const phase = (time - start) / animationDuration;

            // phase is normalized between 0 and 1
            // when the animation is finished, reset start to loop the animation
            if (phase > 1) {
              // wait 1.5 seconds before looping
              setTimeout(() => {
                start = 0.0;
              }, 1500);
            }

            // use the phase to get a point that is the appropriate distance along the route
            // this approach syncs the camera and route positions ensuring they move
            // at roughly equal rates even if they don't contain the same number of points
            const alongRoute = turf.along(
              turf.lineString(targetRoute),
              routeDistance * phase
            ).geometry.coordinates;
            // @ts-expect-error
            map.getSource('airplane').setData({
              type: 'Feature',
              geometry: {
                type: 'Point',
                coordinates: alongRoute
              }
            });

            const alongCamera = turf.along(
              turf.lineString(cameraRoute),
              cameraRouteDistance * phase
            ).geometry.coordinates;

            const camera = map.getFreeCameraOptions();

            // set the position and altitude of the camera
            camera.position = mapboxgl.MercatorCoordinate.fromLngLat(
              {
                lng: alongCamera[0],
                lat: alongCamera[1]
              },
              cameraAltitude
            );

            // tell the camera to look at a point along the route
            camera.lookAtPoint({
              lng: alongRoute[0],
              lat: alongRoute[1]
            });

            map.setFreeCameraOptions(camera);

            window.requestAnimationFrame(frame);
          }

          window.requestAnimationFrame(frame);
        });
      });
    }
    fetchData();
})
    return (
        <div id="map" style={{ width: "99.9vw", height:"100vh"}} />
    );
}

export default MapAnimation;
