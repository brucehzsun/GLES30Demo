package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Update by sunhongzhi on 2017/1/7.
 */

public class DemoActivity extends Activity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(MainActivity.TYPE, 0);

        surfaceView = new GLSurfaceView(this);
        surfaceView.setEGLContextClientVersion(3);
        switch (type) {
            case 0:
                surfaceView.setRenderer(new TriangleRender(this));
                break;
            case 1:
                surfaceView.setRenderer(new Triangle2Render(this));
                break;
            case 2:
                surfaceView.setRenderer(new VertexBufferObjRender(this));
                break;
            case 3:
                surfaceView.setRenderer(new SimpleVertexShaderRender(this));
                break;
        }
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
