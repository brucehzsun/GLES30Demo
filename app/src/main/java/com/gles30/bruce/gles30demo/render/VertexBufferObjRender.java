package com.gles30.bruce.gles30demo.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.gles30.bruce.gles30demo.DemoActivity;
import com.gles30.bruce.gles30demo.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Update by sunhongzhi on 2017/1/7.
 */

class VertexBufferObjRender implements GLSurfaceView.Renderer {

    private final int VERTEX_POS_SIZE = 3;//xyz
    private final int VERTEX_COLOR_SIZE = 4;//rgba;

    final int VERTEX_POS_INDX = 0;
    final int VERTEX_COLOR_INDX = 1;

    private FloatBuffer vertextBuffer;
    private int programId;
    private ShortBuffer indicesBuffer;
    private FloatBuffer colorBuffer;

    // VertexBufferObject Ids
    private int[] mVBOIds = new int[3];

    private int vtxStrides[] = {
            VERTEX_POS_SIZE * 4,
            VERTEX_COLOR_SIZE * 4
    };

    public VertexBufferObjRender(DemoActivity demoActivity) {
        init();
    }

    private void init() {
        float[] vertexData = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        short[] indicesData = new short[]{
                0, 1, 2
        };

        float[] colorData = new float[]{
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
        };

        vertextBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertextBuffer.put(vertexData).position(0);

        colorBuffer = ByteBuffer.allocateDirect(colorData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colorData).position(0);

        indicesBuffer = ByteBuffer.allocateDirect(indicesData.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indicesBuffer.put(indicesData).position(0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        String vertextShader =
                        "#version 300 es                        \n" +
                        "layout(location = 0) in vec4 aPosition;\n" +
                        "layout(location = 1) in vec4 aColor;   \n" +
                        "out vec4 vColor;                       \n" +
                        "void main(){                           \n" +
                        "   gl_Position = aPosition;            \n" +
                        "   vColor = aColor;                    \n" +
                        "}";
        String fargmentShader =
                        "#version 300 es            \n" +
                        "precision mediump float;   \n" +
                        "out vec4 fragColor;        \n" +
                        "in vec4 vColor;            \n" +
                        "void main(){               \n" +
                        "   fragColor = vColor;     \n" +
                        "}";
        programId = ShaderUtil.createProgram(vertextShader, fargmentShader);

//        mVBOIds[0] = 0;
//        mVBOIds[1] = 0;
//        mVBOIds[2] = 0;

        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //清空屏幕
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(programId);
        drawPrimitiveWithVBOs();
    }


    private void drawPrimitiveWithVBOs() {
        int numVertices = 3;
        int numIndices = 3;

        // mVBOIds[0] - used to store vertex position
        // mVBOIds[1] - used to store vertex color
        // mVBOIds[2] - used to store element indices
        if (mVBOIds[0] == 0 && mVBOIds[1] == 0 && mVBOIds[2] == 0) {
            // Only allocate on the first draw
            GLES30.glGenBuffers(3, mVBOIds, 0);

//            vertextBuffer.position(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStrides[0] * numVertices,
                    vertextBuffer, GLES30.GL_STATIC_DRAW);

//            colorBuffer.position(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[1]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStrides[1] * numVertices,
                    colorBuffer, GLES30.GL_STATIC_DRAW);

//            indicesBuffer.position(0);
            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[2]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, 2 * numIndices,
                    indicesBuffer, GLES30.GL_STATIC_DRAW);
        }

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE,
                GLES30.GL_FLOAT, false, vtxStrides[0], 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[1]);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDX);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE,
                GLES30.GL_FLOAT, false, vtxStrides[1], 0);

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[2]);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices,
                GLES30.GL_UNSIGNED_SHORT, 0);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDX);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
