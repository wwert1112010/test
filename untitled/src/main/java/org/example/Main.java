package org.example;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static ArrayList<String> list = new ArrayList<>();

    static {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("1234");

    }

    public static void main(String[] args) throws ParseException {

        List<String> list = new ArrayList<>();

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf2.parse("2023-01-01");
        Date end = sdf2.parse("2023-10-01");
        Integer current = 3;
        Integer pageSize = 4;

        Calendar endCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        Calendar newStartCalendar = Calendar.getInstance();
        Calendar newEndCalendar = Calendar.getInstance();


        endCalendar.setTime(end);
        endCalendar.add(Calendar.MONTH, 1);
        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;

        startCalendar.setTime(start);
        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;



        int diffYear = endYear - startYear;
        int diffMonth = endMonth - startMonth;

        int months = diffYear * 12 + diffMonth;
        System.out.println("Months between the two dates: " + months);

        newStartCalendar.setTime(start);
        newStartCalendar.add(Calendar.MONTH, (current-1)*pageSize);

        newEndCalendar.setTime(newStartCalendar.getTime());
        newEndCalendar.add(Calendar.MONTH, pageSize);

        if(newEndCalendar.after(endCalendar)){
            newEndCalendar.setTime(endCalendar.getTime());
        }

        Date d1 = newStartCalendar.getTime();


        while(newStartCalendar.before(newEndCalendar)){
            String monthStr = (newStartCalendar.get(Calendar.MONTH) < 9) ? "0" + (newStartCalendar.get(Calendar.MONTH) + 1) : String.valueOf(newStartCalendar.get(Calendar.MONTH) + 1);
            list.add(newStartCalendar.get(Calendar.YEAR) + "."  + monthStr);
            newStartCalendar.add(Calendar.MONTH, 1);
        }
        System.out.println(list);
        System.out.println(d1);
        newEndCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date d2 = newEndCalendar.getTime();
        System.out.println(d2);

    }


    private static boolean isExclusionUrl(String path) {
        return list.contains(path);
    }


    private  static  boolean checkIfHasString(List<String> annotationPerms,List<String> perms){
        for (String p:annotationPerms) {
            if(perms.contains(p)) return true;
        }
        return false;
    }
}