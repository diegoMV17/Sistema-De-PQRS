# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom primero para aprovechar cache de dependencias
COPY pom.xml ./
RUN mvn -B -DskipTests --no-transfer-progress dependency:go-offline

# Copiar el código fuente y empaquetar
COPY src ./src
RUN mvn -B -DskipTests --no-transfer-progress package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Opcional: define ajustes de JVM por defecto (puedes sobrescribir con env vars)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Copiar el artefacto desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]