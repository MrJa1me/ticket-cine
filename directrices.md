# Directrices del Proyecto Semestral: Arquitectura de Microservicios

Este documento detalla los requisitos obligatorios y técnicos que debe cumplir el proyecto **"ticket-cine"** para asegurar el estándar académico del tercer semestre [1].

## 1. Arquitectura del Sistema
El proyecto debe basarse en una arquitectura de microservicios completamente desacoplada [2].
*   **Mínimo de Microservicios:** Se exigen al menos **10 microservicios** con responsabilidades únicas [2, 3]. 
    *   *Estado actual:* Tu proyecto ya cumple con este mínimo al incluir servicios como `ms-autenticacion`, `ms-reserva`, `ms-pagos`, entre otros [4].
*   **Tecnología:** Uso obligatorio de **Spring Boot** para el desarrollo de cada componente [2, 5].

## 2. Comunicación e Infraestructura
El sistema debe garantizar una interacción fluida y profesional entre sus módulos [6, 7].
*   **Comunicación Híbrida:** 
    *   **Sincrónica:** Uso de **OpenFeign** para llamadas HTTP directas [7, 8].
    *   **Asincrónica:** Uso de **Apache Kafka** para eventos y consistencia eventual [7, 8].
*   **Service Discovery:** Implementación de **Eureka** para la localización dinámica de servicios (ya presente en tu carpeta `/eureka`) [4, 7, 8].
*   **API Gateway:** Establecimiento de un **punto de entrada único** que centralice las solicitudes externas [7, 8].

## 3. Gestión de Datos y Persistencia
Se exige independencia total a nivel de datos [9].
*   **Autonomía de Datos:** Está **prohibido compartir tablas** entre microservicios; cada uno debe tener su propia base de datos (MySQL, PostgreSQL u Oracle) [5, 9].
*   **Sincronización:** Uso de proyecciones sincronizadas mediante Kafka para replicación de datos necesaria entre servicios [9].
*   **Estructura:** Las bases de datos deben estar normalizadas y validadas mediante diagramas relacionales [9].

## 4. Seguridad y Roles de Usuario
Implementación de una capa de seguridad sólida mediante **Spring Security** [10, 11].
*   **Cifrado:** Uso obligatorio de **BCrypt** para contraseñas [10, 11].
*   **Sesiones:** Manejo de autenticación mediante **JSON Web Tokens (JWT)** sin estado [10, 11].
*   **Roles (RBAC):** Definición de **2 a 3 roles** diferenciados (ej: Administrador, Cliente, Operador) con permisos restringidos por endpoints [10-12].

## 5. Funcionalidades y Calidad del Código
El sistema debe resolver un problema real (como la gestión de entradas de cine) con lógica de negocio robusta [2, 13].
*   **Validación:** Uso de **DTOs** y `Spring Boot Starter Validation` (@NotNull, @Valid, etc.) [14, 15].
*   **Consultas:** Empleo de Query Methods y Custom Queries para búsquedas eficientes [14].
*   **Pruebas Unitarias:** Implementación obligatoria de **JUnit 5 y Mockito** para validar componentes backend de forma aislada [15, 16].

## 6. Documentación y Repositorio
*   **Documentación de API:** Generación de manuales interactivos con **SpringDoc/Swagger** [16, 17].
*   **GitHub:** El repositorio es obligatorio y debe mostrar el **aporte individual** de cada integrante (actualmente AlanisVerdugo y MrJa1me) y un historial de cambios progresivo [18-20].
*   **README.md:** El archivo guía en el repositorio debe explicar la instalación y ejecución del proyecto [15].

## 7. Despliegue
*   **Entorno:** Puede ser local mediante **Docker/Docker Compose** o expuesto a internet usando herramientas como **Ngrok** [16, 21].
*   **URL Funcional:** El resultado final debe ser una URL que permita consumir los endpoints desde cualquier ubicación [16].
