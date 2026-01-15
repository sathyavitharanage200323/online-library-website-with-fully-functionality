package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
    
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAllBooks();
        }
        return bookRepository.findByKeyword(keyword.trim());
    }
    
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAllBooks(pageable);
        }
        return bookRepository.findByKeywordWithPagination(keyword.trim(), pageable);
    }
    
    public List<Book> findAvailableBooks() {
        return bookRepository.findByIsAvailable(true);
    }
    
    public Page<Book> findAvailableBooks(Pageable pageable) {
        return bookRepository.findByIsAvailable(true, pageable);
    }
    
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    public List<Book> findBooksByIsbn(String isbn) {
        return bookRepository.findByIsbnContainingIgnoreCase(isbn);
    }
    
    public boolean existsByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }
    
    public long countBooks() {
        return bookRepository.count();
    }
    
    public long countAvailableBooks() {
        return bookRepository.countByIsAvailable(true);
    }
}
