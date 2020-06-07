package com.stackhack.regapp.service;

import com.stackhack.regapp.model.Admin;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import com.stackhack.regapp.model.User;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmin();
    boolean approveUser(String email);
    Admin addAdmin(String username, String password) throws UserExists;
    List<User> getUserByApproved(boolean isApproved);
    Admin getAdminByEmailAndPassword(String username, String password)throws UserNotFound;
}
