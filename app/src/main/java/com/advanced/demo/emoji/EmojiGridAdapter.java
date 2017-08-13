package com.advanced.demo.emoji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/8/13.
 */

public class EmojiGridAdapter extends BaseAdapter {
    private List<String> mEmojiList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public EmojiGridAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mEmojiList.size();
    }

    @Override
    public String getItem(int position) {
        return mEmojiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmojiHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_emoji_grid, parent, false);
            holder = new EmojiHolder();
            holder.emojiContent = (TextView) convertView.findViewById(R.id.emoji_content);
            convertView.setTag(holder);
        } else {
            holder = (EmojiHolder) convertView.getTag();
        }
        holder.emojiContent.setText(mEmojiList.get(position));
        return convertView;
    }

    public void addData(List<String> data) {
        this.mEmojiList.addAll(data);
        notifyDataSetChanged();
    }

    private static class EmojiHolder {
        TextView emojiContent;
    }
}
