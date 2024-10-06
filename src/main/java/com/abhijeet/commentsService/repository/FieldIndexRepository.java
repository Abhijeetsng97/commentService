package com.abhijeet.commentsService.repository;

import com.abhijeet.commentsService.models.entity.FieldIndex;
import com.flipkart.hbaseobjectmapper.AbstractHBDAO;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FieldIndexRepository extends AbstractHBDAO<String, FieldIndex> {

    @Autowired
    public FieldIndexRepository(Connection connection) {
        super(connection);
    }
}
