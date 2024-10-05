package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.UserDTO;
import com.abhijeet.commentsService.models.entity.User;


public interface UserService {
    User saveUser(UserDTO user);

    User getUserById(long id) throws EntityNotFoundException;

    User updateUser(UserDTO user, long id) throws EntityNotFoundException;

    void deleteUser(long id) throws EntityNotFoundException;
}
