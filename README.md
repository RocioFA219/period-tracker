### 🛠 Estado del Proyecto: En Desarrollo Activo
Aviso Técnico: Este sistema se encuentra actualmente en fase de construcción. La arquitectura base está consolidada, pero se están integrando módulos de análisis avanzado y notificaciones asíncronas.
#### Roadmap de Desarrollo
Para que cualquier colaborador o reclutador entienda en qué punto estás, utilizaremos una lista de control de hitos:

[x] Fase 1: Infraestructura Base (Docker, Bases de Datos, Keycloak).

[x] Fase 2: Core Reactivo (WebFlux, R2DBC, Routers/Handlers).

[x] Fase 3: Seguridad (Integración con OAuth2/JWT y externalización de secretos).

[ ] Fase 4: Comunicación por Eventos (Implementación completa de Kafka Producers/Consumers).

[ ] Fase 5: Lógica de Predicción (Algoritmos de cálculo de ciclo en el Cycle Service).

[ ] Fase 6: Resiliencia (Implementación de Patrón Circuit Breaker con Resilience4j).

# Period Tracker System: Arquitectura Reactiva de Microservicios
Este ecosistema representa una solución avanzada de misión crítica para el seguimiento del ciclo menstrual, diseñado bajo los principios del Manifiesto Reactivo. Utiliza una arquitectura orientada a eventos para garantizar la resiliencia, la escalabilidad y el procesamiento no bloqueante.

## 1. Arquitectura del Sistema
El sistema se divide en dos dominios de negocio claramente desacoplados, comunicados de forma asíncrona mediante Apache Kafka.
┌─────────────────────┐      ┌─────────────────────┐
│   React Frontend    │      │   Tracker Service   │
│   (Vite + React)    │─────▶│     (Port 8080)     │
│   Port 5173         │      │   User Management   │
└─────────────────────┘      │  PostgreSQL + R2DBC │
                             └──────────┬──────────┘
                                        │
           Kafka Event Stream ──────────▼──────────
                               ┌─────────────────────┐
                               │    Cycle Service    │
                               │     (Port 8081)     │
                               │     Cycle Logic     │
                               │   MongoDB Reactive  │
                               └─────────────────────┘

## 2. Stack Tecnológico
### Backend (Java Ecosystem)
Java 17 LTS: Aprovechando Records y Sealed Classes para un dominio más seguro.

Spring Boot 3.5.x: El estándar para microservicios.

Spring WebFlux: Stack 100% reactivo y no bloqueante sobre Netty.

Spring Security + OAuth2 (JWT): Delegación de identidad robusta.

Apache Kafka: Bus de eventos para comunicación inter-service.

### Persistencia de Datos
PostgreSQL 16 (R2DBC): Driver reactivo para evitar el bloqueo de hilos en el manejo de usuarios.

MongoDB Reactive: Almacenamiento flexible para la variabilidad de los ciclos.

## 3. Configuración de Seguridad y Entorno
Nota de Mentoría: Este sistema utiliza configuración externalizada. Nunca se deben incluir credenciales en el código fuente.
El sistema requiere un archivo .env en la raíz (asegúrate de que esté en tu .gitignore):
# Database Credentials
DB_PASSWORD=your_secure_password
MONGO_ROOT_PASSWORD=your_mongo_password
# Keycloak Secrets
KEYCLOAK_CLIENT_SECRET=your_client_secret_from_console
