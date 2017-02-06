package com.gles30.bruce.gles30demo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gles30.bruce.gles30demo.R;

import java.util.ArrayList;

/**
 * Update by sunhongzhi on 2017/2/6.
 */

public class ListAdapter extends RecyclerView.Adapter<ListHolder> {

    private Context context;
    private ArrayList<String> data;

    public ListAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View conentView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        return new ListHolder(context, conentView);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        String content = data.get(position);
        holder.getView(content, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
