package com.ec.libraryse.controller.admin;

import com.ec.libraryse.model.Staff;
import com.ec.libraryse.service.StaffService;
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
@RequestMapping("/admin/staff")
public class AdminStaffController {
    
    private final StaffService staffService;
    
    @Autowired
    public AdminStaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    
    @GetMapping
    public String adminStaffPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", defaultValue = "firstName") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            Model model) {
        
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Staff> staff;
        
        if (search != null && !search.trim().isEmpty()) {
            staff = staffService.searchStaff(search, pageable);
        } else {
            staff = staffService.findAllStaff(pageable);
        }
        
        model.addAttribute("staff", staff);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        
        return "admin/admin-staff";
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        try {
            Staff savedStaff = staffService.saveStaff(staff);
            return ResponseEntity.ok(savedStaff);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Staff> updateStaff(@PathVariable Integer id, @RequestBody Staff staff) {
        try {
            Optional<Staff> existingStaff = staffService.findStaffById(id);
            if (existingStaff.isPresent()) {
                staff.setId(id);
                Staff updatedStaff = staffService.saveStaff(staff);
                return ResponseEntity.ok(updatedStaff);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        try {
            Optional<Staff> staff = staffService.findStaffById(id);
            if (staff.isPresent()) {
                staffService.deleteStaff(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Staff> getStaff(@PathVariable Integer id) {
        Optional<Staff> staff = staffService.findStaffById(id);
        return staff.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
