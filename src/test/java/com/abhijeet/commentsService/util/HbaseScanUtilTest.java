package com.abhijeet.commentsService.util;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class HbaseScanUtilTest {

    @Test
    public void testGetScanRequestWithPrefixAndLimit() {
        String prefix = "testPrefix";
        int limit = 100;
        Scan scan = HbaseScanUtil.getScanRequestWithPrefixAndLimit(prefix, limit);
        assertNotNull(scan);
        assertEquals(limit, scan.getMaxResultSize());
        assertTrue(scan.getFilter() instanceof PrefixFilter);
        PrefixFilter filter = (PrefixFilter) scan.getFilter();
        assertArrayEquals(Bytes.toBytes(prefix), filter.getPrefix());
    }

    @Test
    public void testGetScanRequestWithStartRowAndLimit() {
        String startRow = "testStartRow";
        int limit = 50;
        Scan scan = HbaseScanUtil.getScanRequestWithStartRowAndLimit(startRow, limit);
        assertNotNull(scan);
        assertEquals(limit, scan.getMaxResultSize());
        assertArrayEquals(Bytes.toBytes(startRow), scan.getStartRow());
    }
}
