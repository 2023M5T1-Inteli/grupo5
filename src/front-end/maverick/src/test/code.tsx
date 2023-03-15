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
  
  const existingFeatureCollection: FeatureCollection = {
    type: "FeatureCollection",
    features: [
      {
        type: "Feature",
        properties: {
          id: "3300100",
          name: "Angra dos Reis",
          description: "Angra dos Reis"
        },
        geometry: {
          type: "Polygon",
          coordinates: [
            [
              [-44.1955721491, -23.0983083542],
              [-44.1944529053, -23.1033810644],
              [-44.1904610068, -23.1022239991],
              [-44.1999060342, -23.1089639566],
              [-44.2006786153, -23.1053635257],
              [-44.2071271811, -23.1036230485],
              [-44.217342013, -23.1057227645],
            ]
          ]
        }
      }
    ]
  };
  
  const newFeature: Feature = {
    type: "Feature",
    properties: {
      id: "0210101",
      name: "Rotas",
      description: "Rotas"
    },
    geometry: {
      type: "Polygon",
      coordinates: [
        [
          [-44.1955721491, -23.0983083542],
          [-44.1944529053, -23.1033810644],
          [-44.1904610068, -23.1022239991],
          [-44.1999060342, -23.1089639566],
          [-44.2006786153, -23.1053635257],
          [-44.2071271811, -23.1036230485],
          [-44.217342013, -23.1057227645],
        ]
      ]
    }
  };
  
  existingFeatureCollection.features.push(newFeature);
  
  console.log(existingFeatureCollection);
  