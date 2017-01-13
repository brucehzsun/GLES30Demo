package com.gles30.bruce.gles30demo;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import com.gles30.bruce.gles30demo.Utils.ESShader;
import com.gles30.bruce.gles30demo.Utils.ESShapes;
import com.gles30.bruce.gles30demo.Utils.ESTransform;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Update by sunhongzhi on 2017/1/7.
 */

class SimpleVertexShaderRender implements GLSurfaceView.Renderer {


    private int programId;
    private int mMVPMatrixLocation;
    // Vertex data
    private ESShapes mCube = new ESShapes();
    // MVP matrix
    private ESTransform mMVPMatrix = new ESTransform();
    private long mLastTime;
    private float mAngle;
    private float aspect;

    public SimpleVertexShaderRender(DemoActivity demoActivity) {
        init();
    }

    private void init() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        String vShaderStr =
                "#version 300 es 							 \n" +
                        "uniform mat4 u_mvpMatrix;                   \n" +
                        "layout(location = 0) in vec4 a_position;    \n" +
                        "layout(location = 1) in vec4 a_color;       \n" +
                        "out vec4 v_color;                           \n" +
                        "void main()                                 \n" +
                        "{                                           \n" +
                        "   v_color = a_color;                       \n" +
                        "   gl_Position = u_mvpMatrix * a_position;  \n" +
                        "}                                           \n";

        String fShaderStr =
                "#version 300 es 							 \n" +
                        "precision mediump float;                    \n" +
                        "in vec4 v_color;                            \n" +
                        "layout(location = 0) out vec4 outColor;     \n" +
                        "void main()                                 \n" +
                        "{                                           \n" +
                        "  outColor = v_color;                       \n" +
                        "}                                           \n";

        programId = ESShader.loadProgram(vShaderStr, fShaderStr);
        mMVPMatrixLocation = GLES30.glGetUniformLocation(programId, "u_mvpMatrix");

        mCube.genCube(1.0f);

        // Starting rotation angle for the cube
        mAngle = 45.0f;

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        aspect = (float) width / (float) height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        update();
        // Clear the color buffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        // Use the program object
        GLES30.glUseProgram(programId);

        // Load the vertex data
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false,
                0, mCube.getVertices());
        GLES30.glEnableVertexAttribArray(0);

        // Set the vertex color to red
        GLES30.glVertexAttrib4f(1, 1.0f, 0.0f, 0.0f, 1.0f);

        // Load the MVP matrix
        GLES30.glUniformMatrix4fv(mMVPMatrixLocation, 1, false,
                mMVPMatrix.getAsFloatBuffer());

        // Draw the cube
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mCube.getNumIndices(),
                GLES30.GL_UNSIGNED_SHORT, mCube.getIndices());

    }


    private void update() {
        if (mLastTime == 0) {
            mLastTime = SystemClock.uptimeMillis();
        }

        long curTime = SystemClock.uptimeMillis();
        long elapsedTime = curTime - mLastTime;
        float deltaTime = elapsedTime / 1000.0f;
        mLastTime = curTime;

        ESTransform perspective = new ESTransform();
        ESTransform modelview = new ESTransform();


        // Compute a rotation angle based on time to rotate the cube
        mAngle += (deltaTime * 40.0f);

        if (mAngle >= 360.0f) {
            mAngle -= 360.0f;
        }


        // Generate a perspective matrix with a 60 degree FOV
        perspective.matrixLoadIdentity();
        perspective.perspective(60.0f, aspect, 1.0f, 20.0f);

        // Generate a model view matrix to rotate/translate the cube
        modelview.matrixLoadIdentity();

        // Translate away from the viewer
        modelview.translate(0.0f, 0.0f, -2.0f);

        // Rotate the cube
        modelview.rotate(mAngle, 1.0f, 0.0f, 1.0f);

        // Compute the final MVP by multiplying the
        // modevleiw and perspective matrices together
        mMVPMatrix.matrixMultiply(modelview.get(), perspective.get());
    }
}
