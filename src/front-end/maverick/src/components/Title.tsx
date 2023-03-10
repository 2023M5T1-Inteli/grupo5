//Import libraries
import styled from "styled-components";

//Create styles to components to render the coordinates
const TitleHeader = styled.h1`
  font-family: 'Montserrat';
  font-style: normal;
  font-weight: 900;
  letter-spacing: .1rem;
  font-size: 4.0rem;
  line-height: 4.9rem;
  color: #18568C;
 `
const TitleContainer = styled.div`
  display: flex;
`
// Return the components to export for HTML
function Title (){
    return(
        <TitleContainer>
            <TitleHeader>Preencha os  campos <br/> para gerar uma nova rota </TitleHeader>
        </TitleContainer>
    )
}

//Export component coordinates
export default Title;