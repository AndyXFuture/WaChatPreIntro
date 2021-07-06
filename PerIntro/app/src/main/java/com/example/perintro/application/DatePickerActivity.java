package com.example.perintro.application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;

import static java.lang.Math.abs;

// 该页面类实现了接口OnDateSetListener，意味着要重写日期监听器的onDateSet方法
public class DatePickerActivity extends ChatroomActivity implements OnClickListener, OnDateSetListener {
    private TextView tv_date;
    private DatePicker dp_date; // 声明一个日期选择器对象
    //private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy.MM.DD");

    private ImageView iv_vector_hook; // 声明一个图像视图对象
    private Drawable mDrawable; // 声明一个图形对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        preInit("日期选择器",R.layout.app_date_picker);

        tv_date = findViewById(R.id.tv_date);
        // 从布局文件中获取名叫dp_date的日期选择器
        dp_date = findViewById(R.id.dp_date);
        //dp_date.setOnDateChangedListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        //findViewById(R.id.btn_ok).setOnClickListener(this);
        iv_vector_hook = findViewById(R.id.iv_vector_hook);


        Calendar cd = Calendar.getInstance();
        dp_date.init(cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);

                //String desc = String.format("您选择的日期是%d年%d月%d日",
                //        year, month, dayOfMonth);
                //tv_date.setText(desc);

                int Year = dp_date.getYear();
                int Month = dp_date.getMonth() + 1;
                int Day = dp_date.getDayOfMonth();
                int myYear = 2001;
                int myMonth = 1;
                int myDay = 26;
                String tips0 = "猜对了！！！  ";
                String tips1 = "很接近了  ";
                String tips2 = "还有一段距离呢  ";
                String tips3 = "还差得远呢  ";
                String year_tips = "", month_tips="", day_tips = "";

                if(Year-myYear == 0){
                    year_tips = String.format("年份%s",tips0);
                }else if(abs(Year-myYear) <5){
                    year_tips = String.format("年份%s",tips1);
                }else if(abs(Year-myYear) <15){
                    year_tips = String.format("年份%s",tips2);
                }else{
                    year_tips = String.format("年份%s",tips3);
                }

                if(Month-myMonth == 0){
                    month_tips = String.format("月份%s",tips0);
                }else if(abs(Month-myMonth) <3){
                    month_tips = String.format("月份%s",tips1);
                }else if(abs(Month-myMonth) <5){
                    month_tips = String.format("月份%s",tips2);
                }else{
                    month_tips = String.format("月份%s",tips3);
                }

                if(Day-myDay == 0){
                    day_tips = String.format("日期%s",tips0);
                }else if(abs(Day-myDay) <3){
                    day_tips = String.format("日期%s",tips1);
                }else if(abs(Day-myDay) <7){
                    day_tips = String.format("日期%s",tips2);
                }else{
                    day_tips = String.format("日期%s",tips3);
                }

