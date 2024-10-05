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

@RestController
@RequestMapping("/api/reaction")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping()
    public ResponseEntity<?> createReaction(@RequestBody @Valid ReactionDTO reactionDTO) {
        return new ResponseEntity<>(reactionService.createReaction(reactionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteReaction(@RequestBody @Valid ReactionDTO reactionDTO) {
        return new ResponseEntity<>(reactionService.deleteReaction(reactionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/{reactionType}")
    public ResponseEntity<?> getUserReactionsListForPost(@PathVariable Long postId, @PathVariable ReactionType reactionType) {
        return new ResponseEntity<>(reactionService.getUserList(ReactionEntityType.POST, postId, reactionType), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}/{reactionType}")
    public ResponseEntity<?> getUserReactionsListForComment(@PathVariable Long commentId, @PathVariable ReactionType reactionType) {
        return new ResponseEntity<>(reactionService.getUserList(ReactionEntityType.COMMENT, commentId, reactionType), HttpStatus.OK);
    }
}
