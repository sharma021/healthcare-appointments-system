# 🏥 Healthcare Appointments & EHR Backend System

A secure, role-based backend application built with **Spring Boot 3** and **Java 17** for managing healthcare appointments and electronic health records (EHR).

This project simulates a real-world healthcare backend system with authentication, authorization, audit logging, analytics, pagination, and clean architectural separation.

---

## 🚀 Features

### 🔐 Security & Authentication
- JWT-based authentication
- Role-based authorization using Spring Security
- Supported roles:
  - **ADMIN**
  - **DOCTOR**
  - **PATIENT**

### 👤 Patient Management
- Create, update, delete patient profiles
- Fetch patient details
- Patient self-profile access (`/me`)
- Paginated patient listing

### 👨‍⚕️ Doctor Management
- Doctor profile creation
- Doctor self-profile access
- Public doctor listing

### 📅 Appointment Scheduling
- Schedule appointments between patients and doctors
- Prevent overlapping appointments for doctors
- Update appointment status
- Fetch appointments by patient or doctor (paginated)

### 📄 Electronic Health Records (EHR)
- Create EHR records for appointments
- Fetch EHRs per patient
- Secure access based on role

### 📊 Analytics
- Appointment count grouped by doctor
- Time-range based analytics endpoints

### 📜 Audit Logging
- Logs critical actions such as:
  - Patient creation
  - Updates
  - Deletions

### 📄 API Documentation
- Swagger UI for interactive API exploration and testing
- JWT Bearer authentication supported in Swagger

### 📄 Pagination & Sorting
- Pagination implemented using Spring Data `Pageable`
- Supports `page`, `size`, and `sort` parameters

---

## 🧱 Architecture

This project follows **Clean / Hexagonal Architecture** principles.

```

Controller (Web Adapter)
↓
Use Case (Business Logic)
↓
Port (Interface)
↓
Adapter (Persistence)
↓
Database (JPA)

````

### Benefits
- Clear separation of concerns
- Business logic independent of frameworks
- Easy to maintain and extend
- Production-style backend structure

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA
- H2 (In-memory database for local execution)
- Flyway (Database migrations)
- Swagger / OpenAPI
- Maven

---

## ▶️ Running the Application Locally

### Prerequisites
- Java 17+
- Maven
- IntelliJ IDEA (recommended)

### Steps

```bash
git clone https://github.com/sharma021/healthcare-appointments-system.git
cd healthcare-appointments-system
mvn clean install
mvn spring-boot:run
````

Application runs at:

```
http://localhost:8080
```

---

## 🔎 Swagger UI

Access Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

Swagger supports JWT authentication via the **Authorize** button.

---

## 🔑 Sample Credentials

| Role    | Email                                       | Password    |
| ------- | ------------------------------------------- | ----------- |
| ADMIN   | [admin@demo.com](mailto:admin@demo.com)     | Admin@123   |
| DOCTOR  | [doctor@demo.com](mailto:doctor@demo.com)   | Doctor@123  |
| PATIENT | [patient@demo.com](mailto:patient@demo.com) | Patient@123 |

---

## 📄 Pagination Example

```
GET /api/patients?page=0&size=5&sort=fullName,asc
```

Response includes:

* `content`
* `totalElements`
* `totalPages`
* `number`
* `size`

---

## 🧪 API Testing

* APIs can be tested using **Swagger UI** or **Postman**
* JWT token must be sent as:

```
Authorization: Bearer <token>
```

---

## 📌 Why This Project?

This project demonstrates:

* Secure backend system design
* JWT-based authentication & RBAC
* Clean Architecture implementation
* Scalable pagination support
* Realistic healthcare domain modeling
* Analytics and audit logging

This makes it suitable for **backend / SDE-2 / full-stack interview discussions**.

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👤 Author

**Vidya Sharma**
Backend / Full-Stack Engineer
GitHub: [https://github.com/sharma021](https://github.com/sharma021)