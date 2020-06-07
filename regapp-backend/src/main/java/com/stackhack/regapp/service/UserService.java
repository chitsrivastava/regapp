package com.stackhack.regapp.service;

import com.stackhack.regapp.model.User;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import java.util.List;

public interface UserService {
    boolean alreadyRegistered(String email);

    List<User> getAllUser();

    User addUser(User user) throws UserExists;

    User getUser(long registrationId) throws UserNotFound;
}
