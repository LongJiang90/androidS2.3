package com.longj.androids23;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.Calendar;

import static android.R.id.content;
import static android.R.id.tabhost;

public class HightControlsActivity extends AppCompatActivity {

    String[] citys = new String[] {"中国-重庆", "中国-上海","中国-广州","美国-华盛顿","美国-拉斯维加斯","美国-佛罗里达",
            "英国-曼彻斯特","英国-爱丁堡","英国-伦敦","德国-柏林","德国-汉堡","德国-科隆","德国-慕尼黑","德国-法兰克福"};
    int mYear, mMonth, mDay, mHour, mMinute;
    int status = 0;//progressBar的进度

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight_controls);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.root_scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        /*
                        * getScorollY() 滚动条滑动的距离
                        * getMeasuredHeight() 内容整体的高度
                        * getHeight() 显示的高度（如果显示高度==整体高度，内容未超出屏幕）
                        */
                        if (scrollView.getScrollY()<=0) {
                            //滑动到了顶部
//                            Log.e("滑动到了->","顶部");
                        }
                        if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getScrollY()+scrollView.getHeight()) {
                            //滑动到了顶部
//                            Log.e("滑动到了->","底部");
                        }

                        break;
                }

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


        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        SeekBar seekBar = (SeekBar) findViewById(R.id.voice_change_seekbar);
        int maxV = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//得到听筒模式的最大值
        int curV = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//得到听筒模式的当前值
        seekBar.setMax(maxV);
        seekBar.setProgress(curV);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (lastProgress < progress){
                    //共有3个参数：需要调整的类型，调整的方向，附加参数（FLAG_PLAY_SOUND 调整音量时播放声音、FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个）
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
                }else {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
                }
                lastProgress = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("已接电话")
                .setContent(R.id.tab01));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("未接电话", ResourcesCompat.getDrawable(getResources(), R.drawable.image1, null))
                .setContent(R.id.tab02));

        //提示框
        Button defuctBtn = (Button) findViewById(R.id.btn01);
        final Button listBtn = (Button) findViewById(R.id.btn02);
        final Button mutChooseListBtn = (Button) findViewById(R.id.btn03);

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this, 0);
        defuctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setIcon(R.drawable.image2)
                        .setTitle("默认的提示框")
                        .setMessage("这是一个默认的提示框");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder1.create().show();
            }
        });

        final String[] colors = {"红色","橙色","黄色","绿色","青色","蓝色","紫色"};
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this, 0);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder2.setIcon(R.drawable.image2)
                        .setTitle("请选择你最喜欢的一种颜色");
                builder2.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listBtn.setText("列表对话框 选择了："+colors[which]);
                    }
                });
                builder2.create().show();
            }
        });

        final AlertDialog.Builder builder3 = new AlertDialog.Builder(this, 0);
        final boolean[] checkStatus = {true,false,false,false,false,false,false};
        mutChooseListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder3.setIcon(R.drawable.image2)
                        .setTitle("请选择你喜欢的几种颜色");
                builder3.setMultiChoiceItems(colors, checkStatus, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });

                builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String colorNames = "";
                        for (int i=0; i<checkStatus.length; i++) {
                            boolean isSelect = checkStatus[i];
                            if (isSelect == true) {
                                colorNames += (i==checkStatus.length || i==0?"":",");
                                colorNames += colors[i];
                            }
                        }

                        mutChooseListBtn.setText("多选列表对话框 选择了："+ colorNames);
                    }
                });
                builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder3.create().show();
            }
        });





    }
}
