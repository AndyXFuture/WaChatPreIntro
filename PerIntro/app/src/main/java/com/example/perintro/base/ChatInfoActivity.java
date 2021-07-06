package com.example.perintro.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perintro.R;
import com.example.perintro.bean.App;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text_chatroom_label;
    private TextView tv_info_title, tv_info_content;
    private Button btn_chatroom_left;
    private Drawable ic_fanhui_pure, ic_main;
    private ImageView imageView;
    private LinearLayout ly_setalias, ly_setfriper, ly_friendscir, ly_more;
    private TextView btn_send, btn_send2;
    private Button btn_delfriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatinfo);

        preInit();
    }

    public void preInit(){
        //去除标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        //应用标题和内容
        tv_info_title = findViewById(R.id.tv_info_title);
        tv_info_content = findViewById(R.id.tv_info_content);
        //功能按键
        ly_setalias = findViewById(R.id.ly_setalias);
        ly_setfriper = findViewById(R.id.ly_setfriper);
        ly_friendscir = findViewById(R.id.ly_friendscir);
        ly_more = findViewById(R.id.ly_more);
        btn_send = findViewById(R.id.btn_send);
        btn_send2 = findViewById(R.id.btn_send2);
        btn_delfriend =  findViewById(R.id.btn_delfriend);

        ly_setalias.setOnClickListener(this);
        ly_setfriper.setOnClickListener(this);
        ly_friendscir.setOnClickListener(this);
        ly_more.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_send2.setOnClickListener(this);
        btn_delfriend.setOnClickListener(this);

        Intent intent = getIntent();
        int a = intent.getIntExtra("serial",0);
        ArrayList<App> appList = App.getDefaultList();
        tv_info_title.setText(appList.get(a).name);
        tv_info_content.setText(appList.get(a).appInfo);

        //图片资源
        ic_fanhui_pure = getResources().getDrawable(R.drawable.ic_fanhui_pure);
        ic_fanhui_pure.setBounds(0, 0, 50, 50);

        ic_main = getResources().getDrawable(appList.get(a).image);
        ic_main.setBounds(0, 0, 100, 100);
        imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(ic_main);

        //聊天窗口顶部栏
        btn_chatroom_left = findViewById(R.id.btn_chatroom_left);
        text_chatroom_label = (TextView)findViewById(R.id.text_label);

        //text_chatroom_label.setText("应用信息");
        btn_chatroom_left.setCompoundDrawables(null, ic_fanhui_pure, null, null);

        btn_chatroom_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_setalias:
                showToast("别按了，别想给我起绰号");
                break;
            case R.id.ly_setfriper:
                showToast("别按了，别想屏蔽我");
                break;
            case R.id.ly_friendscir:
                showToast("别按了，朋友圈我不需要");
                break;
            case R.id.ly_more:
                showToast("别按了，没信息了");
                break;
            case R.id.btn_send:
                finish();
                break;
            case R.id.btn_send2:
                showToast("天哪你还想通话");
                break;
            case R.id.btn_delfriend:
                showToast("别按了，辛苦做的呢");
                break;
        }
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
