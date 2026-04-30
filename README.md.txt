# 🩸 Period Tracker - Microservices System

## 📋 Descripción
Sistema de gestión de salud basado en una **Arquitectura de Microservicios Reactivos**. Este proyecto implementa un ecosistema seguro y escalable utilizando el stack tecnológico de Spring.

## 🛠️ Stack Tecnológico
*   **Backend:** Java 17, Spring Boot 3.5.x, WebFlux.
*   **Persistencia:** PostgreSQL (R2DBC), MongoDB Reactive.
*   **Seguridad:** OAuth2 Resource Server con **Keycloak**.
*   **Mensajería:** Apache Kafka.

## 🏗️ Estructura del Proyecto
*   `/tracker`: Gestión de usuarios y perfiles (PostgreSQL).
*   `/cycle-service`: Lógica de cálculo y predicción (MongoDB).