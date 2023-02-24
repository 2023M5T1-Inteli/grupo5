---
title: Modelo para o artigo do Módulo 5
author: André Luís Lessa Junior, Arthur Alberto Cardoso Reis, Cristiane Andrade Coutinho, Giovana Lisbôa Thomé, João Pedro Sartori Garcia de Alcaraz, Luiz Francisco Granville Gonçalves, Stefano Tosi Butori
date: Janeiro de 2023
abstract: Como parte das atividades do módulo 5, cada grupo deverá redigir um texto descrevendo os resultados do projeto no formato de um artigo científico. Este arquivo no formato markdown contém a estrutura básica deste artigo. Cada grupo deverá editar este arquivo com a descrição do projeto que desenvolveu.
---

# Introdução
O meio militar envolve operações que visam o planejamento estratégico. Esse conceito foi publicado em junho de 2011 pela Asplan, orgão de assistência direta do Ministério da Defesa brasileiro e utilizado para definir uma direção ou caminho almejado pela corporação, a fim de que gastos de recursos sejam minimizados e a eficiência do trabalho para alcançar o objetivo seja maximizada. De acordo com o INPI, Instituto Nacional de Pesquisa Industrial, para que esse planejamento estratégico consiga êxito no futuro, diversas empresas ou autarquias necessitam de tecnologias  que potencializem essa ideia de estratégia, além de desempenhar um papel na persecução dos objetivos organizacionais e garantir o alinhamento da empresa com esses mesmos objetivos. Dentro do planejamento estratégico existem diversas missões que necessitam das mais variadas logísticas e estratégias, entre elas existem missões aeroespaciais de reconhecimento, resgate e ataque em percursos próximos ao solo. Assim sendo, uma das ferramentas aeroespaciais militares utilizadas nesse contexto é o radar _Terrain-Following_, permitindo que o piloto ao fazer o reconhecimento de uma região (muitas vezes desconhecida), voe em baixa altitude de modo constante, ou seja, sem variação da elevação da aeronave. A importância desse sistema se deve ao impedimento de uma possível detecção de um radar inimigo, além de facilitar o reconhecimento de um alvo ou até mesmo compreender a estrutura do relevo local.

Entretanto, a tecnologia _Terrain-following_ necessita de segurança e ponderações para a sua utilização durante as missões aéreas. Conforme o portal de comunicações da Ael, o sistema se baseia na fusão de dados provenientes do mapeamento radar e de um banco de dados de terreno e obstáculos. Assim sendo, é de suma importância que ocorra um reconhecimento prévio da região e a escolha de um melhor caminho possível. Todavia, a tecnologia _Terrain-following_ não supre essas necessidades de reconhecimento, gerando uma desvantagem inerente ao sistema. Portanto, o problema de pesquisa está relacionado ao desenvolvimento de um modelo matemático computacional que através do conhecimento prévio da região (dados armazenados nesse banco), construa possíveis rotas que levem o avião de um ponto ao outro e especifique o caminho preferível para o seu deslocamento. Um artigo correlato foi publicado por estudantes de Ciência da Computação da Unesc
(Universidade Estadual de Criciúma) e utilizava os conhecimentos de algoritmo de Dijkstra, grafos e estrutura de dados como embasamento e possuía o intuito de estudar as rotas realizadas pelo transporte público de Criciúma e encontrar as mais viáveis para a solução do problema. Esse trabalho contem similiaridades com o descrito anteriormente, contudo com ênfase na área de defesa, visto que a criação do algoritmo propicia que empresas e as forças armadas realizem o _Terrain-following_, em conjunto com a tecnologia planejadora de voos, sem a preocupação de não percorrerem um trajeto viável. O resultado esperado é que o _software_ consiga destacar uma ou mais trajetórias viáveis através de dados de _input_ das cidades de saída e chegada feitos pelo usuário.

