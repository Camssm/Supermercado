# Supermercado — Microservicios con Spring Cloud

Aplicación para la gestión de un supermercado (productos, stock, usuarios y ventas),
construida con arquitectura de microservicios y un frontend Angular.

## Tecnologías

- **Backend:** Java 17, Spring Boot 3.3, Spring Cloud (Gateway, Eureka, Config Server), Spring Web (API REST), JWT, Spring Data JPA / Hibernate, RabbitMQ, Swagger / OpenAPI, Spring Boot Admin.
- **Base de datos:** H2 en desarrollo, MySQL 8 en producción.
- **Frontend:** Angular 17.
- **Despliegue:** Docker y Docker Compose.

## Arquitectura

| Servicio | Puerto | Función |
|---|---|---|
| `registry` | 8761 | Eureka Server: registro y descubrimiento de servicios |
| `springconfigserver` | 8888 | Config Server: configuración centralizada |
| `gateway` | 8080 | API Gateway: único punto de entrada, valida el JWT y reenvía a cada servicio |
| `security-service` (`ms-security`) | 8085 | Login y emisión del JWT |
| `Usuario` (`ms-usuario`) | 8083 | Usuarios |
| `Productos` (`ms-productos`) | 8081 | Productos y stock (no hay un microservicio de stock aparte) |
| `Facturacion` (`ms-facturacion`) | 8082 | Facturas y detalle de venta |
| `admin-service` | 9090 | Spring Boot Admin: monitoreo de los servicios |
| RabbitMQ | 5672 / 15672 | Mensajería asincrónica (15672 = panel web) |
| MySQL | 3306 | Base de datos en producción |
| Frontend (repo `Supermercado-Angular`) | 4200 | Aplicación Angular |

### Seguridad

1. El usuario inicia sesión en `POST /auth/login` y recibe un JWT.
2. El gateway valida el token en cada pedido a `/api/productos/**` y `/api/facturacion/**` y agrega el header interno `X-Gateway-Secret`.
3. Cada microservicio solo acepta pedidos que vengan del gateway.

### Comunicación asincrónica (RabbitMQ)

Cuando se registra una venta, **Facturación** guarda la factura y publica un evento en el exchange `facturacion.exchange`
(routing key `facturacion.eventos.routingkey`). **Productos** lo consume desde la cola `facturacion.eventos.queue`
y descuenta el stock de cada producto vendido.

## Roles

| Rol | Qué puede hacer |
|---|---|
| **Visitante** | Ve la galería de productos (pública). |
| **CLIENTE** | Ve la galería, arma el carrito, registra compras y ve sus facturas. |
| **ADMIN** | Gestiona productos y stock (pantalla Gestión) y ve las facturas. |

### Usuario de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `1234` | ADMIN |

El administrador se crea solo al iniciar `ms-usuario`. Se pueden crear más usuarios con `POST /api/usuario/registro`.

## Repositorios

El proyecto está dividido en dos repositorios:

- Backend (este repositorio): https://github.com/Camssm/Supermercado
- Frontend Angular: https://github.com/sabriAla/Supermercado-Angular

Para **ejecutar con Docker alcanza con este repositorio**: el `docker-compose.yml` descarga el frontend desde GitHub al construir la imagen.
Solo para desarrollo con `ng serve` hace falta clonar también el frontend.

## Perfiles y configuración

| Perfil | Configuración | Base de datos | Dónde se usa |
|---|---|---|---|
| `dev` (por defecto) | Config Server (repo Git `config-server-supermercado`) | H2 en memoria | Eclipse y `docker-compose.dev.yml` |
| `prod` | `application.yml` de cada servicio + variables de entorno | MySQL | `docker-compose.yml` / `docker-compose.prod.yml` |

Variables de entorno de producción: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`,
`SPRING_RABBITMQ_HOST`, `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`, `CONFIG_SERVER_URL`, `GATEWAY_INTERNAL_SECRET` y `JWT_SECRET`.
Los dos últimos tienen un valor de desarrollo por defecto en el compose; para cambiarlos, crear un archivo `.env`
junto al `docker-compose.yml` con `JWT_SECRET=...` (Base64, 32 bytes) y `GATEWAY_INTERNAL_SECRET=...`.

## Ejecución en desarrollo (H2)

1. **RabbitMQ** (Docker):

   ```
   docker run -d --name rabbitmq-supermercado -p 5672:5672 -p 15672:15672 rabbitmq:3-management
   ```

   Panel web: `http://localhost:15672` (usuario `guest`, contraseña `guest`).
   Si Docker corre en otra máquina, indicarle a Productos y Facturación dónde está con la variable
   `SPRING_RABBITMQ_HOST=<IP>` (en Eclipse: *Run Configurations → Environment*).
