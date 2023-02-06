<table>
<tr>
<td>
<a href= "https://ael.com.br/"><img src="https://www.ael.com.br/images/ael.png" alt="AEL Sistemas" border="0" width="70%"></a>
</td>
<td><a href= "https://www.inteli.edu.br/"><img src="https://www.inteli.edu.br/wp-content/uploads/2021/08/20172028/marca_1-2.png" alt="Inteli - Instituto de Tecnologia e Liderança" border="0" width="30%"></a>
</td>
</tr>
</table>

<font size="+12"><center>
Planejador de trajetórias para voos em baixa altitude
</center></font>

>*Observação 1: A estrutura inicial deste documento é só um exemplo. O seu grupo deverá alterar esta estrutura de acordo com o que está sendo solicitado nos artefatos.*

>*Observação 2: O índice abaixo não precisa ser editado se você utilizar o Visual Studio Code com a extensão **Markdown All in One**. Essa extensão atualiza o índice automaticamente quando o arquivo é salvo.*

**Conteúdo**

- [Autores](#autores)
- [Visão Geral do Projeto](#visão-geral-do-projeto)
  - [Empresa](#empresa)
  - [O Problema](#o-problema)
    - [Contexto do problema](#contexto-do-problema)
    - [Quais os dados disponíveis](#quais-os-dados-disponíveis)
    - [Qual o objetivo do problema](#qual-o-objetivo-do-problema)
    - [Qual a tomada de decisão do problema proposto](#qual-a-tomada-de-decisão-do-problema-proposto)
    - [Pelo menos 1 limitação existente no problema](#pelo-menos-1-limitação-existente-no-problema)
  - [Solução](#solução)
    - [Como a solução deverá ser utilizada](#como-a-solução-deverá-ser-utilizada)
  - [Objetivos](#objetivos)
    - [Objetivos gerais](#objetivos-gerais)
    - [Objetivos específicos](#objetivos-específicos)
  - [Partes interessadas](#partes-interessadas)
- [Análise do Problema](#análise-do-problema)
  - [Análise da área de atuação: Contexto da indústria](#análise-da-área-de-atuação-contexto-da-indústria)
    - [Principais competidores](#principais-competidores)
    - [Modelos de negócio](#modelos-de-negócio)
    - [Tendências de mercado](#tendências-de-mercado)
  - [Análise estratégica: 5 forças de Porter](#análise-estratégica-5-forças-de-porter)
  - [Análise do cenário: Matriz SWOT](#análise-do-cenário-matriz-swot)
  - [Proposta de Valor: Value Proposition Canvas](#proposta-de-valor-value-proposition-canvas)
  - [Matriz de Risco](#matriz-de-risco)
- [Requisitos do Sistema](#requisitos-do-sistema)
  - [Personas](#personas)
  - [Histórias dos usuários (user stories)](#histórias-dos-usuários-user-stories)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
  - [Módulos do Sistema e Visão Geral (Big Picture)](#módulos-do-sistema-e-visão-geral-big-picture)
  - [Descrição dos Subsistemas](#descrição-dos-subsistemas)
    - [Requisitos de software](#requisitos-de-software)
  - [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [UX e UI Design](#ux-e-ui-design)
  - [Wireframe + Storyboard](#wireframe--storyboard)
  - [Design de Interface - Guia de Estilos](#design-de-interface---guia-de-estilos)
- [Projeto de Banco de Dados](#projeto-de-banco-de-dados)
  - [Modelo Conceitual](#modelo-conceitual)
  - [Modelo Lógico](#modelo-lógico)
- [Teste de Software](#teste-de-software)
  - [Testes Unitários](#testes-unitários)
  - [Teste de Usabilidade](#teste-de-usabilidade)
- [Análise de Dados](#análise-de-dados)
- [Manuais](#manuais)
  - [Manual de Implantação](#manual-de-implantação)
  - [Manual do Usuário](#manual-do-usuário)
  - [Manual do Administrador](#manual-do-administrador)
- [Referências](#referências)


# Autores

* Aluno 1
* Aluno 2
* Aluno 3
* Aluno 4
* Aluno 5
* Aluno 6
* Aluno 7
* Aluno 8


# Visão Geral do Projeto

## Empresa

*Descrição_da_empresa*

## O Problema

### Contexto do problema
Diversas operações militares destinadas principalmente à defesa e segurança pública requerem voos de baixa altitude por questões de logística e detecção de aeronaves. Atualmente, a AEL Sistemas S.A. atende clientes que necessitam de sistemas capazes de traçar trajetórias desses tipos de voos, que apresentam altos riscos ligados às suas operações. O termo CFIT, do inglês Controlled Flight Into Terrain, refere-se à acidentes de colisão com solo em voos controlados, uma das principais causas de acidentes aéreos envolvendo mortes, segundo a Boeing. Em decorrência da proximidade com o solo, o risco de CFIT aumenta consideravelmente.

### Quais os dados disponíveis

### Qual o objetivo do problema
A resolução do problema busca trazer um planejador de trajetórias para voos de baixa altitude utilizando grafos, a partir do mapeamento do terreno com base nos dados de relevo disponibilizados.

### Qual a tomada de decisão do problema proposto

### Pelo menos 1 limitação existente no problema
A complexidade de desenvolver a solução para este problema é alta, para o desenvolvimento dos algoritmos é necessário o alto uso de memória, sendo que o uso da memória irá aumentar de acordo com a escala dos grafos fazendo com que haja demora na execução do algoritmo e não seja um produto altamente escalável.
Outro fator importante é a experiência dos desenvolvedores para a criação da solução, devido a complexidade do problema, a acurácia do algoritmo pode ser um obstáculo considerando sua eficiência e implementação, sendo necessário pensar na estruturação dos dados e sua compatibilidade.
Por fim, há fatores externos que são limitações para este problema, como a complexidade dos terrenos e a detecção de obstáculos para que não haja nenhuma colisão, também as condições climáticas podem atrapalhar o percurso criado pelos grafos.


## Solução

### Como a solução deverá ser utilizada
O software desenvolvido será usado para gerar uma trajetória de voo ótima a partir de um ponto de partida e um ponto de destino, levando em consideração as restrições especificadas. A solução deverá utilizada como um auxílio para os pilotos realizarem voos em baixa altitude. Idealmente, a rota gerada será analisada e estudada pelo piloto e sua equipe durante o planejamento do voo, para garantir confiabilidade da rota. A solução, também, poderá servir como base para futuros projetos da empresa, especialmente o sistema de Terrain Following da AEL.

## Objetivos

### Objetivos gerais

*Lista_de_objetivos_gerais*

### Objetivos específicos

*Lista_de_objetivos específicos*

## Partes interessadas

*Lista_e_apresentação_das_partes_interessadas*

# Análise do Problema

*Descrição_da_análise_do_problema*

## Análise da área de atuação: Contexto da indústria

A AEL Sistemas está inserida nas indústrias aeroespacial, de defesa e de segurança pública, sendo sua atuação focada no território brasileiro. A empresa tem como produto principal a produção de sistemas eletrônicos militares para aplicações em plataformas aéreas, mas também atua em outras áreas, como na produção de simuladores de voo e sistemas eletro-ópticos, espaciais e para veículos blindados.

### Principais competidores

Tendo em mente que a AEL Sistemas é uma empresa de médio porte que atua principalmente no mercado brasileiro aeroespacial, seus principais competidores são empresas de médio e grande porte nacionais, como:

- Embraer S.A. -  empresa de capital aberto com sede em São José dos Campos, São Paulo, Brasil. É uma das maiores empresas brasileiras de engenharia e fabricação de aeronaves, com atuação em diversos segmentos do mercado aeroespacial, como aeronaves comerciais, executivas, militares, de defesa e de transporte de cargas.

- Atech - empresa integrante do grupo econômico da Embraer, com foco em sistemas críticos civis e militares, como, por exemplo, sistemas de Controle de Tráfego Aéreo.

- Helibras (Helicópteros do Brasil S.A.) - empresa brasileira consolidada em Itajubá, Minas Gerais, sendo uma subsidiária da Airbus Helicopters. A empresa primariamente no ramo de fabricação de helicópteros civis e militares.

- Avibras Indústria Aeroespacial - empresa sediada em São José dos Campos, São Paulo, Brasil, que atua no ramo de defesa, com foco em sistemas de mísseis, foguetes e sistemas antiaéreos e com atuação no setor aeroespacial.

- Akaer - empresa brasileira de engenharia aeroespacial, com sede em São José dos Campos, São Paulo, Brasil. Desenvolveu projetos militares para aeronaves como o caça SAAB Gripen e helicópteros da Helibras.

### Modelos de negócio

Companhias do setor aeroespacial e de defesa possuem modelos de negócio diversos, que envolvem produtos e serviços complexos e altamente tecnológicos. As atividades envolvidas abrangem desde a produção de aeronaves, como no caso da Embraer (fabricante de aeronaves comerciais, executivas e militares), até o desenvolvimento de sistemas eletrônicos para aeronaves (aviônicos), como ocorre com a AEL Sistemas, que produz sistemas eletrônicos militares com aplicações em plataformas aéreas e outras áreas.

Existe um grande foco na área de defesa em contratos governamentais, pois os clientes das companhias são as forças armadas de diferentes países. Além disso, as companhias do setor aeroespacial e de defesa possuem um grande foco em pesquisa e desenvolvimento, por se tratar de um mercado competitivo em que a inovação tecnológica é um importante diferencial. Existe também uma tendência de diversificação, com o foco em uma área principal e oferta de produtos e serviços complementares, relacionado com as competências e capacidades do corpo técnico da companhia.

### Tendências de mercado

As tendências do mercado de aviação militar no Brasil incluem:

- a modernização de alguns aviões da frota da Força Aérea Brasileira (FAB), como os “aviões-radares” E-99;
- a aquisição de novos aviões militares para melhorar a capacidade de defesa aérea e de operações aéreas, como o caça sueco F-39 Gripen E/F, com a adoção de tecnologias e aviônicos como o Wide Area Display (WAD) desenvolvido pela AEL Sistemas;
- a expansão da cooperação internacional e a parceria com empresas estrangeiras, como no caso da Embraer e da BAE Systems PLC (uma empresa britânica), que firmaram dois memorandos de entendimento ("MoU"s) para possibilitar a venda de aeronaves militares produzidas pela Embraer como o C-390 Millennium, um avião militar de transporte de cargas, em mercados onde a BAE Systems possui maior presença (especialmente no Oriente Médio).

## Análise estratégica: 5 forças de Porter

Considerando a AEL Sistemas como uma empresa que atua no mercado brasileiro aeroespacial, foi utilizada a ferramenta de análise estratégica das 5 forças de Porter, que permite identificar os principais fatores que influenciam a estratégia competitiva de uma empresa.

![5_forças_de_Porter](img/porter.png)

1. Concorrência entre os participantes existentes: a indústria aeroespacial militar brasileira é um mercado relativamente concentradao. Existem alguns players dominantes, especialmente a Embraer S.A., que possuem uma grande participação no mercado.

2. Poder de barganha dos fornecedores: a cadeia de suprimentos da indústria é complexa, incluindo materiais especializados e tecnologia avançada. Isso pode dar aos fornecedores um poder de barganha elevado, pois não existem tantos players capacitados para fornecer os insumos necessários.

3. Poder de barganha dos compradores: os compradores são principalmente governos e agências militares, que têm um poder de barganha considerável devido ao seu grande porte e capacidade elevada de investimento.

4. Ameaça de novos entrantes: a barreira de entrada na indústria aeroespacial é altamente elevada devido aos custos significativos de desenvolvimento e produção. Além disso, se trata de uma indústria muito regulamentada, o que pode limitar a entrada de novos players.

5. Ameaça de produtos substitutos: não existem alternativas viáveis para os sistemas militares avançados desenvolvidos pelas empresas do setor, o que limita a ameaça de produtos substitutos.

## Análise do cenário: Matriz SWOT

![SWOT](img/SWOT.png)

*Matriz_SWOT*


## Proposta de Valor: Value Proposition Canvas

*Value_Proposition_Canvas*


## Matriz de Risco

*Matriz_de_risco*


# Requisitos do Sistema

*Descrição_dos_requisitos*

## Personas

*Descrição_das_personas*


## Histórias dos usuários (user stories)

*Descrição_das_histórias_dos_usuários*


# Arquitetura do Sistema

## Módulos do Sistema e Visão Geral (Big Picture)

## Descrição dos Subsistemas

### Requisitos de software


## Tecnologias Utilizadas


# UX e UI Design

## Wireframe + Storyboard

## Design de Interface - Guia de Estilos


# Projeto de Banco de Dados

## Modelo Conceitual

## Modelo Lógico


# Teste de Software

## Testes Unitários

## Teste de Usabilidade


# Análise de Dados


# Manuais

## Manual de Implantação

## Manual do Usuário

## Manual do Administrador


# Referências
