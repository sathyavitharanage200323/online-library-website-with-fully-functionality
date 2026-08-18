package com.librarysystem.controller;

import com.librarysystem.model.Book;
import com.librarysystem.model.Staff;
import com.librarysystem.service.BookService;
import com.librarysystem.service.StaffService;
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
@RequestMapping("/admin")
public class AdminController {
    
    private final BookService bookService;
    private final StaffService staffService;
    
    @Autowired
    public AdminController(BookService bookService, StaffService staffService) {
        this.bookService = bookService;
        this.staffService = staffService;
    }
    
    @GetMapping
    public String adminDashboard(Model model) {
        // Get recent books and staff for dashboard
        Pageable recentPageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        
        Page<Book> recentBooks = bookService.findAllBooks(recentPageable);
        Page<Staff> recentStaff = staffService.findAllStaff(recentPageable);
        
        model.addAttribute("recentBooks", recentBooks.getContent());
        model.addAttribute("recentStaff", recentStaff.getContent());
        model.addAttribute("totalBooks", bookService.countBooks());
        model.addAttribute("totalStaff", staffService.countStaff());
        
        return "admin/admin-dashboard";
    }
    
    // ========== STAFF CRUD OPERATIONS ==========
    
    @GetMapping("/staff")
    public String adminStaffPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "sort", defaultValue = "firstName") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Staff> staff;
        
        if (search != null && !search.trim().isEmpty()) {
            staff = staffService.searchStaff(search, pageable);
        } else if (position != null && !position.trim().isEmpty()) {
            staff = staffService.findByPosition(position, pageable);
        } else {
            staff = staffService.findAllStaff(pageable);
        }
        
        model.addAttribute("staff", staff);
        model.addAttribute("search", search);
        model.addAttribute("position", position);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        
        return "admin/admin-staff";
    }
    
    @PostMapping("/staff")
    @ResponseBody
    public ResponseEntity<?> createStaff(@RequestBody Staff staff) {
        try {
            Staff savedStaff = staffService.saveStaff(staff);
            return ResponseEntity.ok(savedStaff);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating staff: " + e.getMessage());
        }
    }
    
    @PutMapping("/staff/{id}")
    @ResponseBody
    public ResponseEntity<?> updateStaff(@PathVariable Integer id, @RequestBody Staff staff) {
        try {
            Optional<Staff> existingStaff = staffService.findStaffById(id);
            if (existingStaff.isPresent()) {
                staff.setId(id);
                Staff updatedStaff = staffService.saveStaff(staff);
                return ResponseEntity.ok(updatedStaff);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating staff: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/staff/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteStaff(@PathVariable Integer id) {
        try {
            Optional<Staff> staff = staffService.findStaffById(id);
            if (staff.isPresent()) {
                staffService.deleteStaff(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting staff: " + e.getMessage());
        }
    }
    
    @GetMapping("/staff/{id}")
    @ResponseBody
    public ResponseEntity<Staff> getStaff(@PathVariable Integer id) {
        Optional<Staff> staff = staffService.findStaffById(id);
        return staff.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    // ========== BULK OPERATIONS ==========
    
    @PostMapping("/staff/bulk-delete")
    @ResponseBody
    public ResponseEntity<?> bulkDeleteStaff(@RequestBody List<Integer> staffIds) {
        try {
            for (Integer id : staffIds) {
                staffService.deleteStaff(id);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting staff: " + e.getMessage());
        }
    }
    
    // ========== STATISTICS ==========
    
    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<?> getStats() {
        try {
            long totalBooks = bookService.countBooks();
            long availableBooks = bookService.countAvailableBooks();
            long totalStaff = staffService.countStaff();
            
            return ResponseEntity.ok(new Object() {
                public final long totalBooksCount = totalBooks;
                public final long availableBooksCount = availableBooks;
                public final long totalStaffCount = totalStaff;
                public final long unavailableBooksCount = totalBooks - availableBooks;
            });
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error getting stats: " + e.getMessage());
        }
    }
}
