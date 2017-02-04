package com.gles30.bruce.gles30demo.surfaceview;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.gles30.bruce.gles30demo.modle.Triangle;
import com.gles30.bruce.gles30demo.util.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Update by sunhongzhi on 2017/1/23.
 */

public class TriangleGLSurfaceView extends GLSurfaceView {

    private RotateThread rotateThread;
    private final SceneRender render;

    public TriangleGLSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        render = new SceneRender();
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        super.onPause();
        rotateThread.flag = false;
    }

    class SceneRender implements GLSurfaceView.Renderer {
        Triangle triangle;


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0, 0, 0, 1.0f);
            triangle = new Triangle(getContext());
            GLES30.glEnable(GLES20.GL_DEPTH_TEST);
            rotateThread = new RotateThread(triangle);
            rotateThread.start();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            MatrixState.setPorjectFrustum(-ratio, ratio, -1, 1, 1, 10);
            MatrixState.setCamera(0, 0, 3, 0, 0, 0, 0, 1.0f, 0);
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            triangle.drawSelf();
        }
    }

    class RotateThread extends Thread {
        public boolean flag = true;

        Triangle triangle;

        public RotateThread(com.gles30.bruce.gles30demo.modle.Triangle triangle) {
            this.triangle = triangle;
        }

        @Override
        public void run() {
            super.run();
            while (flag) {
                triangle.xAngle += 0.375f;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
