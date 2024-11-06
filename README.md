## CAJU - Desafio Tecnico


Execução: para rodar o projeto, é necessario dar permissâo de execução ao arquivo build na pasta raiz.
Para isto deve executar o comando abaixo em um terminal
> $ sudo chmod u+x build

Este script irá entrar nas pastas account e authorizer e executar arquivos de bash de build respectivamente.

Cada um deles vai criar uma imagem docker dos respectivos projetos.
Depois de criadas as imagens, o comando de build irá subir uma série de containers definidos no arquivo docker-compose.yml tb na pasta raiz.


### Como acessar.

Depois que todos os containers estiverem rodando, a aplicação fica disponível através de dois serviços.

**Contas**: http://localhost:9001/swagger-ui/index.html

usar endpoint POST /account passando payload 
> {
"MEALBalance": 120,
"FOODBalance": 230,
"CASHBalance": 230
} 

para criar uma conta. O response será o id (UUID) necessário para usar no serviço de autorização

**Autorizador**: http://localhost:8080/swagger-ui/index.html


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
Este foi organizado padrão MVC, bem simples ***controller -> service -> repository -> entity***

### Interação de domínio
Segue abaixo diagrama de sequencia que explica parte das interações de domínio.
* **AuthorizerService** recebe reuisicao de autorizacao
* Resgata com em **AccountService**
* Checa se é possível autorizar (há saldor suficiente e demais regras de acordo com o tipo authorizador)
* Persiste a autorização
* Devolve payload indicando status da requisição (aprovada, rejeitada, erro)

Tudo o que está dent do quadro azul, está sob o escopo do serviço de autorização **AuthorizerService**

Tudo o que está em verde está so o escopo do serviço de contas **AccountService**

Pensei em separa-los desta forma para seguir o principio da responsabilidade unica (single responsability principle)
Onde o seviço autorizador usa o saldo, mas o saldo, como serviço a parte pode ser modificado, versionado e servir a outros serviços
sem impactar nos "chamadores".

![Alt text](img/interacoes-de-dominio.drawio.png?raw=true "Title")

