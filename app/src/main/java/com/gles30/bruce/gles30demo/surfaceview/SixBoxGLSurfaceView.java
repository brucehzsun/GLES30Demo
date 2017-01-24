package com.gles30.bruce.gles30demo.surfaceview;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.gles30.bruce.gles30demo.modle.Cube;
import com.gles30.bruce.gles30demo.modle.FiveStar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bruce on 2017/1/23.
 */

public class SixBoxGLSurfaceView extends GLSurfaceView {
    private final SceneRenderer render;
    private final Cube cube;

    public SixBoxGLSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        render = new SceneRenderer();
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        cube = new Cube();
    }


    class SceneRenderer implements Renderer {


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1);
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);//开启深度测试
            GLES30.glEnableVertexAttribArray(GLES30.GL_CULL_FACE);//开启背面裁剪
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            MatrixState.setPorjectFrustum(-ratio * 0.8f, ratio * 1.2f, -1, 1, 20, 100);
            MatrixState.setCamera(-16f, 8f, 45f,
                    0f, 0f, 0f,
                    0f, 1.0f, 0f);
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            MatrixState.pushMatrix();
            cube.drawSelf();
            MatrixState.popMatrix();

            MatrixState.pushMatrix();
            MatrixState.translate(3.5f, 0, 0);
            cube.drawSelf();
            MatrixState.popMatrix();
        }
    }

}
