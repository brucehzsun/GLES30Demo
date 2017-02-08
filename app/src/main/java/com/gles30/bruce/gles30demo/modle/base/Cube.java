package com.gles30.bruce.gles30demo.modle.base;

import android.content.Context;
import android.opengl.GLES30;

import com.gles30.bruce.gles30demo.util.MatrixState;
import com.gles30.bruce.gles30demo.util.Constant;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Bruce on 2017/1/24.
 */

//颜色立方体
public class Cube {
    int mProgram;//自定义渲染管线着色器程序id
    int muMVPMatrixLocation;//总变换矩阵引用
    int maPositionLocation; //顶点位置属性引用
    int maColorLocation; //顶点颜色属性引用

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    int vCount = 0;

    public Cube(Context context) {
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(context);
    }

    //初始化顶点坐标与着色数据的方法
    public void initVertexData() {
        //顶点坐标数据的初始化================begin============================
        vCount = 3 * 4 * 6;//每个面4个三角形，6个面

        float vertices[] = new float[]{
                //前面 上
                0, 0, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //前 左
                0, 0, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //前 下
                0, 0, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //前 右
                0, 0, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,

                //后面
                0, 0, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, 0, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                //左面
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, 0, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //右面
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, 0, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //上面
                0, Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                //下面
                0, -Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                0, -Constant.UNIT_SIZE, 0,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, -Constant.UNIT_SIZE,
                Constant.UNIT_SIZE, -Constant.UNIT_SIZE, Constant.UNIT_SIZE,
        };

        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        mVertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())//设置字节顺序
                .asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices)//向缓冲区中放入顶点坐标数据
                .position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================

        //顶点颜色值数组，每个顶点4个色彩值RGBA
        float colors[] = new float[]{
                //前面
                1, 0, 0, 1,//中间为白色
                1, 0, 0, 1,
                1, 0, 0, 1,
                1, 1, 0, 1,//中间为白色
                1, 1, 0, 1,
                1, 1, 0, 1,
                0, 0, 1, 1,//中间为白色
                0, 0, 1, 1,
                0, 0, 1, 1,
                0, 1, 0, 1,//中间为白色
                0, 1, 0, 1,
                0, 1, 0, 1,
                //后面
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                //左面
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                //右面
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                //上面
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                //下面
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
        };
        //创建顶点着色数据缓冲
        mColorBuffer = ByteBuffer.allocateDirect(colors.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();//转换为Float型缓冲
        mColorBuffer.put(colors).position(0);//向缓冲区中放入顶点着色数据
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
    }

    //初始化shader
    private void initShader(Context context) {
        //加载顶点着色器的脚本内容
        String mVertexShader = ShaderUtil.loadFromAssetsFile(context, "vertex.sh");
        //加载片元着色器的脚本内容
        String mFragmentShader = ShaderUtil.loadFromAssetsFile(context, "fragment.sh");
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionLocation = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id
        maColorLocation = GLES30.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixLocation = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        //制定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        //将最终变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMVPMatrixLocation, 1, false, MatrixState.getFinalMatrix(), 0);
        //为画笔指定顶点位置数据
        GLES30.glVertexAttribPointer(
                maPositionLocation,
                3,
                GLES30.GL_FLOAT,
                false,
                3 * 4,
                mVertexBuffer
        );
        //为画笔指定顶点着色数据
        GLES30.glVertexAttribPointer(
                maColorLocation,
                4,
                GLES30.GL_FLOAT,
                false,
                4 * 4,
                mColorBuffer
        );
        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionLocation);
        GLES30.glEnableVertexAttribArray(maColorLocation);
        //绘制立方体
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
