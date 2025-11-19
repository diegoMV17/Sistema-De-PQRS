########## STAGE 1: Build Angular ##########
FROM node:18 AS frontend-builder

WORKDIR /app

# Copiar proyecto Angular
COPY pqrs_front/ /app/

# Instalar dependencias
RUN npm install

# Compilar Angular
RUN npm run build --prod


########## STAGE 2: Build Spring Boot ##########
FROM maven:3.8.8-eclipse-temurin-17 AS backend-builder

WORKDIR /build

# Copiar backend
COPY pqrs_back/ /build/

# Copiar Angular dist al backend (carpeta static)
COPY --from=frontend-builder /app/dist/* /build/src/main/resources/static/

# Build backend
RUN mvn -B -DskipTests package


########## STAGE 3: Runtime ##########
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar jar construido
COPY --from=backend-builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
