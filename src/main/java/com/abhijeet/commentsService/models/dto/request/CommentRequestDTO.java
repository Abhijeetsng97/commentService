package com.abhijeet.commentsService.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentRequestDTO {
    @NotBlank
    private String content;

}
