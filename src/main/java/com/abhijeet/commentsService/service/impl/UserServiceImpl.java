package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.UserDTO;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.repository.UserRepository;
import com.abhijeet.commentsService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(UserDTO user) {
        return userRepository.save(convertToUser(user));
    }

    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public User updateUser(UserDTO user, long id) throws EntityNotFoundException {
        userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
        return userRepository.save(convertToUser(user));
    }

    @Override
    public void deleteUser(long id) throws EntityNotFoundException {
        userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
        userRepository.deleteById(id);
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
