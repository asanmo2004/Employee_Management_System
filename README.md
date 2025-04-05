# 📚 EMS - Employee Management System

A web-based application built using **Spring Boot (Backend)** to manage employees, departments, users, and roles with secure authentication and role-based access control.

---

## 🚀 Steps to Run the Application

### ⚙️ Prerequisites

- Java 17
- MySQL running locally (or remote)
- IntelliJ IDEA or another Java IDE
- Maven installed

---

### ⚠️ Initial Setup

1. **Configure your MySQL credentials** in:
   `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/emsdb
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

2. **Create the database manually**:

```sql
CREATE DATABASE emsdb;
```

---

### 🧪 Run the Project

#### Option 1: From IntelliJ IDEA

- Open the project
- Right-click on `EmsSystemApplication.java`
- Click `Run`

#### Option 2: Using Maven (Command Line)

```bash
mvn spring-boot:run
```

## 🔐 Login (Spring Security)

When the Spring Boot app starts, open your browser and go to:

```
http://localhost:8080/employees
```

> You will be prompted to log in using your credentials.

---

## 🔐 Access Control

| Endpoint            | ADMIN | MANAGER | EMPLOYEE |
| ------------------- | ----- | ------- | -------- |
| `/employees` GET    | ✅     | ✅       | ✅        |
| `/employees` POST   | ✅     | ✅       | ✅        |
| `/employees` PUT    | ✅     | ✅       | ❌        |
| `/employees` DELETE | ✅     | ❌       | ❌        |
| `/departments/**`   | ✅     | ❌       | ❌        |
| `/roles/**`         | ✅     | ❌       | ❌        |
| `/users/**`         | ✅     | ❌       | ❌        |

---

## 🧭 Features

### 👨‍💼 Employee Management

- Add, update, view, and delete employee records.
- Role-based access enforced.

### 🏢 Department Management

- Create and view departments.
- Accessible only by Admin.

### 🧑‍💼 Role Management

- Add and view roles.
- Accessible only by Admin.

### 👤 Employee Statistics

- View Employee stats like count,experience,salary in department wise.
- Accessible only all



---

### 🧪 Testing via Postman

🔹 Base URL: `http://localhost:8090`

Make sure to set headers:

```
Content-Type: application/json
```

#### Add Employee

POST /employees

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "salary": 50000,
  "hireDate": "2025-04-01T10:00:00",
  "department": {
    "departmentId": 1
  },
  "role": {
    "roleID": 1
  }
}
```



#### Update Employee

PUT /employees/{id}

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com",
  "salary": 60000
}
```

#### Delete Employee

DELETE /employees/{id}

#### Get Department

GET /departments/{name}

**View Stats**

GET /stats/departments

---

## ✅ Tech Stack

- **Backend:** Spring Boot
- **Security:** Spring Security with Role-Based Access Control (RBAC)
- **Database:** MySQL
- **Testing:** JUnit, Mockito, Postman

---

## 📝 Author

Asan Mohammed

