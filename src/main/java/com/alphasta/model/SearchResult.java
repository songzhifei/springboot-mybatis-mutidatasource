package com.alphasta.model;

import java.util.List;

public class SearchResult {
    //总条数
    private Long recordCount;
    //数据集合
    private List<OrderEntity> itemList;
    //总页数
    private Integer pageCount;
    //当前页
    private Integer curPage;
}
