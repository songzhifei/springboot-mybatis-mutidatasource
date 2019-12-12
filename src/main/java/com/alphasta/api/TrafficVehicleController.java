package com.alphasta.api;

import com.alphasta.mapper.phoenix.TrafficVehiclePassMapper;
import com.alphasta.model.TrafficVehiclePass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrafficVehicleController {

    @Autowired
    private TrafficVehiclePassMapper trafficVehiclePassMapper;

    @RequestMapping("/getList")
    public List<TrafficVehiclePass> getTrafficVehiclePasses() {
        List<TrafficVehiclePass> trafficVehiclePasses=trafficVehiclePassMapper.getList();
        return trafficVehiclePasses;
    }

}
