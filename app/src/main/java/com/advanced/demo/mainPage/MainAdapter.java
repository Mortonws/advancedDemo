package com.advanced.demo.mainPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/8/11.
 */

public class MainAdapter extends RecyclerView.Adapter<MainItemViewHolder> {
    private Context mContext;
    private List<MainItemBean> mDataList = new ArrayList<>();
    private LayoutInflater mInflater;

    public MainAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MainItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main, parent, false);
        return new MainItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {
        MainItemBean itemBean = mDataList.get(position);
        holder.handleItem(itemBean);
    }

    public void addData(Class clazz, String itemName) {
        mDataList.add(new MainItemBean(clazz, itemName));
        notifyItemInserted(mDataList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
