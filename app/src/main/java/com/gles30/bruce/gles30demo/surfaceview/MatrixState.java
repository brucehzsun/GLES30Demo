package com.gles30.bruce.gles30demo.surfaceview;

import android.opengl.Matrix;

/**
 * Created by Bruce on 2017/1/23.
 */
public class MatrixState {
    private static float[] mMVPMatrix = new float[16];
    ;
    public static float[] mProjectMatrix = new float[16];//4*4
    public static float[] mVMatrix = new float[16];
    private static float[] currMatrix;
    static float[][] mStack = new float[10][16];
    private static int stackTop;

    public static void setPorjectOrtho(float left, float right, int bottom, int top, int near, int far) {
        Matrix.orthoM(mProjectMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setPorjectFrustum(float left, float right, int bottom, int top, int near, int far) {
        Matrix.frustumM(mProjectMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setCamera(float cx, float cy, float cz,
                                 float tx, float ty, float tz,
                                 float upx, float upy, float upz) {
        Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }


    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);

    }

    public static void pushMatrix() {
        stackTop++;
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];
        }
    }

    public static void popMatrix() {
        for (int i = 0; i < 16; i++) {
            currMatrix[i] = mStack[stackTop][i];
        }
        stackTop--;
    }

    public static void translate(float x, int y, int z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    public static float[] getFinalMatrix() {
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    public static void rotate(float angle, int x, int y, int z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }
}
