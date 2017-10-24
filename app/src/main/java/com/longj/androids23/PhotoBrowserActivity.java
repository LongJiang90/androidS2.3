package com.longj.androids23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PhotoBrowserActivity extends AppCompatActivity {

    //定义一个访问图片的数组
    int[] images = new int[] {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };
    int currentImg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_browser);

        final ImageView imgV = (ImageView) findViewById(R.id.imageView);
        imgV.setImageResource(images[0]);
        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImg>=3){
                    currentImg = -1;
                }
                imgV.setImageResource(images[++currentImg]);
            }
        });

    }
}
