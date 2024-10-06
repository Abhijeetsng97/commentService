package com.abhijeet.commentsService.util;

import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.abhijeet.commentsService.models.entity.Comment;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.abhijeet.commentsService.models.dto.response.SearchResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Consumer;

@UtilityClass
public class SearchResponseSupport {
    public static <T> SearchResponse<T> createSearchReponseWithPageInfo(List<T> list, int maxDataFetchSize) {
        Integer dataListSize = Math.min(list.size(), maxDataFetchSize);
        SearchResponse<T> searchResponse = new SearchResponse<>();
        searchResponse.setHasMore(list.size() > maxDataFetchSize);
        if (searchResponse.isHasMore())
            searchResponse.setNextToken(((HBRecord<String>) list.get(dataListSize)).composeRowKey());
        searchResponse.setData(list.subList(0, dataListSize));
        return searchResponse;
    }
}
