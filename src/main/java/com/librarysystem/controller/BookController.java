package com.librarysystem.controller;

import com.librarysystem.model.Book;
import com.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public String getAllBooks(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "available", required = false) Boolean available,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            Model model) {
        
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Book> books;
        
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search, pageable);
        } else if (available != null) {
            books = bookService.findAvailableBooks(pageable);
        } else {
            books = bookService.findAllBooks(pageable);
        }
        
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        model.addAttribute("available", available);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        
        return "books";
    }
    
    @GetMapping("/{id}")
    public String getBookById(@PathVariable Integer id, Model model) {
        Optional<Book> book = bookService.findBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-detail";
        }
        return "redirect:/books";
    }
    
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooksApi() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Book> getBookByIdApi(@PathVariable Integer id) {
        Optional<Book> book = bookService.findBookById(id);
        return book.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<List<Book>> searchBooksApi(@RequestParam String keyword) {
        List<Book> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(books);
    }
}
