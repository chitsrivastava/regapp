package com.stackhack.regapp.controller;

import com.stackhack.regapp.model.Admin;
import com.stackhack.regapp.model.User;
import com.stackhack.regapp.service.AdminService;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import java.util.List;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping({"/admin"})
public class AdminController {
    @Autowired
    AdminService adminService;

    public AdminController() {
    }

    @GetMapping
    public List<Admin> getAllAdmin() {
        return this.adminService.getAllAdmin();
    }

    @PostMapping({"login/{username}"})
    public Admin getAdmin(@PathVariable String username, @RequestBody String password) {
        try {
            Admin admin =  this.adminService.getAdminByEmailAndPassword(username, password);
            return admin;
        } catch (UserNotFound var4) {
            return null;
        }
    }

    @PostMapping({"/{username}"})
    public Admin addAdmin(@PathVariable String username, @RequestBody String password) {
        try {
            return this.adminService.addAdmin(username, password);
        } catch (UserExists var4) {
            return null;
        }
    }

    @PutMapping("/approve/{email}")
    public boolean approveUser(@PathVariable String email){
        return adminService.approveUser(email);
    }

    @GetMapping("/approved/{approved}")
    public List<User> getUnapprovedUser(@PathVariable String approved){
        return adminService.getUserByApproved(Boolean.valueOf(approved));
    }
}
