FROM maven:3.8.7-eclipse-temurin-11-alpine
WORKDIR /app
COPY . .
RUN mvn clean install
#ARG JAR_FILE=/app/web/target/*.jar
#COPY ${JAR_FILE} lucut-ovidiu-portfolio.jar
#ENTRYPOINT ["tail", "-f", "/dev/null"] #Run infinitely
ENTRYPOINT ["java","-jar","/app/web/target/web-1.0.0.jar"]