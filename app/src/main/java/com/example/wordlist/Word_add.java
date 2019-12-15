package com.example.wordlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class Word_add extends AppCompatActivity implements View.OnClickListener{
    private EditText word,mean,example;
    private Button btn_add,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        word = findViewById(R.id.word_add);
        mean = findViewById(R.id.word_add_mean);
        example = findViewById(R.id.word_add_example);
        btn_add = findViewById(R.id.btn_add);
        btn_back = findViewById(R.id.btn_back);
        btn_add.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_add:
                if(word.getText().toString().equals("")||mean.getText().toString().equals("")
                        ||example.getText().toString().equals("")){
                    Toast.makeText(this,"请输入完整的信息", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("word",word.getText().toString());
                    intent.putExtra("mean",mean.getText().toString());
                    intent.putExtra("example",example.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

}
