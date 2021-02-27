package com.fagan.demoforfagan.controllers;

import com.fagan.demoforfagan.entities.Users;
import com.fagan.demoforfagan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets users by id.
     *
     * @param userId the user id
     * @return the users by id
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable(value = "id") Long userId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        if (usersOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(usersOptional.get());
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/users")
    public Users createUser(@RequestBody Users user) {
        return userRepository.save(user);
    }

    /**
     * Update user response entity.
     *
     * @param userId      the user id
     * @param userDetails the user details
     * @return the response entity
     */
    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(
            @PathVariable(value = "id") Long userId, @RequestBody Users userDetails) {

        Optional<Users> usersOptional = userRepository.findById(userId);
        if (usersOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Users user = usersOptional.get();
        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(new Date());
        final Users updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long userId) {
        Optional<Users> usersOptional = userRepository.findById(userId);
        if (usersOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userRepository.delete(usersOptional.get());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}