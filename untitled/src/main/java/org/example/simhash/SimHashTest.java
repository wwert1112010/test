package org.example.simhash;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class SimHashTest {


    public static void main(String[] args) {
        // 准备测试标题内容数据
        List<String> titleList = new ArrayList<>();
        titleList.add("有哪些养猫必须知道的冷知识");
        titleList.add("有哪些养猫必须知道的冷");
        titleList.add("有哪些养猫必须知道");
        titleList.add("有哪些养猫");
        titleList.add("有哪些");
//        String s = readAllFile("C:/dev/testin2.txt");
//        titleList.add(s);

        // 原始标题内容数据
        String originalTitle = "有哪些养猫必须知道的冷知识？";
//        String originalTitle = readAllFile("C:/dev/testin1.txt");

        Map<String, Double> simHashMap = new HashMap<>(16, 0.75F);

        System.out.println("======================================");
        long startTime = System.currentTimeMillis();
        System.out.println("原始标题：" + originalTitle);

        // 计算相似度
        titleList.forEach(title -> {
            SimHashUtil mySimHash_1 = new SimHashUtil(title, 64);
            SimHashUtil mySimHash_2 = new SimHashUtil(originalTitle, 64);

            Double similar = mySimHash_1.getSimilar(mySimHash_2);

            simHashMap.put(title, similar);
        });

        // 打印测试结果到控制台
//        simHashMap.forEach((title, similarity) -> {
//            System.out.println("标题："+title+"-----------相似度："+similarity);
//        });

        // 按相标题内容排序输出控制台
        Set<String> titleSet = simHashMap.keySet();
        Object[] titleArrays = titleSet.toArray();
        Arrays.sort(titleArrays, Collections.reverseOrder());

        System.out.println("-------------------------------------");
        for (Object title : titleArrays) {
            System.out.println("标题：" + title + "-----------相似度：" + simHashMap.get(title));
//            System.out.println("标题：" +  "-----------相似度：" + simHashMap.get(title));

        }

        // 求得运算时长（单位：毫秒）
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("\n本次运算总耗时" + totalTime + "毫秒");

        System.out.println("======================================");
    }




    /**
     * 测试用
     * @param filename 名字
     * @return
     */
    public static String readAllFile(String filename) {
        String everything = "";
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            everything = IOUtils.toString(inputStream);
            inputStream.close();
        } catch (IOException e) {
        }

        return everything;
    }

}
