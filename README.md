## CAJU - Desafio Tecnico


Execução: para rodar o projeto, é necessario dar permissâo de execução ao arquivo build na pasta raiz.
Para isto deve executar o comando abaixo em um terminal
> $ sudo chmod u+x build

Este script irá entrar nas pastas account e authorizer e executar arquivos de bash de build respectivamente.

Cada um deles vai criar uma imagem docker dos respectivos projetos.
Depois de criadas as imagens, o comando de build irá subir uma série de containers definidos no arquivo docker-compose.yml tb na pasta raiz.

### Arquitetura da solução

A solução foi pensada da seguinte maneira: um serviço é responsável por receber as requisições dos comerciantes e é responsável 
por registrar uma transação (persistir) no mecanismo de persistencia configurado (para esta poc um banco relacional).
Este serviço foi projetado usando principios de arquitetura limpa e arquitetura hexagonal. Foi dividido nas seguintes camadas
* aplicação: por web e seus adaptadores (controller rest)
* domínio: Casos de Use, Entidades e Gateways (interfaces)
* infrastrutura: adaptadores pros Gateways da camada de domínio (banco e cliente http)
![Alt text](img/caju-desafio.drawio.png?raw=true "Title")