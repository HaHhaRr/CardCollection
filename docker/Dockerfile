FROM eclipse-temurin:21-jre-alpine-3.23
WORKDIR /app
COPY *.jar app/cardCollection.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cardCollection.jar"]
