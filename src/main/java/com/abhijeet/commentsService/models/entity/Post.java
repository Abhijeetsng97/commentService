package com.abhijeet.commentsService.models.entity;

import com.flipkart.hbaseobjectmapper.Family;
import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.flipkart.hbaseobjectmapper.HBTable;
import lombok.Data;

@HBTable(namespace = "default", name = "post",
    families = {
        @Family(name = "data"),
        @Family(name = "meta"),
        @Family(name = "engagement")
    }
)
@Data
public class Post implements HBRecord<String> {

    private Long id;

    @HBColumn(family = "data", column = "title")
    private String title;

    @HBColumn(family = "data", column = "content")
    private String content;

    @HBColumn(family = "data", column = "userId")
    private Long userId;

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
        return String.format("%s", id);
    }

    @Override
    public void parseRowKey(String s) {
        String[] split = s.split(":");
        this.id = Long.parseLong(split[0]);
    }
}
