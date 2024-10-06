package com.abhijeet.commentsService.mapper;

import com.abhijeet.commentsService.models.dto.request.UserRequestDTO;
import com.abhijeet.commentsService.models.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void testFromDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstName("abc");
        userRequestDTO.setLastName("qwe");
        userRequestDTO.setUsername("asd");

        User user = userMapper.fromDTO(userRequestDTO);

        assertEquals("abc", user.getFirstName());
        assertEquals("qwe", user.getLastName());
        assertEquals("asd", user.getUsername());
    }
}