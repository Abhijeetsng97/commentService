package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.models.entity.FieldIndex;
import com.abhijeet.commentsService.repository.FieldIndexRepository;
import com.abhijeet.commentsService.service.FieldIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FieldIndexServiceImpl implements FieldIndexService {

    private final FieldIndexRepository fieldIndexRepository;

    @Override
    public void index(String entityName, String fieldName, String fieldValue, String rowKey) throws IOException {
        FieldIndex fieldIndex = getBaseFieldIndex(entityName, fieldName, fieldValue);
        fieldIndex.setRowKey(rowKey);
        fieldIndexRepository.persist(fieldIndex);
    }

    @Override
    public void deleteIndex(String entityName, String fieldName, String fieldValue, String rowKey) throws IOException {
        FieldIndex fieldIndex = getBaseFieldIndex(entityName, fieldName, fieldValue);
        fieldIndexRepository.delete(fieldIndex.composeRowKey());
    }

    @Override
    public String getRowKey(String entityName, String fieldName, String fieldValue) throws IOException {
        FieldIndex fieldIndex = getBaseFieldIndex(entityName, fieldName, fieldValue);
        return fieldIndexRepository.get(fieldIndex.composeRowKey()).getRowKey();
    }

    private FieldIndex getBaseFieldIndex(String entityName, String fieldName, String fieldValue) {
        FieldIndex fieldIndex = new FieldIndex();
        fieldIndex.setEntityName(entityName);
        fieldIndex.setFieldName(fieldName);
        fieldIndex.setFieldValue(fieldValue);
        return fieldIndex;
    }
}
