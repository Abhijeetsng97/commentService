package com.abhijeet.commentsService.models.entity;

import com.flipkart.hbaseobjectmapper.Family;
import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.flipkart.hbaseobjectmapper.HBTable;
import lombok.Data;

@HBTable(namespace = "default", name = "comment",
    families = {
        @Family(name = "data"),
        @Family(name = "meta"),
        @Family(name = "engagement")
    }
)
@Data
public class Comment implements HBRecord<String> {

    private String rowKey;

    private User user;

    private Long id;

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

    @HBColumn(family = "engagement", column = "likesCount")
    private Long likesCount;

    @HBColumn(family = "engagement", column = "dislikesCount")
    private Long dislikesCount;


    @Override
    public String composeRowKey() {
        return String.format("%s:%s:%s", postId, parentCommentId, id);
    }

    @Override
    public void parseRowKey(String s) {
        String[] split = s.split(":");
        this.postId = Long.parseLong(split[0]);
        this.parentCommentId = Long.parseLong(split[1]);
        this.id = Long.parseLong(split[2]);
    }

    public void updateRowKey() {
        setRowKey(composeRowKey());
    }
}
