package com.alphasta.web;

import com.alphasta.mapper.phoenix.TrafficVehiclePassMapper;
import com.alphasta.mapper.mysql.User1Mapper;
import com.alphasta.model.TrafficVehiclePass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private User1Mapper user1Mapper;

    @Autowired
    private TrafficVehiclePassMapper trafficVehiclePassMapper;
	
	@RequestMapping("/getList")
	public List<TrafficVehiclePass> getUsers() {
		List<TrafficVehiclePass> trafficVehiclePasses=trafficVehiclePassMapper.getList();
		return trafficVehiclePasses;
	}
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        user1Mapper.delete(id);
    }
    
}