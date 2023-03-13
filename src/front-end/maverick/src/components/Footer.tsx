//Import libraries
import styled from "styled-components";

//Import elements
import logo from "../assets/logo.png";

//Create styles to components to render the button
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
// Render the components to export for HTML
function FooterComponents (){
    return(
        <ContainerButton>
            <img src={logo} /> 
            <Button onClick={() => {window.location.href = '/waitingroom'}}>Gerar rota</Button>
        </ContainerButton>
    )}
export default FooterComponents