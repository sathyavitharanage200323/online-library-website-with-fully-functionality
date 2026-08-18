# Library Management System

A comprehensive Spring Boot web application for managing library operations including book cataloging, staff management, and user interactions.

## Features

### User Features
- **Book Browsing**: View all available books with pagination
- **Advanced Search**: Search books by title, author, or ISBN
- **Filtering**: Filter books by availability status
- **Sorting**: Sort books by various criteria (title, author, publication date)
- **Staff Directory**: Browse library staff members and their information
- **Responsive Design**: Mobile-friendly interface

### Admin Features
- **Secure Authentication**: Admin login with Spring Security
- **Book Management**: Complete CRUD operations for books
- **Staff Management**: Complete CRUD operations for staff members
- **Dashboard**: Overview with statistics and quick access
- **Modal Forms**: User-friendly forms for adding/editing records
- **AJAX Operations**: Seamless user experience without page reloads

## Technology Stack

- **Backend**: Spring Boot 3.5.7, Spring Security, Spring Data JPA
- **Database**: H2 (in-memory)
- **Frontend**: Thymeleaf templates, CSS3, JavaScript
- **Build Tool**: Gradle (multi-module)
- **Java Version**: 25

## Prerequisites

- Java 25 or higher
- Gradle 8.14+ (or use included wrapper)

## Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd library-system
   ```

2. **Build the Application**:
   ```bash
   ./gradlew build
   ```

3. **Run the Application**:
   ```bash
   ./gradlew :backend:bootRun
   ```

4. **Access the Application**:
   - Open browser and navigate to `http://localhost:8080`
   - Admin login: `admin@libraryse.com` / `admin123`
   - User login: `john.doe@example.com` / `password123`

## Project Structure

```
library-system/
├── backend/
│   ├── build.gradle
│   └── src/main/java/com/librarysystem/
│       ├── LibrarySystemApplication.java
│       ├── config/
│       │   ├── SecurityConfig.java
│       │   └── DataInitializer.java
│       ├── controller/
│       │   ├── HomeController.java
│       │   ├── LoginController.java
│       │   ├── AuthController.java
│       │   ├── BookController.java
│       │   ├── StaffController.java
│       │   ├── AdminController.java
│       │   └── AdminBookController.java
│       ├── model/
│       │   ├── Book.java
│       │   ├── Staff.java
│       │   ├── User.java
│       │   └── UserRole.java
│       ├── repository/
│       │   ├── BookRepository.java
│       │   ├── StaffRepository.java
│       │   └── UserRepository.java
│       └── service/
│           ├── BookService.java
│           ├── StaffService.java
│           └── UserService.java
├── frontend/
│   ├── build.gradle
│   └── src/main/resources/
│       ├── templates/
│       │   ├── index.html
│       │   ├── login.html
│       │   ├── books.html
│       │   ├── book-detail.html
│       │   ├── staff.html
│       │   ├── staff-detail.html
│       │   ├── auth/
│       │   │   └── signup.html
│       │   └── admin/
│       │       ├── admin-dashboard.html
│       │       ├── admin-books.html
│       │       └── admin-staff.html
│       └── static/
│           └── css/style.css
├── gradle/wrapper/
├── gradlew
├── gradlew.bat
├── build.gradle
├── settings.gradle
└── README.md
```

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /books` - Book listing with search/filter
- `GET /books/{id}` - Book details
- `GET /staff` - Staff listing
- `GET /staff/{id}` - Staff details

### Admin Endpoints
- `GET /admin` - Admin dashboard
- `GET /admin/books` - Book management
- `POST /admin/books` - Create new book
- `PUT /admin/books/{id}` - Update book
- `DELETE /admin/books/{id}` - Delete book
- `GET /admin/staff` - Staff management
- `POST /admin/staff` - Create new staff member
- `PUT /admin/staff/{id}` - Update staff member
- `DELETE /admin/staff/{id}` - Delete staff member

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `POST /logout` - Logout

## Configuration

### Application Properties
```properties
# Database Configuration (H2 in-memory)
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
```

### Color Theme
- **Header**: #343131 (Dark Gray)
- **Primary Buttons**: #A04747 (Red)
- **Highlights**: #D8A25E (Gold)
- **Accent**: #EEDF7A (Light Yellow)

## Sample Data

The application includes sample data initialization on startup:
- **5 Sample Books**: Classic literature with realistic metadata
- **5 Sample Staff Members**: Various library positions
- **2 Users**: Admin and regular user accounts

## Security

- **Authentication**: Spring Security with database-backed users
- **Password Encryption**: BCrypt password encoding
- **Role-based Access**: USER and ADMIN roles
- **Session Management**: Secure session handling

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
