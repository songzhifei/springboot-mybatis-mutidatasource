package com.alphasta.service;

import com.alphasta.model.TrafficVehiclePass;

import java.util.List;

public interface HbaseService {
    List<TrafficVehiclePass> getRowKeyAndColumn(String tableName, String startRowkey, String stopRowkey, String column, String qualifier);
    List<TrafficVehiclePass> getListRowkeyData(String tableName, List<String> rowKeys, String familyColumn, String column);
}
