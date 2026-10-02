# Sistema de Gestión Escolar - Arquitectura de Microservicios

## Descripción

Aplicación desarrollada para la gestión de una institución educativa,
implementada utilizando una arquitectura basada en microservicios.

El sistema permite gestionar:

- Alumnos
- Cursos
- Docentes

Cada dominio posee su propio microservicio y API REST.

La arquitectura incorpora un API Gateway para centralizar el acceso,
Eureka Server para el registro y descubrimiento de servicios y
Spring Cloud Config Server para la configuración centralizada.

Los microservicios se registran en Eureka Server :8761.

El Config Server :8888 proporciona la configuración centralizada.

## Docker

El proyecto incluye Dockerfiles para los microservicios y un
docker-compose.yml para ejecutar la arquitectura completa.

Para construir y levantar todos los servicios:

docker compose up -d --build

Para comprobar los contenedores:

docker ps

Para detenerlos:

docker compose down
