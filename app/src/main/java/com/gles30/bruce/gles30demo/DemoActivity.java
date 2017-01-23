package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles30.bruce.gles30demo.surfaceview.TriangleGLSurfaceView;


/**
 * Update by sunhongzhi on 2017/1/7.
 */

public class DemoActivity extends Activity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(MainActivity.TYPE, 0);

        surfaceView = new TriangleGLSurfaceView(this);
        setContentView(surfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }
}
