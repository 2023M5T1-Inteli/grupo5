import styled from "styled-components";
import React, { useEffect, useState } from "react";

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
 
function Exclusion(){
    return(
        <Container>
            <TitleHeader>Zona de exclusão</TitleHeader>
            <Subtitle>Insira as coordenadas das areas a serem desconsideradas na rota</Subtitle>
            <Subtitle>Insira o primeiro ponto (coordenadas)</Subtitle>
            <DivSub>
            <TitleCoordinates >Latitude</TitleCoordinates>
            <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
            <Input></Input>
            <Input></Input>
            </Div>
            <Subtitle>Insira o segundo ponto (coordenadas)</Subtitle>
            <DivSub>
              <TitleCoordinates>Latitude</TitleCoordinates>
              <TitleCoordinates>Longitude</TitleCoordinates>
            </DivSub>
            <Div>
              <Input></Input>
              <Input></Input>
            </Div>
            <ContainerButton>
            <Button>Gerar rota</Button>
          </ContainerButton>
        </Container>
    )
}
export default Exclusion;