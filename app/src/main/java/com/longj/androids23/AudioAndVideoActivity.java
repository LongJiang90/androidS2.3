package com.longj.androids23;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.longj.androids23.Record_PlayVideoACs.JLVideoRecordActivity;

import java.io.IOException;

public class AudioAndVideoActivity extends AppCompatActivity{

    MediaPlayer mediaPlayer;
    String[] nameArr = {};
    int curentIndex = 0;
    String curentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_and_video);

        mediaPlayer = new MediaPlayer();
        setupAllViews();


    }

    void setupAllViews() {
        final Button playBtn = (Button) findViewById(R.id.audio_paly_btn);
        Button lastBtn = (Button) findViewById(R.id.audio_last_btn);
        Button nextBtn = (Button) findViewById(R.id.audio_next_btn);

        Button recordBtn = (Button) findViewById(R.id.video_record_btn);

        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                curentIndex -= 1;
//                String name;
//                if (curentIndex <0){
//                    curentIndex = 1;
//                    name = nameArr[1];
//                }else {
//                    name = nameArr[curentIndex];
//                }

            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManager am = getAssets();
                try {
                    AssetFileDescriptor afd = am.openFd("air_balloon.mp3");

                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    playBtn.setBackgroundResource(R.drawable.audio_puse);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                curentIndex += 1;
//                String name;
//                if (curentIndex >1){
//                    curentIndex = 0;
//                    name = nameArr[0];
//                }else {
//                    name = nameArr[curentIndex];
//                }

            }
        });

        final int VIDEO_WITH_CAMERA = 1001;
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioAndVideoActivity.this, JLVideoRecordActivity.class);
                startActivity(intent);

//                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                //设置视频录制的最长时间
//                intent.putExtra (MediaStore.EXTRA_DURATION_LIMIT,30);
//                //设置视频录制的画质
//                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//                startActivityForResult (intent, VIDEO_WITH_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Log.i("123", "result:"+ uri.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
