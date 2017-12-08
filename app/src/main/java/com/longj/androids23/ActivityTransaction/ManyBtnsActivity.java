package com.longj.androids23.ActivityTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;

import com.longj.androids23.R;

public class ManyBtnsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_btns);

        //设置进出场动画
        int duration = 1500;

        Intent intent = getIntent();
        int type = intent.getIntExtra("animationType", 1);
        if (type == 1) {
            getWindow().setEnterTransition(new Slide().setDuration(duration));
            getWindow().setExitTransition(new Slide().setDuration(duration));
        }else if (type == 2) {
            getWindow().setEnterTransition(new Fade().setDuration(duration));
            getWindow().setExitTransition(new Fade().setDuration(duration));
        }else {
//            getWindow().setEnterTransition(new Explode().setDuration(duration));
//            getWindow().setExitTransition(new Explode().setDuration(duration));
        }






    }
}
