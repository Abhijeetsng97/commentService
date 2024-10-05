package com.abhijeet.commentsService.models.dto;

import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionDTO {
    @NotNull
    private ReactionType reactionType;
    @NotNull
    private ReactionEntityType reactionEntityType;
}
