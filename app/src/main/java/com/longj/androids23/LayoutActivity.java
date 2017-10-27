package com.longj.androids23;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class LayoutActivity extends AppCompatActivity {

    private int currentColor = 0;
    final int[] colors = new int[]{
            R.color.yellow,
            R.color.red,
            R.color.green,
            R.color.blue,
            R.color.lightBlue,
            R.color.purple};
    final int[] names = new int[]{
            R.id.textV1,
            R.id.textV2,
            R.id.textV3,
            R.id.textV4,
            R.id.textV5,
            R.id.textV6
    };

    TextView[] views = new TextView[names.length];

    //通知响应函数
    Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //该判断表示消息来自本程序
            if (msg.what == 0x123) {
                for (int i=0;i<names.length;i++) {
                    views[i].setBackgroundResource(colors[(i+currentColor)%names.length]);
                }
                currentColor++;
            }
            super.handleMessage(msg);
        }
    };

    Button sendCodeBtn;
    int time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        for (int i=0;i<names.length;i++) {
            views[i] = (TextView) findViewById(names[i]);
        }
        //定时器
//        task--这是被调度的任务。
//        delay--这是以毫秒为单位的延迟之前的任务就是执行。
//        period--这是在连续执行任务之间的毫秒的时间。
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //发送通知
                hander.sendEmptyMessage(0x123);//sendEmptyMessageDelayed(what, 延迟时间);
            }
        },0,200);

        sendCodeBtn = (Button) findViewById(R.id.sendCodeBtn);
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCodeBtn.setClickable(false);
                sendCode();
            }
        });


        final TextView textView = (TextView) findViewById(R.id.bottomTextV);
        String btnText = "我想让第三个字到第五个字的大小和颜色都变化";
        Spannable wordSpan = new SpannableString(btnText);
        wordSpan.setSpan(new AbsoluteSizeSpan(20),2,5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        wordSpan.setSpan(new ForegroundColorSpan(Color.RED),2,5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(wordSpan);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(Html.fromHtml("<b>字体加粗</b>"));
            }
        },5000);


    }

    public void sendCode() {
        if (time<0) {
            time = 60;
            sendCodeBtn.setClickable(true);
            sendCodeBtn.setText("发送验证码");
            return;
        }else {
            time-=1;
            String btnText = "验证码已发送("+time+"s)后可重发";
            Spannable wordSpan = new SpannableString(btnText);
            int beginIndex = time>10 ? 8:7;
            wordSpan.setSpan(new AbsoluteSizeSpan(8),beginIndex,beginIndex+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            wordSpan.setSpan(new ForegroundColorSpan(Color.RED),beginIndex,beginIndex+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //Button设置可变字体失败
            sendCodeBtn.setText(wordSpan);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendCode();
                }
            },1000);


//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    sendCode();
//                }
//            },1000);

        }
    }





}
