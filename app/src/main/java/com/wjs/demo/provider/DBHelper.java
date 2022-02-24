package com.wjs.demo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.wjs.demo.utils.LogUtil;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wallpapertheme.db";

    private static int DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        LogUtil.d("Constructor DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CurrentContact.CREATE_TABLE);
        db.execSQL("INSERT INTO current (val,name,path) VALUES ('0','WallpaperSettingIPC','')");
        db.execSQL("INSERT INTO current (val,name,path) VALUES ('1','TopicSetting','')");
        LogUtil.d("onCreate 建表并插入两条数据");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.w("DBHelper", "-------> onUpgrade" + "   oldVersion = " + oldVersion + "   newVersion = " + newVersion);
    }

}
