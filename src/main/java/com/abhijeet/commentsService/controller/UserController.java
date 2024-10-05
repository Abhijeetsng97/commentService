package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.UserDTO;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long userId) throws EntityNotFoundException {
        return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserDTO user) throws EntityNotFoundException {
        return new ResponseEntity<User>(userService.updateUser(user,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted Successfully.", HttpStatus.OK);
    }
}
