# ---------- STAGE 1: Build ----------
FROM maven:3.8.8-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar backend completo
COPY pqrs_back/ .

# Compilar sin tests
RUN mvn -B -DskipTests package


# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar el jar construido
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
