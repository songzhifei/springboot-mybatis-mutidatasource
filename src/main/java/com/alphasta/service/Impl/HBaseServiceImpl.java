package com.alphasta.service.Impl;

import com.alphasta.model.RowMapper.TrafficVehiclePassRowMapper;
import com.alphasta.model.TrafficVehiclePass;
import com.alphasta.service.HbaseService;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HBaseServiceImpl implements HbaseService {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    public List<TrafficVehiclePass> getRowKeyAndColumn(String tableName, String startRowkey, String stopRowkey, String column, String qualifier) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        if (StringUtils.isNotBlank(column)) {

            filterList.addFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
        }
        if (StringUtils.isNotBlank(qualifier)) {

            filterList.addFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(qualifier))));
        }
        Scan scan = new Scan();
        if (filterList.getFilters().size() > 0) {
            scan.setFilter(filterList);
        }
        scan.setStartRow(Bytes.toBytes(startRowkey));
        scan.setStopRow(Bytes.toBytes(stopRowkey));

        return hbaseTemplate.find(tableName, scan, new TrafficVehiclePassRowMapper());
    }

    public List<TrafficVehiclePass> getListRowkeyData(String tableName, List<String> rowKeys, String familyColumn, String column) {
        return rowKeys.stream().map(rk -> {
            if (StringUtils.isNotBlank(familyColumn)) {
                if (StringUtils.isNotBlank(column)) {
                    return hbaseTemplate.get(tableName, rk, familyColumn, column, new TrafficVehiclePassRowMapper());
                } else {
                    return hbaseTemplate.get(tableName, rk, familyColumn, new TrafficVehiclePassRowMapper());
                }
            }
            return hbaseTemplate.get(tableName, rk, new TrafficVehiclePassRowMapper());
        }).collect(Collectors.toList());
    }

}
