package com.librarysystem.service;

import com.librarysystem.model.Staff;
import com.librarysystem.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffService {
    
    private final StaffRepository staffRepository;
    
    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }
    
    public List<Staff> findAllStaff() {
        return staffRepository.findAll();
    }
    
    public Page<Staff> findAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }
    
    public Optional<Staff> findStaffById(Integer id) {
        return staffRepository.findById(id);
    }
    
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }
    
    public void deleteStaff(Integer id) {
        staffRepository.deleteById(id);
    }
    
    public List<Staff> searchStaff(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAllStaff();
        }
        return staffRepository.findByKeyword(keyword.trim());
    }
    
    public Page<Staff> searchStaff(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAllStaff(pageable);
        }
        return staffRepository.findByKeywordWithPagination(keyword.trim(), pageable);
    }
    
    public List<Staff> findStaffByPosition(String position) {
        return staffRepository.findByPosition(position);
    }
    
    public List<Staff> findStaffByFirstName(String firstName) {
        return staffRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
    
    public List<Staff> findStaffByLastName(String lastName) {
        return staffRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    
    public boolean existsByEmail(String email) {
        return staffRepository.findAll().stream()
                .anyMatch(staff -> staff.getEmail().equalsIgnoreCase(email));
    }
    
    public Page<Staff> findByPosition(String position, Pageable pageable) {
        return staffRepository.findByPosition(position, pageable);
    }
    
    public long countStaff() {
        return staffRepository.count();
    }
}
