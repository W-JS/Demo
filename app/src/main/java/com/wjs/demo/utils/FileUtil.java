package com.wjs.demo.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static final String separator = File.separator;

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return true-创建文件夹成功，false-创建文件夹失败
     */
    // ok
    public static boolean createFolder(String path) {
        boolean createFolderFlag = false;
        try {
            // 判断第一个字符是否为"/"
            if (!path.startsWith(separator)) {
                path = separator + path;
            }

            // 判断最后一个字符是否为"/"
            if (!path.endsWith(separator)) {
                path = path + separator;
            }

            // 临时保存文件夹名称
            String tempFolderName = "";
            // 已经创建的路径
            String alreadyCreatedPath = "";
            // 临时保存路径
            String tempPath = path;

            LogUtil.e("tempPath: " + tempPath);

            while (!separator.equals(tempPath)) {
                // 从前到后依次判断文件夹是否存在，不存在则创建该文件夹
                tempFolderName = tempPath.substring(0, tempPath.indexOf(separator, tempPath.indexOf(separator) + 1));
                LogUtil.i("tempFolderName: " + tempFolderName);
                String fileExtension = StringUtil.truncateAllCharactersAfterTheLastCharacter(false, tempFolderName, ".");
                if (!tempFolderName.equals(fileExtension)) {
                    if (isCommonFileExtensions(fileExtension)) {
                        LogUtil.i("是文件，故退出创建文件夹流程 fileName: " + StringUtil.truncateAllCharactersAfterTheLastCharacter(false, tempFolderName, separator));
                        break;
                    } else {
                        LogUtil.i("不是文件，则创建该文件夹");
                    }
                }

                alreadyCreatedPath = alreadyCreatedPath + tempFolderName;
                LogUtil.i("alreadyCreatedPath: " + alreadyCreatedPath);
                if (!isTheFileExist(alreadyCreatedPath)) {
                    File folder = new File(alreadyCreatedPath);
                    boolean createSubfolderFlag = folder.mkdirs();
                    LogUtil.d("文件夹创建情况: " + createSubfolderFlag);
                    if (!createSubfolderFlag) {
                        break;
                    }

                }
                tempPath = tempPath.substring(tempPath.indexOf(separator, tempPath.indexOf(separator) + 2));
                LogUtil.i("tempPath: " + tempPath);
            }
            LogUtil.d("所有文件夹全部创建成功 path: " + alreadyCreatedPath);
            createFolderFlag = true;
        } catch (Exception e) {
            LogUtil.e("Exception: " + e);
        }
        return createFolderFlag;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return true-创建文件成功，false-创建文件失败
     */
    public static boolean createFile(String path) {
        boolean flag = false;
        File file = getTheFileExist(path);
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
        File file = getTheFileExist(path);
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
     * @return true-文件存在，false-文件不存在
     */
    // ok
    public static boolean isTheFileExist(String path) {
        if (!EmptyUtil.isTheStringEmpty(path)) {
            LogUtil.e("文件路径 path: " + path + " 为空");
            return false;
        }

        File file = new File(path);
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                LogUtil.d("文件夹已存在 path: " + path);
            }
            if (file.isFile()) {
                LogUtil.d("文件已存在 path: " + path);
            }
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
    public static File getTheFileExist(String path) {
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

    /**
     * 是否为常见文件
     *
     * @param string
     * @return true-是文件，false-不是文件
     */
    public static boolean isCommonFileExtensions(String string) {
        // 图形文件
        if ("jpg".equals(string) ||
                "png".equals(string) ||
                "gif".equals(string) ||
                "psd".equals(string) ||
                "bmp".equals(string)
        ) {
            return true;
        }

        // 视频文件
        if ("mp4".equals(string) ||
                "wmv".equals(string) ||
                "avi".equals(string) ||
                "mov".equals(string) ||
                "asf".equals(string) ||
                "rm".equals(string) ||
                "rmvb".equals(string)
        ) {
            return true;
        }

        // 音频文件
        if ("mp3".equals(string) ||
                "wav".equals(string)
        ) {
            return true;
        }

        // 文档文件
        if ("txt".equals(string) ||
                "doc".equals(string) ||
                "docx".equals(string) ||
                "pdf".equals(string) ||
                "wps".equals(string) ||
                "xls".equals(string) ||
                "xlsx".equals(string)
        ) {
            return true;
        }

        // 压缩文件
        if ("rar".equals(string) ||
                "zip".equals(string)
        ) {
            return true;
        }

        // 可执行文件
        if ("exe".equals(string) ||
                "com".equals(string)
        ) {
            return true;
        }

        return false;
    }
}
