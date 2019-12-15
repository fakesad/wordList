package com.example.wordlist.Provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wordlist.R;

import java.util.ArrayList;
import java.util.List;

public class Word_delete_update extends AppCompatActivity implements View.OnClickListener{
    Uri uri = Uri.parse("content://com.example.ximena/wordlist");
    ContentResolver resolver;
    Cursor cursor;
    Button change,delete,back;
    EditText word,mean,example;
    Spinner selection,selectionArgs;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_delete_update);
        word = findViewById(R.id.word_provider2);
        mean = findViewById(R.id.word_provider2_mean);
        example = findViewById(R.id.word_provider2_example);
        change = findViewById(R.id.btn_provider2_change);
        delete = findViewById(R.id.btn_provider2_delete);
        back = findViewById(R.id.btn_provider2_back);
        change.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        selection = findViewById(R.id.sp_selection);
        selectionArgs = findViewById(R.id.sp_selectionArgs);
        adapter = ArrayAdapter.createFromResource(Word_delete_update.this, R.array.selection,
                android.R.layout.simple_spinner_item);
        selection.setAdapter(adapter);
        selection.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String str = (String)selection.getSelectedItem();
                refresh(str);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        refresh("word");
        selectionArgs.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String str = (String)selection.getSelectedItem();
                String str2 = (String)selectionArgs.getSelectedItem();
                resolver = getContentResolver();
                cursor = resolver.query(uri, null, str+"=?", new String[]{str2}, null);
                while (cursor.moveToNext()){
                    word.setText(cursor.getString(cursor.getColumnIndex("word")));
                    mean.setText(cursor.getString(cursor.getColumnIndex("mean")));
                    example.setText(cursor.getString(cursor.getColumnIndex("example")));
                }
                cursor.close();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void refresh(String str){
        List<String> list = new ArrayList<String>();
        resolver = getContentResolver();
        cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(str)));
        }
        cursor.close();
        ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.selection_item,R.id.text,list);
        selectionArgs.setAdapter(adapter2);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_provider2_change:
                ContentValues values = new ContentValues();
                values.put("word", word.getText().toString());
                values.put("mean", mean.getText().toString());
                values.put("example",example.getText().toString());
                resolver =  getContentResolver();
                if(resolver.update(uri,values,selection.getSelectedItem().toString(),
                                new String[]{selectionArgs.getSelectedItem().toString()})==0)
                    Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"修改失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_provider2_delete:
                resolver =  getContentResolver();
                if(resolver.delete(uri,selection.getSelectedItem().toString(),
                        new String[]{selectionArgs.getSelectedItem().toString()})==0)
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_provider2_back:
                finish();
                break;
            default:
                break;
        }
    }
}
