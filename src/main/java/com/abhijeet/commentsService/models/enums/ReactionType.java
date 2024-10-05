package com.abhijeet.commentsService.models.enums;

import static com.abhijeet.commentsService.constant.AppConstants.DISLIKES_COUNT_FIELD;
import static com.abhijeet.commentsService.constant.AppConstants.LIKES_COUNT_FIELD;

public enum ReactionType {
    LIKES(LIKES_COUNT_FIELD),
    DISLIKES(DISLIKES_COUNT_FIELD);

    private final String fieldName;

    ReactionType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
