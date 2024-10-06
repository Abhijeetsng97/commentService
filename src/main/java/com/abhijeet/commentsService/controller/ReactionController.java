package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.models.dto.request.ReactionRequestDTO;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/reaction")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<?> createLikeReactionForComment(@PathVariable Long commentId) throws IOException {
        ReactionRequestDTO reactionRequestDTO = ReactionRequestDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.LIKES).build();
        return new ResponseEntity<>(reactionService.createReaction(commentId, reactionRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> createDislikeReactionForComment(@PathVariable Long commentId) throws IOException {
        ReactionRequestDTO reactionRequestDTO = ReactionRequestDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.DISLIKES).build();
        return new ResponseEntity<>(reactionService.createReaction(commentId, reactionRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeReaction(@PathVariable Long commentId) throws IOException {
        ReactionRequestDTO reactionRequestDTO = ReactionRequestDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.LIKES).build();
        return new ResponseEntity<>(reactionService.deleteReaction(commentId, reactionRequestDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> deleteDislikeReaction(@PathVariable Long commentId) throws IOException {
        ReactionRequestDTO reactionRequestDTO = ReactionRequestDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.DISLIKES).build();
        return new ResponseEntity<>(reactionService.deleteReaction(commentId, reactionRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/comment/{commentId}/like")
    public ResponseEntity<?> getUserLikeReactionsList(@PathVariable Long commentId, @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(reactionService.getReactionUsers(ReactionEntityType.COMMENT, commentId, ReactionType.LIKES, nextToken), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> getUserDisLikeReactionsList(@PathVariable Long commentId, @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(reactionService.getReactionUsers(ReactionEntityType.COMMENT, commentId, ReactionType.DISLIKES, nextToken), HttpStatus.OK);
    }
}
