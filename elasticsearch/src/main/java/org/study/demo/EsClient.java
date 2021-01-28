package org.study.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author chenyao
 * @date 2021/1/28 16:39
 * @description
 */
@Slf4j
public class EsClient {

    private static final ThreadLocal<RestHighLevelClient> CLIENT_HOLDER = new ThreadLocal<>();
    private static final String ES_HOST = "localhost";
    private static final Integer ES_PORT = 9201;

    public static RestHighLevelClient getEsClient() {
        if (CLIENT_HOLDER.get() == null) {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(ES_HOST, ES_PORT, "http")));
            CLIENT_HOLDER.set(client);
            return client;
        }
        return CLIENT_HOLDER.get();
    }

    public static void close() {
        if (CLIENT_HOLDER.get() == null) {
            return;
        }
        try {
            CLIENT_HOLDER.get().close();
        } catch (IOException e) {
            log.error("es client error", e);
        } finally {
            CLIENT_HOLDER.remove();
        }
    }
}
