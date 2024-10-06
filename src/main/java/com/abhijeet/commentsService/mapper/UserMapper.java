package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.UserRequestDTO;
import com.abhijeet.commentsService.models.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User fromDTO(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setUsername(userRequestDTO.getUsername());
        return user;
    }
}
