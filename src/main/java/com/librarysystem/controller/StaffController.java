package com.librarysystem.controller;

import com.librarysystem.model.Staff;
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
@RequestMapping("/staff")
public class StaffController {
    
    private final StaffService staffService;
    
    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
    
    @GetMapping
    public String getAllStaff(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "sort", defaultValue = "firstName") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            Model model) {
        
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        
        Page<Staff> staff;
        
        if (search != null && !search.trim().isEmpty()) {
            staff = staffService.searchStaff(search, pageable);
        } else if (position != null && !position.trim().isEmpty()) {
            List<Staff> staffList = staffService.findStaffByPosition(position);
            model.addAttribute("staff", staffList);
            model.addAttribute("search", search);
            model.addAttribute("position", position);
            model.addAttribute("sort", sort);
            model.addAttribute("direction", direction);
            return "staff";
        } else {
            staff = staffService.findAllStaff(pageable);
        }
        
        model.addAttribute("staff", staff);
        model.addAttribute("search", search);
        model.addAttribute("position", position);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        
        return "staff";
    }
    
    @GetMapping("/{id}")
    public String getStaffById(@PathVariable Integer id, Model model) {
        Optional<Staff> staff = staffService.findStaffById(id);
        if (staff.isPresent()) {
            model.addAttribute("staff", staff.get());
            return "staff-detail";
        }
        return "redirect:/staff";
    }
    
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Staff>> getAllStaffApi() {
        List<Staff> staff = staffService.findAllStaff();
        return ResponseEntity.ok(staff);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Staff> getStaffByIdApi(@PathVariable Integer id) {
        Optional<Staff> staff = staffService.findStaffById(id);
        return staff.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<List<Staff>> searchStaffApi(@RequestParam String keyword) {
        List<Staff> staff = staffService.searchStaff(keyword);
        return ResponseEntity.ok(staff);
    }
}
