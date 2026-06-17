# Student Records Management System 

Student Records Management System for Prince University.
This project is a Monolith application built with **Spring Boot** (Java 21) on the backend and **React** (Vite) on the frontend. It uses SQLite for data persistence and is fully containerized.

## 🔗 Links

- **GitHub Repository:** [https://github.com/Sharmaxz/cs425-swe-2026-final](https://github.com/Sharmaxz/cs425-swe-2026-final)
- **Docker Hub Image:** [https://hub.docker.com/r/sharmaxz/cs425-swe-2026-final](https://hub.docker.com/r/sharmaxz/cs425-swe-2026-final)

## 🚀 Running the Project

### Option 1: Running Locally (Maven)
Make sure you have Java 21 installed.
```bash
./mvnw spring-boot:run
```
Then navigate to `http://localhost:8080` to access the React interface!

### Option 2: Running via Docker
```bash
docker pull sharmaxz/cs425-swe-2026-final:v1.0.0
docker run -p 8080:8080 sharmaxz/cs425-swe-2026-final:v1.0.0
```
Then navigate to `http://localhost:8080`.
