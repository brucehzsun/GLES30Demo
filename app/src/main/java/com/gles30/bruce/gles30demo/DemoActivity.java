package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.gles30.bruce.gles30demo.surfaceview.*;
import com.gles30.bruce.gles30demo.util.Constant;
import com.gles30.bruce.gles30demo.util.Constant.DemoType;


/**
 * Update by sunhongzhi on 2017/1/7.
 */

public class DemoActivity extends Activity {

    private GLSurfaceView surfaceView;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        rootLayout = (RelativeLayout) findViewById(R.id.activity_demo_root);
        initData();

        //普通拖拉条被拉动的处理代码
        SeekBar sb = (SeekBar) findViewById(R.id.seekbar);
        sb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        if (surfaceView instanceof BallDiffuseSurface) {
                            ((BallDiffuseSurface) surfaceView).setLightOffset((seekBar.getMax() / 2.0f - progress) / (seekBar.getMax() / 2.0f) * -4);
                        } else if (surfaceView instanceof BallSpecularSurface) {
                            ((BallSpecularSurface) surfaceView).setLightOffset((seekBar.getMax() / 2.0f - progress) / (seekBar.getMax() / 2.0f) * -4);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
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
        } else if (type.equals(Constant.LightType.ball)) {
            surfaceView = new BallAmbientSurface(this);
        } else if (type.equals(Constant.LightType.ball_diffuse)) {
            surfaceView = new BallDiffuseSurface(this);
        } else if (type.equals(Constant.LightType.ball_specular)) {
            surfaceView = new BallSpecularSurface(this);
        }
        rootLayout.addView(surfaceView);
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
