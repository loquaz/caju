## CAJU - Desafio Tecnico


Execução: para rodar o projeto, é necessario dar permissâo de execução ao arquivo build na pasta raiz.
Para isto deve executar o comando abaixo em um terminal
> $ sudo chmod u+x build

Este script irá entrar nas pastas account e authorizer e executar arquivos de bash de build respectivamente.

Cada um deles vai criar uma imagem docker dos respectivos projetos.
Depois de criadas as imagens, o comando de build irá subir uma série de containers definidos no arquivo docker-compose.yml tb na pasta raiz.

### Arquitetura da solução
![Alt text](img/caju-desafio.drawio.png?raw=true "Title")

A solução foi pensada da seguinte maneira, existe uma API REST responsável pela autorização que se cominica com outro mmicroserviço responsável
pelas contas e saldos (carteiras de benefícios) de cada beneficiário.

São respectivamente AuthorizerService e AccountService.

#### Authorizer Service 
Responsável por receber as requisições dos comerciantes e registrar a transação (persistir).
Este foi organizado usando principios das arquiteturas limpa e hexagonal e dividido nas seguintes camadas.
* **aplicação**: porta web e seus adaptadores (controller rest)
* **domínio**: casos de uso, entidades e gateways (interfaces)
* **infrastrutura**: adaptadores pros gateways da camada de domínio (banco e cliente http)

#### Account Service 
Responsável pelas contas e seus saldos, cria e retorna contas. Atualiza seus saldos.
Este foi organizado padrão MVC, bem simples controler -> service -> repository -> entity

