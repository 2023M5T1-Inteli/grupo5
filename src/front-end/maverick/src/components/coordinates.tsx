//Import libraries
import React from 'react';
import ReactDOM from 'react-dom/client';
import styled from 'styled-components';
import Form {storedSourceLat, storedSourceLon, storedTargetLat, storedTargetLon} from './Form';

const Container = styled.div`
  display: flex;
  flex-direction:column;
  width: 100vw;
  height: 100vh;
  padding: 2rem;
  padding-left: 2rem;
`
const TitleHeader = styled.h1`
  font-family: 'Montserrat';
  font-style: normal;
  font-weight: 900;
  letter-spacing: .1rem;
  font-size: 3.0rem;
  line-height: 4.9rem;
  color: #18568C;
 `
 const Subtitle = styled.h2`
  font-family: 'Montserrat';
  display:flex;
  color: #18568C;
  font-size:1.5rem;
  font-weight:500;
`
const CardBox = styled.div`
  background: #E6E6E6;
  border-radius: 15px;
  flex: 1;
  border-width: 0px;
  font-weight: 400;
  font-size: 1.6rem;
  line-height: 24px;
  color: #18568C;
  padding: 1rem;
  width: 90%;
  margin-bottom: 2rem;
  &:focus {
    outline: none;
  }
`
const TitleCoordinates= styled.h2`
  font-family: 'Montserrat';
  display:flex;
  color: #18568C;
  font-weight: 300;
  padding-bottom: 1rem;
`
const Div = styled.div`
  display: flex;
  flex-direction:row;
  gap 2rem;
`
const DivSub = styled.div`
  display: flex;
  flex-direction:row;
  gap: 12.5rem;
  padding-top: 1rem;
`
const ContainerButton = styled.div`
  display: flex;
  flex-direction:column;
  align-items: center;
  margin-top: 1rem;
  gap 2rem;
`
const Button = styled.button`
  width: 21rem;
  height: 4rem;
  background: #F2CA52;
  border-radius: 100px;
  border-color: #F2CA52;  
  border-width: 0px;
  font-weight: 700;
  font-size: 1.8rem;
  color: #FFFFFF;
`
const ButtonB = styled.button`
  width: 21rem;
  height: 4rem;
  background: #18568C;
  border-radius: 100px;
  border-color: #18568C;  
  border-width: 0px;
  font-weight: 700;
  font-size: 1.8rem;
  color: #FFFFFF;
`
 // Return the components to export for HTML
function Coordinates (){
  
    return(
        <Container>
            <TitleHeader>Gerar rota</TitleHeader>
            <Subtitle>Insira o ponto de partida (coordenadas)</Subtitle>
            <DivSub>
            <TitleCoordinates >Latitude</TitleCoordinates>
            <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
            <CardBox>{storedSourceLat}</CardBox>
            <CardBox>{storedSourceLon}</CardBox>
            </Div>
            <Subtitle>Insira o ponto do destino (coordenadas)</Subtitle>
            <DivSub>
              <TitleCoordinates>Latitude</TitleCoordinates>
              <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
            <CardBox>{storedTargetLat}</CardBox>
            <CardBox>{storedTargetLat}</CardBox>
            </Div>
            <ContainerButton>
            <Button>Gerar rota</Button>
            <ButtonB>Visualizar rota</ButtonB>
          </ContainerButton>
        </Container>
        
    )
}
//Export component coordinates
export default Coordinates;