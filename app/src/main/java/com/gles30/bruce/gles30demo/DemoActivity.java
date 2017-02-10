package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.gles30.bruce.gles30demo.surfaceview.base.BeltGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.base.CircleGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.base.CircleRangeSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.Cube2Surface;
import com.gles30.bruce.gles30demo.surfaceview.base.CubeGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.base.CubeLightSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.Cube_6RectSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.ElementSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.FiveStarGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.base.FiveStarOneColorSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.LineSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.base.PolygonSurface;
import com.gles30.bruce.gles30demo.surfaceview.base.TriangleGLSurfaceView;
import com.gles30.bruce.gles30demo.surfaceview.light.BallAllLightSurface;
import com.gles30.bruce.gles30demo.surfaceview.light.BallAmbientSurface;
import com.gles30.bruce.gles30demo.surfaceview.light.BallDiffuseSurface;
import com.gles30.bruce.gles30demo.surfaceview.light.BallDirectionSurface;
import com.gles30.bruce.gles30demo.surfaceview.light.BallSpecularSurface;
import com.gles30.bruce.gles30demo.surfaceview.texture.EarthAndMoonSurface;
import com.gles30.bruce.gles30demo.surfaceview.texture.PointSurface;
import com.gles30.bruce.gles30demo.surfaceview.texture.Points2Surface;
import com.gles30.bruce.gles30demo.surfaceview.texture.StairsSuface;
import com.gles30.bruce.gles30demo.surfaceview.texture.Texture2Surface;
import com.gles30.bruce.gles30demo.surfaceview.texture.TextureRectSurface;
import com.gles30.bruce.gles30demo.surfaceview.texture.PkmSurface;
import com.gles30.bruce.gles30demo.surfaceview.texture.TextureTriangleSurface;
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

        initSeekBar();
    }

    private void initSeekBar() {
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
                        } else if (surfaceView instanceof BallAllLightSurface) {
                            ((BallAllLightSurface) surfaceView).setLightOffset((seekBar.getMax() / 2.0f - progress) / (seekBar.getMax() / 2.0f) * -4);
                        } else if (surfaceView instanceof BallDirectionSurface) {
                            ((BallDirectionSurface) surfaceView).setLightOffset((seekBar.getMax() / 2.0f - progress) / (seekBar.getMax() / 2.0f) * -4);
                        } else if (surfaceView instanceof CubeLightSurface) {
                            ((CubeLightSurface) surfaceView).setLightOffset((seekBar.getMax() / 2.0f - progress) / (seekBar.getMax() / 2.0f) * -4);
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
        } else if (type.equals(Constant.LightType.ball_all)) {
            surfaceView = new BallAllLightSurface(this);
        } else if (type.equals(Constant.LightType.ball_direction)) {
            surfaceView = new BallDirectionSurface(this);
        } else if (type.equals(Constant.LightType.cube_light)) {
            surfaceView = new CubeLightSurface(this);
        } else if (type.equals(Constant.TextureType.texture_triangle)) {
            surfaceView = new TextureTriangleSurface(this);
        } else if (type.equals(Constant.TextureType.texture_rect)) {
            surfaceView = new TextureRectSurface(this);
        } else if (type.equals(Constant.TextureType.texture_rect2)) {
            surfaceView = new Texture2Surface(this);
        } else if (type.equals(Constant.TextureType.EarthAndMoon)) {
            surfaceView = new EarthAndMoonSurface(this);
        } else if (type.equals(Constant.TextureType.pkm)) {
            surfaceView = new PkmSurface(this);
        } else if (type.equals(Constant.TextureType.point)) {
            surfaceView = new PointSurface(this);
        } else if (type.equals(Constant.TextureType.texture3D)) {
            surfaceView = new StairsSuface(this);
        } else if (type.equals(Constant.TextureType.much_img)) {
            surfaceView = new Points2Surface(this);
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
