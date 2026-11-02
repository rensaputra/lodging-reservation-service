# Lodging Reservation Service

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A lightweight enterprise lodging backend service responsible for managing room inventory, guest profiles, and reservation scheduling. Built with Spring Boot 3, Spring Data JPA, and designed for containerized deployment.

---

## Architecture Overview

The service follows a layered architecture with clear separation of concerns across data persistence, domain logic, and presentation/API layers:

- **Presentation / API Tier:** Exposes RESTful endpoints (`/api/v1/*`) returning JSON payloads alongside server-side rendered operations dashboards.
- **Service Tier:** Orchestrates reservation validation, room allocation business rules, and guest profile synchronization.
- **Persistence Tier:** Spring Data JPA abstractions supporting both an in-memory H2 profile for rapid local validation and PostgreSQL for production environments.

### Tech Stack

- **Runtime:** Java 17 (or Java 21 LTS)
- **Framework:** Spring Boot 3.x, Spring Framework 6.x
- **Data Access:** Spring Data JPA / Hibernate (Jakarta EE 10 namespace)
- **Database:** PostgreSQL (production profile), H2 Database (local/test profile)
- **Build Tool:** Maven

---

## Domain Model

```
+---------------+           +--------------------+           +---------------+
|     Room      |           |    Reservation     |           |     Guest     |
+---------------+           +--------------------+           +---------------+
| id (PK)       | 1       * | id (PK)            | *       1 | id (PK)       |
| room_number   |<--------->| room_id (FK)       |<--------->| first_name    |
| name          |           | guest_id (FK)      |           | last_name     |
| bed_info      |           | reservation_date   |           | email_address |
+---------------+           +--------------------+           | phone_number  |
                                                             +---------------+
```

---

## Getting Started

### Prerequisites

- **JDK 17** or later
- **Maven 3.8+**
- **Docker & Docker Compose** (optional, for running local PostgreSQL)

### Local Development (H2 In-Memory)

By default, the service boots with an embedded H2 database pre-seeded with sample inventory:

```bash
# Clone the repository
git clone https://github.com/<your-username>/lodging-reservation-service.git
cd lodging-reservation-service

# Build and run the service
./mvnw spring-boot:run
```

Once running:
- **Base URL:** `http://localhost:8080`
- **H2 Console:** `http://localhost:8080/h2-console` *(JDBC URL: `jdbc:h2:mem:lodgingdb`)*

### Production Profile (PostgreSQL)

To run against a local PostgreSQL instance via Docker Compose:

1. **Spin up the database:**
   ```bash
   docker compose up -d postgres
   ```

2. **Launch with the `postgres` profile active:**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
   ```

---

## API Reference

### Rooms

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/rooms` | Retrieve all room records and layout details |
| `GET` | `/api/v1/rooms/{id}` | Retrieve room details by identifier |

### Guests

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/guests` | Retrieve directory of registered guests |
| `POST` | `/api/v1/guests` | Register a new guest profile |
| `GET` | `/api/v1/guests/{id}` | Retrieve specific guest profile |

### Reservations

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/reservations` | List reservations (filterable by date: `?date=YYYY-MM-DD`) |
| `POST` | `/api/v1/reservations` | Create a new room booking |

#### Sample Request: Create Reservation

```http
POST /api/v1/reservations HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "roomId": 1,
  "guestId": 42,
  "reservationDate": "2026-12-15"
}
```

---

## Configuration

Core application settings are configured in `src/main/resources/application.yml`:

| Property | Default | Description |
| :--- | :--- | :--- |
| `server.port` | `8080` | HTTP listener port |
| `spring.datasource.url` | `jdbc:h2:mem:lodgingdb` | Database connection string |
| `spring.jpa.hibernate.ddl-auto` | `update` | Schema generation strategy |
| `spring.jpa.open-in-view` | `false` | Explicitly disables Open Session In View |

---

## Testing & Quality

Run unit and slice integration tests:

```bash
# Run unit and repository slice tests
./mvnw clean test

# Run packaged integration suite
./mvnw clean verify
```

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.