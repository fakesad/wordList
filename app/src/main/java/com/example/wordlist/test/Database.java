package com.example.wordlist.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wordlist.R;

public class Database extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        dbHelper = new DatabaseHelper(this,"wordlist.db",null,2);
        Button create = (Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dbHelper.getWritableDatabase();
            }
        });
        Button query = (Button)findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("wordlist",null,null,null,
                        null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String mean = cursor.getString(cursor.getColumnIndex("mean"));
                        String example = cursor.getString(cursor.getColumnIndex("example"));
                        Toast.makeText(Database.this, "word:"+word+"--mean:"+mean+"--example:"+example, Toast.LENGTH_SHORT).show();
                    }while(cursor.moveToNext());
                }
                cursor.close();

            }
        });
        Button add = (Button)findViewById(R.id.add1);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("word","name");
                values.put("mean","n.名字,名称,名誉");
                values.put("example","He stroked the name of himself with his pen.");
                db.insert("wordlist",null,values);
                Toast.makeText(Database.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("wordlist","word=?",new String[]{"name"});
                Toast.makeText(Database.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
