package com.stackhack.regapp.service;

import com.stackhack.regapp.model.User;
import com.stackhack.regapp.repository.UserRepository;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    public UserServiceImplementation() {
    }

    public boolean alreadyRegistered(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User addUser(User user) throws UserExists {
        if (this.userRepository.findByEmail(user.getEmail()) == null) {

            return (User)this.userRepository.save(user);
        } else {
            throw new UserExists();
        }
    }

    public User getUser(long registrationId) throws UserNotFound {
        return (User)this.userRepository.findById(registrationId).get();
    }
}
