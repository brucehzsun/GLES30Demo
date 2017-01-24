package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles30.bruce.gles30demo.surfaceview.FiveStarGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.TriangleGLSurfaceView;


/**
 * Update by sunhongzhi on 2017/1/7.
 */

public class DemoActivity extends Activity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(surfaceView);
    }

    private void initData() {
        int type = getIntent().getIntExtra(MainActivity.TYPE, 0);
        switch (type) {
            case 0:
                surfaceView = new TriangleGLSurfaceView(this);
                break;
            case 1:
                surfaceView = new FiveStarGLSurfaceView(this);
                break;
        }
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
