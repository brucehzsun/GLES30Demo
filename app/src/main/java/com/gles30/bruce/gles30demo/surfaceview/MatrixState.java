package com.gles30.bruce.gles30demo.surfaceview;

import android.opengl.Matrix;

/**
 * Created by Bruce on 2017/1/23.
 */
public class MatrixState {
    private static float[] mMVPMatrix;
    public static float[] mProjectMatrix = new float[16];//4*4
    public static float[] mVMatrix = new float[16];
    public static float[] mMMatrix = new float[16];

    public static void setPorjectOrtho(float left, float right, int bottom, int top, int near, int far) {
        Matrix.orthoM(mProjectMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setCamera(float cx, float cy, float cz,
                                 float tx, float ty, float tz,
                                 float upx, float upy, float upz) {
        Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

    public static float[] getFinalMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
}
