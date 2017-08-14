package com.advanced.demo.emoji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.advanced.demo.R;

import java.util.ArrayList;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiFragment extends Fragment {
    public final static String EXTRA_EMOJI_LIST = "com.advanced.demo.emoji.EXTRA_EMOJI_LIST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emoji, container, false);
        initView(rootView);
        return rootView;
    }


    private void initView(View rootView) {
        Bundle bundle = getArguments();
        ArrayList<EmojiBean.Emoji> emojiList = bundle.getParcelableArrayList(EXTRA_EMOJI_LIST);
        GridView mEmojiGrid = (GridView) rootView.findViewById(R.id.emoji_grid);
        EmojiGridAdapter adapter = new EmojiGridAdapter(getContext());
        mEmojiGrid.setAdapter(adapter);
        adapter.addData(emojiList);
    }
}
