package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.models.dto.request.CommentRequestDTO;
import com.abhijeet.commentsService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId,
                                         @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(commentService.getComments(postId, nextToken), HttpStatus.OK);
    }

    @GetMapping("/{commentId}/reply")
    public ResponseEntity<?> getReplies(@PathVariable Long commentId, @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<>(commentService.getReplies(commentId, nextToken), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> postComment(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable long postId) throws IOException {
        return new ResponseEntity<>(commentService.addCommentToPost(commentRequestDTO, postId), HttpStatus.CREATED);
    }

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<?> postReply(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Long commentId) throws IOException {
        return new ResponseEntity<>(commentService.addReply(commentRequestDTO, commentId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws IOException {
        return new ResponseEntity<>(commentService.deleteComment(commentId), HttpStatus.CREATED);
    }

}
