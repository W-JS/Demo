package com.wjs.demo.test;

import com.wjs.demo.utils.FileUtil;

public class FileTest {

    /**
     * 创建路径下所有文件夹
     * 创建文件夹 "/mnt/sdcard/Download/Te.st/Test/createFolderTest/"
     */
    public static void createFolderTest() {
        String path = "";

        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // success
//        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/"; // success
//        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest"; // success

//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // success
//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/"; // success
//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest"; // success

        FileUtil.createFolder(path);
    }

    /**
     * 创建文件
     */
    public static void createFileTest() {
        String path = "";

        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // success

//        path = "mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // success

        FileUtil.createFile(path);
    }

    /**
     * 删除文件夹及其中文件
     */
    public static void deleteFileTest() {
        String path = "";

        path = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // success

//        path = "mnt/sdcard/Download/Test/"; // success

//        path = "mnt/sdcard/Download/Test"; // success

        FileUtil.deleteFile(path);
    }
}
