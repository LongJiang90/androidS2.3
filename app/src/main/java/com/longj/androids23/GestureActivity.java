package com.longj.androids23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longj.androids23.MyViews.DrowView;

public class GestureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= this.getLayoutInflater().inflate(R.layout.activity_gesture,null);
        setContentView(view);

        LinearLayout lineLayout = (LinearLayout) findViewById(R.id.root);
        TextView titleTextV = (TextView) findViewById(R.id.textView2);

        final DrowView drawV = new DrowView(this);
        drawV.setMinimumWidth(300);
        drawV.setMinimumHeight(500);
        drawV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == drawV) {
                    drawV.currentX = event.getX();
                    drawV.currentY = event.getY();
                    drawV.invalidate();//通知View重绘
                    return true;//返回true表示已经处理了该事件
                }else {
                    return false;//返回true表示已经处理了该事件
                }
            }
        });

        lineLayout.addView(drawV);


    }
}
