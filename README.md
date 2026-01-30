# URL Shortener

---

A backend URL shortener service built with **Java (Spring Boot)** and **PostgreSQL**, supporting short-link creation, redirection, click tracking, and persistent storage using Docker.

This project focuses on backend architecture, persistence, and real-world tooling rather than UI.

---

## Features

- Create short URLs via a REST API  
- Redirect short codes to original URLs (HTTP 302)  
- Click tracking per short link  
- PostgreSQL persistence (data survives restarts)  
- Dockerised database setup  
- Clean separation of controller, service, and repository layers  

---

## Tech Stack

- **Java 25**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**
