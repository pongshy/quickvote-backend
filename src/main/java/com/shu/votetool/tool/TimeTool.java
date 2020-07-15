package com.shu.votetool.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*
  * @Description: 时间工具类
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
public class TimeTool {
    /**
     * @Description: date转换为最小为分的格式
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/6
     */
    public static String timeToMinute(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: date转换为最小为天的格式
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/6
     */
    public static String timeToDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: date转换为最小为天的格式2
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/11
     */
    public static String timeToDay2(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: 字符串转化为Date类型
     * @Param: [time]
     * @Return: java.util.Date
     * @Author: SoCMo
     * @Date: 2019/12/11
     */
    public static Date stringToDay(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @Description: 判断是否是周末
     * @Param: [applicationDo]
     * @Return: java.lang.Boolean
     * @Author: SoCMo
     * @Date: 2019/12/19
     */
    public static Boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return calendar.get(Calendar.DAY_OF_WEEK) % 6 == 1;
    }

    /**
     * @Description: 统一周末记录为周六
     * @Param: [date]
     * @Return: java.util.Date
     * @Author: SoCMo
     * @Date: 2019/12/19
     */
    public static Date weekendFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) return date;
        else {
            calendar.add(Calendar.DATE, -1);
            return calendar.getTime();
        }
    }

    public static String DateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);
    }
}
