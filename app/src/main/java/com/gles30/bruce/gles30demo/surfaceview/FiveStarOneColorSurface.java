package com.gles30.bruce.gles30demo.surfaceview;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.gles30.bruce.gles30demo.modle.FiveStarOneColor;
import com.gles30.bruce.gles30demo.util.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FiveStarOneColorSurface extends GLSurfaceView {
    private final static float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例

    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    private float xAngle;
    private float yAngle;

    public FiveStarOneColorSurface(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        SceneRenderer mRenderer = new SceneRenderer();
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                yAngle += dx * TOUCH_SCALE_FACTOR;//设置六角星数组中的各个六角星绕y轴旋转角度
                xAngle += dy * TOUCH_SCALE_FACTOR;//设置六角星数组中的各个六角星绕x轴旋转角度
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        FiveStarOneColor[] ha = new FiveStarOneColor[6];//六角星数组
        float[][] color = new float[][]{
                {1, 0, 0.1f},//红
                {0.98f, 0.49f, 0.04f},//橙
                {1f, 1f, 0.04f},//黄
                {0.67f, 1, 0},//绿
                {0.27f, 0.41f, 1f},//蓝
                {0.88f, 0.43f, 0.92f}};//紫

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // 设置屏幕背景色RGBA
            // 创建六角星数组中的各个对象
            for (int i = 0; i < ha.length; i++) {
                ha[i] = new FiveStarOneColor(getContext(), 0.4f, 1.0f, -1.0f * i, color[i]);
            }
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);// 打开深度检测
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            float ratio = (float) width / height;
            //设置透视投影
            MatrixState.setProjectFrustum(-ratio * 0.4f, ratio * 0.4f, -1 * 0.4f, 1 * 0.4f, 1, 50);

            //调用此方法产生摄像机矩阵
            MatrixState.setCamera(0, 0, 6, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

            //绘制六角星数组中的各个六角星
            for (FiveStarOneColor h : ha) {
                MatrixState.pushMatrix();
                //设置沿Z轴正向位移1
                MatrixState.translate(0, 0, 1);
                //设置绕y轴旋转
                MatrixState.rotate(yAngle, 0, 1, 0);
                //设置绕z轴旋转
                MatrixState.rotate(xAngle, 1, 0, 0);
                //将最终变换矩阵传入渲染管线
                h.drawSelf();
                MatrixState.popMatrix();
            }
        }
    }
}
