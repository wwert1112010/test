package org.example.simhash;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class OpenAIChatHttpClient {

    public static void main(String[] args) {
        String text = "一名卫生官员周六表示";
        String apiKey = "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";

        // 设置 API 请求URL
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建 POST 请求
            HttpPost httpPost = new HttpPost(apiUrl);

            // 设置请求头部
            httpPost.addHeader("Authorization", "Bearer " + apiKey);
            httpPost.addHeader("Content-Type", "application/json");

            // 构建请求体
            String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"system\",\"content\":\"初始化对话\"},{\"role\":\"user\",\"content\":\"%s\\n帮忙提取前面这段文字的主要观点（30字以内）和政治倾向（30字以内）\"}]}", text);
            StringEntity requestEntity = new StringEntity(requestBody);
            httpPost.setEntity(requestEntity);

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
