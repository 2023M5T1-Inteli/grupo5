import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import styled from "styled-components";
import CircularIndeterminate from "../components/CircularProgress";

const Container = styled.div`
    display: flex;
    width: 100vw;   
    height: 100vh;
    flex-direction:column;
    align-items: center;
    padding-top: 15rem; 

`
const Title = styled.h1`
    font-family: 'Montserrat';
    font-style: normal;
    font-weight: 700;
    letter-spacing: .1rem;
    font-size: 4.0rem;
    line-height: 4.9rem;
    color: #18568C;
    aling-itens: center;
    padding-top: 4.8rem;
`
const Subtitle = styled.h2`
    font-family: 'Montserrat';
    display:flex;
    color: #18568C;
    font-size:1.8rem;
    font-weight:500;
`

const Logo = styled.img`
    position: absolute;
    bottom:4rem;
`

function Waiting (){
    const navigate = useNavigate();

    useEffect(() => {
        const timeoutId = setTimeout(() => {
            navigate("/viewroutes"); // replace with your desired URL
        }, 5000); // replace with your desired timeout in milliseconds

        return () => {
        clearTimeout(timeoutId);
        };
    }, [navigate]);
    return(
        <Container>
            <CircularIndeterminate></CircularIndeterminate>
            <Title>Aguarde</Title>
            <Subtitle>Estamos gerando a sua rota</Subtitle>
            <Logo src={logo}/>
        </Container>
    )}

export default Waiting;