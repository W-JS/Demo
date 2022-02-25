package com.wjs.demo.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return true-创建文件夹成功，false-创建文件夹失败
     */
    public static boolean createFolder(String path) {
        boolean flag = false;
        File file = isTheFileExist(path);
        if (file == null) {
            File folder = new File(path);
            flag = folder.mkdirs();
            LogUtil.d("文件夹创建情况: " + flag + " path: " + path);
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return true-创建文件成功，false-创建文件失败
     */
    public static boolean createFile(String path) {
        boolean flag = false;
        File file = isTheFileExist(path);
        if (file == null) {
            try {
                File newFile = new File(path);
                flag = newFile.createNewFile();
                LogUtil.d("文件创建情况: " + flag + " path: " + path);
            } catch (IOException e) {
                LogUtil.e("IOException: " + e);
            }
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 删除文件夹及其中文件
     *
     * @param path 文件路径
     * @return true-删除成功，false-删除失败
     */
    public static boolean deleteFile(String path) {
        File file = isTheFileExist(path);
        if (file == null) {
            return false;
        }

        if (file.isFile()) {
            file.delete();
            LogUtil.w("成功删除文件 path: " + path);
            return true;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                LogUtil.w("成功删除文件夹且文件夹为空 path: " + path);
                return true;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(path + childFiles[i].getName());
            }
            file.delete();
            LogUtil.w("成功删除文件夹 path: " + path);
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 文件
     */
    public static File isTheFileExist(String path) {
        if (!EmptyUtil.isTheStringEmpty(path)) {
            LogUtil.e("文件路径 path: " + path + " 为空");
            return null;
        }

        File file = new File(path);
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                LogUtil.d("文件夹已存在 path: " + path);
            }
            if (file.isFile()) {
                LogUtil.d("文件已存在 path: " + path);
            }
            return file;
        }
        return null;
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            LogUtil.e("Exception: " + e);
        }
        return size;
    }


    /**
     * 将字节数转化为MB
     *
     * @param size 文件夹或者文件的字节大小
     * @return MB
     */
    public static String byteToKBorMBorGB(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size > kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
