package com.abhijeet.commentsService.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {
    @NotBlank
    private String content;

}
