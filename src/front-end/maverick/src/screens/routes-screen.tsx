//Import libraries
import React from 'react';
import ReactDOM from 'react-dom/client';
import styled from 'styled-components';

//Import components
import Map from '../components/maps';
import Coordinates from '../components/coordinates';
import ButtonComp from '../components/buttons';

//Import elements
import './routes-screen.css'
import logo from '../assets/logo.svg';

//Create styles for components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  height: 100vh;
`

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  width: 95vw;
  height: 10vw;
  background-color: white;
  margin:2rem 0 0 0;
  padding: 4rem 15rem 0 10rem;
`

const Title = styled.h1`
  font-weight: 700;
  font-size: 4.0rem;
  color: #18568C;
`

const Main = styled.div`
  display: flex;
  width: 95vw;
  height: 100vh;
  padding: 4rem;
  flex-direction: column;
`

// Render the components to export for HTML
function ViewRoutes() {
  return (
    <Container>
      <Header>
        <Title>Rota Gerada</Title>
        <img src={logo} />
      </Header>
      <Main>
        <Coordinates />
        <Map />
        <ButtonComp />
      </Main>
    </Container>
  )
}

export default ViewRoutes;
