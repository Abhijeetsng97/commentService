package com.abhijeet.commentsService.service;

import java.io.IOException;

public interface FieldIndexService {
    void index(String entityName, String fieldName, String fieldValue, String rowKey) throws IOException;

    void deleteIndex(String entityName, String fieldName, String fieldValue, String rowKey) throws IOException;

    String getRowKey(String entityName, String fieldName, String fieldValue) throws IOException;
}
