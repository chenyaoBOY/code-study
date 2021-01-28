package org.study;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyao
 * @date 2021/1/28 15:00
 * @description
 */
@Slf4j
public class HighLevelClient {

    static RestHighLevelClient CLIENT ;
    static {
        init();
    }

    public static void main(String[] args) throws IOException {
//        getApi();
        searchApi();
    }

    private static void searchApi() throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest("shein");
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        log.info("SearchResponse info {}", JSON.toJSONString(response));

        SearchHits hits = response.getHits();
        SearchHit[] hitsArr = hits.getHits();
        for (SearchHit hit : hitsArr) {
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            String documentTitle = (String) sourceAsMap.get("title");
//            List<Object> users = (List<Object>) sourceAsMap.get("user");
//            Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
        }


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        CLIENT.close();
    }

    private static void getApi() throws IOException {
        GetRequest request = new GetRequest("shein", "1");
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        String[] includes = new String[]{"message", "*"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);


        GetResponse response = CLIENT.get(request, RequestOptions.DEFAULT);

        log.info("response info:{}",response.getFields());


        CLIENT.close();
    }

    public static void init(){
        CLIENT= new RestHighLevelClient( RestClient.builder(new HttpHost("localhost", 9201, "http")));
    }
}
