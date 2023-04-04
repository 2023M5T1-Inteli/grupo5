//Import libraries
import styled from "styled-components";
import React, { useEffect, useState } from "react";

//Create styles to components to render the coordinates
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
const Input = styled.input`
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
function Form (){
  const [targetLat, setTargetLat] = useState('');
  const [targetLon, setTargetLon] = useState('');
  const [sourceLon, setSourceLon] = useState('');
  const [sourceLat, setSourceLat] = useState('');

  // Load stored data when the component mounts
  useEffect(() => {
    const storedSourceLat = localStorage.getItem('sourceLat');
    if (storedSourceLat) {
      setSourceLat(storedSourceLat);
    }
    const storedSourceLon = localStorage.getItem('sourceLon');
    if (storedSourceLon) {
      setSourceLon(storedSourceLon);
    }
    const storedTargetLat = localStorage.getItem('targetLat');
    if (storedTargetLat) {
      setTargetLat(storedTargetLat);
    }
    const storedTargetLon = localStorage.getItem('targetLon');
    if (storedTargetLon) {
      setTargetLon(storedTargetLon);
    }
  }, []);

  function handleTargetLat(e: any) {
    setTargetLat(e.target.value);
    localStorage.setItem('targetLat', e.target.value);
  } 
  function handleTargetLon(e: any) {
    setTargetLon(e.target.value);
    localStorage.setItem('targetLon', e.target.value);
  } 
  function handleSourceLat(e: any) {
    setSourceLat(e.target.value);
    localStorage.setItem('sourceLat', e.target.value);
  } 
  function handleSourceLon(e: any) {
    setSourceLon(e.target.value);
    localStorage.setItem('sourceLon', e.target.value);
  } 
  async function getMap() {
    const url = `http://localhost:8080/flight-path/cordPath?sourceLat=${sourceLat}&sourceLon=${sourceLon}&targetLat=${targetLat}&targetLon=${targetLon}`
    console.log(url);
    const response = await fetch(url);
    const json = await response.json();
    
    // Redirect to another page and pass the data as query parameters
    window.location.href = '/viewroutesMap'
    console.log(json);
  }

  function getAnimateMap(){
    window.location.href = '/viewanimateMap'
  }
  function getZone(){
    window.location.href = '/exclusionZone'
  }
    return(
        <Container>
            <TitleHeader>Gerar rota</TitleHeader>
            <Subtitle>Insira o ponto de partida (coordenadas)</Subtitle>
            <DivSub>
            <TitleCoordinates >Latitude</TitleCoordinates>
            <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
            <Input type="number" onChange={handleSourceLat} value={sourceLat} ></Input>
            <Input type="number" onChange={handleSourceLon} value={sourceLon}></Input>
            </Div>
            <Subtitle>Insira o ponto do destino (coordenadas)</Subtitle>
            <DivSub>
              <TitleCoordinates>Latitude</TitleCoordinates>
              <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
              <Input type="number" onChange={handleTargetLat} value={targetLat}></Input>
              <Input type="number" onChange={handleTargetLon} value={targetLon}></Input>
            </Div>
            <ContainerButton>
            <Button onClick={getZone} >Adicionar zona de exlusão</Button>
            <Button onClick={getMap} >Gerar rota</Button>
            <ButtonB onClick={getAnimateMap}>Visualizar rota</ButtonB>
          </ContainerButton>
        </Container>
        
    )
}

//Export component coordinates
export default Form;
export const storedSourceLat = localStorage.getItem('sourceLat');
export const storedSourceLon = localStorage.getItem('sourceLon');
export const storedTargetLat = localStorage.getItem('targetLat');
export const storedTargetLon = localStorage.getItem('targetLon');