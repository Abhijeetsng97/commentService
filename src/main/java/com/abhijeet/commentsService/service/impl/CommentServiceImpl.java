package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.mapper.CommentMapper;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.flipkart.hbaseobjectmapper.Records;
import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.models.dto.response.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.repository.CommentRepository;
import com.abhijeet.commentsService.service.CommentService;
import com.abhijeet.commentsService.util.SearchResponseSupport;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.abhijeet.commentsService.constant.AppConstants.*;
import static com.abhijeet.commentsService.models.entity.Comment.getCommentFromRowKey;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithPrefixAndLimit;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithStartRowAndLimit;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final Map<Long, Map<String, Long>> inMemoryReactionCount = new ConcurrentHashMap<>();

    @Override
    public CommentResponseDTO addCommentToPost(CommentRequestDTO commentRequestDTO, long postId) throws IOException {
        Comment comment = commentMapper.fromDTO(commentRequestDTO, postId, TOP_LEVEL_COMMENT_PARENT_ID);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public SearchResponse<CommentResponseDTO> getComments(Long postId, String nextToken) throws IOException {
        return getComments(postId, TOP_LEVEL_COMMENT_PARENT_ID, nextToken);
    }

    @Override
    public SearchResponse<CommentResponseDTO> getReplies(Long commentId, String nextToken) throws IOException {
        Comment comment = getCommentFromRowKey(commentRepository.fetchRowKeyForId(commentId));
        return getComments(comment.getPostId(), comment.getId(), nextToken);
    }

    @Override
    public CommentResponseDTO addReply(CommentRequestDTO commentRequestDTO, Long commentId) throws IOException {
        String rowKey = commentRepository.fetchRowKeyForId(commentId);
        Comment parentComment = new Comment();
        parentComment.parseRowKey(rowKey);
        Comment comment = commentMapper.fromDTO(commentRequestDTO, parentComment.getPostId(), parentComment.getId());
        updateHasChildComments(rowKey);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public void updateReaction(Long commentId, String fieldName, Long i) {
        inMemoryReactionCount.putIfAbsent(commentId, new ConcurrentHashMap<>());
        inMemoryReactionCount.get(commentId).merge(fieldName, i, Long::sum);
    }

    @Override
    public Boolean deleteComment(Long commentId) throws IOException {
        Comment comment = commentRepository.get(commentRepository.fetchRowKeyForId(commentId));
        if(comment != null && ! comment.getContent().equals(DELETED_DATA)) {
            comment.setContent(DELETED_DATA);
            commentRepository.persist(comment);
            return true;
        }
        return false;
    }

    @Scheduled(initialDelay = 0L, fixedDelay = 5000L)
    private void updateReactions() throws IOException {
        Map<Long, Map<String, Long>> reactionCounterMap;
        synchronized (inMemoryReactionCount) {
            reactionCounterMap = new HashMap<>(inMemoryReactionCount);
            inMemoryReactionCount.clear();
        }
        for(Long commentId: reactionCounterMap.keySet()) {
            for(String reactionField: reactionCounterMap.get(commentId).keySet()) {
                System.out.println(reactionField + " " +  reactionCounterMap.get(commentId).get(reactionField));
                commentRepository.increment(
                        commentRepository.fetchRowKeyForId(commentId),
                        reactionField,
                        reactionCounterMap.get(commentId).get(reactionField)
                );
            }
        }
    }

    private SearchResponse<CommentResponseDTO> getComments(Long postId, Long uniqSeq, String nextToken) throws IOException {
        List<Comment> comments = new ArrayList<>();
        Records<Comment> records = commentRepository.records(getScanRequest(postId, uniqSeq, nextToken));
        records.forEach(comments::add);
        SearchResponse<Comment> searchResponse = SearchResponseSupport.createSearchReponseWithPageInfo(comments, MAX_DATA_FETCH_SIZE);
        SearchResponse<CommentResponseDTO> result = new SearchResponse<>();
        result.setData(searchResponse.getData().stream().map(x -> commentMapper.toDTO(x)).collect(Collectors.toList()));
        result.setNextToken(searchResponse.getNextToken());
        result.setHasMore(searchResponse.isHasMore());
        return result;
    }

    private void updateHasChildComments(String parentRowKey) throws IOException {
        Comment parentComment = commentRepository.get(parentRowKey);
        parentComment.setHasChildComments(true);
        parentComment.setUpdatedAt(System.currentTimeMillis());
        commentRepository.persist(parentComment);
    }

    private Scan getScanRequest(Long postId, Long commentId, String nextToken) {
        if (nextToken == null)
            return getScanRequestWithPrefixAndLimit(Comment.getPrefixSearchKey(postId, commentId), MAX_DATA_FETCH_SIZE + 1);
        else
            return getScanRequestWithStartRowAndLimit(nextToken, MAX_DATA_FETCH_SIZE + 1);
    }
}
