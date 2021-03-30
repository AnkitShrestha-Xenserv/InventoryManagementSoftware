import java.sql.Timestamp;
import java.util.Date;


// Created on: 3/27/2021
public class DateStuff{
//    public static void main(String[] args) {
//        String s = (new Timestamp((new Date()).getTime())).toString().substring(0,19);
//        System.out.println(s);
//        System.out.println("Minute : " + getMinute(s));
//        System.out.println("Hour : " + getHour(s));
//        System.out.println("Day : " + getDay(s));
//        System.out.println("Month : " + getMonth(s));
//        System.out.println("Year : " + getYear(s));
//        System.out.println("0000000000000000000000000");
//        System.out.println(System.currentTimeMillis());
//        System.out.println("----------------------");
//        System.out.println(getIntegerTime(s));
//    }
    static String getMinute(String timestamp){
        return timestamp.substring(14,16);
    }

    static String getHour(String timestamp){
        return timestamp.substring(11,13);
    }

    static String getDay(String timestamp){
        return timestamp.substring(8,10);
    }

    static String getMonth(String timestamp){
        return timestamp.substring(5,7);
    }

    static String getYear(String timestamp){
        return timestamp.substring(0,4);
    }

    static long getIntegerTime(String timestamp){
        return Long.parseLong(getYear(timestamp)+getMonth(timestamp)+getDay(timestamp)+getHour(timestamp)+getMinute(timestamp));
    }
}