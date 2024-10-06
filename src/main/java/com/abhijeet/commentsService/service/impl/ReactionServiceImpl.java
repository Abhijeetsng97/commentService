package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.mapper.ReactionMapper;
import com.abhijeet.commentsService.models.dto.request.ReactionRequestDTO;
import com.abhijeet.commentsService.models.dto.response.CommentResponseDTO;
import com.abhijeet.commentsService.models.dto.response.ReactionResponseDTO;
import com.abhijeet.commentsService.models.dto.response.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
import com.abhijeet.commentsService.models.entity.Reaction;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.repository.ReactionRepository;
import com.abhijeet.commentsService.service.CommentService;
import com.abhijeet.commentsService.service.ReactionService;
import com.abhijeet.commentsService.util.SearchResponseSupport;
import com.flipkart.hbaseobjectmapper.Records;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.abhijeet.commentsService.constant.AppConstants.MAX_DATA_FETCH_SIZE;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithPrefixAndLimit;
import static com.abhijeet.commentsService.util.HbaseScanUtil.getScanRequestWithStartRowAndLimit;

@RequiredArgsConstructor
@Service
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;
    private final CommentService commentService;

    @Override
    public Boolean createReaction(Long commentId, ReactionRequestDTO reactionRequestDTO) throws IOException {
        Reaction reaction = reactionMapper.fromDTO(commentId, reactionRequestDTO);
        Boolean isPresent = reactionRepository.get(reaction.composeRowKey()) != null;
        if ( ! isPresent) {
            reactionRepository.persist(reaction);
            commentService.updateReaction(commentId, reactionRequestDTO.getReactionType().getFieldName(), 1L);
        }
        return ! isPresent;
    }

    @Override
    public Boolean deleteReaction(Long commentId, ReactionRequestDTO reactionRequestDTO) throws IOException {
        Reaction reaction = reactionMapper.fromDTO(commentId, reactionRequestDTO);
        Boolean isPresent = reactionRepository.get(reaction.composeRowKey()) != null;
        if (isPresent) {
            reactionRepository.delete(reaction.composeRowKey());
            commentService.updateReaction(commentId, reactionRequestDTO.getReactionType().getFieldName(), -1L);
        }
        return isPresent;
    }

    @Override
    public SearchResponse<ReactionResponseDTO> getReactionUsers(ReactionEntityType reactionEntityType, Long commentId, ReactionType reactionType, String nextToken) throws IOException {
        List<Reaction> reactions = new ArrayList<>();
        Records<Reaction> records = reactionRepository.records(getScanRequest(reactionEntityType, commentId, reactionType, nextToken));
        records.forEach(x -> reactions.add(x));
        SearchResponse<Reaction> searchResponse = SearchResponseSupport.createSearchReponseWithPageInfo(reactions, MAX_DATA_FETCH_SIZE);
        SearchResponse<ReactionResponseDTO> result = new SearchResponse<>();
        result.setData(searchResponse.getData().stream().map(reactionMapper::toDTO).collect(Collectors.toList()));
        result.setNextToken(searchResponse.getNextToken());
        result.setHasMore(searchResponse.isHasMore());
        return result;
    }

    private Scan getScanRequest(ReactionEntityType reactionEntityType, Long uniqSeq, ReactionType reactionType, String nextToken) {
        if (nextToken == null)
            return getScanRequestWithPrefixAndLimit(Reaction.getPrefixSearchKey(reactionType, reactionEntityType, uniqSeq), MAX_DATA_FETCH_SIZE + 1);
        else
            return getScanRequestWithStartRowAndLimit(nextToken, MAX_DATA_FETCH_SIZE + 1);
    }
}
