package com.abhijeet.commentsService.models.dto.response;

import lombok.Data;

@Data
public class ReactionResponseDTO {
    private Long entityId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
}
