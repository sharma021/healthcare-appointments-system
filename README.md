# Healthcare Appointment & EHR Backend (Spring Boot 3 + Java 17)

A **secure, role-based backend** for managing **healthcare appointments** and **electronic health records (EHR)**.

**Tech:** Spring Boot 3, Java 17, JWT, Spring Security, Spring Data JPA, Flyway  
**DB:** H2 (default, zero-setup) + PostgreSQL profile (optional)

---

## ✅ Prerequisites
- Java 17
- Maven 3.9+

---

## ▶️ Run (No Docker)
```bash
mvn spring-boot:run
```

App: http://localhost:8080  
H2 Console: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:healthdb` | user: `sa` | password: (blank)

---

## 🔐 Demo Users (auto-seeded)
| Role | Email | Password |
|------|-------|----------|
| ADMIN | admin@demo.com | Admin@123 |
| DOCTOR | doctor@demo.com | Doctor@123 |
| PATIENT | patient@demo.com | Patient@123 |

---

## 🧪 Quick Test (curl)

### Login (Admin)
```bash
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@demo.com","password":"Admin@123"}'
```

### Public doctors list
```bash
curl -s http://localhost:8080/api/doctors
```

### Admin analytics
```bash
export TOKEN="PASTE_ADMIN_TOKEN"
curl -s "http://localhost:8080/api/analytics/appointments-by-doctor?from=2025-01-01T00:00:00Z&to=2027-01-01T00:00:00Z" \
  -H "Authorization: Bearer $TOKEN"
```

---

## 🐘 PostgreSQL (optional)
1. Create DB `healthdb`
2. Run:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```
Edit `src/main/resources/application-postgres.yml` if needed.

---

## 📌 Architecture notes (Hexagonal-ish)
- `domain` = core types/exceptions
- `application` = use cases + ports
- `adapters/in` = REST controllers
- `adapters/out` = JPA persistence adapters

---

## Suggested upgrades for “resume-grade”
- Add refresh tokens + token revocation
- Field-level encryption for EHR notes
- Doctor availability slots (working hours, holidays)
- Pagination + search filters
- Integration tests (Testcontainers)
