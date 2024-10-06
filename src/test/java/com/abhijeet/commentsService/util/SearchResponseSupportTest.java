package com.abhijeet.commentsService.util;

import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class SearchResponseSupportTest {

    @Test
    public void testCreateSearchResponse_EmptyList() {
        List<Comment> dataList = new ArrayList<>();
        int maxDataFetchSize = 5;

        SearchResponse<Comment> response = SearchResponseSupport.createSearchReponseWithPageInfo(dataList, maxDataFetchSize);

        assertNotNull(response);
        assertFalse(response.isHasMore());
        assertNull(response.getNextToken());
        assertEquals(0, response.getData().size());
    }

    @Test
    public void testCreateSearchResponse_EqualToMaxDataFetchSize() {
        List<Comment> dataList = new ArrayList<>();
        dataList.add(Comment.getCommentFromId("1:2:3"));
        dataList.add(Comment.getCommentFromId("11:23:34"));
        dataList.add(Comment.getCommentFromId("12:22:31"));
        int maxDataFetchSize = 1;

        SearchResponse<Comment> response = SearchResponseSupport.createSearchReponseWithPageInfo(dataList, maxDataFetchSize);

        assertNotNull(response);
        assertTrue(response.isHasMore());
        assertNotNull(response.getNextToken());
        assertEquals(maxDataFetchSize, response.getData().size());
    }

    @Test
    public void testCreateSearchResponse_EqualToMaxDataSize() {
        List<Comment> dataList = new ArrayList<>();
        dataList.add(Comment.getCommentFromId("1:2:3"));
        dataList.add(Comment.getCommentFromId("11:23:34"));
        dataList.add(Comment.getCommentFromId("12:22:31"));
        int maxDataFetchSize = 3;

        SearchResponse<Comment> response = SearchResponseSupport.createSearchReponseWithPageInfo(dataList, maxDataFetchSize);

        assertNotNull(response);
        assertFalse(response.isHasMore());
        assertNull(response.getNextToken());
        assertEquals(maxDataFetchSize, response.getData().size());
    }
}