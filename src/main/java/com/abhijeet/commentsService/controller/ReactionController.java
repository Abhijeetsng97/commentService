package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.models.dto.ReactionDTO;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import com.abhijeet.commentsService.service.ReactionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createLikeReactionForComment(@PathVariable String commentId) throws IOException {
        ReactionDTO reactionDTO = ReactionDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.LIKES).build();
        return new ResponseEntity<>(reactionService.createReaction(commentId, reactionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> createDislikeReactionForComment(@PathVariable String commentId) throws IOException {
        ReactionDTO reactionDTO = ReactionDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.DISLIKES).build();
        return new ResponseEntity<>(reactionService.createReaction(commentId, reactionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeReaction(@PathVariable String commentId) throws IOException {
        ReactionDTO reactionDTO = ReactionDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.LIKES).build();
        return new ResponseEntity<>(reactionService.deleteReaction(commentId, reactionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> deleteDislikeReaction(@PathVariable String commentId) throws IOException {
        ReactionDTO reactionDTO = ReactionDTO.builder().reactionEntityType(ReactionEntityType.COMMENT).reactionType(ReactionType.DISLIKES).build();
        return new ResponseEntity<>(reactionService.deleteReaction(commentId, reactionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/comment/{commentId}/like")
    public ResponseEntity<?> getUserLikeReactionsList(@PathVariable String commentId, @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(reactionService.getUserList(ReactionEntityType.COMMENT, commentId, ReactionType.LIKES, nextToken), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/dislike")
    public ResponseEntity<?> getUserDisLikeReactionsList(@PathVariable String commentId, @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(reactionService.getUserList(ReactionEntityType.COMMENT, commentId, ReactionType.DISLIKES, nextToken), HttpStatus.OK);
    }
}
