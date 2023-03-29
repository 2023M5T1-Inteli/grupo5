//Import libraries
import styled from "styled-components";

//Import elements
import logo from "../assets/logo.png";

//Create styles to components to render the coordinates
const Container = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;
`

const LeftContainer = styled.div`
  background-color: #F2CA52;
  width: 55vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-right: 3rem;
`

const RightContainer = styled.div`
  width: 40vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  padding-bottom:5rem;
`

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 40vh;
  flex-wrap: wrap;
  text-align: right;
  margin-bottom: 4rem;
`

const Title = styled.h1`
  font-family: 'Montserrat';
  font-style: normal;
  font-weight: 900;
  font-size: 4.0rem;
  line-height: 4.9rem;
  color: #FFFFFF;
`

const Subtitle = styled.h2`
  font-family: 'Montserrat';
  font-style: normal;
  font-weight: 600;
  font-size: 2.4rem;
  line-height: 2.9rem;
  color: #FFFFFF;
  padding:0 .5rem;
`

const Button = styled.button`
  background: #18568C;
  border: .1rem solid #18568C;
  border-radius: 10rem;
  margin-bottom: 3rem;
  font-size: 1.8rem;
  color: #FFFFFF;
  padding: 2rem 10rem;
`

async function postData() {

  const url = `http://localhost:8080/flight-path/nodes?elevationWeight=100&distanceWeight=1`
  const options = {
    method: 'POST'
  };
  const response = await fetch(url, options);
  
  window.location.href = 'viewroutesMap';
}

 // Return the components to export for HTML
function Home () {  
  return(
    <Container>
      <LeftContainer>
          <TitleContainer>
            <Title>Bem vindo ao sistema Maverick </Title>
            <Subtitle>O sistema que promove maior segurança para sua viagem em baixas altitudes, planejando as melhores rotas para a sua missão.</Subtitle>
          </TitleContainer>
      </LeftContainer>
      <RightContainer>
          <Button onClick={postData}>Iniciar</Button>
          <img src={logo} />
      </RightContainer>
    </Container>
  )
}

//Export component coordinates
export default Home;