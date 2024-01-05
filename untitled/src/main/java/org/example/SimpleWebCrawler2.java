package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class SimpleWebCrawler2 {

    private static String base_url = "https://www.voanews.com";

    private static Proxy proxy;

    public static void main(String[] args) {
        String url = "https://www.baidu.com"; // 你要爬取的网页 URL
        // 设置 VPN 代理信息
        // 设置代理信息
        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;

        try {
            // 创建代理对象
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            Document document = request(url);
            System.out.println(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private static Document request(Proxy proxy, String url) throws IOException {
        // 使用 Jsoup 进行网页爬取，并通过 proxy 方法设置代理
        return Jsoup.connect(url)
                .proxy(proxy)
                .get();
    }


    private static Document request( String url) throws IOException {
        // 使用 Jsoup 进行网页爬取，并通过 proxy 方法设置代理
        return Jsoup.connect(url)
                .get();
    }

}
