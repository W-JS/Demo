package com.wjs.demo.utils;

public class EmptyUtil {

    /**
     * 判断对象是否为空
     *
     * @param object 需要判断的对象
     * @return true-不为空，false-为空
     */
    public static boolean isTheObjectEmpty(Object object) {
        if (object != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 需要判断的字符串
     * @return true-不为空，false-为空
     */
    public static boolean isTheStringEmpty(String string) {
        if (string != null && !"".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串长度是否为零
     *
     * @param string 需要判断的字符串
     * @return true-不为零，false-为零
     */
    public static boolean isTheStringZeroLength(String string) {
        if (isTheStringEmpty(string) && string.length() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