2. Levantar los servicios del backend en este orden:
   1. `registry`
   2. `springconfigserver`
   3. `Usuario`
   4. `security-service`
   5. `Productos` y `Facturacion`
   6. `gateway`
3. Verificar en http://localhost:8761 que todos estén registrados.
4. **Frontend**, dentro de `Supermercado-Angular`:

   ```
   npm install
   ng serve
   ```

   Abrir http://localhost:4200. El frontend pide la API al mismo origen (`/api` y `/auth`) y `ng serve` la reenvía
   al gateway en `http://localhost:8080` mediante `proxy.conf.json`, por eso el gateway tiene que estar levantado.

## Ejecución en producción (Docker + MySQL)

Solo hace falta este repositorio, Docker con Compose v2 e internet en la primera construcción:

```
git clone https://github.com/Camssm/Supermercado.git
cd Supermercado
docker compose up --build -d
```

- Aplicación: `http://localhost:4200` (si Docker corre en otra máquina: `http://<IP-de-esa-máquina>:4200`)
- Gateway (API): `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Eureka: `http://localhost:8761`
- Spring Boot Admin: `http://localhost:9090`
- RabbitMQ: `http://localhost:15672` (usuario `guest`, contraseña `guest`)

Detalles:

- Cada microservicio y el frontend se compilan dentro de Docker (build multi-stage): no hace falta tener Java, Maven ni Node instalados.
- El frontend se descarga del repositorio `Supermercado-Angular` (rama `main`) al construir. Ese repositorio tiene que ser público y tener subidos los cambios de Docker (`nginx.conf`, `proxy.conf.json`, `config.ts`).
- El contenedor del frontend usa **nginx**: sirve la aplicación en el puerto 80 (publicado como 4200) y reenvía `/api` y `/auth` al gateway por la red interna de Docker.
- Los puertos 4200, 8080, 8761, 3306 y 15672 tienen que estar libres. Si hay un RabbitMQ levantado a mano (`docker run`), eliminarlo antes: `docker rm -f rabbitmq-supermercado`.
- La primera construcción tarda varios minutos (descarga imágenes y dependencias de Maven; las siguientes usan caché). Se necesitan unos 6 GB de RAM libres.
- Esperar unos 30-60 segundos después de `up` a que los servicios se registren en Eureka antes de iniciar sesión.
- Ver el estado: `docker compose ps`. Ver logs de un servicio: `docker compose logs -f gateway`. Detener todo: `docker compose down` (con `-v` también borra la base de datos).

## Ejecución en Docker con H2 (desarrollo)

Igual que producción pero sin MySQL: los servicios usan el perfil `dev` con H2 y reciben la configuración del Config Server
(que necesita internet para leer el repo Git). Los datos se pierden al reiniciar.

```
docker compose -f docker-compose.dev.yml up --build -d
```

## Cómo probar RabbitMQ

1. Iniciar sesión (desde el frontend o con `POST /auth/login`):
   `{"nombreCompleto":"admin","password":"1234"}` → devuelve el token. Usarlo como `Authorization: Bearer <token>`.
2. Crear un producto con stock 10 (`PUT /api/productos/agregar`):
   `{"nombre":"Leche","categoria":"LACTEOS","precioUnitario":1200,"stock":10}`
3. Registrar una venta de 3 unidades (`PUT /api/facturacion/agregar`):
   `{"detalles":[{"productoId":1,"cantidad":3}]}`
4. Consultar el producto (`GET /api/productos/1`): el stock pasó de 10 a 7. En la consola de Productos aparece
   "Factura ... procesada. Stock actualizado."
5. En http://localhost:15672, pestaña **Queues**, se ve la cola `facturacion.eventos.queue` y los mensajes procesados.

## Estructura del backend

```
Supermercado/
├── Facturacion/           ms de facturas
├── Productos/             ms de productos y stock
├── Usuario/               ms de usuarios
├── security-service/      ms de login y JWT
├── gateway/               API Gateway
├── registry/              Eureka Server
├── springconfigserver/    Config Server
├── admin-service/         Spring Boot Admin
├── docker-compose.yml     producción (MySQL)
├── docker-compose.prod.yml
└── docker-compose.dev.yml desarrollo (H2)
```
