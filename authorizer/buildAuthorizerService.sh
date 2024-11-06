echo "***Construindo imagem Docker para servico de autorizacao\n"
echo "\tRemovendo imagem anterior [caju/authorizer] \n"
docker stop $(docker ps -qa) && docker image rm caju/authorizer 2>/dev/null
./gradlew build && docker build --build-arg JAR_FILE=application/build/libs/application-0.0.1-SNAPSHOT.jar -t caju/authorizer .