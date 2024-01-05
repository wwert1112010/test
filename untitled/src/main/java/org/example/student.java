package org.example;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class student {


    public static void main(String[] args) {
        String dateString = "2023-12-23T00:27:55.453Z";

        // 定义日期格式
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            // 解析日期字符串
            Date date = inputFormat.parse(dateString);
            // 输出转换后的北京时间
            System.out.println("Beijing Time: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }


    private static Date f(String dateString){
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMM. d, yyyy, h:mm a z",Locale.US);
        try {
            date = inputFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private String name;

    private Integer age;

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
