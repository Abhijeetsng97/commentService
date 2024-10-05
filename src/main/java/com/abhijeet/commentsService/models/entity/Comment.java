package com.abhijeet.commentsService.models.entity;

import com.flipkart.hbaseobjectmapper.Family;
import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.flipkart.hbaseobjectmapper.HBTable;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.Data;

import static com.abhijeet.commentsService.constant.AppConstants.DISLIKES_COUNT_FIELD;
import static com.abhijeet.commentsService.constant.AppConstants.LIKES_COUNT_FIELD;

@HBTable(namespace = "default", name = "comment",
    families = {
        @Family(name = "data"),
        @Family(name = "meta"),
        @Family(name = "engagement")
    }
)
@Data
public class Comment implements HBRecord<String> {

    private String id;

    private User user;

    private Long uniqueSeq;

    private Long parentCommentId;

    private Long postId;

    @HBColumn(family = "data", column = "content")
    private String content;

    @HBColumn(family = "data", column = "userId")
    private Long userId;

    @HBColumn(family = "data", column = "hasChildComments")
    private Boolean hasChildComments;

    @HBColumn(family = "meta", column = "createdAt")
    private Long createdAt;

    @HBColumn(family = "meta", column = "updatedAt")
    private Long updatedAt;

    @HBColumn(family = "engagement", column = LIKES_COUNT_FIELD)
    private Long likesCount;

    @HBColumn(family = "engagement", column = DISLIKES_COUNT_FIELD)
    private Long dislikesCount;

    @Override
    public String composeRowKey() {
        return String.format("%s:%s:%s", postId, parentCommentId, uniqueSeq);
    }

    @Override
    public void parseRowKey(String s) {
        String[] split = s.split(":");
        this.postId = Long.parseLong(split[0]);
        this.parentCommentId = Long.parseLong(split[1]);
        this.uniqueSeq = Long.parseLong(split[2]);
    }

    public void updateRowKey() {
        setId(composeRowKey());
    }

    public static Comment getCommentFromId(String id) {
        Comment comment = new Comment();
        comment.parseRowKey(id);
        comment.updateRowKey();
        return comment;
    }

    public static String getPrefixSearchKey(Long postId, Long parentCommentId) {
        return String.format("%s:%s", postId, parentCommentId);
    }
}
