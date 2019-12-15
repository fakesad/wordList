package com.example.wordlist.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.wordlist.test.DatabaseHelper;

public class MyProvider extends ContentProvider {

    private Context mContext;
    DatabaseHelper dbHelper = null;
    SQLiteDatabase db = null;
    public static final String AUTOHORITY = "com.example.ximena";
    String table = "wordlist";
    private static final UriMatcher mMatcher;
    static{
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTOHORITY,"wordlist", 1);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        dbHelper = new DatabaseHelper(mContext,"wordlist.db",null,2);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        try {
            db.update(table, values, selection + "=?", selectionArgs);
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            db.delete(table,selection+"=?",selectionArgs);
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}