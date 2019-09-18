package com.netopstec.extensible.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhenye 2019/9/16
 */
@Slf4j
public class HttpUtil {

    /**
     * HTTP---无参get方法
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * HTTP---有参get方法
     */
    public static String doGet(String url, String queryString) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url + (queryString == null ? "" : queryString));
            response = httpClient.execute(get);
            if (response != null) {
                String result = EntityUtils.toString(response.getEntity());
                if (response.getStatusLine().getStatusCode() != 200) {
                    log.error("error in HttpUtil ===> doGet:{}", result);
                    return "fail";
                }
                return result;
            }
        } catch (Exception e) {
            log.error("error in HttpUtil --> doGet: ", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("error in HttpUtil --> doGet: ", e);
            }
        }
        return "fail";
    }

    /**
     * HTTP---有参post方法
     */
    public static String doPost( String url, String query, String body) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url + (StringUtils.isNotEmpty(query) ? ("?" + query) : ""));
            // 构造消息头
            post.setHeader("Content-type", "application/json; charset=utf-8");
            // 构建消息实体
            StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);
            response = httpClient.execute(post);
            if (response != null) {
                String result = EntityUtils.toString(response.getEntity());
                if (response.getStatusLine().getStatusCode() != 200) {
                    log.error("error in HttpUtil ===> doPost:{}", result);
                    return "fail";
                }
                return result;
            }
        } catch (Exception e) {
            log.error("error in HttpUtil ===> doPost:", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("error in HttpUtil ===> doPost:", e);
            }
        }
        return "fail";
    }

}
