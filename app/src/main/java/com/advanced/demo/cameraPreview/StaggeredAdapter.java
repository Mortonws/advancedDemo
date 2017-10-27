package com.advanced.demo.cameraPreview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/10/19.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.DataViewHolder>{
    private List<String> mDataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public StaggeredAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_stag_layout, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.mContentView.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addData() {
        mDataList.clear();
        for (int index = 0; index < 100; index ++) {
            int result = index % 6;
            StringBuilder sb = new StringBuilder("result");
            for (int i = 0; i <= result; i++) {
                sb.append(i);
            }
            mDataList.add(index, sb.toString());
        }
        notifyDataSetChanged();
    }

    public String getData(int index) {
        if (index >= 0 && index < getItemCount()) {
            return mDataList.get(index);
        }

        return "";
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView mContentView;

        public DataViewHolder(View view) {
            super(view);
            mContentView = (TextView) view.findViewById(R.id.content_view);
        }
    }
}
