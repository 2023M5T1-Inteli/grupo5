//Import libraries
import styled from "styled-components";

//Import components
import Title from "../components/Title";
import InputComponents from "../components/Input"
import FooterComponents from "../components/Footer"

//Import elements
import logo from "../assets/logo.png";

//Create styles to components to render the coordinates
const Container = styled.div`
  display: flex;
  flex-direction:column;
  width: 100vw;
  height: 100vh;
  padding: 2rem;
  padding-left: 6rem;
  `

 // Return the components to export for HTML
function Form (){
    return(
        <Container>
            <Title></Title>
            <InputComponents></InputComponents>
            <FooterComponents></FooterComponents>
        </Container>
        
    )
}

//Export component coordinates
export default Form;