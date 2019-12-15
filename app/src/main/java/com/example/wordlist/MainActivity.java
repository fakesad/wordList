package com.example.wordlist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordlist.test.DatabaseHelper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private List<Word> wordList;
    private ListView list_view;
    private Button search;
    private EditText input;
    private TextView text;
    private int ori,showIndex;
    private long mExitTime;
    private boolean isSearch;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(MainActivity.this,Word_add.class);
                startActivityForResult(intent,1);
                break;
            case R.id.refresh_item:
                refresh();
                Toast.makeText(this,"刷新成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help_item:
                if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                    Toast.makeText(this,"横屏的时候不能修改单词数据哦", Toast.LENGTH_SHORT).show();
                } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
                    Toast.makeText(this,"点击单词进行修改，横屏可以查看例句哦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exit_item:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ori = getResources().getConfiguration().orientation;
        dbHelper = new DatabaseHelper(this,"wordlist.db",null,2);
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.main_landscape);
            list_view = findViewById(R.id.show_landscape);
            search = findViewById(R.id.btn_search_landscape);
            input = findViewById(R.id.text_home_landscape);
            text = findViewById(R.id.text_landscape);
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            list_view = findViewById(R.id.show);
            search = findViewById(R.id.btn_search);
            input = findViewById(R.id.text_home);
        }
        refresh();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().equals("")){
                    isSearch = true;
                    List<Word> words = search(input.getText().toString());
                    MyAdapter adapter = new MyAdapter(MainActivity.this,words);
                    list_view.setAdapter(adapter);
                    if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                        showIndex = 0;
                        word_show(0);
                    }
                 }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    add(data.getStringExtra("word"),
                            data.getStringExtra("mean"),
                            data.getStringExtra("example"));
                    refresh();
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    update(data.getStringExtra("oldWord"),
                            data.getStringExtra("word"),
                            data.getStringExtra("mean"),
                            data.getStringExtra("example"));
                    refresh();
                }
                break;
            default:
                break;
        }
    }

    public List<Word> query(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("wordlist",null,null,null,
                null,null,null);
        List<Word> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String mean = cursor.getString(cursor.getColumnIndex("mean"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                Word word1 = new Word(word,mean,example);
                list.add(word1);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void add(String word,String mean,String example){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word",word);
        values.put("mean",mean);
        values.put("example",example);
        db.insert("wordlist",null,values);
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    public void update(String oldWord,String word,String mean,String example){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word",word);
        values.put("mean",mean);
        values.put("example",example);
        db.update("wordlist",values,"word = ?",new String[]{oldWord});
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    public void delete(String str){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("wordlist","word=?",new String[]{str});
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Word> wordList;

        MyAdapter(Context context,List<Word> wordList) {
            this.mInflater = LayoutInflater.from(context);
            this.wordList = wordList;
        }
        @Override
        public int getCount() {
            return wordList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.word, null);
                holder.name = convertView.findViewById(R.id.word_mean);
                holder.delete = convertView.findViewById(R.id.word_delete);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setTag(position);
            if(ori == Configuration.ORIENTATION_PORTRAIT) {
                holder.name.setText((wordList.get(position).getWord() + "    "
                        + wordList.get(position).getMean()));
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        word_change(position);
                    }
                });
            }
            else {
                holder.name.setText(wordList.get(position).getWord());
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        word_show(position);
                    }
                });
            }
            holder.delete.setTag(position);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_delete(position);
                }
            });
            return convertView;
        }
    }

    private final class ViewHolder {
        private Button delete;
        private Button name;
    }

    public void word_show(int i){
        if(wordList.size()==0)
            word_show();
        else {
            text.setText((wordList.get(i).getMean() + "\n\n例句:" + wordList.get(i).getExample()));
            showIndex = i;
        }
    }

    public void word_show(){
        text.setText("试着先添加一个单词吧");
    }

    public void word_change(int i){
        Intent intent = new Intent(MainActivity.this,Word_change.class);
        intent.putExtra("word",wordList.get(i).getWord());
        intent.putExtra("mean",wordList.get(i).getMean());
        intent.putExtra("example",wordList.get(i).getExample());
        startActivityForResult(intent,2);
    }

    //刷新单词列表
    public void refresh(){
        wordList = query();
        MyAdapter adapter = new MyAdapter(this,wordList);
        list_view.setAdapter(adapter);
        isSearch = false;
        input.setText("");
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            showIndex = 0;
            word_show(0);
        }
    }

    public void list_delete(int i){
        delete(wordList.remove(i).getWord());
        MyAdapter adapter = new MyAdapter(this,wordList);
        list_view.setAdapter(adapter);
        if(ori == Configuration.ORIENTATION_LANDSCAPE) {
            if (showIndex == i) {
                word_show(i);
            }
        }
    }

    public List<Word> search(String str) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Word> words = new ArrayList<>();
        String sql = "select * from wordlist where word like '%"+ str + "%' or " +
                "mean like '%" + str + "%' or " +
                "example like '%" + str + "%'";
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String mean = cursor.getString(cursor.getColumnIndex("mean"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                Word word1 = new Word(word,mean,example);
                words.add(word1);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return words;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isSearch)
                refresh();
            else if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
