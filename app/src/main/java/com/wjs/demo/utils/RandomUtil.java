package com.wjs.demo.utils;

import java.util.UUID;

public class RandomUtil {

    public static String getRandomStringByUUID() {
        return UUID.randomUUID().toString();
    }
}
