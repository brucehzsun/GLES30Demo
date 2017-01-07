package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Update by sunhongzhi on 2017/1/7.
 */

public class DemoActivity extends Activity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(3);
        surfaceView.setRenderer(new TriangleRender(this));
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
