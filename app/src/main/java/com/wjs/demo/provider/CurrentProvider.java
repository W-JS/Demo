package com.wjs.demo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wjs.demo.utils.LogUtil;

public class CurrentProvider extends ContentProvider {

    public static final Uri CONTENT_URI_CURRENT = Uri.parse("content://" + CurrentContact.AUTHORITY + "/" + CurrentContact.TABLE_NAME);

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CurrentContact.AUTHORITY, CurrentContact.TABLE_NAME, 1);
        LogUtil.d("静态块 只执行一次");
    }

    DBHelper dbHelper;

    private static CurrentProvider instance = null;

    private CurrentProvider() {
    }

    public static CurrentProvider getInstance() {
        if (instance == null) {
            instance = new CurrentProvider();
        }
        return instance;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        LogUtil.d("onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (CONTENT_URI_CURRENT.equals(uri)) {
            return CurrentContact.queryCursor(dbHelper, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = CurrentContact.insert(dbHelper, values);
        if (rowId > 0) {
            Uri wordUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return wordUri;
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int num = CurrentContact.delete(dbHelper);
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int num = CurrentContact.update(dbHelper, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }


    @Nullable
    public Cursor query(@Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return CurrentContact.queryCursor(dbHelper, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    public long insert(@Nullable ContentValues values) {
        long rowId = CurrentContact.insert(dbHelper, values);
        if (rowId > 0) {
            return rowId;
        } else {
            throw new SQLException("Failed to insert row");
        }
    }

    public int delete(@Nullable String selection, @Nullable String[] selectionArgs) {
        int num = CurrentContact.delete(dbHelper);
        return num;
    }

    public int update(@Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int num = CurrentContact.update(dbHelper, values, selection, selectionArgs);
        return num;
    }

}
