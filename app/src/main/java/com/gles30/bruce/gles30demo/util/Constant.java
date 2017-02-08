package com.gles30.bruce.gles30demo.util;

/**
 * Update by sunhongzhi on 2017/2/4.
 */

public class Constant {
    public static float UNIT_SIZE = 1f;
    public static float ratio;

    //绘制方式
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_STRIP = 2;
    public static final int GL_LINE_LOOP = 3;

    public static int CURR_DRAW_MODE = 0;//当前绘制方式

    public static class DemoType {
        public static String Triangle = "Triangle";
        public static String FiveStar = "FiveStar";
        public static String Cube = "Cube";
        public static String Line = "Line";
        public static String Circle = "Circle";
        public static String Belt = "Belt";
        public static String Element = "Element";
        public static String RangeElement = "RangeElement";
        public static String FiveStarOneColor = "FiveStarOneColor";
        public static String Cube_6Rect = "Cube_6Rect";
        public static String Cube2in1 = "Cube2in1";
        public static String Polygon = "Element";
    }

    public static class LightType {
        public static String ball = "ball_ambient";
        public static String ball_diffuse = "ball_diffuse";
        public static String ball_specular = "ball_specular";
        public static String ball_all = "ball_all";
    }
}
