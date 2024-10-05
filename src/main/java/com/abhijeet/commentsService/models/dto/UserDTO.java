package com.abhijeet.commentsService.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank()
    private String username;

    @NotBlank()
    private String firstName;

    @NotBlank()
    private String lastName;
}
