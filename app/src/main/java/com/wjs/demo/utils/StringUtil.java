package com.wjs.demo.utils;

public class StringUtil {

    /**
     * Java截取最后某一个字符后面的所有字符
     *
     * @param interceptedString  被截获的字符串
     * @param specifiedCharacter 指定字符
     * @return 最后一个指定字符后面的所有字符串
     */
    public static String truncateAllCharactersAfterTheLastCharacter(String interceptedString, String specifiedCharacter) {
        return interceptedString.substring(interceptedString.lastIndexOf(specifiedCharacter) + 1);
    }
}
