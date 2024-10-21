# Dance Studio Backend

This is the backend for the Dance Studio management system. It handles user registration, authentication, and role-based access for Teachers and Students. It allows teachers to manage their profiles and student assignments.

## Installation

To install and run the backend of the project locally, follow these steps:

### Prerequisites

Ensure you have the following installed:

- **Java 17 or higher**: Download Java from [Oracle's official page](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **Maven**: Install Maven from [here](https://maven.apache.org/install.html).
- **MySQL**: Install MySQL from [here](https://dev.mysql.com/downloads/installer/).

### Clone the Repository

First, clone the repository to your local machine using Git:
```bash
git clone https://github.com/karolinaandrulyte/51_DanceStudio.git

Navigate into the backend folder:
```bash
cd 51_DanceStudio

### Database Setup

1. **Install MySQL**: Make sure you have MySQL installed on your machine. You can download it from the [MySQL official website](https://www.mysql.com/downloads/).

2. **Create a Database**: Create a new database for your project. You can do this using the MySQL command line or a GUI tool like MySQL Workbench. 

   ```sql
   CREATE DATABASE dance_studio;
   
3. Update your application.properties file with your database credentials.

Open the `application.properties` file located in `src/main/resources` and update the database connection settings as follows:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dancestudio
spring.datasource.username=root
spring.datasource.password=Labaisunkus1
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### Build the Project

To build the project, navigate to the root directory of the backend application in your terminal and run the following command:
```bash
./mvnw clean install

### Running the Application
After building the project, you can run the application using the following command:
```bash
./mvnw spring-boot:run

The application will start on the default port 8080. You can access it at http://localhost:8080.

## Usage

1. **Sign Up**: Navigate to the signup page and fill in the required fields to create a new user account. You can choose to register as either a student or a teacher.
  
2. **Login**: Use your credentials to log in to the application. After logging in, you'll be directed to your respective dashboard based on your role.

3. **Student Dashboard**: As a student, you can view the list of available teachers along with their dance styles and schedules. You can also see your assigned teacher(s).

4. **Teacher Dashboard**: As a teacher, you can view your assigned students and update your dance styles and descriptions. You can also manage your schedule.

5. **Profile Management**: Both students and teachers can view and edit their profile information, including username, email, first name, last name, and password.

6. **Assign/Unassign Students**: Teachers can assign students to themselves or unassign them from their list of assigned students.

## Features

- **User Management**: 
  - Ability to register new users as either students or teachers.
  - Role-based access control to ensure proper permissions.

- **Profile Management**: 
  - Users can view and edit their profiles, including personal information and dance styles.

- **Teacher-Student Assignment**: 
  - Teachers can assign and unassign students to manage their classes effectively.

- **RESTful API**: 
  - Built with Spring Boot, providing a robust set of RESTful endpoints for user and profile management.

- **Security**: 
  - JWT-based authentication for secure login and API access.
  - CORS configuration to allow cross-origin requests from the frontend.

- **Database Integration**: 
  - Utilizes MySQL for data persistence with JPA/Hibernate for ORM.

## Available Scripts

In the project directory, you can run the following commands:

### `mvn clean install`

Builds the project, compiling the source code and packaging it into a JAR file.

### `mvn spring-boot:run`

Runs the Spring Boot application directly from the source code.

### `mvn test`

Runs the unit tests for the project to ensure the code is functioning as expected.

## API Endpoints

| HTTP Method | Endpoint                                 | Description                                              | Auth Required | Roles               |
|-------------|------------------------------------------|----------------------------------------------------------|---------------|---------------------|
| POST        | `/api/auth/signup`                       | Register a new user (student or teacher).               | No            | Any                 |
| POST        | `/api/auth/signin`                       | Authenticate a user and obtain a JWT.                   | No            | Any                 |
| GET         | `/api/users`                             | Get all users.                                          | Yes           | Admin               |
| GET         | `/api/users/{id}`                        | Get a specific user by ID.                             | Yes           | Admin/Teacher       |
| PUT         | `/api/users/{id}`                        | Update user details (Teachers can update their own profile). | Yes        | Teacher/Admin       |
| GET         | `/api/users/teachers`                    | Get all users with the role ROLE_TEACHER.              | Yes           | Admin               |
| GET         | `/api/users/students`                   | Get all users with the role ROLE_STUDENT.               | Yes           | Admin/Teacher       |
| POST        | `/students/{studentId}/assign/{teacherId}` | Assign a student to a teacher.                          | Yes           | Teacher             |
| POST        | `/students/{studentId}/unassign/{teacherId}` | Unassign a student from a teacher.                     | Yes           | Teacher             |

