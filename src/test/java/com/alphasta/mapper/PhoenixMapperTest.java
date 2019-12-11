package com.alphasta.mapper;

import com.alphasta.mapper.phoenix.TrafficVehiclePassMapper;
import com.alphasta.model.TrafficVehiclePass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoenixMapperTest {

	@Autowired
	private TrafficVehiclePassMapper trafficVehiclePassMapper;

	@Test
	public void testQuery() throws Exception {
		List<TrafficVehiclePass> trafficVehiclePasses = trafficVehiclePassMapper.getList();
		if(trafficVehiclePasses==null || trafficVehiclePasses.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(trafficVehiclePasses.size());
		}
	}


}