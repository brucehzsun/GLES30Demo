package com.gles30.bruce.gles30demo.modle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.gles30.bruce.gles30demo.surfaceview.MatrixState;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruce on 2017/1/23.
 */

public class FiveStar {


    public float xAngle;
    public float yAngle;
    final int UNIT_SIZE = 1;
    private int vCount;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private int progrom;

    public FiveStar(Context context, float r, float R, float z) {
        super();
        initVertexData(R, r, z);
        initShader(context);
    }

    private void initVertexData(float R, float r, float z) {
        List<Float> flist = new ArrayList<>();
        float tempAngle = 360 / 5;
        for (float angle = 0; angle < 360; angle += tempAngle) {
            //第一个三角形；第一个点；
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第一个三角形第二个点；
            flist.add((float) (UNIT_SIZE * R * Math.cos(Math.toRadians(angle))));//x
            flist.add((float) (UNIT_SIZE * R * Math.sin(Math.toRadians(angle))));//y
            flist.add(z);
            //第一个三角形最后一个点；
            flist.add((float) (UNIT_SIZE * r * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (UNIT_SIZE * r * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);

            //第二个三角形；
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第二个三角形第二个点；
            flist.add((float) (UNIT_SIZE * r * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (UNIT_SIZE * r * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);

            flist.add((float) (UNIT_SIZE * R * Math.cos(Math.toRadians(angle + tempAngle))));//x
            flist.add((float) (UNIT_SIZE * R * Math.sin(Math.toRadians(angle + tempAngle))));//y
            flist.add(z);
        }
        vCount = flist.size() / 3;
        float[] vertexArray = new float[flist.size()];
        for (int i = 0; i < flist.size(); i++) {
            vertexArray[i] = flist.get(i);
        }

        mVertexBuffer = ByteBuffer.allocateDirect(vertexArray.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(vertexArray).position(0);

        float[] colorData = new float[vCount * 4];

        int triangleCount = vCount / 3;
        for (int i = 0; i < triangleCount; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 3; j++) {
                    colorData[(i * 3 + j) * 4] = 0;
                    colorData[(i * 3 + j) * 4 + 1] = 0;
                    colorData[(i * 3 + j) * 4 + 2] = 1;
                    colorData[(i * 3 + j) * 4 + 3] = 1;
                }
            } else {
                for (int j = 0; j < 3; j++) {
                    colorData[(i * 3 + j) * 4] = 1;
                    colorData[(i * 3 + j) * 4 + 1] = 0;
                    colorData[(i * 3 + j) * 4 + 2] = 0;
                    colorData[(i * 3 + j) * 4 + 3] = 1;
                }
            }
        }

        mColorBuffer = ByteBuffer.allocateDirect(colorData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mColorBuffer.put(colorData).position(0);
    }

    private void initShader(Context context) {
        String vertexShader = ShaderUtil.loadFromAssetsFile(context, "vertex.sh");
        String fragmentShader = ShaderUtil.loadFromAssetsFile(context, "fragment.sh");
        progrom = ShaderUtil.createProgram(vertexShader, fragmentShader);

//        aPositionHandle = GLES30.glGetAttribLocation(progrom, "aPosition");
//        aColorHandle = GLES30.glGetAttribLocation(progrom, "aColor");
//        uMVPMatrixHandle = GLES30.glGetUniformLocation(progrom, "uMVPMatrix");
//        Log.d(TAG, "uMVPMatrixHandle = " + uMVPMatrixHandle);
    }

    public void drawSelf() {
        GLES30.glUseProgram(progrom);
        MatrixState.setInitStack();
        MatrixState.translate(0, 0, 1);
        MatrixState.rotate(yAngle, 0, 1, 0);
        MatrixState.rotate(xAngle, 1, 0, 0);

        GLES30.glUniformMatrix4fv(0, 1, false, MatrixState.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * 4, mVertexBuffer);
        GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 4 * 4, mColorBuffer);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);

    }


}
