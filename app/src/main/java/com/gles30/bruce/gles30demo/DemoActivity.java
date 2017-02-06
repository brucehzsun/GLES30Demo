package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles30.bruce.gles30demo.surfaceview.BeltGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.CircleGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.CircleRangeSurface;
import com.gles30.bruce.gles30demo.surfaceview.CubeGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.Cube_6RectSurface;
import com.gles30.bruce.gles30demo.surfaceview.ElementSurface;
import com.gles30.bruce.gles30demo.surfaceview.FiveStarGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.FiveStarOneColorSurface;
import com.gles30.bruce.gles30demo.surfaceview.LineSurfaceView;
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
            case 2:
                surfaceView = new CubeGLSurfaceView(this);
                break;
            case 3:
                surfaceView = new LineSurfaceView(this);
                break;
            case 4:
                surfaceView = new CircleGLSurfaceView(this);
                break;
            case 5:
                surfaceView = new BeltGLSurfaceView(this);
                break;
            case 6:
                surfaceView = new ElementSurface(this);
                break;
            case 7:
                surfaceView = new CircleRangeSurface(this);
                break;
            case 8:
                surfaceView = new FiveStarOneColorSurface(this);
                break;
            case 9:
                surfaceView = new Cube_6RectSurface(this);
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
