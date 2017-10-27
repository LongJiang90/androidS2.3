package com.longj.androids23.MyViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.longj.androids23.R;

/**
 * Created by xp on 2017/10/26.
 */

public class GuaGuaKaView extends View {
    private Paint pathPaint;
    private Path path;
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Bitmap backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);

    float pointX = 0;
    float pointY = 0;

    public GuaGuaKaView(Context context){
        this(context, null);
    }

    public GuaGuaKaView(Context context, AttributeSet attrs) {
        super(context);
        this.setBackgroundColor(Color.WHITE);
        //初始化画笔
        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setDither(true);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(50);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        cacheBitmap = Bitmap.createBitmap(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingTop() - getPaddingBottom(), Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointX = event.getX();
                pointY = event.getY();
                path.moveTo(pointX,pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(),event.getY());//根据点画线 quadTo与lineTo的区别
                pointX = event.getX();
                pointY = event.getY();
                break;
        }
        invalidate();//使...无效 即重绘视图
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画背景，再画前景。背景就是一张图片，前景就是灰色遮盖层和path轨迹的结合体，结合规则是DST_OUT

        //在画布上面画背景
        canvas.drawBitmap(backBitmap,0,0,null);
        //利用PorterDuff.Mode画线条，重叠模式为DST_OUT 灰色背景和path的重叠
        cacheCanvas.drawColor(Color.GRAY);
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        cacheCanvas.drawPath(path,pathPaint);
        //画前景
        canvas.drawBitmap(cacheBitmap,0,0,null);
    }
}
