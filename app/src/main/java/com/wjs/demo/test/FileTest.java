package com.wjs.demo.test;

import com.wjs.demo.utils.FileUtil;

public class FileTest {

    /**
     * 创建路径下所有文件夹
     * 创建文件夹 "/mnt/sdcard/Download/Te.st/Test/createFolderTest/"
     */
    public static void createFolderTest() {
        String path = "";

        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // ok
//        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/"; // ok
//        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest"; // ok

//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // ok
//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/"; // ok
//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest"; // ok

        FileUtil.createFolder(path);
    }
}
