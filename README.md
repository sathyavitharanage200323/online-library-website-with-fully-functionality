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
- **Database**: Microsoft SQL Server with Windows Authentication
- **Frontend**: Thymeleaf templates, CSS3, JavaScript
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Microsoft SQL Server (Express or higher)
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Database Setup

1. **SQL Server Configuration**:
   - Ensure SQL Server is running
   - Create database named `LibraryDB`
   - Configure Windows Authentication
   - Ensure `sqljdbc_auth.dll` is in system PATH

2. **Database Tables**:
   The application will automatically create the following tables:
   - `libraryBook` - Stores book information
   - `libraryStaff` - Stores staff member information

## Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd library-system
   ```

2. **Configure Database**:
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost\\IMA\\SQLEXPRESS;databaseName=LibraryDB;integratedSecurity=true
   ```

3. **Build the Application**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application**:
   - Open browser and navigate to `http://localhost:8080`
   - Admin login: `admin` / `admin123`

## Project Structure

```
library-system/
├── src/
│   ├── main/
│   │   ├── java/com/librarysystem/
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── BookController.java
│   │   │   │   ├── StaffController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── model/
│   │   │   │   ├── Book.java
│   │   │   │   └── Staff.java
│   │   │   ├── repository/
│   │   │   │   ├── BookRepository.java
│   │   │   │   └── StaffRepository.java
│   │   │   ├── service/
│   │   │   │   ├── BookService.java
│   │   │   │   └── StaffService.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── DataInitializer.java
│   │   │   └── LibrarySystemApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── books.html
│   │       │   ├── staff.html
│   │       │   ├── admin-dashboard.html
│   │       │   ├── admin-books.html
│   │       │   ├── admin-staff.html
│   │       │   └── login.html
│   │       ├── static/css/style.css
│   │       └── application.properties
│   └── test/
├── pom.xml
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
# Database Configuration
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost\\IMA\\SQLEXPRESS;databaseName=LibraryDB;integratedSecurity=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

# Server Configuration
server.port=8080
```

### Color Theme
The application uses a consistent color scheme:
- **Header**: #343131 (Dark Gray)
- **Primary Buttons**: #A04747 (Red)
- **Highlights**: #D8A25E (Gold)
- **Accent**: #EEDF7A (Light Yellow)

## Sample Data

The application includes sample data initialization:
- **5 Sample Books**: Classic literature with realistic metadata
- **5 Sample Staff Members**: Various library positions

## Security

- **Admin Authentication**: Spring Security with in-memory user
- **Password Encryption**: BCrypt password encoding
- **CSRF Protection**: Enabled for form submissions
- **Session Management**: Secure session handling

## Development

### Adding New Features
1. Create model classes in `model` package
2. Add repository interfaces in `repository` package
3. Implement business logic in `service` package
4. Create controllers in `controller` package
5. Add Thymeleaf templates in `templates` directory

### Database Changes
- Update model classes with new fields
- Modify repository interfaces for new queries
- Update service layer for new business logic
- Run application to apply schema changes

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Verify SQL Server is running
   - Check Windows Authentication settings
   - Ensure `sqljdbc_auth.dll` is in PATH

2. **Login Issues**:
   - Use default credentials: `admin` / `admin123`
   - Check Spring Security configuration

3. **Port Already in Use**:
   - Change port in `application.properties`
   - Or stop other applications using port 8080

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Library Management System** - Built with Spring Boot and modern web technologies.
"# online-library-website-with-fully-functionality" 
