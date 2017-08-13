package com.advanced.demo.emoji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiActivity extends BaseActivity {
    private ViewPager mEmojiViewPager;
    private EmojiPagerAdapter mAdapter;

    @Override
    protected void initPages() {
        super.initPages();
        List<EmojiBean.Emoji> emojiList = EmojiUtils.getEmojiData().list;
        int sizeGap = emojiList.size() / 3;
        int emojiListIndex = 0;

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<EmojiBean.Emoji> subEmojiList = emojiList.subList(emojiListIndex, emojiListIndex + sizeGap);
            ArrayList<String> emojiStrList = new ArrayList<>();
            emojiListIndex = emojiListIndex + sizeGap;
            for (EmojiBean.Emoji emoji : subEmojiList) {
                String emojiContent = EmojiUtils.getEmoji(emoji.content);
                emojiStrList.add(emojiContent);
            }
            Fragment fragment = new EmojiFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(EmojiFragment.EXTRA_EMOJI_LIST, emojiStrList);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        mAdapter.addAllData(fragmentList);
    }

    @Override
    protected void initView() {
        super.initView();
        mEmojiViewPager = (ViewPager) findViewById(R.id.emoji_view_pager);
        mAdapter = new EmojiPagerAdapter(getSupportFragmentManager());
        mEmojiViewPager.setAdapter(mAdapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_emoji;
    }
}
