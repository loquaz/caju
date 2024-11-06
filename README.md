### CAJU - Desafio Tecnico


Execução: para rodar o projeto, é necessario dar permissâo de execução ao arquivo build na pasta raiz.
Para isto deve executar o comando abaixo em um terminal
> $ sudo chmod u+x build

Este script irá entrar nas pastas account e authorizer e executar arquivos de bash de build respectivamente.

Cada um deles vai criar uma imagem docker dos respectivos projetos.
Depois de criadas as imagens, o comando de build irá subir uma série de containers definidos no arquivo docker-compose.yml tb na pasta raiz.