                if(Year-myYear == 0 && Month-myMonth == 0 && Day-myDay == 0){
                    // 开始播放画圈的矢量动画
                    startVectorAnim(R.drawable.animated_vector_pay_circle);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 为画圈动画注册一个矢量动画图形的监听器
                        ((AnimatedVectorDrawable) mDrawable)
                                .registerAnimationCallback(new VectorAnimListener());
                    } else {
                        // 延迟1秒后启动打勾动画的播放任务
                        new Handler().postDelayed(mHookRunnable, 1000);
                    }
                }

                String desc = String.format("您选择的日期是%d年%d月%d日\n\n\t%s\n\n\t%s\n\n\t%s",
                        dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth(),
                        year_tips, month_tips, day_tips);

                tv_date.setText(desc);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date) {
            // 获取日历的一个实例，里面包含了当前的年月日
            Calendar calendar = Calendar.getInstance();
            // 构建一个日期对话框，该对话框已经集成了日期选择器。
            // DatePickerDialog的第二个构造参数指定了日期监听器
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR), // 年份
                    calendar.get(Calendar.MONTH), // 月份
                    calendar.get(Calendar.DAY_OF_MONTH)); // 日子
            // 把日期对话框显示在界面上
            dialog.show();
        } /*else if (v.getId() == R.id.btn_ok) {
             //获取日期选择器dp_date设定的年月份
            String desc = String.format("您选择的日期是%d年%d月%d日",
                    dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
            tv_date.setText(desc);
        }*/
    }

    // 一旦点击日期对话框上的确定按钮，就会触发监听器的onDateSet方法
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        //dp_date.(year, monthOfYear, dayOfMonth);

        int Year = year;
        int Month = monthOfYear+1;
        int Day = dayOfMonth;
        int myYear = 2001;
        int myMonth = 1;
        int myDay = 26;
        String tips0 = "猜对了  ";
        String tips1 = "很接近了  ";
        String tips2 = "快了快了，没差多少了  ";
        String tips3 = "还差得远呢  ";
        String year_tips = "", month_tips="", day_tips = "";

        if(Year-myYear == 0){
            year_tips = String.format("年份%s",tips0);
        }else if(abs(Year-myYear) <5){
            year_tips = String.format("年份%s",tips1);
        }else if(abs(Year-myYear) <15){
            year_tips = String.format("年份%s",tips2);
        }else{
            year_tips = String.format("年份%s",tips3);
        }

        if(Month-myMonth == 0){
            month_tips = String.format("月份%s",tips0);
        }else if(abs(Month-myMonth) <2){
            month_tips = String.format("月份%s",tips1);
        }else if(abs(Month-myMonth) <4){
            month_tips = String.format("月份%s",tips2);
        }else{
            month_tips = String.format("月份%s",tips3);
        }

        if(Day-myDay == 0){
            day_tips = String.format("日%s",tips0);
        }else if(abs(Day-myDay) <3){
            day_tips = String.format("日%s",tips1);
        }else if(abs(Day-myDay) <6){
            day_tips = String.format("日%s",tips2);
        }else{
            day_tips = String.format("日%s",tips3);
        }

        if(Year-myYear == 0 && Month-myMonth == 0 && Day-myDay == 0){
            // 开始播放画圈的矢量动画
            startVectorAnim(R.drawable.animated_vector_pay_circle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 为画圈动画注册一个矢量动画图形的监听器
                ((AnimatedVectorDrawable) mDrawable)
                        .registerAnimationCallback(new VectorAnimListener());
            } else {
                // 延迟1秒后启动打勾动画的播放任务
                new Handler().postDelayed(mHookRunnable, 1000);
            }
        }

        String desc = String.format("您选择的日期是%d年%d月%d\n\n\t%s\n\n\t%s\n\n\t%s",
                year, monthOfYear + 1, dayOfMonth,
                year_tips, month_tips, day_tips);


        tv_date.setText(desc);

        // 获取日期对话框设定的年月份
        //String desc = String.format("您选择的日期是%d年%d月%d日",
        //        year, monthOfYear + 1, dayOfMonth);
        //tv_date.setText(desc);
    }


    // 开始播放矢量动画
    private void startVectorAnim(int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 从指定资源编号的矢量文件中获取图形对象
            mDrawable = getResources().getDrawable(drawableId);
            // 设置图像视图的图形对象
            iv_vector_hook.setImageDrawable(mDrawable);
            // 将该图形强制转换为动画图形，并开始播放
            ((Animatable) mDrawable).start();
        } else {
            // 设置图像视图的图像资源编号
            iv_vector_hook.setImageResource(drawableId);
            // 将图像视图承载的图形强制转换为动画图形，然后再进行播放
            ((Animatable) iv_vector_hook.getDrawable()).start();
        }
    }

    // 定义一个动画图形的监听器
    // Android6.0以后系统采取监听器Animatable2.AnimationCallback监控动画播放事件
    @TargetApi(Build.VERSION_CODES.M)
    private class VectorAnimListener extends Animatable2.AnimationCallback {
        // 在动画图形开始播放时触发
        public void onAnimationStart(Drawable drawable) {}

        // 在动画图形结束播放时触发
        public void onAnimationEnd(Drawable drawable) {
            // 开始播放打勾的矢量动画
            startVectorAnim(R.drawable.animated_vector_pay_success);
        }
    }

    // 定义一个打勾动画的播放任务
    // Android4.*和5.*系统，只能利用定时任务来延迟执行新动画的播放
    private Runnable mHookRunnable = new Runnable() {
        @Override
        public void run() {
            // 开始播放打勾的矢量动画
            startVectorAnim(R.drawable.animated_vector_pay_success);
        }
    };
}
