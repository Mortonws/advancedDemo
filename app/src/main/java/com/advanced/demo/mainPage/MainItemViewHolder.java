package com.advanced.demo.mainPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2018/4/28.
 */

public class MainItemViewHolder extends RecyclerView.ViewHolder {
    private final static String TAG = MainItemViewHolder.class.getSimpleName();
    private TextView itemName;
    private Context mContext;

    MainItemViewHolder(View view, Context context) {
        super(view);
        this.mContext = context;
        itemName = view.findViewById(R.id.item_name);
    }

    public void handleItem(final MainItemBean itemBean) {
        itemName.setText(itemBean.itemName);
        itemView.setOnClickListener(new View.OnClickListener() {
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
}
