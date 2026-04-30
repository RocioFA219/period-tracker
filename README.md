Period Tracker System
A reactive microservices-based menstrual cycle tracking application that allows users to register, log their cycles, and receive predictions for their next period.
Architecture
┌─────────────────────┐       ┌─────────────────────┐
│  React Frontend     │──────▶│  Tracker Service     │
│  (Vite + React)     │       │  (Port 8080)         │
│  Port 5173          │       │  User Management     │
│                     │       │  PostgreSQL + R2DBC  │
└─────────────────────┘       └──────────┬──────────┘
                                         │ Kafka
                                         ▼
                                ┌─────────────────────┐
                                │  Cycle Service       │
                                │  (Port 8081)         │
                                │  Cycle Logic         │
                                │  MongoDB Reactive    │
                                └─────────────────────┘
        ┌─────────────────────┐       ┌─────────────────────┐
        │  Keycloak           │       │  Kafka + Zookeeper  │
        │  (Port 8085)        │       │  (Port 9092)        │
        │  OAuth2 / OIDC      │       │  Event Streaming    │
        └─────────────────────┘       └─────────────────────┘
Tech Stack
Backend
- Java 17 with Spring Boot 3.5.x
- Spring WebFlux (reactive, non-blocking)
- Spring Security with OAuth2 Resource Server (JWT)
- Apache Kafka for inter-service messaging
Frontend
- React 19 with Vite
- keycloak-js for authentication
- react-calendar for cycle visualization
- Lucide React for icons
- Axios for HTTP requests
Infrastructure
- PostgreSQL 16 with R2DBC
- MongoDB (reactive)
- Keycloak 24 (IAM / SSO)
- Apache Kafka with Zookeeper
- Docker Compose
Project Structure
period-tracker-system/
├── docker-compose.yml          # Infrastructure services
├── .env                        # Environment variables
├── tracker/                    # User management microservice
│   └── src/main/java/...
│       ├── config/             # Security, CORS, Keycloak
│       ├── domain/             # DTOs, models, exceptions, service
│       ├── router/             # WebFlux routes
│       ├── respository/        # R2DBC repositories
│       └── producer/           # Kafka event producer
├── cycle-service/              # Cycle tracking microservice
│   └── src/main/java/...
│       ├── domain/             # Models, DTOs, service, repository
│       ├── infrastructure/     # Config, routes, handlers, Kafka consumer
└── cycle-tracker-front/        # React frontend
    └── src/
        ├── App.jsx             # Main app + dashboard
        ├── keycloak.js         # Keycloak configuration
        ├── components/         # RegisterForm, etc.
        └── services/           # API service clients
Features
- User Registration & Login via Keycloak (OAuth2/OIDC)
- Cycle Registration - Log period start dates
- Cycle Tracking - View current cycle day and status
- Predictions - Automatic next-period prediction based on average cycle length
- Calendar View - Visual calendar highlighting active period days
- Reactive Architecture - Non-blocking I/O across all services
Getting Started
Prerequisites
- Docker and Docker Compose
- Java 17
- Maven
- Node.js 18+
1. Start Infrastructure
docker-compose up -d
Starts PostgreSQL (5432), MongoDB (27017), Kafka (9092), Keycloak (8085)
2. Run Backend Services
cd tracker && mvn spring-boot:run       # Port 8080
cd cycle-service && mvn spring-boot:run # Port 8081
3. Run Frontend
cd cycle-tracker-front
npm install
npm run dev  # Port 5173
4. Keycloak Setup
1. Access admin console at http://localhost:8085
2. Create realm: period-tracker
3. Create client: tracker-app
API Endpoints
Tracker Service (Port 8080)
Method	Endpoint	Description
POST	/api/users	Register a new user
GET	/api/users/email/{email}	Find user by email
Cycle Service (Port 8081)
Method	Endpoint	Description
GET	/api/cycles/{userId}	Get all cycles for a user
POST	/api/cycles/register	Register a new period
Event Flow
1. User registers → Tracker Service
2. Tracker creates user in PostgreSQL + Keycloak
3. Tracker publishes UserCreated event to Kafka
4. Cycle Service consumes event and initializes cycle tracking in MongoDB
