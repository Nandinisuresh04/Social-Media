# 🚀 Social Media Backend – Core API & Guardrails

## 📖 Overview
This project is a backend microservice built using Spring Boot that simulates a social media platform with controlled bot interactions. It is designed to handle high concurrency, enforce strict operational limits, and prevent abuse using a Redis-based guardrail system.

The system uses PostgreSQL as the source of truth for persistent data and Redis for real-time computation, atomic operations, and event throttling.

---

## 🏗️ Architecture
- API Layer: Stateless REST APIs built with Spring Boot  
- Database: PostgreSQL (users, posts, comments)  
- Cache & Control Layer: Redis (virality score, locks, cooldowns, notifications)  

Redis acts as a guardrail layer, ensuring all constraints are validated before database writes.

---

## ⚙️ Features

### Core APIs
- Create posts  
- Add comments  
- Like posts  

---

### ⚡ Virality Engine
Real-time scoring using Redis:

- Bot reply → +1  
- Human like → +20  
- Human comment → +50  

---

### 🔐 Atomic Guardrails
- Max 100 bot replies per post  
- Max comment depth of 20  
- Bot-to-user interaction cooldown (10 minutes)  

All constraints are enforced using Redis atomic operations to prevent race conditions.

---

### 🔔 Notification Engine
- 15-minute notification cooldown per user  
- Stores pending notifications in Redis  
- Scheduled job aggregates and sends summarized updates  

---

## 🚀 Getting Started

### Prerequisites
- Java 17+  
- Maven  
- Docker  

---

### Run Services
```bash
docker-compose up -d
Run Application
mvn spring-boot:run
📡 API Endpoints
Method	Endpoint	Description
POST	/api/posts	Create a post
POST	/api/posts/{postId}/comments	Add a comment
POST	/api/posts/{postId}/like	Like a post
🧪 Testing
Concurrency Handling
Supports high concurrent requests
Ensures strict enforcement of limits using Redis
Stateless Design
No in-memory storage
All runtime state managed in Redis
📦 Deliverables
Spring Boot source code
Docker setup for PostgreSQL & Redis
Postman collection
README documentation
💡 Key Highlights
Redis used for atomic operations and concurrency control
Clean separation between persistence and real-time logic
Designed for scalability and fault tolerance
✅ Conclusion

This project demonstrates the design of a scalable and reliable backend system capable of handling high concurrency using Redis-based guardrails. By combining Spring Boot, PostgreSQL, and Redis, the system ensures data integrity, controlled interactions, and efficient notification management.

👨‍💻 Author

Nandini S B