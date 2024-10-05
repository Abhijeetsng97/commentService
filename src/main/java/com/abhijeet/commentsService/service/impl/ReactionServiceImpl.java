package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.exception.EntityNotFoundException;
import com.abhijeet.commentsService.models.dto.ReactionDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.models.entity.Reaction;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.repository.ReactionRepository;
import com.abhijeet.commentsService.service.CommentService;
import com.abhijeet.commentsService.service.ReactionService;
import com.abhijeet.commentsService.service.UserService;
import com.abhijeet.commentsService.util.HeaderUtil;
import com.abhijeet.commentsService.util.SearchResponseSupport;
import com.flipkart.hbaseobjectmapper.Records;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.abhijeet.commentsService.constant.AppConstants.MAX_DATA_FETCH_SIZE;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithPrefixAndLimit;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithStartRowAndLimit;

@Slf4j
@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    private HeaderUtil headerUtil;

    @Override
    public Boolean createReaction(String commentKey, ReactionDTO reactionDTO) throws IOException {
        Comment comment = Comment.getCommentFromId(commentKey);
        Reaction reaction = convertToReaction(comment.getUniqueSeq(), reactionDTO);
        Boolean isPresent = reactionRepository.get(reaction.getId()) != null;
        if ( ! isPresent) {
            reactionRepository.persist(reaction);
            commentService.updateReaction(comment.getId(), reactionDTO.getReactionType().getFieldName(), 1L);
        }
        return ! isPresent;
    }

    @Override
    public Boolean deleteReaction(String commentKey, ReactionDTO reactionDTO) throws IOException {
        Comment comment = Comment.getCommentFromId(commentKey);
        Reaction reaction = convertToReaction(comment.getUniqueSeq(), reactionDTO);
        Boolean isPresent = reactionRepository.get(reaction.getId()) != null;
        if (isPresent) {
            reactionRepository.delete(reaction.composeRowKey());
            commentService.updateReaction(comment.getId(), reactionDTO.getReactionType().getFieldName(), -1L);
        }
        return isPresent;
    }

    @Override
    public SearchResponse<Reaction> getUserList(ReactionEntityType reactionEntityType, String commentId, ReactionType reactionType, String nextToken) throws IOException {
        Comment comment = Comment.getCommentFromId(commentId);
        List<Reaction> reactions = new ArrayList<>();
        Records<Reaction> citizens = reactionRepository.records(getScanRequest(reactionEntityType, comment.getUniqueSeq(), reactionType, nextToken));
        citizens.forEach(x -> {
            try {
                x.setUser(userService.getUserById(x.getUserId()));
            } catch (EntityNotFoundException e) {
                x.setUser(userService.getDeletedUser());
            }
            x.updateRowKey();
            reactions.add(x);
        });
        return SearchResponseSupport.createSearchReponseWithPageInfo(reactions, MAX_DATA_FETCH_SIZE);
    }

    private Reaction convertToReaction(Long commentId, ReactionDTO reactionDTO) {
        Reaction reaction = new Reaction();
        reaction.setReactionType(reactionDTO.getReactionType());
        reaction.setReactionEntityType(reactionDTO.getReactionEntityType());
        reaction.setUserId(headerUtil.getUserId());
        reaction.setEntitySeqId(commentId);
        reaction.setCreatedAt(System.currentTimeMillis());
        reaction.setUpdatedAt(System.currentTimeMillis());
        reaction.updateRowKey();
        return reaction;
    }

    private Scan getScanRequest(ReactionEntityType reactionEntityType, Long uniqSeq, ReactionType reactionType, String nextToken) {
        if (nextToken == null)
            return getScanRequestWithPrefixAndLimit(Reaction.getPrefixSearchKey(reactionType, reactionEntityType, uniqSeq), MAX_DATA_FETCH_SIZE + 1);
        else
            return getScanRequestWithStartRowAndLimit(nextToken, MAX_DATA_FETCH_SIZE + 1);
    }
}
