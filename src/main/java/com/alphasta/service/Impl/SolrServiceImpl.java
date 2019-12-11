package com.alphasta.service.Impl;

import com.alphasta.model.SearchResult;
import com.alphasta.service.SolrService;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class SolrServiceImpl implements SolrService {

    @Autowired
    private SolrClient solrClient;

    public void searchItem(){
        try {
            /**创建 query 查询对象
             * SolrQuery()：先创建对象，后设置查询条件
             * SolrQuery(String q)：创建对象的同时设置查询条件
             * SolrQuery 继承于 org.apache.solr.common.params.SolrParams*/
            SolrQuery query = new SolrQuery();

            query.setQuery("PASS_TIME:[2018-05-20 TO 2018-05-21]");

            query.setQuery("PLATE_NO:冀AW122Y");

            query.setStart(0);

            query.setRows(10);

            QueryResponse response = solrClient.query(query);

            SolrDocumentList solrDocumentList = response.getResults();

            System.out.println("Solr 检索文档总数 >>> " + solrDocumentList.getNumFound());

            List<Get> getList = new ArrayList();

            for(SolrDocument solrDocument : solrDocumentList){
                String plate_no = solrDocument.getOrDefault("PLATE_NO","").toString();
                String pass_time = solrDocument.getOrDefault("PASS_TIME", "").toString();
                int crossing_id = (int) solrDocument.getOrDefault("CROSSING_ID", 0);
                String id = (String) solrDocument.getOrDefault("id", "");

                Get get = new Get(Bytes.toBytes(id));

                getList.add(get);

                System.out.println("id:"+id+",plate_no:"+plate_no+",pass_time:"+pass_time+",crossing_id"+crossing_id);

            }
//            ReadFromHbaseTest readFromHbaseTest = new ReadFromHbaseTest();

//            readFromHbaseTest.read(getList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String importItemToIndex() throws Exception {
        return null;
    }

    @Override
    public SearchResult searchItem(String queryString, Integer page, Integer rows) throws Exception {
        return null;
    }
}
