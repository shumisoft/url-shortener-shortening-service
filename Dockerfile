FROM eclipse-temurin:21-jdk-alpine-3.22
ENV PORT=1337
EXPOSE 1337
COPY /target/url-shortener-shortening-service-0.0.1-SNAPSHOT.jar url-shortener-shortening-service.jar
ENTRYPOINT ["java", "-jar", "url-shortener-shortening-service.jar"]
