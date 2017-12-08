package com.longj.androids23;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap bitmap;
    //定义图片被横、纵向分割为好多格
    int hCount = 20;
    int vCount = 20;
    //格子的顶点个数
    int COUNT = (hCount+1) * (vCount+1);
    //记录原本点坐标
    float[] verts = new float[COUNT * 2];
    //记录变化后的点坐标
    float[] orig = new float[COUNT * 2];

    RelativeLayout mainLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = LayoutInflater.from(this).inflate(R.layout.activity_change_layout,null, false);
        setContentView(R.layout.activity_animation);

        ImageView imageView = (ImageView) findViewById(R.id.image1);

        View view= new MyView(this,R.drawable.image1);

        mainLinearLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.BELOW, R.id.text1);//让视图添加到 textview1下面
        mainLinearLayout.addView(view, lp2);
        
        getAnimationBtn();
    }

    View animationV;
    public void getAnimationBtn() {
//        平移、旋转、缩放、透明度、组合动画
        Button pyBtn = (Button) findViewById(R.id.btn_1);
        Button xzBtn = (Button) findViewById(R.id.btn_2);
        Button sfBtn = (Button) findViewById(R.id.btn_3);
        Button tmdBtn = (Button) findViewById(R.id.btn_4);
        Button zhdhBtn = (Button) findViewById(R.id.btn_5);
        pyBtn.setOnClickListener(this);
        xzBtn.setOnClickListener(this);
        sfBtn.setOnClickListener(this);
        tmdBtn.setOnClickListener(this);
        zhdhBtn.setOnClickListener(this);
        animationV = findViewById(R.id.animation_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                /**
                 * 构造方法如下
                 * fromXType、toXType、fromYType、toYType(Animation.ABSOLUTE,、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT)
                 * 当type为Animation.ABSOLUTE时，这个个值为具体的像素值，当type为Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT，这个个值为比例值，取值范围是[0f, 1.0f]
                 *public TranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue,int fromYType, float fromYValue, int toYType, float toYValue) {}
                 */
                TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, 100);
                animation1.setDuration(2000);
                //动画结束后View停留在结束位置
                //animation.setFillAfter(true);
                animationV.startAnimation(animation1);
                break;
            case R.id.btn_2:
                /**
                 * 构造方法如下
                 *fromDegrees、toDegrees表示开始、结束的角度(0度为水平方向右侧的开始角度)，pivotXValue、pivotYValue代表旋转的中心位置，[0.0f-1.0f],
                 *pivotXType、pivotYType表示旋转的类型(Animation.ABSOLUTE,、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT)
                 *当type为Animation.ABSOLUTE时，这个个值为具体的像素值，当type为Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT，这个个值为比例值，取值范围是[0f, 1.0f]
                 *public RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,int pivotYType, float pivotYValue) {     }
                 */
                // 初始化RotateAnimation
                RotateAnimation animation2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                // 动画执行时间
                animation2.setDuration(2000);
                // 动画重复次数-1表示不停重复
                //animation.setRepeatCount(-1);
                // 给控件设置动画
                animationV.startAnimation(animation2);

                break;
            case R.id.btn_3:
                /**
                 * 构造方法如下
                 * fromX、toX 开始结束的X轴缩放比率[0.0f-1.0f]，fromY、toYtoY开始结束的Y轴缩放比率[0.0f-1.0f]，pivotXValue、pivotYValue代表旋转的中心位置，[0.0f-1.0f],
                 * pivotXType、pivotYType表示旋转的类型(Animation.ABSOLUTE,、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT)
                 * 当type为Animation.ABSOLUTE时，这个个值为具体的像素值，当type为Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT，这个个值为比例值，取值范围是[0f, 1.0f]
                 *  public ScaleAnimation(float fromX, float toX, float fromY, float toY,int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {}
                 */
                //初始化ScaleAnimation
                ScaleAnimation animation3 = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                //动画执行时间
                animation3.setDuration(2000);
                animationV.startAnimation(animation3);
                break;
            case R.id.btn_4:
                /***
                 * 构造方法如下
                 * fromAlpha、toAlpha表示透明度的起始值和结束值，0.0f表示全透明，1.0f表示不透明。
                 * public AlphaAnimation(float fromAlpha, float toAlpha) {}
                 */
                //初始化AlphaAnimation
                AlphaAnimation animation4 = new AlphaAnimation(1.0f, 0.0f);
                //动画执行时间
                animation4.setDuration(2000);
                animationV.startAnimation(animation4);
                break;
            case R.id.btn_5:
                AnimationSet animationSet = new AnimationSet(true);

                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, 200);

                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(2000);
                animationSet.setRepeatCount(-1);
                animationV.startAnimation(animationSet);
                break;
        }


    }

    private class MyView extends View {

        public MyView(Context context, int drawableId) {
            super(context);
            setFocusable(true);
            bitmap = BitmapFactory.decodeResource(getResources(),drawableId);

            float imgW = 600;
            float imgH = 360;
            int index = 0;
            for (int y = 0;y<=hCount; y++) {
                float fy = imgH*y/hCount;
                for (int x = 0;x<=vCount; x++) {
                    float fx = imgW * x/vCount;
                    orig[index *2+0] = verts[index*2+0] = fx;
                    orig[index *2+1] = verts[index*2+1] = fy;
                    index += 1;
                }
            }

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmapMesh(bitmap, hCount, vCount, verts, 0, null, 0, null);
        }

        //工具方法：根据触碰点计算扭曲的点坐标
        public void warp(float cx, float cy) {
            for (int i=0; i< COUNT *2; i+=2) {
                float dx = cx - orig[i];
                float dy = cy - orig[i+1];
                float dd = dx *dx + dy*dy;
                //计算每个坐标与当前点(cx,cy)之间的距离
                float d = (float) Math.sqrt(dd);
                //计算扭曲度，距离当前点越远，扭曲度越小
                float pull = 8000 / (dd*d);
                if (pull >= 1) {
                    verts[i] = cx;
                    verts[i+1] = cy;
                }else {
                    verts[i] = orig[i] + dx*pull;
                    verts[i+1] = orig[i+1]+ dy*pull;
                }
            }
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            warp(event.getX(),event.getY());

            return super.onTouchEvent(event);
        }
    }


}
