# Sistema de Gestión de Academia — Arquitectura de Microservicios

## Descripción del proyecto

Este proyecto consiste en el desarrollo de un sistema backend para una academia que necesita organizar la información de sus docentes, cursos e inscripciones.

La aplicación utiliza una **arquitectura de microservicios con Spring Boot**, donde cada servicio tiene una responsabilidad específica y administra sus propios datos. Esto permite mantener el sistema organizado, facilitar su mantenimiento y desarrollar nuevas funcionalidades sin afectar directamente a los demás servicios.

Para la comunicación y administración de la arquitectura se utilizan **Eureka Server**, **Spring Cloud Config Server** y **API Gateway**, junto con archivos YAML para centralizar las configuraciones.

## Objetivo

Desarrollar un sistema de gestión académica que permita registrar y administrar docentes, cursos e inscripciones mediante servicios REST independientes, incorporando validaciones de datos, persistencia en MySQL y comunicación entre microservicios.

## Tecnologías utilizadas

| Tecnología | Descripción |
|---|---|
| Java 17 | Lenguaje principal de desarrollo |
| Spring Boot 4.1.2 | Framework utilizado para construir los microservicios |
| Spring Cloud 2025.1.4 | Herramientas para la arquitectura distribuida |
| Eureka Server | Registro y descubrimiento de microservicios |
| Spring Cloud Config | Administración centralizada de configuraciones |
| Spring Cloud Gateway WebFlux | Punto de entrada para las peticiones HTTP |
| Spring Cloud OpenFeign | Comunicación HTTP entre microservicios |
| Spring Data JPA | Persistencia y operaciones sobre la base de datos |
| Hibernate | Mapeo de entidades Java a tablas relacionales |
| Spring Validation | Validación de los datos recibidos |
| MySQL / MariaDB (XAMPP) | Almacenamiento de la información |
| SQLyog | Administración de las bases de datos |
| Maven | Gestión de dependencias y ejecución |
| Postman | Pruebas de los endpoints REST |

## Arquitectura del sistema

El proyecto está dividido en seis aplicaciones independientes:

| Componente | Puerto | Responsabilidad |
|---|---|---|
| eureka-server | 8761 | Registrar y descubrir los microservicios |
| config-server | 8888 | Distribuir las configuraciones YAML |
| api-gateway | 8080 | Centralizar y redirigir las solicitudes HTTP |
| docentes-service | 8081 | Gestionar la información de los docentes |
| cursos-service | 8082 | Administrar los cursos y sus docentes |
| inscripciones-service | 8083 | Gestionar las inscripciones de los participantes |

### Funcionamiento de la arquitectura

El cliente realiza sus solicitudes a través de **API Gateway**, que identifica el microservicio correspondiente y redirige la petición.

Eureka Server permite localizar los servicios registrados, mientras que Config Server proporciona sus configuraciones mediante archivos YAML.

Los microservicios de negocio se comunican mediante OpenFeign cuando necesitan verificar información de otro servicio.

Por ejemplo:

1. Se registra un docente mediante `docentes-service`.
2. Al registrar un curso, `cursos-service` verifica que el docente exista.
3. Al registrar una inscripción, `inscripciones-service` verifica que el curso esté registrado.
4. Cada microservicio almacena su información en una base de datos independiente.

## Estructura del proyecto

```text
academia-microservicios/
│
├── eureka-server/
├── config-server/
├── api-gateway/
├── docentes-service/
├── cursos-service/
├── inscripciones-service/
│
└── config-repo/
    ├── docentes-service.yml
    ├── cursos-service.yml
    ├── inscripciones-service.yml
    └── api-gateway.yml
```

Cada microservicio cuenta con sus propias dependencias, configuración y clases Java.

Los microservicios de negocio están organizados principalmente en las capas `controller`, `service`, `repository`, `entity` y `dto`. Los servicios que se comunican con otros microservicios también incluyen la capa `client`.

## Funcionalidades implementadas

### Gestión de docentes

Permite registrar, consultar, actualizar y eliminar docentes.

Cada docente tiene un identificador único, nombres, apellidos, correo electrónico y especialidad.

Se validan los campos obligatorios, el formato del correo y la existencia de correos duplicados.

### Gestión de cursos

Permite registrar, consultar, actualizar y eliminar cursos.

Cada curso almacena su nombre, duración en horas, precio e identificador del docente encargado.

Se aplican validaciones para evitar nombres vacíos, duraciones no positivas y precios inválidos.

Además, mediante OpenFeign, se verifica que el docente asignado exista antes de registrar o actualizar un curso.

### Gestión de inscripciones

Permite administrar las inscripciones de los participantes a los cursos.

