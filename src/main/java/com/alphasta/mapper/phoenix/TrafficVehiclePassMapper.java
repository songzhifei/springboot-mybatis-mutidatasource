package com.alphasta.mapper.phoenix;


import com.alphasta.model.TrafficVehiclePass;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 何建哲 on 18-5-31.
 */

public interface TrafficVehiclePassMapper {

  @Select("select * from TRAFFIC_VEHICLE_PASS limit 10")
  List<TrafficVehiclePass> getList();
  @Select("<script>" +
            "select * from TRAFFIC_VEHICLE_PASS where ROWKEY in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=', ' close=')'>" +
              "#{item}" +
            "</foreach>" +
          "</script>")
  List<TrafficVehiclePass> getListByID(List<String> ids);

}
