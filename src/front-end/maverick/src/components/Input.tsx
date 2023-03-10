//Import libraries
import styled from "styled-components";

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

// Return the components to export for HTML
function InputComponents (){
    return(
        <InputComponent>
            <Subtitle>Insira o ponto de partida (coordenadas)</Subtitle>
            <DivLong>
            <InputWrapper>
                <TitleCoordinates>Latitude</TitleCoordinates>
                <Input></Input>
            </InputWrapper>
            <InputWrapper>
                <TitleCoordinates>Longitude</TitleCoordinates>
                <Input></Input>
            </InputWrapper>
            </DivLong>
                <Subtitle>Insira o ponto do destino (coordenadas)</Subtitle>
            <DivLong>
                <InputWrapper>
                    <TitleCoordinates>Latitude</TitleCoordinates>
                    <Input></Input>
                </InputWrapper>
                <InputWrapper>
                    <TitleCoordinates>Longitude</TitleCoordinates>
                    <Input></Input>
                </InputWrapper>
            </DivLong>
        </InputComponent>
    )
}

//Export component coordinates
export default InputComponents;