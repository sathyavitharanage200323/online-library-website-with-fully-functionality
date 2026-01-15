package com.ec.libraryse.controller.admin;

import com.ec.libraryse.model.Book;
import com.ec.libraryse.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {
    
    private final BookService bookService;
    
    @Autowired
    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public String adminBooksPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            Model model) {
        
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Book> books;
        
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search, pageable);
        } else {
            books = bookService.findAllBooks(pageable);
        }
        
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        
        return "admin/admin-books";
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.saveBook(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        try {
            Optional<Book> existingBook = bookService.findBookById(id);
            if (existingBook.isPresent()) {
                book.setId(id);
                Book updatedBook = bookService.saveBook(book);
                return ResponseEntity.ok(updatedBook);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isPresent()) {
                bookService.deleteBook(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        Optional<Book> book = bookService.findBookById(id);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
}
