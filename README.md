# Tournament Manager API

A RESTful API for managing virtual sports tournaments with JWT authentication and role-based access control, built with Java and Spring Boot.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MariaDB
- Docker & Docker Compose
- Maven
- Lombok
- Swagger UI

## Getting Started

### Prerequisites

- Docker
- Docker Compose

### Run with Docker

```bash
git clone https://github.com/your-username/tournament-manager.git
cd tournament-manager
./mvnw clean package -DskipTests
docker compose up --build
```

Application will be available at `http://localhost:8081`

API Documentation (Swagger UI): `http://localhost:8081/swagger-ui/index.html`

## Authentication

The API uses JWT token authentication. Add the token to the Authorization header as shown below.

Example header:

    Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...

### Register

```json
POST /auth/register
{
    "ime": "John",
    "prezime": "Doe",
    "email": "john@mail.com",
    "lozinka": "john123"
}
```

### Login

```json
POST /auth/login
{
    "email": "john@mail.com",
    "lozinka": "john123"
}
```

## Roles

| Role | Permissions |
|------|-------------|
| USER | GET endpoints only |
| ADMIN | Full access (GET, POST, PUT, DELETE) |

## API Endpoints

### Auth

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /auth/register | Register new user | Public |
| POST | /auth/login | Login and get JWT token | Public |

### Tournaments

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/turniri | Get all tournaments | USER, ADMIN |
| GET | /api/turniri/{id} | Get tournament by ID | USER, ADMIN |
| GET | /api/turniri?status=UPCOMING | Filter by status | USER, ADMIN |
| GET | /api/turniri?sport=FOOTBALL | Filter by sport | USER, ADMIN |
| POST | /api/turniri | Create tournament | ADMIN |
| PUT | /api/turniri/{id} | Update tournament | ADMIN |
| DELETE | /api/turniri/{id} | Delete tournament | ADMIN |

### Players

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/turniri/{id}/igraci | Get players in tournament | USER, ADMIN |
| GET | /api/turniri/{id}/rang-lista | Get tournament ranking | USER, ADMIN |
| POST | /api/turniri/{id}/igraci | Add player to tournament | ADMIN |
| DELETE | /api/turniri/igraci/{id} | Delete player | ADMIN |

### Results

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/turniri/{id}/rezultati | Get tournament results | USER, ADMIN |
| POST | /api/turniri/{id}/rezultati | Add result | ADMIN |

## Tournament Status

| Status | Description |
|--------|-------------|
| UPCOMING | Tournament has not started yet |
| ACTIVE | Tournament is in progress |
| FINISHED | Tournament has ended |

## Available Sports

`FOOTBALL` `BASKETBALL` `TENNIS` `BASEBALL` `HOCKEY`

## Project Structure

| Package | Description |
|---------|-------------|
| controller/ | REST endpoints |
| service/ | Business logic |
| repository/ | Database communication |
| model/ | JPA entities and enums |
| security/ | JWT filter, service and Spring Security config |
| dto/ | Data Transfer Objects |
| exception/ | Custom exceptions and global handler |