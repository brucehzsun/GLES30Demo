package com.gles30.bruce.gles30demo.modle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import android.util.Log;

import com.gles30.bruce.gles30demo.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Update by sunhongzhi on 2017/1/23.
 */

public class Triangle {
    private static final String TAG = "Triangle";

    private static float[] mMVPMatrix;
    public static float[] mProjectMatrix = new float[16];//4*4
    public static float[] mVMatrix = new float[16];
    public static float[] mMMatrix = new float[16];
    private final Context context;


    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private int progrom;
    //    private int uMVPMatrixHandle;//0
    //    private int aPositionHandle;//0
//    private int aColorHandle;//1
    public float xAngle;

    public Triangle(Context context) {
        this.context = context;
        initVertexData();
        initShader();
    }

    private void initVertexData() {
        int vCount = 3;
        final float UNIT_SIZE = 0.2f;
        float[] vertices = new float[]{
                -4 * UNIT_SIZE, 0, 0,
                0, -4 * UNIT_SIZE, 0,
                4 * UNIT_SIZE, 0, 0
        };

        mVertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(vertices).position(0);

        float[] colors = new float[]{
                1, 1, 1, 0,
                0, 0, 1, 0,
                0, 1, 0, 0
        };

        mColorBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mColorBuffer.put(colors).position(0);
    }

    private void initShader() {
        String vertexShader = ShaderUtil.loadFromAssetsFile(context, "vertex.sh");
        String fragmentShader = ShaderUtil.loadFromAssetsFile(context, "fragment.sh");
        progrom = ShaderUtil.createProgram(vertexShader, fragmentShader);

//        aPositionHandle = GLES30.glGetAttribLocation(progrom, "aPosition");
//        aColorHandle = GLES30.glGetAttribLocation(progrom, "aColor");
//        uMVPMatrixHandle = GLES30.glGetUniformLocation(progrom, "uMVPMatrix");
//        Log.d(TAG, "uMVPMatrixHandle = " + uMVPMatrixHandle);
    }

    public static float[] getFinalMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    public void drawSelf() {
        GLES30.glUseProgram(progrom);

        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);

        GLES30.glUniformMatrix4fv(0, 1, false, getFinalMatrix(mMMatrix), 0);
        GLES30.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 3 * 4, mVertexBuffer);
        GLES30.glVertexAttribPointer(1, 4, GLES20.GL_FLOAT, false, 4 * 4, mColorBuffer);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
