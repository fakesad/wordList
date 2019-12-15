package com.example.wordlist.Provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wordlist.R;

public class testProvider extends AppCompatActivity implements View.OnClickListener{
    private Uri uri = Uri.parse("content://com.example.ximena/wordlist");
    Intent intent;
    ContentResolver resolver;
    Button add,query;
    private EditText word,mean,example;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_update_item:
                intent = new Intent(testProvider.this, Word_delete_update.class);
                startActivity(intent);
                break;
            case R.id.help_test_item:
                Toast.makeText(this,"此为帮助", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_test_item:
                finish();
                break;
            default:
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_provider);
        add = findViewById(R.id.btn_provider_add);
        query = findViewById(R.id.btn_provider_query);
        add.setOnClickListener(this);
        query.setOnClickListener(this);
        word = findViewById(R.id.provider_add);
        mean = findViewById(R.id.provider_mean);
        example = findViewById(R.id.provider_example);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_provider_add:
                ContentValues values = new ContentValues();
                values.put("word", word.getText().toString());
                values.put("mean", mean.getText().toString());
                values.put("example",example.getText().toString());
                resolver =  getContentResolver();
                resolver.insert(uri,values);
                Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_provider_query:
                resolver = getContentResolver();
                Cursor cursor = resolver.query(uri, null, null, null, null);
                while (cursor.moveToNext()){
                    Toast.makeText(this,cursor.getString(1) +"  "+ cursor.getString(2)
                            +"\n例句:"+ cursor.getString(3), Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                break;
            default:
                break;
        }
    }
}
