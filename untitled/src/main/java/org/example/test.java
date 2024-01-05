package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {

    public String func1(String text) throws IOException {
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
            String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"system\",\"content\":\"初始化对话\"},{\"role\":\"user\",\"content\":\"%s\\n帮忙提取前面这段文字的主要观点（控制字数30字以内）\"}]}", text);
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
                    System.out.println("Model Response: " + modelResponse);
                    return modelResponse;

                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String text = "本周，60多个国家在COP28上签署了大幅减少与制冷相关的碳排放的承诺。中国——世界上最大的空调生产国——不是其中之一。周三在迪拜举行的联合国气候变化大会上宣布的这项协议旨在到2050年将与制冷相关的排放量比2022年减少68%。制冷占世界用电量的五分之一，联合国环境规划署预计到2050年将翻一番。根据联合国环境规划署的数据，世界上大约三分之一的人口暴露在致命的热浪中，而超过10亿人无法获得任何冷却技术。" +
                "亚洲社会政策研究所中国气候中心主任李硕表示，尽管今年早些时候遭遇了创纪录的热浪，但中国没有在COP28上承诺，因为北京认为之前的此类协议缺乏问责制。" +
                "李说：“就像到2030年将可再生能源产能增加三倍的协议一样，中国在与美国的联合声明中签署了这一协议，但在COP28上，中国的外交习惯是不同意这些协议。”。" +
                "这位气候专家表示，随着热浪期间对空调和冷却系统的需求激增，冷却对中国控制能源消耗的努力提出了“巨大挑战”。" +
                "根据国际能源署的数据，自2010年以来，冷却需求占中国总用电量增长的10%以上，从2000年到2018年，与冷却相关的碳排放量增加了五倍，主要来自中国不断增长的燃煤发电。" +
                "李说：“冷却是中国能源转型道路上的一个非常重要的问题。中国必须考虑如何减少对空调的需求，提高能源效率。”。" +
                "中国的降温政策" +
                "中国于2019年启动了《国家绿色制冷行动计划》，目标是到2030年将其制冷产品的能效比2022年提高25%，将公共建筑的能效提高30%。建议采取的行动包括提高能效标准和开展国际合作。" +
                "中国目前在空调制造市场上处于领先地位，约70%的空调产品销往世界各地。随着化石燃料燃烧导致全球气温上升，预计对冷却产品的需求将飙升。" +
                "尼哈尔·沙阿说，虽然现在判断中国的制冷计划是否有效还为时过早，但对其能源性能标准的改革已经“使中国国内市场几乎完全转向变频空调，预计节能约20%至30%，这是此类空调的典型节能效果。”，劳伦斯伯克利国家实验室全球冷却效率项目的总统主任。" +
                "尽管中国没有签署最新的承诺，但在11月初美国总统气候特使约翰·克里和中国总统解振华在加利福尼亚州桑尼兰举行会谈后宣布的一项协议中，北京同意进一步提高空调的能效。" +
                "作为该协议的一部分，两国承诺共同努力逐步减少氢氟碳化合物，这是一种常见于冰箱和空调中的全球变暖温室气体。" +
                "但李说，为了有效削减与制冷相关的排放，中国还必须增加可再生能源的发电量，以满足日益增长的需求。尽管中国已经拥有世界上最大的可再生能源产能，但化石燃料仍占其发电量的80%以上，该国每两周签署一次新的煤电厂许可证，以确保能源安全。" +
                "中国正在通过加强预警系统、健康和气候适应研究、城市绿地、注重空间的城市规划和其他措施来适应未来的热浪。" +
                "但李表示，实施这些措施的动机是有限的，“因为地方州长希望取得更多的短期成果。量化适应措施的结果也不那么容易。没有固定的目标，而且高度本地化。" +
                "冷却的未来" +
                "其他减少空调需求的举措包括“被动式建筑”，这是20世纪80年代在德国推出的一个概念，使用隔热、自然遮阳、通风和其他措施来减少制冷需求。" +
                "联合国环境规划署计算出，被动冷却措施可以在2050年将冷却能力需求减少约四分之一，并消除13亿吨碳排放。" +
                "自2014年以来，中国一直采用被动冷却措施，但研究人员表示，这种措施仍处于发展阶段。但他们表示，随着更多新的可持续建筑的建设，这种设计理念将越来越多地被使用。" +
                "专家还建议使用分散式制冷来减少中国的需求。这种类型的系统允许只有当有人在房间里时，单元才能运行。";
        System.out.println(text);
        String text1 = "一名卫生官员周六表示，中国呼吸道疾病的激增是由已知病原体引起的，没有新的传染病迹象。中国正面临自解除新冠肺炎严格限制以来的第一个完整冬天。世界卫生组织上周援引一份关于儿童未确诊肺炎集群的报告寻求信息时，2019年末出现新冠肺炎的国家的疾病激增引起了人们的关注。中国国家卫健委官员米锋在新闻发布会上表示，中国当局将开设更多儿科门诊，努力确保更多的老人和儿童接种流感疫苗，并鼓励人们戴口罩和洗手。中国的医生和国外的专家并没有对中国的疫情表示担忧，因为许多其他国家在去年年底放松了疫情防控措施后，呼吸道疾病也出现了类似的增加。周五，在中国呼吸道疾病病例激增后，以马尔科·卢比奥为首的五名共和党参议员要求乔·拜登总统的政府禁止美国和中国之间的旅行。拜登政府的一名官员表示，美国正在密切关注中国呼吸道疾病的上升，但他补充道，“我们看到了季节性趋势。没有什么异常……目前，没有迹象表明在美国急诊科寻求治疗的人与中国爆发的呼吸道疾病之间存在联系。”世界卫生组织流行病和大流行准备与预防司代理司长Maria Van Kerkhove本周早些时候表示，这一增长似乎是由感染病原体的儿童数量增加推动的，而在新冠肺炎限制措施的两年中，他们避免了感染病原体。中国驻华盛顿大使馆发言人刘鹏宇在回应卢比奥的信时表示，“有关说法纯属恶意捏造。中方坚决反对。”参议员J.D.万斯、里克·斯科特、汤米·图伯维尔和迈克·布劳恩也在卢比奥的信上签名”";

        test test = new test();
        String s = test.func1(text);
        System.out.println("s: " + s);
    }

}
