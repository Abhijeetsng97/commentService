package com.abhijeet.commentsService.models.entity;

import com.flipkart.hbaseobjectmapper.Family;
import com.flipkart.hbaseobjectmapper.HBColumn;
import com.flipkart.hbaseobjectmapper.HBRecord;
import com.flipkart.hbaseobjectmapper.HBTable;
import lombok.Data;

@HBTable(namespace = "default", name = "fieldIndex",
        families = {
                @Family(name = "data")
        }
)
@Data
public class FieldIndex implements HBRecord<String> {
    private String entityName;
    private String fieldName;
    private String fieldValue;

    @HBColumn(family = "data", column = "rowKey")
    private String rowKey;

    @Override
    public String composeRowKey() {
        return String.format("%s:%s:%s", entityName, fieldName, fieldValue);
    }

    @Override
    public void parseRowKey(String s) {
        String[] split = s.split(":");
        this.entityName = split[0];
        this.fieldName = split[1];
        this.fieldValue = split[2];
    }
}
