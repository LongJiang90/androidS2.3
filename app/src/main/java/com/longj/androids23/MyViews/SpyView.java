package com.longj.androids23.MyViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.longj.androids23.R;

/**
 * Created by xp on 2017/11/20.
 */

public class SpyView extends View {

    public Bitmap back;
    public Bitmap bubble;
    public int bubbleX, bubbleY, screenWidth;

    public SpyView(Context content, AttributeSet attrs) {
        super(content, attrs);

        if (screenWidth == 0) {
            screenWidth = 600;
        }

        //获取窗口管理器
        WindowManager wm = (WindowManager) content.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度和高度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics disMetrics = new DisplayMetrics();
        display.getMetrics(disMetrics);
//        screenWidth = disMetrics.widthPixels;
        //创建位图
        back = Bitmap.createBitmap(screenWidth, screenWidth, Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(back);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        Shader shader = new LinearGradient(0, screenWidth, screenWidth * 0.8f, screenWidth *0.2f, Color.YELLOW, Color.WHITE, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        //创建圆形
        canvas.drawCircle(screenWidth /2, screenWidth/2, screenWidth/2, paint);

        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(5);
        paint1.setColor(Color.BLACK);
        canvas.drawCircle(screenWidth /2, screenWidth/2, screenWidth/2, paint1);
        canvas.drawLine(0, screenWidth/2, screenWidth, screenWidth/2, paint1);
        canvas.drawLine(screenWidth/2, 0, screenWidth/2, screenWidth, paint1);

        paint1.setStrokeWidth(10);
        paint1.setColor(Color.RED);
        //绘制中心的红色"十"字
        canvas.drawLine(screenWidth/2-30, screenWidth/2, screenWidth/2+30, screenWidth/2, paint1);
        canvas.drawLine(screenWidth/2, screenWidth/2-30, screenWidth/2, screenWidth/2+30, paint1);
        //绘制气泡
        bubble = BitmapFactory.decodeResource(getResources(), R.drawable.spy_shizi);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(back, 0, 0, null);
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = Math.round(width);
        screenWidth = width;
        setMeasuredDimension(width, height);
    }
}
