package com.advanced.demo.scrollView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2018/4/28.
 */

public class DispatchIndexAdapter extends RecyclerView.Adapter<DispatchIndexViewHolder> {
    private LayoutInflater mInflater;

    public DispatchIndexAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DispatchIndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main, parent, false);
        return new DispatchIndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DispatchIndexViewHolder holder, int position) {
        holder.handleItemIndex(position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
