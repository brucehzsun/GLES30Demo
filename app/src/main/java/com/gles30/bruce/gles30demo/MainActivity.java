package com.gles30.bruce.gles30demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        switch (view.getId()) {
            case R.id.button1:
                intent.putExtra(TYPE, 0);
                break;
            case R.id.button2:
                intent.putExtra(TYPE, 1);
                break;
            case R.id.button3:
                intent.putExtra(TYPE, 2);
                break;
            case R.id.button4:
                intent.putExtra(TYPE, 3);
                break;
            case R.id.button5:
                intent.putExtra(TYPE, 4);
                break;
            case R.id.button6:
                intent.putExtra(TYPE, 5);
                break;
        }
        startActivity(intent);
    }
}
