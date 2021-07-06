package com.example.perintro.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perintro.MainActivity;
import com.example.perintro.R;
import com.example.perintro.application.BannerAnim;
import com.example.perintro.application.BatteyInfo;
import com.example.perintro.application.CalculatorActivity;
import com.example.perintro.application.DatePickerActivity;
import com.example.perintro.application.SeekBarDemo;
import com.example.perintro.application.SersorActivity;
import com.example.perintro.application.TimePickerActivity;
import com.example.perintro.application.VibratorDevice;
import com.example.perintro.application.VolumeActivity;
import com.example.perintro.bean.App;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    // 定义自动完成的提示文本数组
    //private String[] hintArray = {"第一", "第一次", "第一次写代码", "第一次领工资", "第二", "第二个"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //去除标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        ArrayList<App> appList = App.getDefaultList();
        String[] hintArray = new String[appList.toArray().length];
        for(int i=0;i<appList.toArray().length;i++){
            hintArray[i] = appList.get(i).name;
        }


        TextView tv_cal = findViewById(R.id.tv_cal);
        tv_cal.setOnClickListener(this);
        // 从布局文件中获取名叫ac_text的自动完成编辑框
        AutoCompleteTextView ac_text = findViewById(R.id.ac_text);
        // 声明一个自动完成时下拉展示的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.item_dropdown, hintArray);
        // 设置自动完成编辑框的数组适配器
        ac_text.setAdapter(adapter);
        ac_text.setOnItemClickListener(this);
    }
    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayList<App> appList = App.getDefaultList();

        Intent intent = new Intent(SearchActivity.this, TimePickerActivity.class);

        MaterialTextView b = (MaterialTextView)view;
        String buttonText = b.getText().toString();
        for(int j=0;j<appList.toArray().length;j++){
            if(appList.get(j).name == b.getText().toString()){
                showToast("将跳转至"+appList.get(j).name);
                switch (j) {
                    case 0:
                        intent.setClass(SearchActivity.this, TimePickerActivity.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(SearchActivity.this, DatePickerActivity.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(SearchActivity.this, CalculatorActivity.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(SearchActivity.this, BatteyInfo.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 4:
                        //intent.setClass(MainActivity.this, TimePickerActivity.class);
                        //intent.putExtra("serial",position);
                        //startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(SearchActivity.this, BannerAnim.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(SearchActivity.this, VibratorDevice.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.setClass(SearchActivity.this, SeekBarDemo.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 8:
                        intent.setClass(SearchActivity.this, VolumeActivity.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 9:
                        intent.setClass(SearchActivity.this, SersorActivity.class);
                        intent.putExtra("serial",j);
                        startActivity(intent);
                        break;
                    case 10:
                        //intent.setClass(MainActivity.this, TimePickerActivity.class);
                        //intent.putExtra("serial",position);
                        //startActivity(intent);
                        break;
                    case 11:
                        //intent.setClass(MainActivity.this, TimePickerActivity.class);
                        //intent.putExtra("serial",position);
                        //startActivity(intent);
                        break;
                }
            }
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cal:
                finish();
                break;
        }
    }
}