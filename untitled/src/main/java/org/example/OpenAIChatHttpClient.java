package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class OpenAIChatHttpClient {

    public static void main(String[] args) {
        String text = "一名卫生官员周六表示，中国呼吸道疾病的激增是由已知病原体引起的，没有新的传染病迹象。中国正面临自解除新冠肺炎严格限制以来的第一个完整冬天。世界卫生组织上周援引一份关于儿童未确诊肺炎集群的报告寻求信息时，2019年末出现新冠肺炎的国家的疾病激增引起了人们的关注。中国国家卫健委官员米锋在新闻发布会上表示，中国当局将开设更多儿科门诊，努力确保更多的老人和儿童接种流感疫苗，并鼓励人们戴口罩和洗手。中国的医生和国外的专家并没有对中国的疫情表示担忧，因为许多其他国家在去年年底放松了疫情防控措施后，呼吸道疾病也出现了类似的增加。周五，在中国呼吸道疾病病例激增后，以马尔科·卢比奥为首的五名共和党参议员要求乔·拜登总统的政府禁止美国和中国之间的旅行。拜登政府的一名官员表示，美国正在密切关注中国呼吸道疾病的上升，但他补充道，“我们看到了季节性趋势。没有什么异常……目前，没有迹象表明在美国急诊科寻求治疗的人与中国爆发的呼吸道疾病之间存在联系。”世界卫生组织流行病和大流行准备与预防司代理司长Maria Van Kerkhove本周早些时候表示，这一增长似乎是由感染病原体的儿童数量增加推动的，而在新冠肺炎限制措施的两年中，他们避免了感染病原体。中国驻华盛顿大使馆发言人刘鹏宇在回应卢比奥的信时表示，“有关说法纯属恶意捏造。中方坚决反对。”参议员J.D.万斯、里克·斯科特、汤米·图伯维尔和迈克·布劳恩也在卢比奥的信上签名”";
        String apiKey = "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";

        // 设置 API 请求URL
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String pro = "127.0.0.1";//本机地址
            int pro1 = 7890; //代理端口号

            //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
            HttpHost httpHost = new HttpHost(pro, pro1);

            // 创建 POST 请求
            HttpPost httpPost = new HttpPost(apiUrl);

            //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
            RequestConfig build = RequestConfig.custom().setProxy(httpHost).build();


            // 设置请求头部
            httpPost.addHeader("Authorization", "Bearer " + apiKey);
            httpPost.addHeader("Content-Type", "application/json");

            httpPost.setConfig(build);

            // 构建请求体
            String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"system\",\"content\":\"初始化对话\"},{\"role\":\"user\",\"content\":\"%s\\n帮忙提取前面这段文字的主要观点（30字以内）和政治倾向（30字以内）\"}]}", text);
            StringEntity requestEntity = new StringEntity(requestBody, ContentType.create("text/json", "UTF-8"));
            httpPost.setEntity(requestEntity);

            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 获取响应实体
            HttpEntity responseEntity = response.getEntity();

            // 解析响应内容
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println(responseString);
                System.out.println("************************************");
                // 处理JSON响应
                // 这里可以使用Jackson或其他JSON库解析responseString
                // 使用Jackson库解析JSON响应
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                // 现在你可以从JsonNode中提取数据
                JsonNode choicesNode = jsonNode.path("choices");
                if (choicesNode.isArray() && choicesNode.size() > 0) {
                    JsonNode messageNode = choicesNode.get(0).path("message");
                    String modelResponse = messageNode.path("content").asText();
                    System.out.println("**************************************************************************************************");
                    System.out.println("Model Response: " + modelResponse);
                    System.out.println("**************************************************************************************************");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
