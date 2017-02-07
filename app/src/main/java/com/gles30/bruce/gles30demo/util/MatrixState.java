package com.gles30.bruce.gles30demo.util;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//管理系统矩阵状态的类
public class MatrixState {
    private static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    private static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    private static float[] currMatrix;//当前变换矩阵

    private static float[][] mStack = new float[10][16];//用于保存变换矩阵的栈
    private static int stackTop = -1;//栈顶索引
    //获取具体物体的总变换矩阵
    private static float[] mMVPMatrix = new float[16];//总变换矩阵


    private static FloatBuffer lightPositionFloatBuffer;

    private static float[] lightLocation = new float[]{0, 0, 0};//光源位置数组

    /**
     * 产生无任何变换的初始矩阵
     */
    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    /**
     * 将当前变换矩阵存入栈中
     */
    public static void pushMatrix() {
        stackTop++;//栈顶索引加1
        System.arraycopy(currMatrix, 0, mStack[stackTop], 0, 16);
    }

    /**
     * 从栈顶取出变换矩阵
     */
    public static void popMatrix() {
        System.arraycopy(mStack[stackTop], 0, currMatrix, 0, 16);
        stackTop--;//栈顶索引减1
    }

    /**
     * 沿X、Y、Z轴方向进行平移变换的方法
     *
     * @param x x轴
     * @param y y轴
     * @param z z轴
     */
    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

//    static float[] cameraLocation = new float[3];//摄像机位置

    /**
     * 设置摄像机的方法
     *
     * @param cx  摄像机位置x
     * @param cy  摄像机位置y
     * @param cz  摄像机位置z
     * @param tx  观察目标点x
     * @param ty  观察目标点y
     * @param tz  观察目标点z
     * @param upx 摄像机顶部朝向x
     * @param upy 摄像机顶部朝向y
     * @param upz 摄像机顶部朝向z
     */
    public static void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(
                mVMatrix,    //存储生成矩阵元素的float[]类型数组
                0,            //填充起始偏移量
                cx, cy, cz,    //摄像机位置的X、Y、Z坐标
                tx, ty, tz,    //观察目标点X、Y、Z坐标
                upx, upy, upz    //up向量在X、Y、Z轴上的分量
        );
    }


    /**
     * 设置透视投影参数
     *
     * @param left   near面的left
     * @param right  near面的right
     * @param bottom near面的bottom
     * @param top    near面的top
     * @param near   near面与视点的距离
     * @param far    far面与视点的距离
     */
    public static void setProjectFrustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }


    /**
     * 设置正交投影参数
     *
     * @param left   near面的left
     * @param right  near面的right
     * @param bottom near面的bottom
     * @param top    near面的top
     * @param near   near面与视点的距离
     * @param far    far面与视点的距离
     */
    public static void setProjectOrtho(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    /**
     * 计算产生总变换矩阵的方法
     *
     * @return 总变换矩阵
     */
    public static float[] getFinalMatrix() {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        //投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }


    /**
     * 获取具体物体的变换矩阵
     *
     * @return 当前物体的变换矩阵
     */
    public static float[] getMMatrix() {
        return currMatrix;
    }

    public static void translate(float x, int y, int z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    public static void rotate(float angle, int x, int y, int z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    public static void setPorjectOrtho(float left, float right, int bottom, int top, int near, int far) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setPorjectFrustum(float left, float right, int bottom, int top, int near, int far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    //沿X、Y、Z轴方向进行缩放变换的方法
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix, 0, x, y, z);
    }

    public static void setLightLocation(float x, float y, float z) {
        if (lightPositionFloatBuffer == null) {
            lightPositionFloatBuffer = ByteBuffer.allocateDirect(3 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        }

        lightLocation[0] = x;
        lightLocation[1] = y;
        lightLocation[2] = z;

        lightPositionFloatBuffer.clear();
        lightPositionFloatBuffer.put(lightLocation);
        lightPositionFloatBuffer.position(0);
    }

    public static FloatBuffer getLightPositionFloatBuffer() {
        return lightPositionFloatBuffer;
    }
}
