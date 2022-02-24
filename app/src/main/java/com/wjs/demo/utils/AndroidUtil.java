package com.wjs.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import com.wjs.demo.DemoApplication;

import java.io.DataOutputStream;
import java.io.IOException;

public class AndroidUtil {

    /**
     * @return 判断是否可以访问网络
     */
    public static boolean isInternetConnection() {
        boolean isInternetConnection = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) DemoApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (networkCapabilities != null) {
                    isInternetConnection = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                }
            }
        } catch (Exception e) {
            LogUtil.e("Exception: " + e);
            isInternetConnection = false;
        } finally {
            LogUtil.i("isInternetConnection: 网络是否连接 -> " + isInternetConnection);
            return isInternetConnection;
        }
    }

    /**
     * @param packageCodePath 当前应用程序对应的 apk 文件的路径
     * @return 应用获取 ROOT 权限
     */
    public static boolean upgradeRootPermission(String packageCodePath) {
        boolean upgradeRootPermission = false;
        if (packageCodePath == null || "".equals(packageCodePath)) {
            LogUtil.e("getPackageCodePath() 为空");
            return upgradeRootPermission;
        }

        Process process = null;
        DataOutputStream dataOutputStream = null;
        try {
            String cmd = "chmod 777 " + packageCodePath;
            process = Runtime.getRuntime().exec("su");// 切换到 root 账号
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataOutputStream.writeBytes(cmd + "\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            upgradeRootPermission = true;
        } catch (Exception e) {
            LogUtil.e("Exception: " + e);
            upgradeRootPermission = false;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                LogUtil.e("IOException: " + e);
            }
        }
        LogUtil.i("upgradeRootPermission: 应用是否获取 ROOT 权限 -> " + upgradeRootPermission);
        return upgradeRootPermission;
    }

    /**
     * 获取图片扩展名
     *
     * @param imageUrl           图片网络地址路径
     * @param specifiedCharacter 扩展名前的点
     * @return 图片扩展名
     */
    public static String getImageExtension(String imageUrl, String specifiedCharacter) {
        return specifiedCharacter + StringUtil.truncateAllCharactersAfterTheLastCharacter(imageUrl, specifiedCharacter);
    }

}
