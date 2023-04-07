//Import libraries
import styled from 'styled-components';

//Import components
import Map from '../components/maps'
import Form from '../components/Form';
import Coordinates from '../components/coordinates';


//Create styles for components
const Container = styled.div`
  display: flex;
  flex-direction: row ;
  width: 100vw;
  height: 100vh;
`
const ContainerForm = styled.div`
  display:flex;
  flex-direction: row;
  width: 30vw;
  height: 100vh;
`
const ContainerMap = styled.div`
  display:flex;
  width: 70vw;
  height: 100vh;
`


// Render the components to export for HTML
function ViewRoutesMap() {
  return (
    <Container>
      <ContainerMap>
        <Map></Map>
      </ContainerMap>
    <ContainerForm>
      <Form></Form>
    </ContainerForm>
    </Container>
    
    
  )
}

export default ViewRoutesMap;
