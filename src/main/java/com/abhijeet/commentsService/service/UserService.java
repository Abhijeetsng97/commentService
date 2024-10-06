package com.abhijeet.commentsService.service;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.request.UserRequestDTO;
import com.abhijeet.commentsService.models.entity.User;


public interface UserService {
    User saveUser(UserRequestDTO user);

    User getUserById(long id);
}
