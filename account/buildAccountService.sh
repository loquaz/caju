echo "***Construindo imagem Docker para servico de contas\n"
echo "\tRemovendo imagem anterior [caju\accounts] \n"
docker stop $(docker ps -qa) && docker image rm caju/accounts 2>/dev/null
./gradlew build && docker build --build-arg JAR_FILE=build/libs/account-service-0.0.1-SNAPSHOT.jar -t caju/accounts .