package com.gles30.bruce.gles30demo;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.gles30.bruce.gles30demo.Utils.ESShader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Update by sunhongzhi on 2017/1/7.
 */

class TriangleRender implements GLSurfaceView.Renderer {

    private FloatBuffer vertextBuffer;
    private int programId;

    public TriangleRender(DemoActivity demoActivity) {
        init();
    }

    private void init() {
        float[] data = new float[]{
                0.0f, 0.5f, 0,
                0.5f, 0.0f, 0,
                -0.5f, 0.0f, 0
        };

        vertextBuffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertextBuffer.put(data).position(0);


    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        String vertextShader = "#version 300 es\n" +
                "layout(location = 0) in vec4 vPosition;\n" +
                "void main(){\n" +
                "   gl_Position = vPosition;\n" +
                "}\n";
        String fargmentShader = "#version 300 es\n" +
                "precision mediump float;\n" +
                "out vec4 fragColor;\n" +
                "void main(){\n" +
                "   fragColor = vec4(1.0,0.0,0.0,1.0);\n" +
                "}\n";
        programId = ESShader.loadProgram(vertextShader, fargmentShader);
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //清空屏幕
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(programId);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_HALF_FLOAT, false, 0, vertextBuffer);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
