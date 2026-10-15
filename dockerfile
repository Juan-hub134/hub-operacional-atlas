
# Etapa 1: compilar a aplicação com Java 21 e Maven
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY huboperacional/pom.xml ./pom.xml
COPY huboperacional/src ./src

RUN mvn -B clean package -DskipTests \
    && cp target/*.jar /build/app.jar

# Etapa 2: executar a aplicação
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /build/app.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "exec java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
