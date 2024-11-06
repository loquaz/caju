FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD apt-get update -y
EXPOSE 9001
ENTRYPOINT ["java","-jar","/app.jar"]