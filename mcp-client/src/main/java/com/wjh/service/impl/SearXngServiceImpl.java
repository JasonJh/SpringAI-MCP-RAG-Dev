package com.wjh.service.impl;

import cn.hutool.json.JSONUtil;
import com.wjh.bean.SearXNGResponse;
import com.wjh.bean.SearchResult;
import com.wjh.service.SearXngService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearXngServiceImpl implements SearXngService {

    @Value("${internet.webSearch.searxng.url}")
    String SEARXNG_url;
    @Value("${internet.webSearch.searxng.counts}")
    Integer COUNTS;

    private final OkHttpClient okHttpClient;

    @Override
    public List<SearchResult> search(String query) {

        HttpUrl url = HttpUrl.get(SEARXNG_url)
                .newBuilder()
                .addQueryParameter("q",query)
                .addQueryParameter("format","json")
                .build();
        log.info("搜索的url地址为：{}",url.url());

        Request request = new Request.Builder()
                .url(url)
                .build();
        //发送请求
        try (Response response = okHttpClient.newCall(request).execute()){

            //如果失败直接抛出异常
            if (!response.isSuccessful()){
                throw new RuntimeException("请求失败：HTTP {}" + response.code());
            }

            //获得响应数据
            if (response.body() != null){
                String responseBody = response.body().string();
                SearXNGResponse searXNGResponse = JSONUtil.toBean(responseBody, SearXNGResponse.class);
                return delResults(searXNGResponse.getResults());
            }
            log.error("搜索失败：{}",response.message());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return List.of();
    }

    /**
     * 处理结果集，截取限制个数
     * @param results
     * @return
     */
    private List<SearchResult> delResults(List<SearchResult> results) {
        List<SearchResult> list =  results.subList(0,Math.min(COUNTS,results.size()))
                .parallelStream()
                .sorted(Comparator.comparing(SearchResult::getScore).reversed())
                .limit(COUNTS)
                .toList();
        return list;
    }
}
