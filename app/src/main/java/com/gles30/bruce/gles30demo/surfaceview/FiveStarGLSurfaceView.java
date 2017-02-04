package com.gles30.bruce.gles30demo.surfaceview;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.gles30.bruce.gles30demo.modle.FiveStar;
import com.gles30.bruce.gles30demo.util.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bruce on 2017/1/23.
 */

public class FiveStarGLSurfaceView extends GLSurfaceView {
    private final FiveStarRender render;
    private float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousY;
    private float mPreviousX;

    public FiveStarGLSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        render = new FiveStarRender();
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;

                for (FiveStar fiveStar : render.fiveStars) {
                    fiveStar.xAngle += dx * TOUCH_SCALE_FACTOR;
                    fiveStar.yAngle += dy * TOUCH_SCALE_FACTOR;
                }

                mPreviousX = x;
                mPreviousY = y;

                break;
        }
        return true;
    }

    class FiveStarRender implements GLSurfaceView.Renderer {

        public FiveStar[] fiveStars = new FiveStar[6];

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1);
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            for (int i = 0; i < fiveStars.length; i++) {
                fiveStars[i] = new FiveStar(getContext(), 0.4f, 1.0f, -1.0f * i);
            }
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            MatrixState.setPorjectFrustum(-ratio, ratio, -1, 1, 1, 10);
            MatrixState.setCamera(0, 0, 3f, 0, 0, 0f, 9f, 1.0f, 0f);
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            for (FiveStar fiveStar : fiveStars) {
                fiveStar.drawSelf();
            }
        }
    }

}
