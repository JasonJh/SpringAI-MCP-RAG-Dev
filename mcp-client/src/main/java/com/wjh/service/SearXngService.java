package com.wjh.service;


import com.wjh.bean.SearchResult;

import java.util.List;

public interface SearXngService {

    /**
     * 调用本地搜索引擎searxng进行搜索
     * @param query
     * @return
     */
    public List<SearchResult> search(String query);
}
