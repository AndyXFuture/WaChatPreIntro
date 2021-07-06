package com.example.perintro.application;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.perintro.R;
import com.example.perintro.base.ChatroomActivity;
import com.example.perintro.util.PermissionUtil;

public class SersorActivity extends ChatroomActivity implements View.OnClickListener {
    private TextView tv_sensor;
    private String[] mSensorType = {
            "加速度", "磁场", "方向", "陀螺仪", "光线",
            "压力", "温度", "距离", "重力", "线性加速度",
            "旋转矢量", "湿度", "环境温度", "无标定磁场", "无标定旋转矢量",
            "未校准陀螺仪", "特殊动作", "步行检测", "计步器", "地磁旋转矢量",
            "心跳", "倾斜检测", "唤醒手势", "瞥一眼", "捡起来"};
    private Map<Integer, String> mapSensor = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        preInit("传感器",R.layout.app_sensor);
        tv_sensor = findViewById(R.id.tv_sensor);
        showSensorInfo(); // 显示手机自带的传感器信息

        findViewById(R.id.btn_acceleration).setOnClickListener(this);
        findViewById(R.id.btn_light).setOnClickListener(this);
        findViewById(R.id.btn_direction).setOnClickListener(this);
        findViewById(R.id.btn_step).setOnClickListener(this);
        findViewById(R.id.btn_gyroscope).setOnClickListener(this);
    }

    private void showSensorInfo() {
        // 从系统服务中获取传感管理器对象
        SensorManager mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 获取当前设备支持的传感器列表
        List<Sensor> sensorList = mSensorMgr.getSensorList(Sensor.TYPE_ALL);
        String show_content = "当前支持的传感器包括：\n";
        for (Sensor sensor : sensorList) {
            if (sensor.getType() >= mSensorType.length) {
                continue;
            }
            mapSensor.put(sensor.getType(), sensor.getName());
        }
        for (Map.Entry<Integer, String> item_map : mapSensor.entrySet()) {
            int type = item_map.getKey();
            String name = item_map.getValue();
            String content = String.format("%d %s：%s\n", type, mSensorType[type - 1], name);
            show_content += content;
        }
        tv_sensor.setText(show_content);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_acceleration) {
            Intent intent = new Intent(this, AccelerationActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_light) {
            if (PermissionUtil.checkWriteSettings(this, R.id.btn_light % 4096)) {
                PermissionUtil.goActivity(this, LightActivity.class);
            }
        } else if (v.getId() == R.id.btn_direction) {
            Intent intent = new Intent(this, DirectionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_step) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                showToast("计步器需要Android4.4或以上版本");
            } else if (Build.VERSION.SDK_INT >= 29) {
                // Android10.0之后使用计步器需要健身运动权限
                if (PermissionUtil.checkPermission(this, "android.permission.ACTIVITY_RECOGNITION", (int) v.getId() % 4096)) {
                    startActivity(new Intent(this, StepActivity.class));
                }
            } else {
                Intent intent = new Intent(this, StepActivity.class);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btn_gyroscope) {
            Intent intent = new Intent(this, GyroscopeActivity.class);
            startActivity(intent);
        }

    }
    private void showToast (String desc){
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
