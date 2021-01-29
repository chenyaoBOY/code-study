package org.study.demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/1/28 16:38
 * @description 创建员工的索引
 */
@Slf4j
public class QueryEmployee {

    private static final String INDEX_NAME = "employee";


    public static void main(String[] args) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("id",1));
        String dsl = clearBlank(boolQueryBuilder);

        searchEmployee(new QueryInfo(dsl,INDEX_NAME));
    }

    private static String clearBlank(BoolQueryBuilder boolQueryBuilder){
        return boolQueryBuilder.toString()
                .replaceAll("\n","")//换行
                .replaceAll(" ","")
                .replaceAll("\r\n","");//\r 回车
    }

    public static void searchEmployee(QueryInfo queryInfo) {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.wrapperQuery(queryInfo.getQueryDsl()));

        SearchRequest request = new SearchRequest(queryInfo.getIndexName());
        request.source(builder);

        SearchResponse response;
        try {
            response = EsClient.getEsClient().search(request, RequestOptions.DEFAULT);
            log.info("search response:{}", JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("search failure", e);
            throw new RuntimeException(e);
        }
        RestStatus status = response.status();
        if (!RestStatus.OK.equals(status)) {
            log.error("search failure code:{}", JSON.toJSONString(status));
            return;
        }
        TimeValue took = response.getTook();//耗时
        Boolean terminatedEarly = response.isTerminatedEarly();//是否提前终止
        boolean timedOut = response.isTimedOut();//是否超时
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Employee employee = JSON.parseObject(hit.getSourceAsString(), Employee.class);
            log.info("employee info:{}", employee);
        }

        EsClient.close();
    }

}
