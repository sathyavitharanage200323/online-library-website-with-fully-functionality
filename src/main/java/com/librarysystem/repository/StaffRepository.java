package com.librarysystem.repository;

import com.librarysystem.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    
    List<Staff> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Staff> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Staff> findByPositionContainingIgnoreCase(String position);
    
    @Query("SELECT s FROM Staff s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.position) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Staff> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT s FROM Staff s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.position) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Staff> findByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);
    
    Page<Staff> findAll(Pageable pageable);
    
    List<Staff> findByPosition(String position);
    
    Page<Staff> findByPosition(String position, Pageable pageable);
}
