package com.example.perintro.application;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.TextView;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;

/**
 * Created by ouyangshen on 2017/10/7.
 */
@SuppressLint("DefaultLocale")
// 该页面类实现了接口OnTimeSetListener，意味着要重写时间监听器的onTimeSet方法
public class TimePickerActivity extends ChatroomActivity implements OnClickListener, OnTimeSetListener, TimePicker.OnTimeChangedListener {
    private TextView tv_time;
    private TimePicker tp_time; // 声明一个时间选择器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        preInit("时间选择器",R.layout.app_time_picker);

        tv_time = findViewById(R.id.tv_time);
        // 从布局文件中获取名叫tp_time的时间选择器
        tp_time = findViewById(R.id.tp_time);
        tp_time.setOnTimeChangedListener(this);
        findViewById(R.id.btn_time).setOnClickListener(this);
        //findViewById(R.id.btn_ok).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_time) {
            // 获取日历的一个实例，里面包含了当前的时分秒
            Calendar calendar = Calendar.getInstance();
            // 构建一个时间对话框，该对话框已经集成了时间选择器。
            // TimePickerDialog的第二个构造参数指定了时间监听器
            TimePickerDialog dialog = new TimePickerDialog(this, this,
                    calendar.get(Calendar.HOUR_OF_DAY), // 小时
                    calendar.get(Calendar.MINUTE), // 分钟
                    true); // true表示24小时制，false表示12小时制
            // 把时间对话框显示在界面上
            dialog.show();
        }
        //else if (v.getId() == R.id.btn_ok) {
            // 获取时间选择器tp_time设定的小时和分钟
        //    String desc = String.format("您选择的时间是%d时%d分", tp_time.getCurrentHour(), tp_time.getCurrentMinute());
        //    tv_time.setText(desc);
        //}
    }

    // 一旦点击时间对话框上的确定按钮，就会触发监听器的onTimeSet方法
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String State =  "刚断网，准备点烧烤看电影呢";
        switch(hourOfDay){
            case 0:
                State =  "刚断网，准备点烧烤看电影呢";break;
            case 1:
                State =  "看电影 / 在床上刷手机";break;
            case 2:
                State =  "看完电影刷手机 / 刷完手机睡觉";break;
            case 3:
                State =  "入寐ing/失眠ing/打鸡血ing";break;
            case 4:
                State =  "睡觉ing/焦虑ing/考虑褪黑素ing";break;
            case 5:
                State =  "睡觉ing/深睡ing";break;
            case 6:
                State =  "熟睡ing/退出深睡ing";break;
            case 7:
                State =  "熟睡ing/有课焦虑ing";break;
            case 8:
                State =  "熟睡ing/被拖着起床ing/被子封印最强时刻/爬去上课ing";break;
            case 9:
                State =  "熟睡ing/双眼无神的看着老师";break;
            case 10:
                State =  "熟睡ing/点外卖ing";break;
            case 11:
                State =  "熟睡ing/期待下课ing";break;
            case 12:
                State =  "刚醒/百米冲刺ing/干饭ing/酝酿午睡ing";break;
            case 13:
                State =  "午睡ing";break;
            case 14:
                State =  "神志不清ing/幻想翘课ing";break;
            case 15:
                State =  "坐牢ing/活着";break;
            case 16:
                State =  "活着，但没完全活着";break;
            case 17:
                State =  "解放感UpUp/思考吃什么味的泡面";break;
            case 18:
                State =  "感慨新的一天又来了/吃泡面ing/逛B站ing";break;
            case 19:
                State =  "规划今晚目标ing/新建文件夹ing";break;
            case 20:
                State =  "醒着，但可能开始困了";break;
            case 21:
                State =  "封闭自我/项目在一天中进展最快的时间（无人社交烦恼的时间）";break;
            case 22:
                State =  "肾虚，往往在过度劳累之后/思考今晚看什么电影";break;
            case 23:
                State =  "这项目不是我不行，是真的赶不完/放弃今日目标ing";break;
        }


        String desc = String.format("您选择的时间是%d时%d分\n\n%s", hourOfDay, minute, State);
        tv_time.setText(desc);

        for(int i = 0;i <= minute%3;i++){
            tv_time.append(" ,阿巴阿巴");
        }
        // 获取时间对话框设定的小时和分钟
        //String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
        //tv_time.setText(desc);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        String State =  "刚断网，准备点烧烤看电影呢";
        switch(hourOfDay){
            case 0:
                State =  "刚断网，准备点烧烤看电影呢";break;
            case 1:
                State =  "看电影 / 在床上刷手机";break;
            case 2:
                State =  "看完电影刷手机 / 刷完手机睡觉";break;
            case 3:
                State =  "入寐ing/失眠ing/打鸡血ing";break;
            case 4:
                State =  "睡觉ing/焦虑ing/考虑褪黑素ing";break;
            case 5:
                State =  "睡觉ing/深睡ing";break;
            case 6:
                State =  "熟睡ing/退出深睡ing";break;
            case 7:
                State =  "熟睡ing/有课焦虑ing";break;
            case 8:
                State =  "熟睡ing/被拖着起床ing/被子封印最强时刻/爬去上课ing";break;
            case 9:
                State =  "熟睡ing/双眼无神的看着老师";break;
            case 10:
                State =  "熟睡ing/点外卖ing";break;
            case 11:
                State =  "熟睡ing/期待下课ing";break;
            case 12:
                State =  "刚醒/百米冲刺ing/干饭ing/酝酿午睡ing";break;
            case 13:
                State =  "午睡ing";break;
            case 14:
                State =  "神志不清ing/幻想翘课ing";break;
            case 15:
                State =  "坐牢ing/活着";break;
            case 16:
                State =  "活着，但没完全活着";break;
            case 17:
                State =  "解放感UpUp/思考吃什么味的泡面";break;
            case 18:
                State =  "感慨新的一天又来了/吃泡面ing/逛B站ing";break;
            case 19:
                State =  "规划今晚目标ing/新建文件夹ing";break;
            case 20:
                State =  "醒着，但可能开始困了";break;
            case 21:
                State =  "封闭自我/项目在一天中进展最快的时间（无人社交烦恼的时间）";break;
            case 22:
                State =  "肾虚，往往在过度劳累之后/思考今晚看什么电影";break;
            case 23:
                State =  "这项目不是我不行，是真的赶不完/放弃今日目标ing";break;
        }


        String desc = String.format("您选择的时间是%d时%d分\n\n%s", hourOfDay, minute, State);
        tv_time.setText(desc);

        for(int i = 0;i <= minute%3;i++){
            tv_time.append(" ,阿巴阿巴");
        }
    }
}
