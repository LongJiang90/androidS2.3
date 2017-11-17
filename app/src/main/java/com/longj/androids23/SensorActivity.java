package com.longj.androids23;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    TextView jsdTextV;
    TextView fxTextV;
    TextView tlyTextV;
    TextView ccTextV;
    TextView zlTextV;
    TextView xxjsdTextV;
    TextView wdTextV;
    TextView ggTextV;
    TextView ylTextV;
    TextView xlTextV;

    ImageView zlzImgV;
    float currentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        jsdTextV = (TextView) findViewById(R.id.jsd_text_v);       // 加速度
        fxTextV = (TextView) findViewById(R.id.fx_text_v);         // 方向
        tlyTextV = (TextView) findViewById(R.id.tly_text_v);       // 陀螺仪
        ccTextV = (TextView) findViewById(R.id.cc_text_v);         // 磁场
        zlTextV = (TextView) findViewById(R.id.zl_text_v);         // 重力
        xxjsdTextV = (TextView) findViewById(R.id.xxjsd_text_v);   // 线性加速度
        wdTextV = (TextView) findViewById(R.id.wd_text_v);         // 温度
        ggTextV = (TextView) findViewById(R.id.gg_text_v);         // 光感
        ylTextV = (TextView) findViewById(R.id.yl_text_v);         // 压力
        xlTextV = (TextView) findViewById(R.id.xl_text_v);         // 心率

        zlzImgV = (ImageView) findViewById(R.id.zlz_image_view);










    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);// 指定获取的传感器的类型
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME); //心率暂时没找到对应的监听类型
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
// 加速度  3个加速度  1-X方向的加速度，2-Y方向的加速度，Z方向的加速度
// 方向    3个角度   1-手机顶部与正北方的夹角 2-顶部或尾部翘起的角度 3-左、右侧翘起的角度
// 陀螺仪  3个角速度  1-设备绕X轴旋转的角速度 2-绕Y轴 3-绕Z轴
// 磁场    3个磁场分量 XYZ三个方向的磁场分量
// 重力    1个三维向量 显示重力的方向和强度
// 线性加速度 1个三维向量 显示设备各个方向的加速度 加速度传感器= 重力传感器+线性加速度传感器
// 温度    1个单位为摄氏度的值
// 光感    1个单位为勒克斯（lux）的值
// 压力    1个压力大小的数据
// 心率    暂未介绍

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER://
                jsdTextV.setText("加速度："+"\n  X方向的加速度:"+values[0] + "\n  Y方向的加速度:"+values[1] +"\n  Z方向的加速度:"+values[2]);
                break;
            case Sensor.TYPE_ORIENTATION:
                fxTextV.setText("方向："+"\n  绕Z轴转过的角度:"+values[0] + "\n  绕X轴转过的角度:"+values[1] +"\n  绕Y轴转过的角度:"+values[2]);

                float degree = values[0];
                RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ra.setDuration(200);
                zlzImgV.startAnimation(ra);
                currentDegree = -degree;

                break;
            case Sensor.TYPE_GYROSCOPE:
                tlyTextV.setText("陀螺仪："+"\n  绕X轴旋转的的角速度:"+values[0] + "\n  绕Y轴旋转的角速度:"+values[1] +"\n  绕Z轴旋转的角速度:"+values[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                ccTextV.setText("磁场："+"\n  X轴方向的的磁场强度:"+values[0] + "\n  Y轴方向的磁场强度:"+values[1] +"\n  Z轴方向的磁场强度:"+values[2]);
                break;
            case Sensor.TYPE_GRAVITY:
                zlTextV.setText("重力："+"\n  X轴方向的的重力:"+values[0] + "\n  Y轴方向的重力:"+values[1] +"\n  Z轴方向的重力:"+values[2]);
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                xxjsdTextV.setText("线性加速度："+"\n  X轴方向的的线性加速度:"+values[0] + "\n  Y轴方向的线性加速度:"+values[1] +"\n  Z轴方向的线性加速度:"+values[2]);
                break;
            case Sensor.TYPE_TEMPERATURE:
                wdTextV.setText("当前温度为(摄氏度)："+values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                wdTextV.setText("当前光强为(勒克斯)："+values[0]);
                break;
            case Sensor.TYPE_PRESSURE:
                ylTextV.setText("当前压力为："+values[0]);
                break;

        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
