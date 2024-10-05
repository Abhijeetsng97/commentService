package com.abhijeet.commentsService.repository;

import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import com.abhijeet.commentsService.models.entity.Post;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository extends AbstractHBDAO<String, Post> {

    @Autowired
    public PostRepository(Connection connection) {
        super(connection);
    }
}
