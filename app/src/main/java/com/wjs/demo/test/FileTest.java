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

    /**
     * 复制文件
     */
    public static void copyFileTest() {
        String oldPath = "/mnt/sdcard/Download/Test/T.e.s.t/createFolderTest/createFolderTest.txt"; // 该文件必须已存在，否则没有文件可以复制
        String newPath = "/mnt/sdcard/Download/Test/test/createFolderTest.txt"; // success

        FileUtil.verifyCopyFile(oldPath, newPath, "壁纸图片");
    }

    /**
     * 复制文件夹及其中的文件
     */
    public static void copyFolderTest() {
        String oldPath = "", newPath = "";

        oldPath = "/mnt/sdcard/Download/wallpaper"; // success 该文件夹必须已存在，否则没有文件夹可以复制
        newPath = "/mnt/sdcard/Download/background/wallpaper"; // success

//        oldPath = "/mnt/sdcard/Download/wallpaper/"; // success 该文件夹必须已存在，否则没有文件夹可以复制
//        newPath = "/mnt/sdcard/Download/background/wallpaper/"; // success

        FileUtil.verifyCopyFolder(oldPath, newPath, "壁纸合集");
    }
}
