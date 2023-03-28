//Import libraries
import styled from 'styled-components';

//Import components
import Map from '../components/maps'

//Create styles for components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  height: 100vh;
`

// Render the components to export for HTML
function ViewRoutesMap() {
  return (
    <Container>
      <Map></Map>
    </Container>
  )
}

export default ViewRoutesMap;
