package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gles30.bruce.gles30demo.ui.ListAdapter;
import com.gles30.bruce.gles30demo.util.Constant;
import com.gles30.bruce.gles30demo.util.Constant.DemoType;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> data = new ArrayList<>();
        data.add(DemoType.Triangle);
        data.add(DemoType.FiveStar);
        data.add(DemoType.Cube);
        data.add(DemoType.Line);
        data.add(DemoType.Circle);
        data.add(DemoType.Belt);
        data.add(DemoType.Element);
        data.add(DemoType.RangeElement);
        data.add(DemoType.FiveStarOneColor);
        data.add(DemoType.Cube_6Rect);
        data.add(DemoType.Cube2in1);
        data.add(DemoType.Polygon);
        ListAdapter adapter = new ListAdapter(this, data);
        recyclerView.setAdapter(adapter);

        initList2();
        initList3();
    }

    private void initList2() {
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> data = new ArrayList<>();
        data.add(Constant.LightType.ball);
        data.add(Constant.LightType.ball_diffuse);
        data.add(Constant.LightType.ball_specular);
        data.add(Constant.LightType.ball_all);
        data.add(Constant.LightType.ball_direction);
        data.add(Constant.LightType.cube_light);
        ListAdapter adapter = new ListAdapter(this, data);
        recyclerView2.setAdapter(adapter);
    }

    private void initList3() {
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> data = new ArrayList<>();
        data.add(Constant.TextureType.texture_triangle);
        data.add(Constant.TextureType.texture_rect);
        data.add(Constant.TextureType.texture_rect2);
        data.add(Constant.TextureType.EarthAndMoon);
        data.add(Constant.TextureType.pkm);
        data.add(Constant.TextureType.point);
        ListAdapter adapter = new ListAdapter(this, data);
        recyclerView3.setAdapter(adapter);
    }

}
