# syntax=docker/dockerfile:1
# Un solo Dockerfile para todos los modulos. Se usa con: --build-arg MODULE=<carpeta>
# El contexto de build es la RAIZ del repo (los modulos heredan del pom.xml de la raiz).

# ---------- Etapa 1: compilar con Maven ----------
FROM maven:3.9-eclipse-temurin-17 AS build
ARG MODULE
WORKDIR /build
COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -q -pl ${MODULE} -am package -DskipTests

# ---------- Etapa 2: imagen liviana para ejecutar ----------
FROM eclipse-temurin:17-jre
ARG MODULE
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /build/${MODULE}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
