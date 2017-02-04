package com.gles30.bruce.gles30demo.modle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLES30;

import com.gles30.bruce.gles30demo.util.Constant;
import com.gles30.bruce.gles30demo.util.MatrixState;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

//颜色点或线
public class PointsOrLines {
    private int mProgram;// 自定义渲染管线着色器程序id
    private int muMVPMatrixLocation;// 总变换矩阵引用
    private int maPositionLocation; // 顶点位置属性引用
    private int maColorLocation; // 顶点颜色属性引用

    private FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
    private FloatBuffer mColorBuffer;// 顶点着色数据缓冲
    private int vCount = 0;

    public PointsOrLines(Context context) {
        // 初始化顶点坐标与着色数据
        initVertexData();
        // 初始化shader
        initShader(context);
    }

    // 初始化顶点坐标与着色数据的方法
    private void initVertexData() {
        // 顶点坐标数据的初始化================begin============================
        vCount = 5;

        float vertices[] = new float[]{
                0, 0, 0, //
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,//
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,//
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0,//
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0,};//

        // 创建顶点坐标数据缓冲
        // vertices.length*4是因为一个整数四个字节
        mVertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexBuffer.put(vertices).position(0);// 向缓冲区中放入顶点坐标数据
        // 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        // 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        // 顶点坐标数据的初始化================end============================

        // 顶点颜色值数组，每个顶点4个色彩值RGBA
        float colors[] = new float[]{
                1, 1, 0, 0,// 黄
                1, 1, 1, 0,// 白
                0, 1, 0, 0,// 绿
                1, 1, 1, 0,// 白
                1, 1, 0, 0,// 黄
        };
        // 创建顶点着色数据缓冲
        mColorBuffer = ByteBuffer.allocateDirect(colors.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mColorBuffer.put(colors).position(0);// 向缓冲区中放入顶点着色数据
        // 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        // 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        // 顶点着色数据的初始化================end============================
    }

    // 初始化着色器
    private void initShader(Context context) {
        // 加载顶点着色器的脚本内容
        String mVertexShader = ShaderUtil.loadFromAssetsFile(context, "vertex.sh");
        // 加载片元着色器的脚本内容
        String mFragmentShader = ShaderUtil.loadFromAssetsFile(context, "fragment.sh");
        // 基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        // 获取程序中顶点位置属性引用id
        maPositionLocation = GLES30.glGetAttribLocation(mProgram, "aPosition");
        // 获取程序中顶点颜色属性引用id
        maColorLocation = GLES30.glGetAttribLocation(mProgram, "aColor");
        // 获取程序中总变换矩阵引用id
        muMVPMatrixLocation = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        //指定使用某套着色器程序
        GLES30.glUseProgram(mProgram);
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixLocation, 1, false,
                MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer(maPositionLocation, 3, GLES30.GL_FLOAT,
                false, 3 * 4, mVertexBuffer);
        //将顶点颜色数据送入渲染管线
        GLES30.glVertexAttribPointer(maColorLocation, 4, GLES30.GL_FLOAT, false,
                4 * 4, mColorBuffer);
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionLocation);
        //启用顶点颜色数据数组
        GLES30.glEnableVertexAttribArray(maColorLocation);

        GLES30.glLineWidth(10);//设置线的宽度
        //绘制点或线
        switch (Constant.CURR_DRAW_MODE) {
            case Constant.GL_POINTS:// GL_POINTS方式
                GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vCount);
                break;
            case Constant.GL_LINES:// GL_LINES方式
                GLES30.glDrawArrays(GLES30.GL_LINES, 0, vCount);
                break;
            case Constant.GL_LINE_STRIP:// GL_LINE_STRIP方式
                GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, vCount);
                break;
            case Constant.GL_LINE_LOOP:// GL_LINE_LOOP方式
                GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, vCount);
                break;
        }
    }
}