Para que tal solução fosse viável, os principais materiais e métodos que foram utilizados para a construção do algoritmo de alta performance, capaz de identificar o melhor trajeto possível, são a linguagem de programação Java, necessária para a construção desses modelos computacionais, o banco de dados NoSql, que contém as informações de relevo da região diponibilizadas pela empresa Ael. Desse modo, a linguagem java com o _framework Spring Boot Web_ como arquitetura MVC (_Model_, _View_, _Control_)  para a realização do CRUD (_Create_, _Read_, _Update_, _Delete_), nescessário para manipular a estrutura do banco de dados orientado à grafos através de uma _API RESTful_. Além disso, esses dados estão na extensão de arquivo DTED e por isso se faz necessário o uso da biblioteca _Geospatial Data Abstraction Library_ (GDAL), disponível na linguagem Java, capaz de extrair essas informações geográficas. De tal maneira, convertemos os dados do arquivo DTED com essa biblioteca e convertemos em objetos Java. Por outro lado, para o NoSql, utilizamos outro banco de dados denominado Neo4j, orientado a grafos (manipulação dos dados através da linguagem Cypher), reponsável pela modelagem matemática e a construção do raciocínio algorítmico dos fluxos. Por fim, para a visualização das rotas e a identificação do melhor caminho possível, utilizamos a biblioteca do Javascript chamada D3js. Desse modo, para que tais ferramentas fossem possíveis, todas essas tecnologias foram hospedadas através da aplicação Docker que isola as mesmas em conteíneres únicos, justamente para garantir a padronização do ambiente de desenvolvimento.

# Descrição do problema

# Trabalhos relacionados

# Descrição da estratégia adotada para resolver o problema

# Análise da complexidade da solução proposta

Neste artigo, cada grupo precisará fazer a análise de complexidade da solução proposta, utilizando as notações $O(.)$, $\Omega(.)$ e $\Theta(.)$.

A seguir temos a citação de alguns trechos de DASGUPTA et. al. (2011) para mostrar como estas notações são em \LaTeX. 

> Sejam $f(n)$ e $g(n)$ duas funções de inteiros positivos em reais positivos. Dizemos que $f = O(g)$ (que significa que "$f$ não cresce mais rápido do que $g$") se existe uma constante $c > 0$ tal que $f(n) \leq c \cdot g(n)$.

Ainda em outro trecho de DASGUPTA et. al. (2011), temos:

> Assim como $O(.)$ é análogo a $\leq$, podemos definir análogos de $\geq$ e $=$ como se segue:

> $f = \Omega(g)$ significa $g = O(f)$

# Análise da corretude da solução proposta

# Resultados obtidos

# Conclusão

# Referências Bibliográficas
LINTZMAYER, CN; OLIVEIRA MOTA, G. **Análise de Algoritmos e Estruturas de Dados** .Estrutura de dados,cap.3. Disponível em: <https://www.ime.usp.br/~mota/livros/livro_AAED.pdf>. Acesso em: 20 fev. 2023.

PRESTES, E. **Introdução à Teoria dos Grafos**. Fundamentação Teórica,cap.1. Disponível em: <https://www.inf.ufrgs.br/~prestes/Courses/Graph%20Theory/Livro/LivroGrafos.pdf>. Acesso em: 20 fev. 2023.


LUIZ, O. **PETI**.Intodução,cap.2 Disponível em: <https://www.gov.br/inpi/pt-br/acesso-a-informacao/tecnologia-da-informacao/arquivos/documentos/peti_20162019.pdf>. Acesso em: 20 fev. 2023.

AEL,Portal de comunicações.**Terrain Following**. Disponível em: <https://www.ael.com.br/terrain-following.html>. Acesso em: 20 fev. 2023.

L., A. et al. **Utilização do Algoritmo de Dijkstra para Cálculo de Rotas no Trabalho Público do Município de Criciúma/SC**. Disponível em: <https://periodicos.unesc.net/ojs/index.php/sulcomp/article/download/1815/1717/5477>. Acesso em: 20 fev. 2023.

ASPLAN.**Sistema de Planejamento Estratégico e Defesa**. Importância do Planejamento Estratégico,cap.3. Disponível em: <https://portal.tcu.gov.br/lumis/portal/file/fileDownload.jsp?fileId=8A8182A24F0A728E014F0AC5DCB653E4>. Acesso em: 20 fev. 2023

.

