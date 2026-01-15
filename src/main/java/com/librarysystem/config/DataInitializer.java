package com.librarysystem.config;

import com.librarysystem.model.Book;
import com.librarysystem.model.Staff;
import com.librarysystem.model.User;
import com.librarysystem.model.UserRole;
import com.librarysystem.repository.BookRepository;
import com.librarysystem.repository.StaffRepository;
import com.librarysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final BookRepository bookRepository;
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public DataInitializer(BookRepository bookRepository, StaffRepository staffRepository, 
                          UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            initializeBooks();
        }
        
        if (staffRepository.count() == 0) {
            initializeStaff();
        }
        
        if (userRepository.count() == 0) {
            initializeUsers();
        }
    }
    
    private void initializeBooks() {
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565");
        book1.setDescription("A classic American novel set in the Jazz Age, exploring themes of wealth, love, and the American Dream.");
        book1.setPublicationDate(LocalDate.of(1925, 4, 10));
        book1.setIsAvailable(true);
        bookRepository.save(book1);
        
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084");
        book2.setDescription("A gripping tale of racial injustice and childhood innocence in the American South.");
        book2.setPublicationDate(LocalDate.of(1960, 7, 11));
        book2.setIsAvailable(true);
        bookRepository.save(book2);
        
        Book book3 = new Book("1984", "George Orwell", "9780451524935");
        book3.setDescription("A dystopian social science fiction novel about totalitarian control and surveillance.");
        book3.setPublicationDate(LocalDate.of(1949, 6, 8));
        book3.setIsAvailable(false);
        bookRepository.save(book3);
        
        Book book4 = new Book("Pride and Prejudice", "Jane Austen", "9780141439518");
        book4.setDescription("A romantic novel that critiques the British landed gentry of the early 19th century.");
        book4.setPublicationDate(LocalDate.of(1813, 1, 28));
        book4.setIsAvailable(true);
        bookRepository.save(book4);
        
        Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769174");
        book5.setDescription("A coming-of-age story about teenage rebellion and alienation.");
        book5.setPublicationDate(LocalDate.of(1951, 7, 16));
        book5.setIsAvailable(true);
        bookRepository.save(book5);
    }
    
    private void initializeStaff() {
        Staff staff1 = new Staff("Sarah", "Johnson", "Library Director", "sarah.johnson@libraryse.com");
        staff1.setPhoneNumber("15550101");
        staff1.setBio("Sarah has been leading our library for over 10 years, bringing innovation and community engagement to our services.");
        staffRepository.save(staff1);
        
        Staff staff2 = new Staff("Michael", "Chen", "Reference Librarian", "michael.chen@libraryse.com");
        staff2.setPhoneNumber("15550102");
        staff2.setBio("Michael specializes in research assistance and digital resources, helping patrons find exactly what they need.");
        staffRepository.save(staff2);
        
        Staff staff3 = new Staff("Emily", "Rodriguez", "Technical Services", "emily.rodriguez@libraryse.com");
        staff3.setPhoneNumber("15550103");
        staff3.setBio("Emily manages our cataloging and technical systems, ensuring smooth operations behind the scenes.");
        staffRepository.save(staff3);
        
        Staff staff4 = new Staff("David", "Thompson", "Assistant Librarian", "david.thompson@libraryse.com");
        staff4.setPhoneNumber("15550104");
        staff4.setBio("David assists with circulation services and helps maintain our collection organization.");
        staffRepository.save(staff4);
        
        Staff staff5 = new Staff("Lisa", "Wang", "Cataloging Specialist", "lisa.wang@libraryse.com");
        staff5.setPhoneNumber("15550105");
        staff5.setBio("Lisa ensures all new materials are properly cataloged and accessible to our patrons.");
        staffRepository.save(staff5);
    }
    
    private void initializeUsers() {
        User admin = new User("Admin", "User", "admin@libraryse.com", "admin123");
        admin.setRole(UserRole.ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepository.save(admin);
        
        User regularUser = new User("John", "Doe", "john.doe@example.com", "password123");
        regularUser.setRole(UserRole.USER);
        regularUser.setPassword(passwordEncoder.encode(regularUser.getPassword()));
        userRepository.save(regularUser);
    }
}
