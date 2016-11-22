package com.example.air.map;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Bundle;

public class CameraActivity extends Activity {

    private Camera mCam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }
}
