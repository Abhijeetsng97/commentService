package com.abhijeet.commentsService.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse<T> {
    private boolean hasMore;
    private String nextToken;
    private List<T> data;
}
