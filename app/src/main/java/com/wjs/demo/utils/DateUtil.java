package com.wjs.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 获取当前时间的完整日期格式
     *
     * @return yyyy-MM-dd HH:mm:ss 格式日期
     */
    public static String getCurrentTimeFullDateFormat() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = simpleDateFormat.format(date);
        LogUtil.i("当前时间的完整日期格式(yyyy-MM-dd HH:mm:ss): " + formatDate);
        return formatDate;
    }
}
