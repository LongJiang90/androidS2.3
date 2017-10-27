package com.longj.androids23;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.Calendar;

public class HightControlsActivity extends AppCompatActivity {

    String[] citys = new String[] {"中国-重庆", "中国-上海","中国-广州","美国-华盛顿","美国-拉斯维加斯","美国-佛罗里达",
            "英国-曼彻斯特","英国-爱丁堡","英国-伦敦","德国-柏林","德国-汉堡","德国-科隆","德国-慕尼黑","德国-法兰克福"};
    int mYear, mMonth, mDay, mHour, mMinute;
    int status = 0;//progressBar的进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight_controls);

        ScrollView scrollView = (ScrollView) findViewById(R.id.root_scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });


        AutoCompleteTextView cityChooseTextView = (AutoCompleteTextView) findViewById(R.id.city_choose_textview);
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,citys);
        cityChooseTextView.setAdapter(arr);


        final TextView textView = (TextView) findViewById(R.id.show_time_textView);
        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        //获取当前的时间
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR);
        mMinute = cal.get(Calendar.MINUTE);

        datePicker.init(2017, 10, 27, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year; mMonth = monthOfYear; mDay = dayOfMonth;
                textView.setText(" 日期、时间选择器的使用:"+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日"+mHour+":"+mMinute);
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay; mMinute = minute;
                textView.setText(" 日期、时间选择器的使用:"+mYear+"年"+(mMonth+1)+"月"+mDay+"日"+mHour+":"+mMinute);
            }
        });

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.my_progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(20);
                progressBar1.setProgress(20);
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(40);
                progressBar1.setProgress(40);
            }
        },3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(90);
                progressBar1.setProgress(90);
            }
        },5000);








    }
}
