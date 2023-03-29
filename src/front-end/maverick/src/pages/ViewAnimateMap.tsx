//Import libraries
import styled from 'styled-components';

//Import components
import MapAnimation from '../components/interactiveMap';
import ButtonComp from '../components/buttons';

//Create styles for components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
`

// Render the components to export for HTML
function ViewRoutesMapAnimation() {
  return (
    <Container>
      <MapAnimation></MapAnimation>
      <ButtonComp></ButtonComp>
    </Container>
  )
}

export default ViewRoutesMapAnimation;
