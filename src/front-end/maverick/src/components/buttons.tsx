//Import libraries
import React from 'react';
import ReactDOM from 'react-dom/client';
import styled from 'styled-components';

//Create styles to components to render the button
const ContainerButton = styled.div`
  display: flex;
  flex-direction: row;
  width:95vw;
  margin-top: 5rem;
  align-items:flex-end;
  position: absolute;
  justify-content: flex-end;
  bottom:0;
  padding-bottom: 5%;
`

const Button = styled.button`
  width: 25rem;
  height: 8rem;
  background: #18568C;
  border-radius: 100px;
  border-color: #18568C;  
  border-width: 0px;
  font-weight: 700;
  font-size: 1.8rem;
  color: #FFFFFF;
`

//Return the components to export for HTML
function ButtonComp(){ 
    return(
        <ContainerButton>
            <Button>Calcular novamente</Button>
        </ContainerButton>
    )
}

//Export component coordinates
export default ButtonComp;