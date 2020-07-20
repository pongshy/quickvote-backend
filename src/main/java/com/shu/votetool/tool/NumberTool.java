package com.shu.votetool.tool;


/**
 * program: NumberTool
 * description: 数字转换
 * author: SoCMo
 * create: 2020/2/23
 */
public class NumberTool {
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @Description: 浮点数保留一位小数转换为百分数
     * @Param: [number]
     * @return: java.lang.String
     * @Author: SoCMo
     * @Date: 2020/2/23
     */
    public static String doubleToStringWithH(Double number) {
        if(number == null) return "0%";
        return String.format("%.1f", number * 100) + "%";
    }

    /**
     * @Description: 浮点数保留一位小数
     * @Param: [number]
     * @return: java.lang.String
     * @Author: SoCMo
     * @Date: 2020/2/23
     */
    public static String doubleToStringWotH(Double number) {
        if(number == null) return "0%";
        return String.format("%.1f", number);
    }

    /**
     * @Description: 整数除法转浮点数
     * @Param: [a, b]
     * @return: java.lang.String
     * @Author: SoCMo
     * @Date: 2020/2/23
     */
    public static Double intDivision(Integer a, Integer b) {
        if(b == 0) return null;
        return (double) a / (double) b;
    }

    /**
     * @Description: 经纬度计算距离
     * @Param: [lat1, lng1, lat2, lng2]
     * @return: double
     * @Author: SoCMo
     * @Date: 2020/3/30
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));

        //地球半径
        double EARTH_RADIUS = 6378.137;
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }
}
