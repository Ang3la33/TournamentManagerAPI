# 🏌️‍♂️ TournamentManagerAPI

A Java Spring Boot REST API to manage members and tournaments for a golf club. This project allows you to create, update, and search members, manage tournaments, and associate members with tournaments using a many-to-many relationship.

---

## 📦 Tech Stack

- **Java 17**
- **Spring Boot 3.1**
- **Spring Data JPA**
- **MySQL (via Docker)**
- **Lombok**
- **JUnit & Mockito**
- **Docker + Docker Compose**
- **Postman (for API testing)**

---

## 🧱 Features

### ✅ Member Management

- Create a new member
- Get all members
- Get member by ID
- Update member
- Search member by:
  - Name
  - Membership Type
  - Phone Number
  - Tournament Start Date

### ✅ Tournament Management

- Create a tournament
- Get all tournaments
- Join members to a tournament
- Get all members in a tournament

## 🐳 Getting Started with Docker

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/TournamentManagerAPI.git

cd TournamentManagerAPI

```

### 2. Start MySQL and the App using Docker Compose

```bash
docker-compose up -d
```

MySQL will be running on `localhost:3307`  
Spring Boot app will be accessible on `localhost:8080`

---

## 🚀 Running the API (Alternative to Docker)

If you'd like to run it manually:

```bash
mvn clean package

java -jar target/TournamentManagerAPI-1.0-SNAPSHOT.jar
```

---

## 🔍 API Endpoints

| Method | Endpoint                                 | Description                              |
|--------|------------------------------------------|------------------------------------------|
| POST   | `/members`                               | Create Member                            |
| GET    | `/members`                               | Get All Members                          |
| GET    | `/members/{id}`                          | Get Member by ID                         |
| PUT    | `/members/{id}`                          | Update Member                            |
| GET    | `/members/search?name=`                  | Search Member by Name                    |
| GET    | `/members/type?type=`                    | Get Members by Membership Type           |
| GET    | `/members/phone?phone=`                  | Get Members by Phone Number              |
| GET    | `/members/tournament-date?startDate=`    | Get Members by Tournament Start Date     |
| POST   | `/tournaments`                           | Create Tournament                        |
| GET    | `/tournaments`                           | Get All Tournaments                      |
| POST   | `/tournaments/{id}/join`                 | Join Members to Tournament               |
| GET    | `/tournaments/{id}/members`              | Get All Members in a Tournament          |

---

## 🧪 Postman API Testing

You can find screenshots of all Postman tests inside the project:

[📂 Postman Screenshots PDF](docs/screenshots/Postman_TournamentManagerAPI_Postman_Screenshots.pdf)



It includes:

- Creating and retrieving members  
- Creating tournaments  
- Joining members to tournaments  
- Searching members by various fields  


---

## 🧠 Learning Objectives

- Use Spring Boot to create RESTful APIs  
- Understand and implement many-to-many relationships with JPA  
- Build and run containerized applications using Docker  
- Apply clean coding practices and architecture  
- Use Postman to test API functionality  
- Export API documentation as screenshots  

---

## 🧾 License

This project is for academic purposes.

---

## ✍️ Author

**Angela Smith**  
Software Development Student @ Keyin College  
📧 [angela.smith@keyin.com](mailto:angela.smith@keyin.com)










