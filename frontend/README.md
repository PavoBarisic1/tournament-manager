# Tournament Manager Frontend

A simple Vue.js frontend application for the Tournament Manager API with JWT authentication and role-based access control.

## Tech Stack

- Vue.js 3
- Vue Router
- Vite

## Features

- User login with JWT authentication
- View all tournaments
- View players and ranking for each tournament
- Add new tournaments (ADMIN only)
- Add new players to tournaments (ADMIN only)
- Role-based UI (ADMIN sees more options than USER)

## Getting Started

### Prerequisites

- Node.js
- npm

### Run locally

```bash
git clone https://github.com/PavoBarisic/tournament-manager-frontend.git
cd tournament-manager-frontend
npm install
npm run dev
```

Application will be available at `http://localhost:5173`

## Full Setup (Backend + Frontend)

1. Clone and start the backend:

```bash
git clone https://github.com/PavoBarisic/tournament-manager.git
cd tournament-manager
./mvnw clean package -DskipTests
docker compose up --build
```

Backend will be available at `http://localhost:8081`

2. Clone and start the frontend:

```bash
git clone https://github.com/PavoBarisic/tournament-manager-frontend.git
cd tournament-manager-frontend
npm install
npm run dev
```

3. Open `http://localhost:5173`

## Backend Connection

By default the app connects to `http://localhost:8081`.

To change the backend URL, edit `src/services/api.js`:

```javascript
const API_URL = 'http://localhost:8081'
```

## Setting Up Admin Role

After registering through the app, your account will have USER role by default. To set ADMIN role, run the following command while Docker containers are running:

```bash
docker exec -it tournament-manager-db-1 mariadb -u root -proot123 tournament_manager -e "UPDATE korisnik SET rola = 'ADMIN' WHERE email = 'your@email.com';"
```

## Project Structure

| File/Folder | Description |
|-------------|-------------|
| src/views/ | Page components (Login, Tournaments, Tournament Detail) |
| src/services/ | API communication with backend |
| src/router/ | Navigation and route guards |
| src/App.vue | Root component |