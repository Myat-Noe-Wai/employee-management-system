## 📘 Employee Management System

### 🔹 Project Overview

The **Employee Management System** is a full-stack enterprise-style web application built to streamline workforce administration, attendance monitoring, and leave management within organizations. It implements **secure role-based access control**, automated attendance logic, and advanced data management features to ensure efficiency, transparency, and accuracy in HR operations.

This system simulates real-world business workflows and demonstrates scalable architecture, secure authentication, and clean UI/UX practices suitable for production-ready applications.

---

### 🔹 Technologies Used

**Backend**

* Java
* Spring Boot
* Spring Security
* JWT + Refresh Token Authentication
* RESTful API Architecture
* DTO Pattern + Validation
* Logging & Exception Handling
* Flyway (Database Migration)
* Role-Based Access Control (RBAC)

**Frontend**

* React
* Bootstrap UI Components
* Axios API Integration

**Database**

* MySQL (Relational Schema Design)

**Deployment**

* Cloud-hosted application environment

---

### 🔹 System Architecture Highlights

* Role-Based Access Control (RBAC) using Roles and Permissions
* Fine-grained authorization with Spring Security @PreAuthorize
* Stateless authentication using JWT Access Token + Refresh Token
* Secure REST APIs protected by permission-based authorization
* Database migration with Flyway
* Layered architecture (Controller → Service → Repository)
* DTO pattern with request/response separation
* Standardized API responses
* Pagination, filtering, and data export support

---

### 🔹 Core Features

#### 👨‍💼 Admin Capabilities

* Interactive dashboard with real-time statistics

  * Total employees
  * Present vs Absent today
  * Pending leave requests
  * Recent attendance logs
* Employee management secured by permission-based authorization (View, Create, Update, Delete)
* Job title/role management
* Advanced attendance tracking

  * Auto status calculation (Present / Late / Half Day / Absent)
  * Filter by employee, date range
  * Export attendance reports (Excel / PDF)
* Leave request administration

  * Approve / Reject requests
  * Status badges with color indicators
  * Multi-criteria filtering
  * CSV/Excel export

---

#### 👩‍💻 Employee Capabilities

* Personal dashboard with:

  * Today’s attendance status
  * Remaining leave balance
  * Last time-in record
* Time In / Time Out tracking system
* Weekly attendance view
* Monthly attendance analytics

  * Present / Absent / Half Days
  * Total working hours
  * Export reports (Excel / PDF)
* Leave request submission + history tracking

---

### 🔹 Demo Access

**Live System**
http://13.61.161.105/login

**Swagger API Documentation**
http://13.61.161.105:8081/swagger-ui/index.html

**Admin Login**

* Username: `admin@gmail.com`
* Password: `admin`

**Employee Login**

* Username: `myatnoewai@gmail.com`
* Password: `myatnoewai`

---

### 🔹 Source Code

**Backend Repository**
https://github.com/Myat-Noe-Wai/employee-management-system

**Frontend Repository**
https://github.com/Myat-Noe-Wai/ems-frontend

---

### 🔹 Learning & Development Goals

This project was designed to demonstrate real-world **full-stack engineering competency**, including:

* Secure authentication design
* Enterprise-level API structuring
* Enterprise RBAC architecture using roles and permissions
* Fine-grained authorization using Spring Security Method Security
* Database versioning with Flyway
* Business logic implementation
* State management between frontend and backend
* Production-style features such as exports, analytics dashboards, and filters