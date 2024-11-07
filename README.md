## CAJU - Desafio Tecnico

* [Como rodar o projeto](#como-rodar-o-projeto)
* [Como acessar](#como-acessar)
* [Arquitetura da solução](#arquitetura-da-solução)
* [AuthorizerService](#authorizer-service-)
* [AccountService](#account-service-)
* [Interação de domínio](#interação-de-domínio)
* [Questão Aberta- Uma transação por vez](#l4-apenas-uma-transação-por-vez)



### Como rodar o projeto

Execução: para rodar o projeto, é necessario executar o script bash ***build*** na pasta raiz.
Para isto, em sistemas operacionais *nix like, deve-se dar permissão de execução deste, e dos arquivos _**account/buildAccountService.sh**_, _**authorizer/buildAuthorizerService.sh**_,  comando abaixo em um terminal.
> $ sudo chmod u+x build && chmod u+x account/buildAccountService.sh &&  chmod u+x account/buildAuthorizerService.sh 

Após isto deve-se executar o script usando o commando
> $ ./build

(este script irá entrar nas pastas dos projetos account e authorizer, fazer build, gerar imagens docker e tentar subir um docker-compose com tudo o que é necessário).

Alem disso, deve-se criar um arquivo .env ta raiz do projeto com as seguintes variaveis

* DB_USERNAME=_usuario do banco_
* DB_PASSWORD=_senha para este usuario_
* DB_NAME=_nome da base de dados_


### Como acessar

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

### L4 Apenas uma transação por vez

![Alt text](img/garantia-de-1-transacao-por-vez.drawio.png?raw=true "Title")

Como provavelmente, em um cenário de produção, uma transação envolve mais uma operação de banco de dados, como consultas e alterações,
pensei em encapsular todas as operações necessárias para manter o estado consistente da transação em poucos objetos e métodos.
Envolver toda a execução em um objeto **Account**, por exemplo. 

Ao atualizar a conta (**account**), o método seria anotado como **@Transactional**, caso fosse usada alguma linguagem 
que rode na JVM. Além disso a execução da transação estaria controlada em um bloco try/catch para:
* Havendo erro, rollback em toda a operação
* Registrar o erro, logar, monitorar
* possível mecanismo de retentativa (fila para tentativas falhas?)

Não pensei em estratégias de lock individuais das entidades envolvidas pois isso seria muito granular e difícil de manter/lidar.

