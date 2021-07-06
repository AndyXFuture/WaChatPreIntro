package com.example.perintro.application;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;
import com.example.perintro.override.ExpandTextLayout;

public class AboutMe extends ChatroomActivity {
    private int[] newsArray = {R.string.news1, R.string.news2, R.string.news3, R.string.news4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        preInit("关于我",R.layout.app_expand);

        // 从布局文件中获取名叫etl_content的展开文字布局
        ExpandTextLayout etl_content = findViewById(R.id.etl_content);
        int seq = (int) (Math.random() * 100 % 4);
        // 设置展开文字布局的文本内容
        //etl_content.setBackgroundColor(Color.parseColor("#FFAAAAFF"));
        etl_content.setText(newsArray[seq]);

    }
}
