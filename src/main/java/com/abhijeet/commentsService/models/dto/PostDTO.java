package com.abhijeet.commentsService.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDTO {
    @NotBlank()
    private String title;

    @NotBlank()
    private String content;
}
