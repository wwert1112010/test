package org.example;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class OpenAIChatOkHttp {

    public static void main(String[] args) {
        String text = "一名卫生官员周六表示";
        String apiKey = "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";

        // 设置 API 请求 URL
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // 设置请求体
        String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"system\",\"content\":\"初始化对话\"},{\"role\":\"user\",\"content\":\"%s\\n帮忙提取前面这段文字的主要观点（30字以内）和政治倾向（30字以内）\"}]}", text);

        // 设置请求头
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        // 创建 OkHttpClient
        OkHttpClient client = new OkHttpClient();

        try {
            // 发送请求并获取响应
            Response response = client.newCall(request).execute();

            // 处理响应
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println(responseBody);

                // 在这里你可以使用 Jackson 或其他 JSON 库来解析 responseBody
            } else {
                System.out.println("Request failed. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
