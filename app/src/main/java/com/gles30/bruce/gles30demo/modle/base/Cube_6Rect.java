package com.gles30.bruce.gles30demo.modle.base;

import android.content.Context;

import com.gles30.bruce.gles30demo.util.MatrixState;


//立方体
public class Cube_6Rect {
    private final float unitSize;
    //用于绘制各个面的颜色矩形
    private ColorRect colorRect;

    public Cube_6Rect(Context context, float unitSize, float[] color) {
        this.unitSize = unitSize;
        //创建用于绘制各个面的颜色矩形对象
        colorRect = new ColorRect(context, unitSize, color);
    }

    public void drawSelf() {
        //总绘制思想：通过把一个颜色矩形旋转移位到立方体每个面的位置
        //绘制立方体的每个面

        //保护现场
        MatrixState.pushMatrix();

        //绘制前小面
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, unitSize);//移到相应位置
        colorRect.drawSelf();//绘制矩形面
        MatrixState.popMatrix();

        //绘制后小面
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, -unitSize);
        MatrixState.rotate(180, 0, 1, 0);
        colorRect.drawSelf();
        MatrixState.popMatrix();

        //绘制上大面
        MatrixState.pushMatrix();
        MatrixState.translate(0, unitSize, 0);
        MatrixState.rotate(-90, 1, 0, 0);
        colorRect.drawSelf();
        MatrixState.popMatrix();

        //绘制下大面
        MatrixState.pushMatrix();
        MatrixState.translate(0, -unitSize, 0);
        MatrixState.rotate(90, 1, 0, 0);
        colorRect.drawSelf();
        MatrixState.popMatrix();

        //绘制左大面
        MatrixState.pushMatrix();
        MatrixState.translate(unitSize, 0, 0);
        MatrixState.rotate(-90, 1, 0, 0);
        MatrixState.rotate(90, 0, 1, 0);
        colorRect.drawSelf();
        MatrixState.popMatrix();

        //绘制右大面
        MatrixState.pushMatrix();
        MatrixState.translate(-unitSize, 0, 0);
        MatrixState.rotate(90, 1, 0, 0);
        MatrixState.rotate(-90, 0, 1, 0);
        colorRect.drawSelf();
        MatrixState.popMatrix();

        //恢复现场
        MatrixState.popMatrix();
    }


}
