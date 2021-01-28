package org.study.demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

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
public class IndexEmployee {

    private static final String INDEX_NAME = "employee";


    public static void createIndex() throws IOException {
        RestHighLevelClient client = EsClient.getEsClient();
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2)
//        );

        /**
         * 设置文档的类型 为json
         */
        Map<String, Object> message = new HashMap<>();
        message.put("type", "text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        request.mapping(mapping);

        client.indices().create(request, RequestOptions.DEFAULT);
        EsClient.close();
    }

    /**
     * 如果没有INDEX_NAME 则自动创建该索引
     *
     * @param employee
     */
    public static void indexDoc(Employee employee) {
        IndexRequest request = new IndexRequest(INDEX_NAME);
        request.id(employee.getId().toString());
        request.source(JSON.toJSONString(employee), XContentType.JSON);
        IndexResponse indexResponse;
        try {
            indexResponse= EsClient.getEsClient().index(request, RequestOptions.DEFAULT);
            log.info("index doc res:{}", JSON.toJSONString(indexResponse));
        } catch (IOException e) {
            log.error("索引文档异常", e);
            throw new RuntimeException(e);
        }
        /**
         * 返回值处理
         */
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            log.info("create index success");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            log.info("update index success");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                log.info("create index got error shard reason :{}", reason);
            }
        }
        EsClient.close();
    }

    public static void main(String[] args) {
        employee3();
    }

    private static void employee1() {
        indexDoc(Employee.builder()
                .id(1)
                .firstName("chenyao")
                .lastName("chenyao")
                .about("chen yao is a great man")
                .age(26)
                .hobbies(Arrays.asList(Hobby.MUSIC, Hobby.SWIM))
                .build());
    }
    private static void employee3() {
        indexDoc(Employee.builder()
                .id(3)
                .firstName("John")
                .lastName("Smith")
                .about("I love to go rock climbing")
                .age(26)
                .hobbies(Arrays.asList(Hobby.MUSIC, Hobby.SWIM))
                .build());
    }

    private static void employee2() {
        indexDoc(Employee.builder()
                .id(2)
                .firstName("zhangdi")
                .lastName("zhangdidi")
                .about("zhangdi  is a great man")
                .age(20)
                .hobbies(Arrays.asList(Hobby.MUSIC))
                .build());
    }
    private static void employee4() {
        indexDoc(Employee.builder()
                .id(4)
                .firstName("yinjiafu")
                .lastName("jiafu")
                .about("jiafu  is a yin ba wan")
                .age(20)
                .hobbies(Arrays.asList(Hobby.values()))
                .build());
    }
}
