package com.longj.androids23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.longj.androids23.MyViews.GuaGuaKaView;

public class GgkAndChangeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ggk_and_change_layout);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ggk_root_layout);

        GuaGuaKaView ggkView = new GuaGuaKaView(this);
        //设置大小
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ggkView.setTop(30);
        ggkView.setLayoutParams(lp);

        linearLayout.addView(ggkView,lp);



    }
}
