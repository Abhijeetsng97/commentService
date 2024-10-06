package com.abhijeet.commentsService.models.dto.request;

import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ReactionRequestDTO {
    @NotNull
    private ReactionType reactionType;
    @NotNull
    private ReactionEntityType reactionEntityType;
}
