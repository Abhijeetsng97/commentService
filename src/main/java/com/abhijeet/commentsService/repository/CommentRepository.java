package com.abhijeet.commentsService.repository;

import com.abhijeet.commentsService.service.FieldIndexService;
import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.abhijeet.commentsService.models.entity.Comment;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class CommentRepository extends AbstractHBDAO<String, Comment> {

    private final String ENTITY_NAME = "comments";
    private final String ID_FIELD = "id";
    private FieldIndexService fieldIndexService;

    @Autowired
    public CommentRepository(Connection connection, FieldIndexService fieldIndexService) {
        super(connection);
        this.fieldIndexService = fieldIndexService;
    }

    public void save(Comment comment) throws IOException {
        persist(comment);
        fieldIndexService.index(ENTITY_NAME, ID_FIELD, String.valueOf(comment.getId()), comment.composeRowKey());
    }

    public String fetchRowKeyForId(Long id) throws IOException {
        return fieldIndexService.getRowKey(ENTITY_NAME, ID_FIELD, String.valueOf(id));
    }
}
