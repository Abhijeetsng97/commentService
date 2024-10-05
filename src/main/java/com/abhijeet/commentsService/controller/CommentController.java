package com.abhijeet.commentsService.controller;

import com.abhijeet.commentsService.models.dto.CommentDTO;
import com.abhijeet.commentsService.models.dto.SearchResponse;
import com.abhijeet.commentsService.models.entity.Comment;
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
    CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId,
                                         @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<SearchResponse<Comment>>(commentService.getComments(postId, nextToken), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<?> getReplies(@PathVariable Long postId, @PathVariable Long commentId,
                                        @RequestParam(value = "nextToken", required = false) String nextToken) throws IOException {
        return new ResponseEntity<SearchResponse<Comment>>(commentService.getReplies(postId, commentId, nextToken), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> postComment(@RequestBody CommentDTO commentDTO, @PathVariable long postId) throws IOException {
        return new ResponseEntity<Comment>(commentService.addCommentToPost(commentDTO, postId), HttpStatus.CREATED);
    }

    @PostMapping("/post/{postId}/commentKey/{rowKey}")
    public ResponseEntity<?> postReply(@RequestBody CommentDTO commentDTO, @PathVariable long postId,
                                       @PathVariable String rowKey) throws IOException {
        return new ResponseEntity<Comment>(commentService.addReply(commentDTO, postId, rowKey), HttpStatus.CREATED);
    }
}
