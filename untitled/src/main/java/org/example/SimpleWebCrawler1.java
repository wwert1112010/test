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

public class SimpleWebCrawler1 {

    private static String base_url = "https://www.voanews.com";

    private static Proxy proxy;

    public static void main(String[] args) {
        String url = "https://www.voanews.com/immigration"; // 你要爬取的网页 URL
        // 设置 VPN 代理信息
        // 设置代理信息
        String proxyHost = "127.0.0.1";
        int proxyPort = 7890;

        try {
            // 创建代理对象
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            Document jsoupContent = request(proxy,url);

            // 使用 CSS 选择器查找第 6 个 <ul> 标签下的所有 <li> 标签
            Elements liElements = jsoupContent.select("ul").get(6).select("li");
            // 输出找到的 <li> 元素的文本内容
            for (Element liElement : liElements) {

                Elements a_tag = liElement.select("a");
                if (a_tag.size() > 0) {
                    String href = a_tag.attr("href");
                    execute(href);
                }
                System.out.println("***********************************************");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void execute(String path) throws IOException, InterruptedException {
        String a_url = base_url + path;
        System.out.println("href: " + a_url);
        Document document = request(proxy, a_url);
        Element divElement = document.selectFirst("div.publishing-details");
        Element content = document.getElementById("article-content");
        Element titleEle = document.selectFirst("h1.title pg-title");
        if(titleEle!=null){
            System.out.println("Text: " + titleEle.text());
        }

        StringBuilder result_string = new StringBuilder();
        List<String> authors = new ArrayList<>();

        if (content!= null) {
            Elements all_p = content.select("p");
            for (Element element : all_p) {
                result_string.append(element.text().replaceAll("\\xa0", ""));
            }
            System.out.println("content: " + result_string);
        }
        if(divElement!=null){
            Elements li_list = divElement.select("li");
            Elements time_tag = divElement.select("time");
            String datetime = time_tag.attr("datetime");
            System.out.println("datetime: " + datetime);
            for (Element element : li_list) {
                authors.add(element.text().trim());
            }
            System.out.println("authors: " + authors);
        }
        Thread.sleep(1000);

    }


    private static Document request(Proxy proxy, String url) throws IOException {
        // 使用 Jsoup 进行网页爬取，并通过 proxy 方法设置代理
        return Jsoup.connect(url)
                .proxy(proxy)
                .get();
    }


}
