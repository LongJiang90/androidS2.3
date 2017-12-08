package com.longj.androids23;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.longj.androids23.ActivityTransaction.ManyBtnsActivity;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = this.getLayoutInflater().inflate(R.layout.activity_transaction, null);

        setContentView(view);

        Transition transition = TransitionManager.getDefaultTransition();
        transition.setDuration(300);
        transition.setInterpolator(new FastOutSlowInInterpolator());
        transition.setStartDelay(200);


        final ViewGroup transactionContainer = (ViewGroup) view.findViewById(R.id.transitions_container);
        final TextView textV = (TextView) transactionContainer.findViewById(R.id.text_view);
        Button button = (Button) transactionContainer.findViewById(R.id.begin_animation_btn);
        Button button1 = (Button) transactionContainer.findViewById(R.id.huadong_btn);//滑动动画
        Button button2 = (Button) transactionContainer.findViewById(R.id.drdc_btn);//淡入淡出动画
        Button button3 = (Button) transactionContainer.findViewById(R.id.gxys_btn);//共享元素动画

        final View view1 = transactionContainer.findViewById(R.id.left_out_view);


        button.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transactionContainer);
                visible = !visible;
                textV.setVisibility(visible? View.VISIBLE : View.GONE);

                TransitionManager.beginDelayedTransition(transactionContainer, new Slide(Gravity.RIGHT));
                view1.setVisibility(visible? View.VISIBLE : View.GONE);

            }
        });

        final Intent intent = new Intent(this, ManyBtnsActivity.class);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("animationType", 1);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransactionActivity.this).toBundle());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("animationType", 2);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransactionActivity.this).toBundle());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("animationType", 3);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TransactionActivity.this, v, "mybtn").toBundle());
            }
        });








    }
}
