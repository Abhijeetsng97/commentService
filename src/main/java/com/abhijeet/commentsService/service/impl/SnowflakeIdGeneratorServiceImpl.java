package com.abhijeet.commentsService.service.impl;

import com.abhijeet.commentsService.service.SnowflakeIdGeneratorService;
import de.mkammerer.snowflakeid.SnowflakeIdGenerator;
import de.mkammerer.snowflakeid.options.Options;
import de.mkammerer.snowflakeid.structure.Structure;
import de.mkammerer.snowflakeid.time.MonotonicTimeSource;
import de.mkammerer.snowflakeid.time.TimeSource;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SnowflakeIdGeneratorServiceImpl implements SnowflakeIdGeneratorService {

    private SnowflakeIdGenerator generator;

    public SnowflakeIdGeneratorServiceImpl() {
        TimeSource timeSource = new MonotonicTimeSource(Instant.parse("2024-10-01T00:00:00Z"));
        Structure structure = new Structure(45, 2, 16);
        Options options = new Options(Options.SequenceOverflowStrategy.THROW_EXCEPTION);
        long generatorId = 1;
        generator = SnowflakeIdGenerator.createCustom(generatorId, timeSource, structure, options);
    }

    @Override
    public long getSnowflakeId() {
        return generator.next();
    }
}
