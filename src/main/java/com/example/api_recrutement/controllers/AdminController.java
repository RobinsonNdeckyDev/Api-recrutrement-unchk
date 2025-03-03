package com.example.api_recrutement.controllers;

import com.example.api_recrutement.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api_recrutement.models.Admin;
import com.example.api_recrutement.dtos.AdminDTO;
import com.example.api_recrutement.mappers.AdminMapper;

import java.util.List;

// Controller for managing administrators
@RestController
@RequestMapping("/api/admins")
public class AdminController {
    public final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Retrieve all administrators
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // Retrieve an administrator by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new administrator
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminDTO adminDTO) {
        // Convert AdminDTO to Admin
        Admin admin = AdminMapper.INSTANCE.toAdmin(adminDTO);
        Admin createdAdmin = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    // Update an existing administrator
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        // Convert AdminDTO to Admin
        Admin admin = AdminMapper.INSTANCE.toAdmin(adminDTO);
        Admin updatedAdmin = adminService.updateAdmin(id, admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete an administrator by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}