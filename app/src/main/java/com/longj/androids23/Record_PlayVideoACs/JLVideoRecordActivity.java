package com.longj.androids23.Record_PlayVideoACs;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.longj.androids23.R;

public class JLVideoRecordActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jlaudio_record);

        FragmentManager fm = getFragmentManager();
        Camera2VideoFragment camera2VideoFragment = Camera2VideoFragment.newInstance();
        fm.beginTransaction().add(camera2VideoFragment,"camera2VideoFragment").commit();

    }

}
