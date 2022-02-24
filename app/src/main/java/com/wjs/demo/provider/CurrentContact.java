package com.wjs.demo.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wjs.demo.utils.LogUtil;

public class CurrentContact {

    public static final String AUTHORITY = "com.wjs.demo.provider";

    public static final String TABLE_NAME = "current";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            CurrentColumns._ID + " INTEGER PRIMARY KEY," +
            CurrentColumns.VAL + " TEXT," +
            CurrentColumns.NAME + " TEXT," +
            CurrentColumns.PATH + " TEXT" + ")";


    private static final String[] AVAILABLE_PROJECTION = new String[]{
            CurrentColumns._ID,
            CurrentColumns.VAL,
            CurrentColumns.NAME,
            CurrentColumns.PATH
    };

    private static CurrentContact instance = null;

    private CurrentContact() {
    }

    public static CurrentContact getInstance() {
        if (instance == null) {
            instance = new CurrentContact();
        }
        return instance;
    }

    public static Cursor queryCursor(SQLiteOpenHelper helper, String selection, String[] selectionArgs,
                                     String groupBy, String having, String orderBy) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, selection, selectionArgs, groupBy, having, orderBy);
        LogUtil.d("queryCursor 查询数据");
        return cursor;
    }

    public static long insert(SQLiteOpenHelper helper, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        LogUtil.d("insert 插入数据");
        return db.insert(TABLE_NAME, null, values);
    }

    public static int update(SQLiteOpenHelper helper, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        LogUtil.d("update 更新数据");
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    public static int delete(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        LogUtil.d("delete 删除数据");
        return db.delete(TABLE_NAME, null, null);
    }

}
