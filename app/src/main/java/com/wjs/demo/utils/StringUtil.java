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

    /**
     * 验证字符串为非空字符串
     *
     * @param input 需要验证非空的字符串
     * @return
     */
    public static String verifyNonNullString(String input) {
        char[] originalChars = input.toCharArray();
        char[] chars = new char[originalChars.length];
        for (int i = 0; i < originalChars.length; i++) {
            chars[i] = purifyChar(originalChars[i]);
        }
        return new String(chars);
    }

    /**
     * 净化字符
     *
     * @param inputChar
     * @return
     */
    public static char purifyChar(char inputChar) {
        return inputChar;
    }

    /**
     * 判断某个字符串是否包含指定字符串（字符）
     *
     * @param string             需要判断的字符串
     * @param specifiedCharacter 指定字符串（字符）
     * @return
     */
    public static boolean isContainsStringOrChar(String string, String specifiedCharacter) {
        return string.contains(specifiedCharacter);
    }
}
