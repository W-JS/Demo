package com.wjs.demo.utils;

public class StringUtil {

    /**
     * Java截取最后某一个字符之前的所有字符
     *
     * @param type               是否带该字符(true-带，false-不带)
     * @param interceptedString  被截获的字符串
     * @param specifiedCharacter 指定字符
     * @return 最后一个指定字符之前的所有字符串
     */
    public static String truncateAllCharactersBeforeTheLastCharacter(boolean type, String interceptedString, String specifiedCharacter) {
//        interceptedString = "/123/456/789";
//        specifiedCharacter = "/";

        String string = "";
        if (type) {
            // "/123/456/"
            string = interceptedString.substring(0, interceptedString.lastIndexOf(specifiedCharacter) + 1);
        } else {
            // "/123/456"
            string = interceptedString.substring(0, interceptedString.lastIndexOf(specifiedCharacter));
        }
        return string;
    }

    /**
     * Java截取最后某一个字符之后的所有字符
     *
     * @param type               是否带该字符(true-带，false-不带)
     * @param interceptedString  被截获的字符串
     * @param specifiedCharacter 指定字符
     * @return 最后一个指定字符之后的所有字符串
     */
    public static String truncateAllCharactersAfterTheLastCharacter(boolean type, String interceptedString, String specifiedCharacter) {
//        interceptedString = "/123/456/789";
//        specifiedCharacter = "/";

        String string = "";
        if (type) {
            // "/789"
            string = interceptedString.substring(interceptedString.lastIndexOf(specifiedCharacter));
        } else {
            // "789"
            string = interceptedString.substring(interceptedString.lastIndexOf(specifiedCharacter) + 1);
        }
        return string;
    }
}
