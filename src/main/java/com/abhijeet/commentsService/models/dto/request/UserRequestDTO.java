package com.abhijeet.commentsService.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank()
    private String username;

    @NotBlank()
    private String firstName;

    @NotBlank()
    private String lastName;
}
