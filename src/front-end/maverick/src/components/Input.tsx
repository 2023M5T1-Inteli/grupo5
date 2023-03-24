//Import libraries
import styled from "styled-components";
import React, { useEffect, useState } from "react";
import Map from "./maps";

import logo from "../assets/logo.png";

//Create styles to components to render the coordinates
const InputComponent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding-top: 4.7rem;
`

const TitleCoordinates= styled.h2`
font-family: 'Montserrat';
display:flex;
color: #18568C;
font-weight: 300;
padding-bottom: 1rem;
`
const InputDiv = styled.div`
  display: flex;
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
  margin-bottom: 5rem;
  &:focus {
    outline: none;
  }
`
const DivLong = styled.div`
  display: flex;
  width: 100%;
  gap: 5rem;
`

const InputWrapper = styled.div`
width: 40%;
margin-bottom: -2rem;
`

const DivLat = styled.div`
  display: flex;
  padding-left: 2rem;
  flex-direction:column;
`

const Subtitle = styled.h2`
font-family: 'Montserrat';
display:flex;
color: #18568C;
font-size:1.8rem;
font-weight:500;
`

const ContainerButton = styled.div`
  display: flex;
  width:95vw;
  margin-top: 1rem;
  gap: 42rem;
`

const Button = styled.button`
  width: 31rem;
  height: 5rem;
  background: #F2CA52;
  border-radius: 100px;
  border-color: #F2CA52;  
  border-width: 0px;
  font-weight: 700;
  font-size: 1.8rem;
  color: #FFFFFF;
`

// Return the components to export for HTML
function InputComponents (){

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
    window.location.href = '`/viewRoutesMap`'
    console.log(json);

  }
  

    return(
        <>
          <InputComponent>
              <Subtitle>Insira o ponto de partida (coordenadas)</Subtitle>
              <DivLong>
              <InputWrapper>
                  <TitleCoordinates>Latitude</TitleCoordinates>
                  <Input type="number" onChange={handleSourceLat} value={sourceLat}></Input>
              </InputWrapper>
              <InputWrapper>
                  <TitleCoordinates>Longitude</TitleCoordinates>
                  <Input type="number" onChange={handleSourceLon} value={sourceLon}></Input>
              </InputWrapper>
              </DivLong>
                  <Subtitle>Insira o ponto do destino (coordenadas)</Subtitle>
              <DivLong>
                  <InputWrapper>
                      <TitleCoordinates>Latitude</TitleCoordinates>
                      <Input type="number" onChange={handleTargetLat} value={targetLat}></Input>
                  </InputWrapper>
                  <InputWrapper>
                      <TitleCoordinates>Longitude</TitleCoordinates>
                      <Input type="number" onChange={handleTargetLon} value={targetLon}></Input>
                  </InputWrapper>
              </DivLong>
          </InputComponent>
          <ContainerButton>
            <img src={logo} /> 
            <Button onClick={getMap}>Gerar rota</Button>
          </ContainerButton>
        </>
    )
}

//Export component coordinates
export default InputComponents;
export const storedSourceLat = localStorage.getItem('sourceLat');
export const storedSourceLon = localStorage.getItem('sourceLon');
export const storedTargetLat = localStorage.getItem('targetLat');
export const storedTargetLon = localStorage.getItem('targetLon');