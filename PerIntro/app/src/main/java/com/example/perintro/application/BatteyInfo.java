package com.example.perintro.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;
import com.example.perintro.util.DateUtil;

public class BatteyInfo extends ChatroomActivity {
    private TextView tv_battery_change;
    private static String[] mStatus = {"不存在", "未知", "正在充电", "正在断电", "不在充电", "充满"};
    private static String[] mHealthy = {"不存在", "未知", "良好", "过热", "坏了", "短路", "未知错误", "冷却"};
    private static String[] mPlugged = {"电池", "充电器", "USB", "不存在", "无线"};
    private static String[] tips = {"没电了，但没有完全没。",
            "代入感很强，已经开始焦虑了，充着电呢，那没事了",
            "在？充个电？",
            "拉了拉了，电量拉了。",
            "成大事者，怎能只有40%的电。",
            "四舍五入就是100%，没事，浪！",
            "电量持久，就到离谱，60%嗨翻一整夏\n（炫迈阿巴阿巴）",
            "传下去，我的主人还剩70%多的电，随便浪！",
            "哇塞，这么多电能不能分我点。",
            "小米9用户表示，这光景实属罕见。",
            "天哪，您就是不满电会死星人？"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        preInit("电量信息",R.layout.app_battey_info);
        tv_battery_change = findViewById(R.id.tv_battery_change);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 创建一个电量变化的广播接收器
        batteryChangeReceiver = new BatteryChangeReceiver();
        // 创建一个意图过滤器，只处理指定事件来源的广播
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        // 注册广播接收器，注册之后才能正常接收广播
        // 注意电量变化不能采取静态注册，只能采取动态注册，而且是全局的动态注册，不是本地广播管理器的动态注册
        registerReceiver(batteryChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器，注销之后就不再接收广播
        unregisterReceiver(batteryChangeReceiver);
    }

    // 声明一个电量变化的广播接收器对象
    private BatteryChangeReceiver batteryChangeReceiver;

    // 定义一个电量变化的广播接收器
    private class BatteryChangeReceiver extends BroadcastReceiver {
        // 一旦接收到电池电量发生变化的广播，马上触发接收器的onReceive方法
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                // 下面从广播意图中依次获取电量刻度、当前电量、当前状态、健康程度等参数信息
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
                int healthy = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 3);
                String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
                String desc = String.format("%s : 收到广播：%s", DateUtil.getNowTime(), intent.getAction());
                desc = String.format("%s\n电量刻度=%d", desc, scale);
                desc = String.format("%s\n当前电量=%d", desc, level);
                desc = String.format("%s\n当前状态=%s", desc, mStatus[status]);
                desc = String.format("%s\n健康程度=%s", desc, mHealthy[healthy]);
                desc = String.format("%s\n当前电压=%d", desc, voltage);
                desc = String.format("%s\n当前电源=%s", desc, mPlugged[plugged]);
                desc = String.format("%s\n当前技术=%s", desc, technology);
                desc = String.format("%s\n当前温度=%d", desc, temperature / 10);
                desc = String.format("%s\n是否提供电池=%s", desc, present ? "是" : "否");
                desc = String.format("%s\n\n\n\n%s", desc, tips[level/10]);
                tv_battery_change.setText(desc);
            }
        }
    }
}
