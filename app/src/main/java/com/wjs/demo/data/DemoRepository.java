package com.wjs.demo.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.wjs.demo.interfaces.DemoDataSource;
import com.wjs.demo.provider.CurrentColumns;
import com.wjs.demo.provider.CurrentProvider;
import com.wjs.demo.utils.Config;
import com.wjs.demo.utils.LogUtil;

public class DemoRepository implements DemoDataSource {

    private static volatile DemoRepository instance = null;
    private Context mContext;

    private DemoRepository(Context context) {
        mContext = context;
    }

    public static DemoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DemoRepository(context);
        }
        return instance;
    }

    @Override
    public String getWallpaperList(boolean isAgain) {
        return null;
    }

    @Override
    public void updateCurrent(String name, String val, String path) {
        LogUtil.i("更新当前保存的壁纸信息到数据库 name: " + name + "   val: " + val + "   path: " + path);
        AsyncTask<String, Void, String[]> task = new AsyncTask<String, Void, String[]>() {
            @Override
            protected String[] doInBackground(String... strings) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CurrentColumns.NAME, strings[0]);
                contentValues.put(CurrentColumns.VAL, strings[1]);
                contentValues.put(CurrentColumns.PATH, strings[2]);

                ContentResolver resolver = mContext.getContentResolver();
                resolver.update(CurrentProvider.CONTENT_URI_CURRENT, contentValues, "name = ?", new String[]{strings[0]});

                String[] currentInfo = new String[]{strings[0], strings[1], strings[2]};
                LogUtil.i("currentInfo: " + currentInfo.toString());
                LogUtil.i("成功更新当前保存的壁纸信息到数据库");
                return currentInfo;
            }

            @Override
            protected void onPostExecute(String[] currentInfo) {
                super.onPostExecute(currentInfo);
                String name = currentInfo[0];
                String val = currentInfo[1];
                String path = currentInfo[2];
                LogUtil.i("currentInfo: " + currentInfo.toString());

                if (Config.WallpaperSetting.equals(name)) {
                    LogUtil.i("name: " + name);
                    LogUtil.i("val: " + val);
                    LogUtil.i("path: " + path);
                }
            }
        };
        task.execute(name, val, path);
    }

    @Override
    public void getCurrent() {
        LogUtil.i("getCurrent");
        Cursor cursor = mContext.getContentResolver().query(CurrentProvider.CONTENT_URI_CURRENT, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LogUtil.i("name: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.NAME)));
                LogUtil.i("val: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.VAL)));
                LogUtil.i("path: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.PATH)));
            }
            cursor.close();
        }
    }

    @Override
    public void getCurrentWallpaper() {
        LogUtil.i("getCurrentWallpaper");
        Cursor cursor = mContext.getContentResolver().query(CurrentProvider.CONTENT_URI_CURRENT, null, "name = ?", new String[]{Config.WallpaperSetting}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                LogUtil.i("name: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.NAME)));
                LogUtil.i("val: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.VAL)));
                LogUtil.i("path: " + cursor.getString(cursor.getColumnIndex(CurrentColumns.PATH)));
            }
            cursor.close();
        }
    }


}
