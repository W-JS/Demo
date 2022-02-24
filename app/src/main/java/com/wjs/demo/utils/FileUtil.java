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


}
