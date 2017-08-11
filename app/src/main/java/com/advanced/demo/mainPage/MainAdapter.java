package com.advanced.demo.mainPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/8/11.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainItemViewHolder> {
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
        return new MainItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {
        final MainItemBean itemBean = mDataList.get(position);
        holder.itemName.setText(itemBean.itemName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, itemBean.clazz);
                if (!TextUtils.isEmpty(itemBean.extraKey) && !TextUtils.isEmpty(itemBean.extraValue)) {
                    intent.putExtra(itemBean.extraKey, itemBean.extraValue);
                }
                mContext.startActivity(intent);
            }
        });
    }

    public void addData(MainItemBean itemBean) {
        mDataList.add(itemBean);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void addData(Class clazz, String itemName) {
        mDataList.add(new MainItemBean(clazz, itemName));
        notifyItemInserted(mDataList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    static class MainItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        MainItemViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.item_name);
        }
    }
}
