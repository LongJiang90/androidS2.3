package com.longj.androids23.MyViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by xp on 2017/10/24.
 */

public class DrowView extends View {

    public float currentX = 40;
    public float currentY = 50;

    public DrowView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建画笔
        Paint pa = new Paint();
        //设置画笔颜色
        pa.setColor(Color.RED);
        //绘制一个圆
        canvas.drawCircle(currentX,currentY,45,pa);
    }
}
