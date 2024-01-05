package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIChat {

    public static void main(String[] args) {
        String text = "一名卫生官员周六表示，中国呼吸道疾病的激增是由已知病原体引起的，没有新的传染病迹象。中国正面临自解除新冠肺炎严格限制以来的第一个完整冬天。世界卫生组织上周援引一份关于儿童未确诊肺炎集群的报告寻求信息时，2019年末出现新冠肺炎的国家的疾病激增引起了人们的关注。中国国家卫健委官员米锋在新闻发布会上表示，中国当局将开设更多儿科门诊，努力确保更多的老人和儿童接种流感疫苗，并鼓励人们戴口罩和洗手。中国的医生和国外的专家并没有对中国的疫情表示担忧，因为许多其他国家在去年年底放松了疫情防控措施后，呼吸道疾病也出现了类似的增加。周五，在中国呼吸道疾病病例激增后，以马尔科·卢比奥为首的五名共和党参议员要求乔·拜登总统的政府禁止美国和中国之间的旅行。拜登政府的一名官员表示，美国正在密切关注中国呼吸道疾病的上升，但他补充道，“我们看到了季节性趋势。没有什么异常……目前，没有迹象表明在美国急诊科寻求治疗的人与中国爆发的呼吸道疾病之间存在联系。”世界卫生组织流行病和大流行准备与预防司代理司长Maria Van Kerkhove本周早些时候表示，这一增长似乎是由感染病原体的儿童数量增加推动的，而在新冠肺炎限制措施的两年中，他们避免了感染病原体。中国驻华盛顿大使馆发言人刘鹏宇在回应卢比奥的信时表示，“有关说法纯属恶意捏造。中方坚决反对。”参议员J.D.万斯、里克·斯科特、汤米·图伯维尔和迈克·布劳恩也在卢比奥的信上签名”";

        // API密钥
        String apiKey = "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";

        // 设置 API 请求URL
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // 设置请求头部
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 构建请求体
            String requestBody = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"system\",\"content\":\"初始化对话\"},{\"role\":\"user\",\"content\":\""
                    + text + "\\n帮忙提取前面这段文字的主要观点（30字以内）和政治倾向（30字以内）\"}]}";

            // 将请求体写入输出流
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(requestBody.getBytes());
            }

            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // 解析JSON响应
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode result = objectMapper.readTree(response.toString());

                // 处理响应
                JsonNode choices = result.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    String output = choices.get(0).path("message").path("content").asText();
                    System.out.println(output);
                } else {
                    System.out.println("No choices found in the response.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
