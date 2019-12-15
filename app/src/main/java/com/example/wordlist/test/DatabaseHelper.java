package com.example.wordlist.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table wordlist (" +
            "id integer primary key autoincrement," +
            "word varchar(20) not null," +
            "mean nvarchar(20) not null," +
            "example text" +
            ")";

    private Context mContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        Toast.makeText(mContext, "create successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//       sqLiteDatabase.execSQL("drop table if EXISTS wordlist");
//        sqLiteDatabase.execSQL(CREATE_USER);
//        Toast.makeText(mContext, "update successfully!", Toast.LENGTH_LONG).show();
    }

    public void dropTable(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = 'wordinfo';");
//        sqLiteDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = 'wordorig';");
//        sqLiteDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = 'wordpartofspeech';");
//        Toast.makeText(mContext, "drop successfully!", Toast.LENGTH_LONG).show();
    }

    public void deleteDatabase(Context context) {
//        context.deleteDatabase("WordBook.db");
//        Toast.makeText(mContext, "delete successfully!", Toast.LENGTH_LONG).show();
    }
}
