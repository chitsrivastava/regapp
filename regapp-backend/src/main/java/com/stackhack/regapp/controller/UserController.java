package com.stackhack.regapp.controller;

import com.stackhack.regapp.model.User;
import com.stackhack.regapp.exception.UserExists;
import com.stackhack.regapp.exception.UserNotFound;
import com.stackhack.regapp.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping({"/user"})
public class UserController {
    @Autowired
    UserService userService;

    public UserController() {
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> userList = this.userService.getAllUser().stream().map(user -> {user.setImage(decompressBytes(user.getImage())); return user;}).collect(Collectors.toList());
        return userList;
    }

    @GetMapping({"/{registration-id}"})
    public User getUser(@PathVariable(name = "registration-id") long registrationId) {
        try {

            return this.userService.getUser(registrationId);
        } catch (UserNotFound var4) {
            return null;
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public User addUser(@RequestPart("user") User user, @RequestPart("file") MultipartFile file) {
        try {
            user.setImage(compressBytes(file.getBytes()));
            User registered = this.userService.addUser(user);
            registered.setImage(decompressBytes(registered.getImage()));

            return registered;
        } catch (UserExists | IOException var3) {
            return null;
        }
    }

    @GetMapping({"/registered/{email}"})
    public boolean alreadyRegistered(@PathVariable String email) {
        return this.userService.alreadyRegistered(email);
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
