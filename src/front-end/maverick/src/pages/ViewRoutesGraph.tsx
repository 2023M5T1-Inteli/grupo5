//Import libraries
import React from 'react';
import ReactDOM from 'react-dom/client';
import styled from 'styled-components';

//Import components
import Graph from '../components/Graphs';
import Coordinates from '../components/Coordinates';
import ButtonComp from '../components/Buttons';

//Import elements
import logo from '../assets/logoSVG.svg';

//Create styles for components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  height: 100vh;
`

const Logo = styled.img`
  width: 20rem;
  height: 6.0rem;
`

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  width: 90vw;
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
  width: 90vw;
  height: 100vh;
  padding: 4rem;
  flex-direction: column;
`

// Render the components to export for HTML
function ViewRoutesGraph() {
  return (
    <Container>
      <Header>
        <Title>Rota Gerada</Title>
        <Logo src={logo} />
      </Header>
      <Main>
        <Coordinates />
        <Graph />
        <ButtonComp />
      </Main>
    </Container>
  )
}

export default ViewRoutesGraph;
