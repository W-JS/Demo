package com.wjs.demo.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {

    public static final String separator = File.separator;

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return true-创建文件夹成功，false-创建文件夹失败
     */
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
        if (!createFolder(path)) {
            return flag;
        }

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

        // 判断最后一个字符是否为"/"
        if (!path.endsWith(separator)) {
            path = path + separator;
        }

        File file = getTheFileExist(path);
        if (file == null) {
            return false;
        }

        if (file.isFile()) {
            LogUtil.d("文件删除情况: " + file.delete() + " path: " + path);
            return true;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                LogUtil.d("文件夹为空 文件夹删除情况: " + file.delete() + " path: " + path);
                return true;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(path + childFiles[i].getName());
            }
            LogUtil.d("文件夹删除情况: " + file.delete() + " path: " + path);
            return true;
        }
        return false;
    }

    /**
     * 验证文件复制情况
     *
     * @param oldPath
     * @param newPath
     * @param describe
     * @return
     */
    public static boolean verifyCopyFile(String oldPath, String newPath, String describe) {
        if (isTheFileExist(oldPath)) {
            boolean status = copyFile(oldPath, newPath);
            LogUtil.i(describe + "复制情况: " + status + " oldPath: " + oldPath + " newPath: " + newPath);
            return status;
        } else {
            LogUtil.i(describe + "不存在 " + " oldPath: " + oldPath);
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            if (new File(newPath).exists()) {
                LogUtil.i("已存在 newPath: " + newPath);
                deleteFile(newPath);
            }

            if (!createFolder(newPath)) {
                LogUtil.e("复制失败，新文件所在文件夹创建失败");
                return false;
            }

            // 如果是文件
            FileInputStream is = new FileInputStream(oldPath);
            FileOutputStream fos = new FileOutputStream(new File(newPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            // 循环从输入流读取buffer字节
            while ((byteCount = is.read(buffer)) != -1) {
                // 将读取的输入流写入到输出流
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            LogUtil.i("成功复制文件 oldPath: " + oldPath + " to newPath: " + newPath);
            return true;
        } catch (IOException e) {
            LogUtil.e("IOException: " + e);
            return false;
        }
    }

    /**
     * 验证文件复制情况
     *
     * @param oldPath
     * @param newPath
     * @param describe
     * @return
     */
    public static boolean verifyCopyFolder(String oldPath, String newPath, String describe) {
        if (isTheFileExist(oldPath)) {
            boolean status = copyFolder(oldPath, newPath);
            LogUtil.i(describe + "复制情况: " + status + " oldPath: " + oldPath + " newPath: " + newPath);
            return status;
        } else {
            LogUtil.i(describe + "不存在 " + " oldPath: " + oldPath);
            return false;
        }
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            if (new File(newPath).exists()) {
                LogUtil.i("已存在 newPath: " + newPath);
                deleteFile(newPath);
            }

            if (!createFolder(newPath)) {
                LogUtil.e("复制失败，新文件夹创建失败");
                return false;
            }

            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldFile + separator + file);
                }

                // 如果是子文件夹
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    LogUtil.e("copyFolder: oldFile不存在 oldPath: " + oldPath);
                    return false;
                } else if (!temp.isFile()) {
                    LogUtil.e("copyFolder: oldFile不是文件 oldPath: " + oldPath);
                    return false;
                } else if (!temp.canRead()) {
                    LogUtil.e("copyFolder: oldFile无法读取文件 oldPath: " + oldPath);
                    return false;
                } else {
                    // 如果是文件
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead = 0;
                    // 循环从输入流读取buffer字节
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        // 将读取的输入流写入到输出流
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileOutputStream.flush();
                    fileInputStream.close();
                    fileOutputStream.close();
                }
            }
            LogUtil.i("copyFolder oldPath: " + oldPath + " to newPath: " + newPath);
            return true;
        } catch (IOException e) {
            LogUtil.e("IOException: " + e);
            return false;
        }
    }

    /**
     * zip压缩包解压
     *
     * @param zipPath   解压缩文件名
     * @param unZipPath 目标路径
     * @return 是否解压成功
     */
    public static boolean unZip(String zipPath, String unZipPath) {
        boolean unZipFlag = false;
        if (!EmptyUtil.isTheStringZeroLength(zipPath)) {
            LogUtil.e("解压缩文件名为空 zipPath: " + zipPath);
            return unZipFlag;
        }
        if (!EmptyUtil.isTheStringZeroLength(unZipPath)) {
            LogUtil.e("解压缩文件路径为空 unZipPath: " + unZipPath);
            return unZipFlag;
        }
        if (!isTheFileExist(zipPath)) {
            LogUtil.e("压缩文件不存在 zipPath: " + zipPath);
            return unZipFlag;
        } else {
            LogUtil.e("压缩文件已存在 zipPath: " + zipPath);
        }
        if (new File(unZipPath).exists()) {
            LogUtil.i("已存在 unZipPath: " + unZipPath);
            deleteFile(unZipPath);
        }
        if (!createFolder(unZipPath)) {
            LogUtil.e("解压路径创建失败");
            return unZipFlag;
        }
        if (!unZipPath.endsWith("/")) {
            unZipPath = unZipPath + "/";
        }

        byte buffer[] = new byte[4096];
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileInputStream = new FileInputStream(zipPath);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            zipInputStream = new ZipInputStream(bufferedInputStream);

            ZipEntry zipEntry = null;
            while (true) {
                if (!((zipEntry = zipInputStream.getNextEntry()) != null)) {
                    break;
                }
                File file = new File(unZipPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                    LogUtil.i("mkdirs");
                } else {
                    // 如果指定文件的文件目录不存在，则创建文件目录
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    int readBytes = 0;
                    fileOutputStream = new FileOutputStream(file);

                    while (true) {
                        if (!((readBytes = zipInputStream.read(buffer)) > 0)) {
                            break;
                        }
                        fileOutputStream.write(buffer, 0, readBytes);
                    }
                    fileOutputStream.close();
                    fileOutputStream = null;
                    LogUtil.i("write file");
                }
                zipInputStream.closeEntry();
                unZipFlag = true;
            }
//            zipInputStream.close();
//            bufferedInputStream.close();
//            fileInputStream.close();
//            zipInputStream = null;
//            bufferedInputStream = null;
//            fileInputStream = null;
        } catch (FileNotFoundException e) {
            LogUtil.e("FileNotFoundException: " + e);
            unZipFlag = false;
        } catch (IOException e) {
            LogUtil.e("IOException: " + e);
            unZipFlag = false;
        } finally {
            try {
                closeOutputStream(fileOutputStream);
                closeInputStream(zipInputStream);
                closeInputStream(bufferedInputStream);
                closeInputStream(fileInputStream);
            } catch (Exception e) {
                LogUtil.e("Exception: " + e);
            }
            LogUtil.d("压缩包解压情况: " + unZipFlag + " zipPath: " + zipPath + " unZipPath: " + unZipPath);
            return unZipFlag;
        }
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LogUtil.e("IOException: " + e);
            }
        }
    }

    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                LogUtil.e("IOException: " + e);
            }
        }
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