Cada inscripción registra el curso seleccionado, nombres, apellidos, correo, teléfono, fecha y estado.

La fecha se genera automáticamente al registrar la inscripción y su estado inicial es `PENDIENTE`.

Los estados disponibles son:

- **PENDIENTE:** inscripción registrada y pendiente de confirmación.
- **CONFIRMADA:** inscripción aceptada.
- **CANCELADA:** inscripción cancelada.

Antes de registrar una inscripción, el sistema consulta a `cursos-service` para verificar la existencia del curso.

## Base de datos

El sistema utiliza tres bases de datos independientes en MySQL:

| Base de datos | Microservicio |
|---|---|
| academia_docentes | docentes-service |
| academia_cursos | cursos-service |
| academia_inscripciones | inscripciones-service |

Para crearlas, ejecutar desde SQLyog:

```sql
CREATE DATABASE IF NOT EXISTS academia_docentes;
CREATE DATABASE IF NOT EXISTS academia_cursos;
CREATE DATABASE IF NOT EXISTS academia_inscripciones;
```

Las tablas son administradas mediante Spring Data JPA e Hibernate durante el desarrollo.

Se utiliza `ddl-auto: update` en el entorno local para crear o actualizar las tablas a partir de las entidades Java.

## Configuración distribuida

Las configuraciones de los microservicios se administran mediante Spring Cloud Config Server.

Para el entorno de desarrollo se utiliza el perfil `native`, que permite leer archivos YAML almacenados en la carpeta `config-repo`.

Cada microservicio obtiene sus propiedades desde Config Server, como el puerto, la conexión a MySQL y la dirección de Eureka Server.

La configuración local de un cliente tiene la siguiente estructura:

```yaml
spring:
  application:
    name: docentes-service

  config:
    import: "configserver:http://localhost:8888"
```

De esta manera se evita repetir las configuraciones principales dentro de cada aplicación.

## Requisitos para ejecutar el proyecto

Antes de iniciar el sistema, se necesita contar con:

- JDK 17 instalado.
- Maven Wrapper incluido en cada proyecto.
- XAMPP con MySQL/MariaDB habilitado.
- Las tres bases de datos creadas.
- Los puertos 8080, 8081, 8082, 8083, 8761 y 8888 disponibles.

En el entorno local de desarrollo se utiliza MySQL en el puerto `3306`, con el usuario `root` y contraseña vacía, de acuerdo con la configuración de XAMPP.

Esta configuración es únicamente para pruebas locales. En otros entornos se deben utilizar credenciales seguras.

## Ejecución del proyecto

Para ejecutar correctamente el sistema es necesario iniciar los componentes en el siguiente orden:

1. Iniciar MySQL desde XAMPP.
2. Ejecutar `eureka-server`.
3. Ejecutar `config-server`.
4. Ejecutar `docentes-service`.
5. Ejecutar `cursos-service`.
6. Ejecutar `inscripciones-service`.
7. Ejecutar `api-gateway`.

Desde la carpeta de cada proyecto, utilizar:

```bash
./mvnw spring-boot:run
```

En Windows PowerShell se puede utilizar:

```powershell
.\mvnw.cmd spring-boot:run
```

Se recomienda mantener una terminal independiente para cada componente.

Una vez iniciados, acceder al panel de Eureka:

`http://localhost:8761`

Allí se pueden verificar las instancias registradas y su estado.

## Endpoints REST

Todas las peticiones de los microservicios de negocio pueden realizarse a través de API Gateway utilizando la dirección base:

`http://localhost:8080`

### Docentes

| Método | Endpoint | Descripción |
|---|---|---|
| POST | /api/docentes | Registrar docente |
| GET | /api/docentes | Listar docentes |
| GET | /api/docentes/{id} | Buscar docente |
| PUT | /api/docentes/{id} | Actualizar docente |
| DELETE | /api/docentes/{id} | Eliminar docente |

### Cursos

| Método | Endpoint | Descripción |
|---|---|---|
| POST | /api/cursos | Registrar curso |
| GET | /api/cursos | Listar cursos |
| GET | /api/cursos/{id} | Buscar curso |
| PUT | /api/cursos/{id} | Actualizar curso |
| DELETE | /api/cursos/{id} | Eliminar curso |

### Inscripciones

| Método | Endpoint | Descripción |
|---|---|---|
| POST | /api/inscripciones | Registrar inscripción |
| GET | /api/inscripciones | Listar inscripciones |
| GET | /api/inscripciones/{id} | Buscar inscripción |
| PUT | /api/inscripciones/{id} | Actualizar inscripción |
| PATCH | /api/inscripciones/{id}/estado | Cambiar estado |
| DELETE | /api/inscripciones/{id} | Eliminar inscripción |