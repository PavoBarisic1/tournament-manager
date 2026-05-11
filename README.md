# Tournament Manager

A full-stack application for managing virtual sports tournaments with JWT authentication and role-based access control.

## Tech Stack

### Backend
- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MariaDB
- Docker & Docker Compose
- Maven
- Lombok
- Swagger UI

### Frontend
- Vue.js 3
- Vue Router
- Vite

## Features

- User registration and login with JWT authentication
- Role-based access control (ADMIN and USER)
- Create and manage tournaments
- Add players to tournaments
- Record match results
- View player rankings per tournament
- Filter tournaments by sport and status

## Getting Started

### Prerequisites

- Docker
- Docker Compose
- Node.js
- npm

### Run Backend with Docker

```bash
git clone https://github.com/PavoBarisic/tournament-manager.git
cd tournament-manager
./mvnw clean package -DskipTests
docker compose up --build
```

Backend available at `http://localhost:8081`

Swagger UI: `http://localhost:8081/swagger-ui/index.html`

### Run Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend available at `http://localhost:5173`

## Setting Up Users

Users can be registered through **Swagger UI** or **Postman**.

**Swagger UI:** `http://localhost:8081/swagger-ui/index.html`

Navigate to `/auth/register` endpoint, click **Try it out** and send the request body below.

**Postman:** Send a POST request to `http://localhost:8081/auth/register` with the following JSON body.

```json
POST /auth/register
{
    "ime": "User",
    "prezime": "User",
    "email": "user@mail.com",
    "lozinka": "user123"
}
```

### Register Admin

```json
POST /auth/register
{
    "ime": "Admin",
    "prezime": "Admin",
    "email": "admin@mail.com",
    "lozinka": "admin123"
}
```

After registering, set the ADMIN role by running this command while Docker containers are running:

```bash
docker exec -it tournament-manager-db-1 mariadb -u root -proot123 tournament_manager -e "UPDATE korisnik SET rola = 'ADMIN' WHERE email = 'admin@mail.com';"
```

### Login

```json
POST /auth/login
{
    "email": "your@email.com",
    "lozinka": "yourpassword"
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

```
tournament-manager/
├── src/                    # Spring Boot backend
│   └── main/java/com/projekt1/tournament_manager/
│       ├── controller/     # REST endpoints
│       ├── service/        # Business logic
│       ├── repository/     # Database communication
│       ├── model/          # JPA entities and enums
│       ├── security/       # JWT filter, service and Spring Security config
│       ├── dto/            # Data Transfer Objects
│       └── exception/      # Custom exceptions and global handler
├── frontend/               # Vue.js frontend
│   └── src/
│       ├── views/          # Page components
│       ├── services/       # API communication
│       └── router/         # Navigation and route guards
├── docker-compose.yml
└── Dockerfile
```