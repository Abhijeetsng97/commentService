package com.abhijeet.commentsService.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequestDTO {
    @NotBlank()
    private String title;

    @NotBlank()
    private String content;
}
