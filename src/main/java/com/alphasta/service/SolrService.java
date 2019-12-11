package com.alphasta.service;

import com.alphasta.model.SearchResult;

public interface SolrService {
    String importItemToIndex() throws Exception ;
    SearchResult searchItem(String queryString, Integer page, Integer rows) throws Exception;

}
