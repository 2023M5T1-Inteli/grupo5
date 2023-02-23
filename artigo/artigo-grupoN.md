---
title: Modelo para o artigo do Módulo 5
author: André Luís Lessa Junior, Arthur Alberto Cardoso Reis, Cristiane Andrade Coutinho, Giovana Lisbôa Thomé, João Pedro Sartori Garcia de Alcaraz, Luiz Francisco Granville Gonçalves, Stefano Tosi Butori
date: Janeiro de 2023
abstract: Como parte das atividades do módulo 5, cada grupo deverá redigir um texto descrevendo os resultados do projeto no formato de um artigo científico. Este arquivo no formato markdown contém a estrutura básica deste artigo. Cada grupo deverá editar este arquivo com a descrição do projeto que desenvolveu.
---

# Introdução
O meio militar envolve operações que visam o planejamento estratégico. Esse conceito é utilizado para definir uma direção ou caminho almejado pela corporação, a fim de que gastos de recursos sejam minimizados e a eficiência do trabalho para alcançar o objetivo seja maximizada. De acordo com o INPI, Instituto Nacional de Pesquisa Industrial, para que esse planejamento estratégico consiga êxito no futuro, diversas empresas ou autarquias necessitam de tecnologias  que potencializem essa ideia de estratégia, além de desempenhar um papel importante na precursão dos objetivos organizacionais e garantir o alinhamento da empresa com os seus objetivos. Dentro do planejamento estratégico existem diversas missões que necessitam das mais variadas logísticas e estratégias, entre elas existem missões aeroespaciais de reconhecimento, resgate e ataque em percursos próximos ao solo. Assim sendo, uma das ferramentas aeroespaciais militares utilizadas nesse contexto é o radar Terrain-Following, permitindo que o piloto ao fazer o reconhecimento de uma região(muitas vezes desconhecida), voe em baixa altitude, de modo constante, ou seja, sem variação da elevação da aeronave. A importância desse sistema se deve ao impedimento de uma possível detecção de um radar inimigo, além de facilitar o reconhecimento de um alvo ou até mesmo compreender o funcionamento do relevo local.

Entretanto, a tecnologia Terrain-following necessita de segurança e ponderações para a sua utilização durante as missões aéreas. Conforme o portal de comunicações da Ael, o sistema se baseia na fusão de dados provenientes do mapeamento radar e de um banco de dados de terreno e obstáculos, assim sendo, é de suma importância que ocorra um reconhecimento prévio da região e a escolha de um melhor caminho possível, entretanto a tecnologia Terrain-following não supri essas necessidades de reconhecimento, gerando uma deficiência inerente ao sistema. Portanto, a solução desse problema está relacionada à criação de um modelo matemático computacional que através do conhecimento prévio da região (dados armazenados nesse banco), construa possíveis rotas que levem o avião de um ponto ao outro e especifique o melhor caminho para o seu deslocamento. De modo inerente, a criação desse algoritmo é essencial para a resolução do problema, porque o mesmo propicia que empresas e as forças armadas realizem o Terrain-following, em conjunto com essa solução agregadora, sem a preocupação de não percorrerem um trajeto viável.

Para que tal solução fosse viável, os principais materiais e métodos que serão utilizados para a construção do algoritmo de alta performance, capaz de identificar o melhor trajeto possível, são a linguagem de programação Java, necessária para a construção desses modelos computacionais, o banco de dados NoSql, que contém as informações de relevo da região diponibilizadas pela empresa Ael. Desse modo, a linguagem java com o _framework Spring Boot Web_ como arquitetura MVC (_Model_, _View_, _Control_)  para a realização do CRUD (_Create_, _Read_, _Update_, _Delete_), nescessário para manipular a estrutura do banco de dados orientado à grafos através de uma _API RESTful_. Além disso, esses dados estão na extensão de arquivo DTED e por isso se faz necessário o uso da biblioteca _Geospatial Data Abstraction Library_ (GDAL), disponível na linguagem Java, capaz de extrair essas informações geográficas. De tal maneira, convertemos os dados do arquivo DTED com essa biblioteca e convertemos em objetos Java. Por outro lado, para o NoSql, utilizamos outro banco de dados denominado Neo4j, orientado a grafos (manipulação dos dados através da linguagem Cypher), reponsável pela modelagem matemática e a construção do raciocínio algorítmico dos fluxos. Por fim, para a visualização das rotas e a identificação do melhor caminho possível, utilizamos a biblioteca do Javascript chamada D3js.Desse modo, para que tais ferramentas fossem possíveis, todas essas tecnologias foram hospedadas através da aplicação Docker que isola as mesmas em conteíneres únicos, justamente para garantir a padronização do ambiente de desenvolvimento.

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

DASGUPTA, S.; Papadimitriou, C.; Vazirani, U. **Algoritmos.** Porto Alegre: AMGH, 2011. 1 recurso online. ISBN 9788563308535. Disponível em: https://integrada.minhabiblioteca.com.br/books/9788563308535. Acesso em: 17 jan. 2023.
