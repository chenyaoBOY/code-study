package org.study;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author chenyao
 * @date 2021/1/28 13:35
 * @description
 */
@Slf4j
public class ClientTest {
    private static Logger logger = LoggerFactory.getLogger(ClientTest.class);

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static RestClient client;

    static {
        getClient();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        List<Node> nodes = client.getNodes();
        log.info("es nodes info:{}", JSON.toJSONString(nodes));

        requestSync();
        requestAsync();


        Thread.sleep(3000);
        client.close();
    }

    private static void requestAsync() {
        client.performRequestAsync(new Request(GET, "/shein"), new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                log.info("async success. response info {}", JSON.toJSONString(response));
            }

            @Override
            public void onFailure(Exception e) {
                log.info("async failure.", e);
            }
        });
    }

    private static void requestSync() throws IOException {
        Request request = new Request(GET, "/");
        request.addParameter("pretty", "true");
        Response response = client.performRequest(request);
        log.info("response entity:{}", EntityUtils.toString(response.getEntity()));
    }


    private static void wrapResponse(Response response) throws IOException {
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
    }


    public static void getClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9201, "http"));
        /**
         * Set the default headers that need to be sent with each request,
         * to prevent having to specify them with each single request
         */
//        将认证信息放在请求头中也可以
        Header[] defaultHeaders = new Header[]{new BasicHeader("Authorization", "Bearer u6iuAxZ0RG1Kcm5jVFI4eU4tZU9aVFEwT2F3")};
        builder.setDefaultHeaders(defaultHeaders);
        /**
         * Set a listener that gets notified every time a node fails,
         * in case actions need to be taken. Used internally when sniffing on failure is enabled.
         */
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                log.error("failure node :{}", JSON.toJSONString(node));
            }
        });

        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);

        /**
         * Set a callback that allows to modify the default request configuration
         */
        builder.setRequestConfigCallback(
                new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        /**
                         * 设置request超时时间
                         */
                        return requestConfigBuilder
                                .setSocketTimeout(10000)
                                .setConnectTimeout(5000);
                    }
                });

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user", "password"));

        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                return httpAsyncClientBuilder
                        /**
                         * 设置异步回调的线程数
                         */
                        .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(3).build())
                        /**
                         * 认证信息
                         */
                        .setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        //Set a callback that allows to modify the http client configuration
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"));
//            }
//        });
        client = builder.build();
    }
}
