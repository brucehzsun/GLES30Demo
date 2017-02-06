package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gles30.bruce.gles30demo.ui.ListAdapter;

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
        data.add("三角形");
        data.add("五角形");
        data.add("Cube");
        data.add("Line");
        data.add("Circle");
        data.add("Belt");
        data.add("Element");
        ListAdapter adapter = new ListAdapter(this, data);
        recyclerView.setAdapter(adapter);

    }
}
