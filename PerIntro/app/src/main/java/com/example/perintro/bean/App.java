package com.example.perintro.bean;

import android.content.Context;

import java.util.ArrayList;

import com.example.perintro.R;
import com.example.perintro.application.TimePickerActivity;

public class App {
    public int image;
    public String name;
    public String desc;
    public String time;
    public String appInfo;
    public int state;

    public App(int image, String name, String desc, String time, String appInfo,int state) {
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.appInfo = appInfo;
        this.state = state;
    }

    private static int[] iconArray = {R.drawable.ic_time_pure, R.drawable.ic_rili_pure, R.drawable.ic_calculator_pure,
            R.drawable.ic_battey_pure, R.drawable.ic_jindutiao, R.drawable.ic_huadong,R.drawable.ic_zhendong,R.drawable.ic_tuodong,
            R.drawable.ic_yinliang,R.drawable.ic_chuanganqi,R.drawable.ic_sanwei,R.drawable.ic_guanyu};
    private static String[] nameArray = {"时间选择器", "日期选择器", "计算器（逆波兰）", "电量信息", "进度条", "飞掠动画","震动器","拖动条","音量设置","传感器","三维物体","关于我"};
    private static String[] descArray = {
            "我一天在干什么？",
            "猜猜我的出生日期",
            "想用计算器?，没事，我这里有",
            "让我康康，让我康康",
            "旧版本的东西适配进来太麻烦了，先放弃",
            "看广告，识家乡",
            "震出节奏",
            "单纯的拖动进度条",
            "我都不知道手机有这么多种音量",
            "物联网学生怎么能不知道手机的传感器",
            "圆形和方形的三维动画",
            "可能有彩蛋？也可能还没想好"
    };
    private static String[] AppInfoArray = {
            "时间选择器TimePicker，判断时刻向用户列出我一天的作息",
            "日期选择器DatePicker，实时获取选择给予用户提醒，达到最终猜出我的出生日期效果",
            "通过按钮获取输入，四则运算基本实现，并加入了防呆设计，目前已知BUG：使用点号可能会计算错误",
            "通过广播接收器BroadcastReceiver，接收广播获得电量信息，判断后发出感慨",
            "旧版本的东西适配进来太麻烦了，先放弃",
            "利用飞掠视图动画模拟广告条的展现，图片来自江门市政府官网",
            "震动操作，节奏还没想好",
            "拖动进度条，打算配合动画做展示，太麻烦了，先不弄了",
            "各种音量调节罢了，方便我在重要场合确保各种音量关闭",
            "各种传感器，罢了，希望以后连接单片机后控制能用得上他们",
            "本来想做的，后来觉得有点无聊就不做了，就当是展示右边的叉叉了",
            "用展开文字隐藏文字给予给真正想了解我的人，里面也许只有我做这个项目的感言"
    };
    private static String[] timeArray = {"1:00", "2:00", "3:00", "4:00", "5:00", "6:00","7:00","8:00","9:00","10:00","11:00","12:00"};
    private static int[] stateArray = {1,1,1,1,0,1,1,1,1,1,0,0};

    public static ArrayList<App> getDefaultList() {
        ArrayList<App> appList = new ArrayList<App>();
        for (int i = 0; i < iconArray.length; i++) {
            appList.add(new App(iconArray[i], nameArray[i], descArray[i], timeArray[i],AppInfoArray[i], stateArray[i]));
        }
        return appList;
    }
}
