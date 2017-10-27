package com.advanced.demo.cameraPreview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/10/20.
 */

public class AppraiseStarAdapter extends RecyclerView.Adapter<AppraiseStarAdapter.StarViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mItemWidth;

    public AppraiseStarAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        int listAppraiseStarWidth = context.getResources().getDisplayMetrics().widthPixels - 2 * context.getResources().getDimensionPixelSize(R.dimen.list_appraise_star_margin);
        mItemWidth = listAppraiseStarWidth / getItemCount();
    }

    @Override
    public StarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_appraise_star, parent, false);
        RelativeLayout root = (RelativeLayout) view.findViewById(R.id.root_appraise_star);
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        layoutParams.width = mItemWidth;
        root.setLayoutParams(layoutParams);
        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarViewHolder holder, int position) {
        final int index = position;
        holder.appraiseStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = "你点击了" + (index + 1) + "星";
                Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        ImageView appraiseStar;

        public StarViewHolder(View view) {
            super(view);
            appraiseStar = (ImageView) view.findViewById(R.id.appraise_star);
        }

    }
}
