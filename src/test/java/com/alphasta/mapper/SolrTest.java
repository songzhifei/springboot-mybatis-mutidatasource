package com.alphasta.mapper;

import com.alphasta.mapper.phoenix.TrafficVehiclePassMapper;
import com.alphasta.model.TrafficVehiclePass;
import com.alphasta.service.HbaseService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrTest {
    @Autowired
    private SolrClient solrClient;

    @Autowired
    private TrafficVehiclePassMapper trafficVehiclePassMapper;

    @Autowired
    private HbaseService hbaseService;

    @Test
    public void testQuery() throws Exception {
        try {
            /**创建 query 查询对象
             * SolrQuery()：先创建对象，后设置查询条件
             * SolrQuery(String q)：创建对象的同时设置查询条件
             * SolrQuery 继承于 org.apache.solr.common.params.SolrParams*/
            SolrQuery query = new SolrQuery();

            query.set("fq","PASS_TIME:[2018-05-20 TO 2018-05-21]");

            //query.set("q","PLATE_NO:冀AW122Y");

            query.set("q","*:*");

            query.setStart(0);

            query.setRows(10);

            QueryResponse response = solrClient.query(query);

            SolrDocumentList solrDocumentList = response.getResults();

            System.out.println("Solr 检索文档总数 >>> " + solrDocumentList.getNumFound());

            ArrayList<String> ids = new ArrayList<String>();

            for(SolrDocument solrDocument : solrDocumentList){
//                String plate_no = solrDocument.getOrDefault("PLATE_NO","").toString();
//                String pass_time = solrDocument.getOrDefault("PASS_TIME", "").toString();
//                int crossing_id = (int) solrDocument.getOrDefault("CROSSING_ID", 0);
                String id = (String) solrDocument.getOrDefault("id", "");

                ids.add(id);

                //System.out.println("id:"+id+",plate_no:"+plate_no+",pass_time:"+pass_time+",crossing_id"+crossing_id);

            }

//            List<TrafficVehiclePass> list = hbaseService.getListRowkeyData("TRAFFIC_VEHICLE_PASS", ids, "cf1", "");

            List<TrafficVehiclePass> list = trafficVehiclePassMapper.getListByID(ids);
//            for (TrafficVehiclePass trafficVehiclePass:list){
//                System.out.println(trafficVehiclePass.getPLATE_NO());
//            }

            System.out.println(list.size());

        }catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
