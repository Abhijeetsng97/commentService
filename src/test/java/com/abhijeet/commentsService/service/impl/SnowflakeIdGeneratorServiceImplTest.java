package com.abhijeet.commentsService.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class SnowflakeIdGeneratorServiceImplTest {

    @InjectMocks
    private SnowflakeIdGeneratorServiceImpl snowflakeIdGeneratorServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateId() {
        Long id1 = snowflakeIdGeneratorServiceImpl.getSnowflakeId();
        Long id2 = snowflakeIdGeneratorServiceImpl.getSnowflakeId();

        assertNotEquals(id1, id2);
    }

}