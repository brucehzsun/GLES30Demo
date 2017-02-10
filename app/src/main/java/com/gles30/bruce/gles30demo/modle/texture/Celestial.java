package com.gles30.bruce.gles30demo.modle.texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLES30;

import com.gles30.bruce.gles30demo.util.MatrixState;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

public class Celestial {    //表示星空天球的类
    private final float UNIT_SIZE = 10.0f;//天球半径
    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private int vCount = 0;//星星数量
    private float yAngle;//天球绕Y轴旋转的角度
    private float scale;//星星尺寸
    private int mProgram;//自定义渲染管线程序id
    private int muMVPMatrixHandle;//总变换矩阵引用
    private int maPositionHandle; //顶点位置属性引用
    private int uPointSizeHandle;//顶点尺寸参数引用

    public Celestial(Context context, float scale, float yAngle, int vCount) {
        this.yAngle = yAngle;//绕Y轴旋转的角度
        this.scale = scale;
        this.vCount = vCount;    //顶点的数量
        initVertexData();//调用初始化顶点数据的initVertexData方法
        intShader(context);//调用初始化着色器的initShader方法
    }

    public void initVertexData() {//自定义初始化顶点数据的initVertexData方法
        //顶点坐标数据的初始化
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < vCount; i++) {
            //随机产生每个星星的xyz坐标
            double angleTempJD = Math.PI * 2 * Math.random();
            double angleTempWD = Math.PI * (Math.random() - 0.5f);
            vertices[i * 3] = (float) (UNIT_SIZE * Math.cos(angleTempWD) * Math.sin(angleTempJD));
            vertices[i * 3 + 1] = (float) (UNIT_SIZE * Math.sin(angleTempWD));
            vertices[i * 3 + 2] = (float) (UNIT_SIZE * Math.cos(angleTempWD) * Math.cos(angleTempJD));
        }
        //创建顶点坐标数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
    }

    private void intShader(Context context) {    //初始化着色器
        //加载顶点着色器的脚本内容
        String mVertexShader = ShaderUtil.loadFromAssetsFile(context, "texture/vertex_xk.sh");
        //加载片元着色器的脚本内容
        String mFragmentShader = ShaderUtil.loadFromAssetsFile(context, "texture/frag_xk.sh");
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        //获取顶点尺寸参数引用
        uPointSizeHandle = GLES30.glGetUniformLocation(mProgram, "uPointSize");
    }

    public void drawSelf() {
        GLES30.glUseProgram(mProgram); //指定使用某套着色器程序
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        GLES30.glUniform1f(uPointSizeHandle, scale);  //将顶点尺寸传入渲染管线
        GLES30.glVertexAttribPointer(maPositionHandle,3,GLES30.GL_FLOAT,false,3 * 4,mVertexBuffer);
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vCount); //绘制星星点
    }
}
