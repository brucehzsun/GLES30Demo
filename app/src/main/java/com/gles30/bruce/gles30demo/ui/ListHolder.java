package com.gles30.bruce.gles30demo.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gles30.bruce.gles30demo.DemoActivity;
import com.gles30.bruce.gles30demo.MainActivity;
import com.gles30.bruce.gles30demo.R;

/**
 * Update by sunhongzhi on 2017/2/6.
 */

public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final Button contentView;
    private final Context context;
    private int position;

    public ListHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        contentView = (Button) itemView.findViewById(R.id.content);
        contentView.setOnClickListener(this);
    }

    public void getView(String content, int position) {
        this.position = position;
        contentView.setText(content);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.putExtra(MainActivity.TYPE, contentView.getText().toString());
        context.startActivity(intent);
    }
}
