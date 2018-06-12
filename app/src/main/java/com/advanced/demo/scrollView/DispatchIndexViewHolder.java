package com.advanced.demo.scrollView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2018/4/28.
 */

public class DispatchIndexViewHolder extends RecyclerView.ViewHolder {
    private TextView mItemIndex;
    public DispatchIndexViewHolder(View view) {
        super(view);
        mItemIndex = view.findViewById(R.id.item_name);
    }

    public void handleItemIndex(int position) {
        mItemIndex.setText(String.valueOf(position));
    }
}
