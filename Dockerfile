# ---------- STAGE 1: build ----------
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# set workdir
WORKDIR /build

# Copy only what is necessary to leverage layer caching
COPY pqrs_back/pom.xml pqrs_back/mvnw pqrs_back/.mvn /build/pqrs_back/
# if there are parent poms or modules you can copy them too (adjust if needed)
# Copy the rest of backend and frontend
COPY pqrs_back /build/pqrs_back
COPY pqrs_front/src /build/pqrs_front_src

# Make mvnw executable if present
RUN if [ -f /build/pqrs_back/mvnw ]; then chmod +x /build/pqrs_back/mvnw; fi

# Move frontend static files into backend resources so Spring Boot will serve them.
# (This step assumes the Spring Boot app will pick static files from src/main/resources/static)
RUN rm -rf /build/pqrs_back/src/main/resources/static || true
RUN mkdir -p /build/pqrs_back/src/main/resources/static
RUN cp -R /build/pqrs_front_src/* /build/pqrs_back/src/main/resources/static/ || true

# Build the backend (skip tests for faster builds; remove -DskipTests for CI)
WORKDIR /build/pqrs_back
# Use the wrapper if exists, otherwise use mvn installed in image
RUN if [ -f ./mvnw ]; then ./mvnw -B -DskipTests package; else mvn -B -DskipTests package; fi

# ---------- STAGE 2: runtime ----------
FROM eclipse-temurin:17-jdk-jammy

# Create non-root user (optional but recommended)
RUN useradd -m appuser || true
USER appuser
WORKDIR /home/appuser

# Copy JAR from builder. Use wildcard to handle generated jar name.
COPY --from=builder /build/pqrs_back/target/*.jar app.jar

# Expose port used by Spring Boot (adjust if your app uses another port)
EXPOSE 8080

# Set sensible default env vars (you can override at runtime)
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
