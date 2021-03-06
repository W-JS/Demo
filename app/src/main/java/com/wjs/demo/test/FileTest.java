package com.wjs.demo.test;

import com.wjs.demo.DemoApplication;
import com.wjs.demo.utils.FileUtil;
import com.wjs.demo.utils.LogUtil;

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

    /**
     * zip压缩包解压
     */
    public static void unZipTest() {
        String zipPath = "", unZipPath = "";

        // 注意：zip压缩包里文件命名不能含有中文
        zipPath = "/mnt/sdcard/Download/wallpaper/wallpaper.zip"; // success 该文件夹必须已存在，否则没有文件夹可以复制
        unZipPath = "/mnt/sdcard/Download/background/"; // success
//        unZipPath = "/mnt/sdcard/Download/background"; // success

        FileUtil.unZip(zipPath, unZipPath);
    }

    /**
     * 获取SD卡的剩余容量
     */
    public static void getSdCadFeeSizeTest() {
        LogUtil.i("获取SD卡的剩余容量: " + FileUtil.getSdCadFeeSize());
    }

    /**
     * 获取文件夹大小
     */
    public static void getFolderSizeTest() {
        String path = "";

        path = "/mnt/sdcard/Download/wallpaper/"; // success
        path = "/mnt/sdcard/Download/wallpaper"; // success

        LogUtil.i("获取文件夹大小: " + FileUtil.getFolderSize(path));

    }

    /**
     * 从assets目录中复制文件到指定目录
     */
    public static void copyAssetsFileTest() {
        String assetsFileName = "", newFilePath = "";

        assetsFileName = "wallpaper.jpg";
        newFilePath = "/mnt/sdcard/Download/background/wallpaper/wallpaper.jpg"; // success

        LogUtil.i("从assets目录中复制文件到指定目录的复制情况: " + FileUtil.copyAssetsFile(DemoApplication.getContext(), assetsFileName, newFilePath));

    }

    /**
     * 从assets目录中复制文件到指定目录
     */
    public static void copyAssetsFolderTest() {
        String assetsFolderName = "", newFolderPath = "";

        assetsFolderName = "wallpaper";
        newFolderPath = "/mnt/sdcard/Download/background/wallpaper/"; // success

        LogUtil.i("从assets目录中复制文件夹到指定目录的复制情况: " + FileUtil.copyAssetsFolder(DemoApplication.getContext(), assetsFolderName, newFolderPath + assetsFolderName));

    }
}
