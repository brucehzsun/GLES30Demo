package com.gles30.bruce.gles30demo.modle.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.GLES30;

import com.gles30.bruce.gles30demo.util.MatrixState;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

//六角星
public class FiveStarOneColor {
    private int mProgram;//自定义渲染管线着色器程序id
    private int muMVPMatrixLocation;//总变换矩阵引用
    private int maPositionLocation; //顶点位置属性引用
    private int maColorLocation; //顶点颜色属性引用
    private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    private int vCount = 0;
//    public float yAngle = 0;//绕y轴旋转的角度
//    public float xAngle = 0;//绕z轴旋转的角度
    final float UNIT_SIZE = 1;
    //    final float UNIT_COLOR = 1;
    private float color[];

    public FiveStarOneColor(Context context, float r, float R, float z, float[] color) {
        this.color = color;//五角星的颜色
        //调用初始化顶点数据的initVertexData方法
        initVertexData(R, r, z);
        //调用初始化着色器的intShader方法
        initShader(context);
    }

    // 初始化顶点坐标数据的方法
    public void initVertexData(float R, float r, float z) {
        List<Float> flist = new ArrayList<>();
        float tempAngle = 360 / 6;//平均角度值
        for (float angle = 0; angle < 360; angle += tempAngle) {
            //第一个三角形
            //第一个中心点
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第二个点
            flist.add((float) (R * UNIT_SIZE * Math.cos(Math.toRadians(angle))));
            flist.add((float) (R * UNIT_SIZE * Math.sin(Math.toRadians(angle))));
            flist.add(z);
            //第三个点
            flist.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);

            //第二个三角形
            //第一个中心点
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第二个点
            flist.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);
            //第三个点
            flist.add((float) (R * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle))));
            flist.add((float) (R * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle))));
            flist.add(z);
        }
        vCount = flist.size() / 3;//顶点个数
        float[] vertexArray = new float[flist.size()];//顶点坐标数组
        for (int i = 0; i < vCount; i++)//循环遍历顶点坐标数组
        {
            vertexArray[i * 3] = flist.get(i * 3);//为顶点坐标数组赋值-x
            vertexArray[i * 3 + 1] = flist.get(i * 3 + 1);//为顶点坐标数组赋值-y
            vertexArray[i * 3 + 2] = flist.get(i * 3 + 2);//为顶点坐标数组赋值-z
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());    //设置字节顺序为本地操作系统顺序
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertexArray);//将顶点坐标数据放进缓冲
        mVertexBuffer.position(0);//设置缓冲起始位置
    }

    // 初始化着色器
    public void initShader(Context context) {
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

    //绘制方法
    public void drawSelf() {
        // 指定使用某套着色器程序
        GLES30.glUseProgram(mProgram);
        //初始化变换矩阵
        GLES30.glUniformMatrix4fv(muMVPMatrixLocation, 1, false, MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionLocation,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        //将顶点颜色数据送入渲染管线
        GLES30.glVertexAttrib4f(maColorLocation, color[0], color[1], color[2], 1.0f);
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionLocation);
        //绘制六角星
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }


}
