# Build from the repository root so the parent Maven POM is available.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./pom.xml
COPY banking-services/ banking-services/
RUN mvn -B -pl banking-services/transaction-service -am -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --uid 10001 --create-home appuser
COPY --from=build /workspace/banking-services/transaction-service/target/transaction-service-1.0.0-SNAPSHOT.jar app.jar
USER 10001
EXPOSE 8083
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
