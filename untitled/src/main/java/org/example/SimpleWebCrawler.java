package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SimpleWebCrawler {

    public static void main(String[] args) {
        String url = "https://www.voanews.com/china"; // 你要爬取的网页 URL

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String pro = "127.0.0.1";//本机地址
            int pro1 = 7890; //代理端口号

            //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
            HttpHost proxy = new HttpHost(pro, pro1);

            // 创建 POST 请求
            HttpGet httpPost = new HttpGet(url);

            //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
            // 创建RequestConfig对象，将代理主机配置进去
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();

            // 设置请求头部
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setConfig(config);


            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应实体
            HttpEntity responseEntity = response.getEntity();

            // 解析响应内容
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println(responseString);

                // 处理JSON响应
                // 这里可以使用Jackson或其他JSON库解析responseString
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
