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

### Virality Engine
Real-time scoring using Redis:

- Bot reply → +1  
- Human like → +20  
- Human comment → +50  

---

### Atomic Guardrails
- Max 100 bot replies per post  
- Max comment depth of 20  
- Bot-to-user interaction cooldown (10 minutes)  

All enforced using Redis atomic operations to prevent race conditions.

---

### Notification Engine
- 15-minute notification cooldown per user  
- Stores pending notifications in Redis  
- Scheduled job aggregates and sends summarized updates

- 
---

- ## ✅ Conclusion

This project demonstrates the design of a scalable and reliable backend system capable of handling high concurrency using Redis-based guardrails. By combining Spring Boot for API development, PostgreSQL for persistent storage, and Redis for real-time processing, the system ensures data integrity, controlled bot interactions, and efficient notification management.

The implementation highlights strong backend fundamentals such as stateless architecture, atomic operations for concurrency control, and event-driven scheduling, making it suitable for real-world, high-traffic applications.
