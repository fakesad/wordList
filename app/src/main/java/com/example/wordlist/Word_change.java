package com.example.wordlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Word_change extends AppCompatActivity implements View.OnClickListener{
    private EditText word,mean,example;
    private String oldWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_change);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        word = findViewById(R.id.word_change);
        mean = findViewById(R.id.word_change_mean);
        example = findViewById(R.id.word_change_example);
        Button btn_change = findViewById(R.id.btn_change);
        Button btn_back = findViewById(R.id.btn_change_back);
        btn_change.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        Intent in = getIntent();
        oldWord = in.getStringExtra("word");
        word.setText(oldWord);
        mean.setText(in.getStringExtra("mean"));
        example.setText(in.getStringExtra("example"));
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_change:
                if(word.getText().toString().equals("")||mean.getText().toString().equals("")
                        ||example.getText().toString().equals("")){
                    Toast.makeText(this,"请输入完整的信息", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("oldWord",oldWord);
                    intent.putExtra("word",word.getText().toString());
                    intent.putExtra("mean",mean.getText().toString());
                    intent.putExtra("example",example.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.btn_change_back:
                finish();
                break;
            default:
                break;
        }
    }
}
