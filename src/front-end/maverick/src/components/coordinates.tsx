//Import libraries
import React from 'react';
import ReactDOM from 'react-dom/client';
import styled from 'styled-components';

//Create styles to components to render the coordinates
const Cord = styled.div`
  display: flex;
  width:95vw;
  height:30vh;
  justify-content: space-evenly;
  margin-bottom: -3rem;
`
const CordContainer = styled.div`
  display: flex;
  width: 90vw;
  flex-direction: column;
`

const CardCord = styled.div`
  display: flex;
  flex-direction:column;
  width: 40vw;
  align-items: right;
`

const TitleCord = styled.h2`
  font-weight: 500;
  font-size: 1.8rem;
  line-height: 29px;
  color: #18568C;
  flex: none;
  order: 0;
  flex-grow: 0;
`

const Subtitle = styled.h2`
  font-weight: 300;
  font-size: 1.6rem;
  line-height: 2.9rem;
  color: #18568C;
  flex: none;
  order: 0;
  flex-grow: 0;
`

const CordBox = styled.div`
  background: #E6E6E6;
  border-radius: 15px;
  width: 40rem;
  height: 59px;
  font-weight: 400;
  font-size: 1.6rem;
  line-height: 24px;
  color: #18568C;
  padding: 2rem;
`

const SpaceBox = styled.div`
  height: 2.9rem;
`

// Return the components to export for HTML
function Coordinates() {
    return(
        <CordContainer>
            <Cord>
                <CardCord>
                    <TitleCord>Ponto de partida (coordenadas)</TitleCord>
                    <br />
                    <Subtitle>Latitude:</Subtitle>
                    <br />
                    <CordBox> 23°34'49.43"S  </CordBox>
                </CardCord>
                <CardCord>
                    <SpaceBox></SpaceBox>
                    <br />
                    <Subtitle>Longitude:</Subtitle>
                    <br />
                    <CordBox> 23°34'49.43"S </CordBox>
                </CardCord>
            </Cord>
            <Cord>
            <CardCord>
                    <TitleCord>Ponto do destino (coordenadas)</TitleCord>
                    <br />
                    <Subtitle>Latitude:</Subtitle>
                    <br />
                    <CordBox> 23°34'49.43"S </CordBox>
                </CardCord>
                <CardCord>
                    <SpaceBox></SpaceBox>
                    <br />
                    <Subtitle>Longitude:</Subtitle>
                    <br />
                    <CordBox> 23°34'49.43"S </CordBox>
                </CardCord>
            </Cord>
        </CordContainer>
    )
}

//Export component coordinates
export default Coordinates;