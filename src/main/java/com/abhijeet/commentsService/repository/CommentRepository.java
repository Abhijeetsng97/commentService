package com.abhijeet.commentsService.repository;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.abhijeet.commentsService.models.entity.Comment;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends AbstractHBDAO<String, Comment> {

    @Autowired
    public CommentRepository(Connection connection) {
        super(connection);
    }
}
