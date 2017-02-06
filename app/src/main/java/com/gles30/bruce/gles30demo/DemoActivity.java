package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gles30.bruce.gles30demo.surfaceview.BeltGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.CircleGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.CircleRangeSurface;
import com.gles30.bruce.gles30demo.surfaceview.Cube2Surface;
import com.gles30.bruce.gles30demo.surfaceview.CubeGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.Cube_6RectSurface;
import com.gles30.bruce.gles30demo.surfaceview.ElementSurface;
import com.gles30.bruce.gles30demo.surfaceview.FiveStarGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.FiveStarOneColorSurface;
import com.gles30.bruce.gles30demo.surfaceview.LineSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.PolygonSurface;
import com.gles30.bruce.gles30demo.surfaceview.TriangleGLSurfaceView;
import com.gles30.bruce.gles30demo.ui.DemoType;


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
        String type = getIntent().getStringExtra(MainActivity.TYPE);
        if (type.equals(DemoType.Triangle)) {
            surfaceView = new TriangleGLSurfaceView(this);
        } else if (type.equals(DemoType.FiveStar)) {
            surfaceView = new FiveStarGLSurfaceView(this);
        } else if (type.equals(DemoType.Cube)) {
            surfaceView = new CubeGLSurfaceView(this);
        } else if (type.equals(DemoType.Line)) {
            surfaceView = new LineSurfaceView(this);
        } else if (type.equals(DemoType.Circle)) {
            surfaceView = new CircleGLSurfaceView(this);
        } else if (type.equals(DemoType.Belt)) {
            surfaceView = new BeltGLSurfaceView(this);
        } else if (type.equals(DemoType.Element)) {
            surfaceView = new ElementSurface(this);
        } else if (type.equals(DemoType.RangeElement)) {
            surfaceView = new CircleRangeSurface(this);
        } else if (type.equals(DemoType.FiveStarOneColor)) {
            surfaceView = new FiveStarOneColorSurface(this);
        } else if (type.equals(DemoType.Cube_6Rect)) {
            surfaceView = new Cube_6RectSurface(this);
        } else if (type.equals(DemoType.Cube2in1)) {
            surfaceView = new Cube2Surface(this);
        } else if (type.equals(DemoType.Polygon)) {
            surfaceView = new PolygonSurface(this);
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
