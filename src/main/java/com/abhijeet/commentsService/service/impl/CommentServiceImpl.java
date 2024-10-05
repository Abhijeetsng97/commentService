package com.abhijeet.commentsService.service.impl;

import com.flipkart.hbaseobjectmapper.Records;
import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.CommentDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.repository.CommentRepository;
import com.abhijeet.commentsService.service.CommentService;
import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import com.abhijeet.commentsService.service.UserService;
import com.abhijeet.commentsService.util.HeaderUtil;
import com.abhijeet.commentsService.util.SearchResponseSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.abhijeet.commentsService.constant.AppConstants.MAX_DATA_FETCH_SIZE;
import static com.abhijeet.commentsService.constant.AppConstants.TOP_LEVEL_COMMENT_PARENT_ID;
import static com.abhijeet.commentsService.models.entity.Comment.getCommentFromId;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithPrefixAndLimit;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithStartRowAndLimit;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SnowflakeIdGeneratorService snowflakeIdGeneratorService;

    @Autowired
    private HeaderUtil headerUtil;


    @Autowired
    private UserService userService;

    @Override
    public Comment addCommentToPost(CommentDTO commentDTO, long postId) throws IOException {
        Comment comment = convertToComment(commentDTO, postId, TOP_LEVEL_COMMENT_PARENT_ID);
        commentRepository.persist(comment);
        return comment;
    }

    @Override
    public SearchResponse<Comment> getComments(Long postId, String nextToken) throws IOException {
        return getComments(postId, TOP_LEVEL_COMMENT_PARENT_ID, nextToken);
    }

    @Override
    public SearchResponse<Comment> getReplies(String commentId, String nextToken) throws IOException {
        Comment comment = getCommentFromId(commentId);
        return getComments(comment.getPostId(), comment.getUniqueSeq(), nextToken);
    }

    @Override
    public Comment addReply(CommentDTO commentDTO, String rowKey) throws IOException {
        Comment parentComment = new Comment();
        parentComment.parseRowKey(rowKey);
        Comment comment = convertToComment(commentDTO, parentComment.getPostId(), parentComment.getUniqueSeq());
        updateHasChildComments(rowKey);
        commentRepository.persist(comment);
        return comment;
    }

    @Override
    public void updateReaction(String id, String fieldName, Long i) throws IOException {
        commentRepository.increment(id, fieldName, i);
    }

    private SearchResponse<Comment> getComments(Long postId, Long uniqSeq, String nextToken) throws IOException {
        List<Comment> comments = new ArrayList<>();
        Records<Comment> citizens = commentRepository.records(getScanRequest(postId, uniqSeq, nextToken));
        citizens.forEach(x -> {
            try {
                x.setUser(userService.getUserById(x.getUserId()));
            } catch (EntityNotFoundException e) {
                x.setUser(userService.getDeletedUser());
            }
            x.updateRowKey();
            comments.add(x);
        });
        return SearchResponseSupport.createSearchReponseWithPageInfo(comments, MAX_DATA_FETCH_SIZE);
    }

    private void updateHasChildComments(String parentRowKey) throws IOException {
        Comment parentComment = commentRepository.get(parentRowKey);
        parentComment.setHasChildComments(true);
        parentComment.setUpdatedAt(System.currentTimeMillis());
        commentRepository.persist(parentComment);
    }

    private Comment convertToComment(CommentDTO commentDTO, long postId, long parentCommentId) {
        Comment comment = new Comment();
        comment.setParentCommentId(parentCommentId);
        comment.setUniqueSeq(snowflakeIdGeneratorService.getSnowflakeId());
        comment.setUserId(headerUtil.getUserId());
        comment.setPostId(postId);
        comment.setContent(commentDTO.getContent());
        comment.setHasChildComments(false);
        comment.setCreatedAt(System.currentTimeMillis());
        comment.setUpdatedAt(System.currentTimeMillis());
        comment.setLikesCount(0L);
        comment.setDislikesCount(0L);
        comment.setId(comment.composeRowKey());
        return comment;
    }

    private Scan getScanRequest(Long postId, Long commentId, String nextToken) {
        if (nextToken == null)
            return getScanRequestWithPrefixAndLimit(Comment.getPrefixSearchKey(postId, commentId), MAX_DATA_FETCH_SIZE + 1);
        else
            return getScanRequestWithStartRowAndLimit(nextToken, MAX_DATA_FETCH_SIZE + 1);
    }
}
