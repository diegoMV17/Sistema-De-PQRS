########## STAGE 1: Build Angular ##########
FROM node:18 AS frontend-builder

WORKDIR /app

# Copiar solo package.json primero (mejor cache)
COPY pqrs_front/package*.json ./

RUN npm install

# Copiar el resto del frontend
COPY pqrs_front/ ./

RUN npm run build --prod


########## STAGE 2: Build Spring Boot ##########
FROM maven:3.8.8-eclipse-temurin-17 AS backend-builder

WORKDIR /build

COPY pqrs_back/ ./

# Copiar Angular dist
COPY --from=frontend-builder /app/dist/* /build/src/main/resources/static/

RUN mvn -B -DskipTests package


########## STAGE 3: Runtime ##########
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=backend-builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
