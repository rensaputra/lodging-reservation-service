# Lodging Reservation Service

[![Java 17](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A lightweight enterprise lodging backend service responsible for managing room inventory, guest profiles, and reservation scheduling. Built with Spring Boot 3 and Spring Data JPA, it exposes both RESTful JSON endpoints and server-side rendered web pages (Thymeleaf).

---

## Architecture Overview

The service follows a layered architecture with clear separation of concerns:

- **Web Tier:** Two kinds of entry point live under `web/`. REST controllers in `web/api` return JSON under `/api/*`; MVC controllers in `web/controller` render Thymeleaf pages for the operations dashboard.
- **Service Tier:** Orchestrates room-reservation aggregation and business logic (`service/`).
- **Persistence Tier:** Spring Data JPA entities and repositories (`data/`), backed by PostgreSQL.

### Tech Stack

- **Runtime:** Java 17
- **Framework:** Spring Boot 3.x, Spring Framework 6.x
- **Web Pages:** Thymeleaf (server-side rendering)
- **Data Access:** Spring Data JPA / Hibernate (Jakarta EE 10 namespace)
- **Database:** PostgreSQL
- **Build Tool:** Maven

---

## Project Structure

```
lodging-reservation-service/
├── db/                                  # Local PostgreSQL via Docker Compose (schema + seed data)
│   ├── docker-compose.yml
│   ├── schema.sql
│   ├── data.sql
│   └── README.md
├── src/main/java/.../lodging_reservation_service/
│   ├── LodgingReservationServiceApplication.java
│   ├── data/
│   │   ├── entity/                      # JPA entities: Room, Guest, Reservation
│   │   └── repository/                  # Spring Data repositories
│   ├── service/                         # Business logic (RoomReservationService + models)
│   └── web/
│       ├── api/                         # REST controllers (JSON, under /api/*)
│       │   ├── RoomApiController.java
│       │   ├── GuestApiController.java
│       │   └── ReservationApiController.java
│       ├── controller/                  # MVC controllers rendering Thymeleaf pages
│       │   ├── RoomController.java
│       │   ├── OccupancyController.java
│       │   └── WelcomeController.java
│       └── exception/                   # BadRequestException, NotFoundException
└── src/main/resources/
    ├── application.properties
    ├── static/index.html                # Landing page
    └── templates/                       # Thymeleaf templates (rooms.html, occupancy.html)
```

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

- **JDK 17** (as specified in `pom.xml`)
- **Maven 3.8+**
- **Docker & Docker Compose** (for the local PostgreSQL database)

### Running Locally

This replicates what IntelliJ does when you press the play button.

**1. Start the database**

The `db/` directory provides a Docker Compose setup that runs PostgreSQL and applies `schema.sql` then `data.sql` on first startup. See [db/README.md](db/README.md) for details.

```bash
cd db
cp .env.example .env    # adjust values if needed (first time only)
docker compose up -d
cd ..
```

**2. Verify Java version**

The project requires JDK 17:

```bash
java -version
```

If you see a different version, set `JAVA_HOME` to point at your JDK 17 installation before proceeding.

**3. Run the service**

```bash
mvn spring-boot:run
```

Alternatively, build a jar and run it:

```bash
mvn clean package
java -jar target/lodging-reservation-service-0.0.1-SNAPSHOT.jar
```

Once running:

- **Base URL:** `http://localhost:8080`
- **Landing page:** `http://localhost:8080/` (`static/index.html`)
- **Web pages:** `/rooms`, `/occupancy`, `/welcome`
- **API endpoints:** `/api/rooms`, `/api/guests`, `/api/reservations`

---

## Web Pages (Thymeleaf)

MVC controllers in `web/controller` render server-side pages:

| Path | Template | Description |
| :--- | :--- | :--- |
| `GET /rooms` | `rooms.html` | Room inventory page |
| `GET /occupancy` | `occupancy.html` | Occupancy / reservations dashboard |
| `GET /welcome` | — | Inline HTML greeting |

---

## API Reference

REST controllers in `web/api` return JSON. All three resources support full CRUD.

### Rooms — `/api/rooms`

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/rooms` | List all rooms |
| `POST` | `/api/rooms` | Create a room |
| `GET` | `/api/rooms/{id}` | Retrieve a room by id |
| `PUT` | `/api/rooms/{id}` | Update a room |
| `DELETE` | `/api/rooms/{id}` | Delete a room |

### Guests — `/api/guests`

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/guests` | List all guests |
| `POST` | `/api/guests` | Register a guest |
| `GET` | `/api/guests/{id}` | Retrieve a guest by id |
| `PUT` | `/api/guests/{id}` | Update a guest |
| `DELETE` | `/api/guests/{id}` | Delete a guest |

### Reservations — `/api/reservations`

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/reservations` | List reservations (filter by date: `?date=YYYY-MM-DD`) |
| `POST` | `/api/reservations` | Create a reservation |
| `GET` | `/api/reservations/{id}` | Retrieve a reservation by id |
| `PUT` | `/api/reservations/{id}` | Update a reservation |
| `DELETE` | `/api/reservations/{id}` | Delete a reservation |

#### Sample Request: Create Reservation

```http
POST /api/reservations HTTP/1.1
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

Application settings live in `src/main/resources/application.properties`:

| Property | Default | Description |
| :--- | :--- | :--- |
| `spring.application.name` | `lodging-reservation-service` | Application name |
| `spring.jpa.database` | `postgresql` | JPA database dialect |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/myapp` | Database connection string |
| `spring.datasource.username` | `appuser` | Database user |
| `spring.datasource.password` | `apppassword` | Database password |

---

## Testing & Quality

```bash
# Run unit and slice tests
./mvnw clean test

# Run the packaged integration suite
./mvnw clean verify
```

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
