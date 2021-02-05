package org.study.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.study.rpc.ann.Rest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * @author chenyao
 * @date 2021/2/4 14:00
 * @description
 */
@Slf4j
public class RestRequest {

    public static Object invokeUrl(String url, Method method, Rest rest){

        log.info("request url:{}",url);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        try {
            HttpResponse httpResponse = client.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode == 200){
                InputStream content = httpResponse.getEntity().getContent();
                return IOUtils.toString(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
