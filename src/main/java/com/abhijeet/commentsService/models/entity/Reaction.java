package com.abhijeet.commentsService.models.entity;

import com.flipkart.hbaseobjectmapper.Family;
import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.flipkart.hbaseobjectmapper.HBTable;
import com.abhijeet.commentsService.models.enums.ReactionEntityType;
import com.abhijeet.commentsService.models.enums.ReactionType;
import lombok.Data;

@HBTable(namespace = "default", name = "reaction",
        families = {
                @Family(name = "meta")
        }
)
@Data
public class Reaction implements HBRecord<String> {
    private ReactionType reactionType;

    private ReactionEntityType reactionEntityType;

    private Long entitySeqId;

    private Long userId;

    private String id;

    private User user;

    @HBColumn(family = "meta", column = "createdAt")
    private Long createdAt;

    @HBColumn(family = "meta", column = "updatedAt")
    private Long updatedAt;

    @Override
    public String composeRowKey() {
        return String.format("%s:%s:%s:%s", reactionType, reactionEntityType, entitySeqId, userId);
    }

    @Override
    public void parseRowKey(String s) {
        String[] split = s.split(":");
        this.reactionType = ReactionType.valueOf(split[0]);
        this.reactionEntityType = ReactionEntityType.valueOf(split[1]);
        this.entitySeqId = Long.parseLong(split[2]);
        this.userId = Long.parseLong(split[3]);
    }

    public void updateRowKey() {
        setId(composeRowKey());
    }

    public static String getPrefixSearchKey(ReactionType reactionType, ReactionEntityType reactionEntityType, Long uniqSeq) {
        return String.format("%s:%s:%s", reactionType, reactionEntityType, uniqSeq);
    }
}
