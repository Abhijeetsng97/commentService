package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.mapper.UserMapper;
import com.abhijeet.commentsService.models.dto.request.UserRequestDTO;
import com.abhijeet.commentsService.models.entity.User;
import com.abhijeet.commentsService.repository.UserRepository;
import com.abhijeet.commentsService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.abhijeet.commentsService.constant.AppConstants.DELETED_DATA;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(UserRequestDTO user) {
        return userRepository.save(userMapper.fromDTO(user));
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(UserServiceImpl::getNonExistantUser);
    }

    public static User getNonExistantUser() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        user.setUsername(DELETED_DATA);
        return user;
    }
}
