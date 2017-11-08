package com.longj.androids23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ChangeLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout showRelativeLayout;
    RelativeLayout mainLinearLayout;
    View myView;
    LinearLayout addLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_layout);

        initAllView();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_view_btn:
                showRelativeLayout.setVisibility(View.VISIBLE);//设置底部可见
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,0,0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                showRelativeLayout.setLayoutParams(lp);
                break;

            case R.id.close_view_btn:
                showRelativeLayout.setVisibility(View.GONE);//设置底部不可见
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.setMargins(10, 0, 0, 10);
                lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                showRelativeLayout.setLayoutParams(lp1);

                break;

            case R.id.add_view_btn:

                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.addRule(RelativeLayout.BELOW, R.id.delete_view_btn);//让视图添加到 delete按钮下面
                mainLinearLayout.addView(addLayout, lp2);
                addLayout.setVisibility(View.VISIBLE);

                break;

            case R.id.delete_view_btn:
                mainLinearLayout.removeView(addLayout);
                break;
        }
    }

    public void initAllView() {
        showRelativeLayout = (RelativeLayout) findViewById(R.id.info_tip);
        mainLinearLayout = (RelativeLayout) findViewById(R.id.main_linear_layout);

        myView = LayoutInflater.from(this).inflate(R.layout.activity_change_layout,null, false);

        //此处要获取其他xml的控件需要先引入改layout的view(这个linearlayout用于演示添加和删除)
        View view= LayoutInflater.from(this).inflate(R.layout.activity_pupop_view,null,false);
        addLayout = (LinearLayout)view.findViewById(R.id.pupop_linear_layout);

        Button showBtn = (Button) findViewById(R.id.show_view_btn);
        Button closeBtn = (Button) findViewById(R.id.close_view_btn);

        Button addViewBtn = (Button) findViewById(R.id.add_view_btn);
        Button deleteViewBtn = (Button) findViewById(R.id.delete_view_btn);

        showBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        addViewBtn.setOnClickListener(this);
        deleteViewBtn.setOnClickListener(this);
    }


}
