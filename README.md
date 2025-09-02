# 🚀 Club Management API

This project is the backend for a member management system designed for student clubs and similar organizations. It provides a comprehensive RESTful API for managing members, their roles, and activities. The system is built with Java and the Spring Boot framework, offering a robust and scalable foundation for club administration.

This is a backend application and requires a frontend to be used. You can find the corresponding frontend repository and setup instructions here:
**[➡️ enesoglu/club-management-ui](https://github.com/enesoglu/club-management-ui)**

-----

## ✨ Key Features

- **Full Member Management:** Complete **CRUD** (Create, Read, Update, Delete) functionality for all club members.
- **Position & Role Tracking:** Assign detailed positions to members (e.g., President, Crew Member) and track the history of these roles across different academic terms.
- **Term Management:** Easily create and manage academic terms, allowing for a historical record of club activities.
- **Bulk CSV Import:** Onboard multiple new members at once by uploading a `.csv` file.
- **Secure Authentication:** A secure, password-based login system for authorized users, with basic role-based access control.
- **Efficient Data Handling:** Utilizes **DTOs** and **MapStruct** for clean and optimized data transfer between the client and the database.

-----

## 🗺️ Roadmap / Planned Features

This project is actively under development. Here are some of the features planned for upcoming releases:

- **Advanced Role-Based Access:** Finer-grained permissions and access control for different user roles.
- **Dashboard Analytics:** Endpoints to provide data for visualizing member statistics and club growth.
- **Automated Emails:** Integration for sending notifications and updates to members.
- **Event Management:** Functionality for creating and managing club events.

-----

## 🛠️ Tech Stack

- **Java 17:** The core programming language for the application.
- **Spring Boot 3:** Framework for creating stand-alone, production-grade Spring-based Applications.
- **Spring Data JPA:** For simplified data access with the database.
- **Spring Security:** For handling authentication and authorization.
- **PostgreSQL:** A powerful, open-source object-relational database system.
- **Maven:** A build automation and project management tool.
- **MapStruct:** A code generator that simplifies the implementation of mappings between Java bean types.
- **Lombok:** A java library that helps to reduce boilerplate code.

-----

## 🗄️ Database Schema

The database is designed with three core tables: `club_members`, `positions`, and `terms`. This structure creates clear and efficient relationships between members, their roles within the club, and the academic term in which they served.

-----

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### ✔️ Prerequisites

- **JDK 17** or later
- **Maven**
- A running instance of **PostgreSQL**

### ⚙️ Installation and Setup

1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/enesoglu/club-management-api.git
    cd club-management-api
    ```

2.  **Configure the Database:**

    - Create a new database in your PostgreSQL instance.
    - In the `src/main/resources/` directory, rename `application.properties.example` to `application.properties`.
    - Open the new `application.properties` file and update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to match your database configuration.

3.  **Run the Development Server:**
    Once the database is configured, use the Maven wrapper to run the application:

    ```bash
    ./mvnw spring-boot:run
    ```

    The API will start and be accessible at `http://localhost:8080/`.