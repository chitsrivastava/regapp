package com.stackhack.regapp.service;

import com.stackhack.regapp.model.Admin;
import com.stackhack.regapp.model.User;
import com.stackhack.regapp.repository.AdminRepository;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import java.util.List;

import com.stackhack.regapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImplementation implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    public AdminServiceImplementation() {
    }

    public List<Admin> getAllAdmin() {
        return this.adminRepository.findAll();
    }

    @Override
    public boolean approveUser(String email) {
        try{
            User user = userRepository.findByEmail(email);
            if(user!=null){
                user.setApproved(true);
                userRepository.save(user);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public Admin getAdminByEmailAndPassword(String username, String password) throws UserNotFound {
        if (this.adminRepository.findByEmailAndPassword(username, password) == null) {
            throw new UserNotFound();
        } else {
            return this.adminRepository.findByEmailAndPassword(username, password);
        }
    }

    public Admin addAdmin(String username, String password) throws UserExists {
        Admin admin = this.adminRepository.findByEmail(username);
        if (admin==null) {
            return this.adminRepository.save(new Admin(username, password));
        } else {
            throw new UserExists();
        }
    }

    @Override
    public List<User> getUserByApproved(boolean isApproved) {
        return userRepository.findByIsApproved(isApproved);
    }
}
