package com.longj.androids23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longj.androids23.Util.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class InternetActivity extends AppCompatActivity {
    ImageView showImgV;
    Bitmap bitmap;
    TextView jsonTextV;
    String jsonStr;

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                showImgV.setImageBitmap(bitmap);
            }
            if (msg.what == 0x124) {
                jsonTextV.setText(jsonStr);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        setupAllView();
    }

    public void setupAllView() {
        Button getRequesBtn = (Button) findViewById(R.id.get_requres_btn);
        Button postRequesBtn = (Button) findViewById(R.id.post_requres_btn);
        jsonTextV = (TextView) findViewById(R.id.json_text_view);

        Button downloadImageBtn = (Button) findViewById(R.id.download_image_btn);
        showImgV = (ImageView) findViewById(R.id.show_image_view);

        getRequesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        jsonStr = NetworkUtil.getSend("http://api.winowe.com/Server/QueryObjectOut","jkid=301&pageindex=1&pagesize=20&userid=3204&version=1.8.1.170804");
                        handler.sendEmptyMessage(0x124);
                    }
                }.start();
            }
        });

        postRequesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        jsonStr = NetworkUtil.postSend("http://api.winowe.com/Server/QueryObjectOut","jkid=401&userid=3204&version=1.8.1.170804");
                        handler.sendEmptyMessage(0x124);
                    }
                }.start();
            }
        });

        downloadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        try {
                            URL url = new URL("http://upload-images.jianshu.io/upload_images/2761781-755a8b7c98d8fed7.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500");
                            InputStream is = url.openStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            handler.sendEmptyMessage(0x123);
                            is.close();
                            is = url.openStream();
                            OutputStream os = openFileOutput("2761781-755a8b7c98d8fed7.jpg",MODE_PRIVATE);
                            byte[] buff = new byte[1024];
                            int hasRead = 0;
                            while ((hasRead = is.read(buff)) >0 ) {
                                os.write(buff, 0, hasRead);
                            }
                            is.close();
                            os.close();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });


    }


}
