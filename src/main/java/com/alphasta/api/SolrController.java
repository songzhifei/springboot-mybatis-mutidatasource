package com.alphasta.api;

import com.alphasta.mapper.phoenix.TrafficVehiclePassMapper;
import com.alphasta.model.TrafficVehiclePass;
import com.alphasta.service.HbaseService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/solr")
public class SolrController {
    @Autowired
    private SolrClient solrClient;

    @Autowired
    private HbaseService hbaseService;

    @Autowired
    private TrafficVehiclePassMapper trafficVehiclePassMapper;

    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public List<TrafficVehiclePass> getList(@RequestParam(value = "keyword",required = false) String keyword,
                                            @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
                                            @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                            @RequestParam(value = "startTime",required = true) String startTime,
                                            @RequestParam(value = "endTime",required = true) String endTime){
        try {
            long startSystemTime = System.currentTimeMillis();
            /**创建 query 查询对象
             * SolrQuery()：先创建对象，后设置查询条件
             * SolrQuery(String q)：创建对象的同时设置查询条件
             * SolrQuery 继承于 org.apache.solr.common.params.SolrParams*/
            SolrQuery query = new SolrQuery();

            query.set("fq",String.format("PASS_TIME:[%s TO %s]",startTime,endTime));

            if(!StringUtils.isEmpty(keyword)){
                query.set("q",String.format("PLATE_NO:*%s*",keyword));
            }else{
                query.set("q","*:*");
            }

            query.setStart((pageIndex-1)*pageSize);

            query.setRows(pageSize);

            query.set("fl","id");

            QueryResponse response = solrClient.query(query);

            SolrDocumentList solrDocumentList = response.getResults();

            System.out.println("Solr 检索文档总数 >>> " + solrDocumentList.getNumFound());

            ArrayList<String> ids = new ArrayList<String>();

            for(SolrDocument solrDocument : solrDocumentList){

                String id = (String) solrDocument.getOrDefault("id", "");

                ids.add(id);

            }

            //List<TrafficVehiclePass> list = hbaseService.getListRowkeyData("TRAFFIC_VEHICLE_PASS", ids, "cf1", "");

            List<TrafficVehiclePass> list = trafficVehiclePassMapper.getListByID(ids);

            System.out.println(list.size());
            System.out.println("本次查询耗时："+ (System.currentTimeMillis() - startSystemTime));
            return list;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
