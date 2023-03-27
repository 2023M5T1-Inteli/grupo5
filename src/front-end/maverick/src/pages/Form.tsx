//Import libraries
import styled from "styled-components";

//Import components
import Title from "../components/Title";
import InputComponents from "../components/Input"
// import FooterComponents from "../components/Footer"

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
function Form (){
    return(
        <Container>
            <Title></Title>
            <InputComponents></InputComponents>
        </Container>
        
    )
}

//Export component coordinates
export default Form;