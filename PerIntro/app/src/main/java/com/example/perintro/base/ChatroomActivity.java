package com.example.perintro.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perintro.MainActivity;
import com.example.perintro.R;
import com.example.perintro.application.TimePickerActivity;

public class ChatroomActivity extends AppCompatActivity {
    private TextView text_chatroom_label;
    private Button btn_chatroom_left,btn_chatroom_right;
    private Drawable ic_fanhui_pure,ic_sandian_pure;
    private ViewStub inc_chatroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void preInit(String name,int i){
        //去除标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //图片资源
        ic_fanhui_pure = getResources().getDrawable(R.drawable.ic_fanhui_pure);
        ic_fanhui_pure.setBounds(0, 0, 50, 50);
        ic_sandian_pure  = getResources().getDrawable(R.drawable.ic_sandian_pure);
        ic_sandian_pure.setBounds(0, 0, 50, 50);

        //聊天窗口顶部栏
        btn_chatroom_left = (Button)findViewById(R.id.btn_chatroom_left);
        btn_chatroom_right = (Button)findViewById(R.id.btn_chatroom_right);
        text_chatroom_label = (TextView)findViewById(R.id.text_label);

        text_chatroom_label.setText(name);
        btn_chatroom_left.setCompoundDrawables(null, ic_fanhui_pure, null, null);
        btn_chatroom_right.setCompoundDrawables(null, ic_sandian_pure, null, null);

        btn_chatroom_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_chatroom_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int a = intent.getIntExtra("serial",0);
                intent.setClass(ChatroomActivity.this, ChatInfoActivity.class);
                intent.putExtra("serial", a);
                startActivity(intent);
            }
        });


        ViewStub inc_chatroom = (ViewStub) findViewById(R.id.inc_chatroom);
        inc_chatroom.setLayoutResource(i);
        inc_chatroom.inflate(); // inflate 1st layout

        //第二次膨胀
        //LinearLayout ll = (LinearLayout) findViewById(R.id.time_picker);
        //ll.removeAllViews(); // remove previous view, add 2nd layout
        //ll.addView(LayoutInflater.from(context).inflate(secondLayoutId, ll, false));

    }
}
