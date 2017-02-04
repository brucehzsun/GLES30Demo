package com.gles30.bruce.gles30demo.modle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLES30;

import com.gles30.bruce.gles30demo.util.Constant;
import com.gles30.bruce.gles30demo.util.MatrixState;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

//颜色条状物
public class Belt2 {
    private int mProgram;//自定义渲染管线着色器程序id
    private int muMVPMatrixLocation;//总变换矩阵引用
    private int maPositionLocation; //顶点位置属性引用
    private int maColorLocation; //顶点颜色属性引用

    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mColorBuffer;//顶点着色数据缓冲
    private int vCount = 0;

    public Belt2(Context context) {
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(context);
    }

    //初始化顶点坐标与着色数据的方法
    private void initVertexData() {
        //顶点坐标数据的初始化================begin============================
        int n1 = 3;
        int n2 = 5;
        vCount = 2 * (n1 + n2 + 2) + 2;    //计算总顶点数
        float angdegBegin1 = 0;
        float angdegEnd1 = 90;
        float angdegSpan1 = (angdegEnd1 - angdegBegin1) / n1;

        float angdegBegin2 = 180;
        float angdegEnd2 = 270;
        float angdegSpan2 = (angdegEnd2 - angdegBegin2) / n2;
        float[] vertices = new float[vCount * 3];//顶点坐标数据

        //坐标数据初始化
        int count = 0;
        for (float angdeg = angdegBegin1; angdeg <= angdegEnd1; angdeg += angdegSpan1) {
            double angrad = Math.toRadians(angdeg);//当前弧度
            //大圆上的点
            vertices[count++] = (float) (-0.6f * Constant.UNIT_SIZE * Math.sin(angrad));//顶点坐标
            vertices[count++] = (float) (0.6f * Constant.UNIT_SIZE * Math.cos(angrad));
            vertices[count++] = 0;
            //小圆上的点
            vertices[count++] = (float) (-Constant.UNIT_SIZE * Math.sin(angrad));//顶点坐标
            vertices[count++] = (float) (Constant.UNIT_SIZE * Math.cos(angrad));
            vertices[count++] = 0;
        }

        //重复第一批三角形的最后一个顶点
        vertices[count++] = vertices[count - 4];
        vertices[count++] = vertices[count - 4];
        vertices[count++] = 0;

        for (float angdeg = angdegBegin2; angdeg <= angdegEnd2; angdeg += angdegSpan2) {
            double angrad = Math.toRadians(angdeg);//当前弧度
            //重复第二批三角形的第一个顶点
            if (angdeg == angdegBegin2) {
                vertices[count++] = (float) (-0.6f * Constant.UNIT_SIZE * Math.sin(angrad));//顶点坐标
                vertices[count++] = (float) (0.6f * Constant.UNIT_SIZE * Math.cos(angrad));
                vertices[count++] = 0;
            }
            //大圆上的点
            vertices[count++] = (float) (-0.6f * Constant.UNIT_SIZE * Math.sin(angrad));//顶点坐标
            vertices[count++] = (float) (0.6f * Constant.UNIT_SIZE * Math.cos(angrad));
            vertices[count++] = 0;
            //小圆上的点
            vertices[count++] = (float) (-Constant.UNIT_SIZE * Math.sin(angrad));//顶点坐标
            vertices[count++] = (float) (Constant.UNIT_SIZE * Math.cos(angrad));
            vertices[count++] = 0;
        }
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        mVertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices).position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================

        //顶点颜色值数组，每个顶点4个色彩值RGBA
        count = 0;
        float colors[] = new float[vCount * 4];
        for (int i = 0; i < colors.length; i += 8) {
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 0;

            colors[count++] = 0;
            colors[count++] = 1;
            colors[count++] = 1;
            colors[count++] = 0;
        }

        //创建顶点着色数据缓冲
        mColorBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();//转换为Float型缓冲
        mColorBuffer.put(colors).position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
    }

    //初始化着色器
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
        //指定使用某套着色器程序
        GLES30.glUseProgram(mProgram);
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixLocation, 1, false, MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer(
                maPositionLocation,
                3,
                GLES30.GL_FLOAT,
                false,
                3 * 4,
                mVertexBuffer
        );
        //将顶点颜色数据送入渲染管线
        GLES30.glVertexAttribPointer(
                maColorLocation,
                4,
                GLES30.GL_FLOAT,
                false,
                4 * 4,
                mColorBuffer
        );
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionLocation);
        //启用顶点颜色数据数组
        GLES30.glEnableVertexAttribArray(maColorLocation);
        //绘制条状物
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount); //采用三角形条带方式绘制
    }
}
