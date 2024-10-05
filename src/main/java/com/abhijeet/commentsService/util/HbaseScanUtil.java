package com.abhijeet.commentsService.util;

import lombok.experimental.UtilityClass;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

@UtilityClass
public class HbaseScanUtil {

    public static Scan getScanRequestWithPrefixAndLimit(String prefix, int limit) {
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(Bytes.toBytes(prefix)));
        scan.setMaxResultSize(limit);
        return scan;
    }

    public static Scan getScanRequestWithStartRowAndLimit(String startRow, int limit) {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setMaxResultSize(limit);
        return scan;
    }
}
