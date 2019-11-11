package com.advanced.demo.emoji;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.advanced.demo.retrofit.RetrofitRestClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiActivity extends BaseActivity {

    private final static String TAG = EmojiActivity.class.getSimpleName();

    private final static int DEFAULT_PER_PAGE_EMOJI_SIZE = 21;
    private int mScrollState = ViewPager.SCROLL_STATE_IDLE;
    private ViewPager mEmojiViewPager;
    private EmojiPagerAdapter mAdapter;

    private Button mUrlConvert;
    private EditText mUrlContent;
    private TextView mUrlResult;
    private Button mUrlReset;
    private Button mUrlClear;

    private RelativeLayout mLayoutRootView;

    private String mDefaultUrlContent = "https://www.baidu.com?keywords=今天天气不错";
    private DialogEdit mDialogEdit;
    private int mDialogHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void initPages() {
        super.initPages();
        requestByRetrofit();
        List<EmojiBean.Emoji> emojiList = EmojiUtils.getEmojiData().list;
        int emojiListIndex = 0;

        List<Fragment> fragmentList = new ArrayList<>();
        int fragmentPages = emojiList.size() / DEFAULT_PER_PAGE_EMOJI_SIZE;
        if (emojiList.size() % DEFAULT_PER_PAGE_EMOJI_SIZE != 0) {
            fragmentPages++;
        }
        for (int i = 0; i < fragmentPages; i++) {
            ArrayList<EmojiBean.Emoji> subEmojiList = new ArrayList<>();
            int remainEmojiListSize = emojiList.size() - emojiListIndex - 1;
            if (remainEmojiListSize < DEFAULT_PER_PAGE_EMOJI_SIZE) {
                subEmojiList.addAll(emojiList.subList(emojiListIndex, emojiListIndex + remainEmojiListSize));
                emojiListIndex = emojiListIndex + remainEmojiListSize;
            } else {
                subEmojiList.addAll(emojiList.subList(emojiListIndex, emojiListIndex + DEFAULT_PER_PAGE_EMOJI_SIZE));
                emojiListIndex = emojiListIndex + DEFAULT_PER_PAGE_EMOJI_SIZE;
            }

            Fragment fragment = new EmojiFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EmojiFragment.EXTRA_EMOJI_LIST, subEmojiList);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        boolean addFirstLastFragment = false;
        if (fragmentList.size() > 1) {
            addFirstLastFragment = true;
            Fragment firstFragment = new EmojiFragment();
            firstFragment.setArguments(fragmentList.get(0).getArguments());
            Fragment lastFragment = new EmojiFragment();
            lastFragment.setArguments(fragmentList.get(fragmentList.size() - 1).getArguments());
            fragmentList.add(0, lastFragment);
            fragmentList.add(fragmentList.size(), firstFragment);
        }
        mAdapter.addAllData(fragmentList);
        if (addFirstLastFragment) {
            mEmojiViewPager.setCurrentItem(1);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mDialogEdit = new DialogEdit(mContext);
        mEmojiViewPager = findViewById(R.id.emoji_view_pager);
        mAdapter = new EmojiPagerAdapter(getSupportFragmentManager());
        mEmojiViewPager.setAdapter(mAdapter);

        mUrlContent = findViewById(R.id.url_content);
        mUrlConvert = findViewById(R.id.url_convert);
        mUrlResult = findViewById(R.id.url_result);
        mUrlReset = findViewById(R.id.url_reset);
        mUrlClear = findViewById(R.id.url_clear);
        mLayoutRootView = findViewById(R.id.layout_root);

        mUrlContent.setText(mDefaultUrlContent);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEmojiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pageSelectedIndex = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mScrollState = state;
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (pageSelectedIndex == 0) {
                        int index = mAdapter.getCount() - 2;
                        if (index >= 0) {
                            mEmojiViewPager.setCurrentItem(mAdapter.getCount() - 2, false);
                        }
                    } else if (pageSelectedIndex == mAdapter.getCount() - 1) {
                        mEmojiViewPager.setCurrentItem(1, false);
                    }
                }
            }
        });

        mEmojiViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mScrollState == ViewPager.SCROLL_STATE_IDLE;
            }
        });
        mUrlConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mUrlContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    String result = content;
                    try {
                        result = URLEncoder.encode(content, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mUrlResult.setText(result);
                }
            }
        });
        mUrlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlContent.setText("");
                mUrlResult.setText("");
            }
        });
        mUrlReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlContent.setText(mDefaultUrlContent);
                mUrlResult.setText("");
            }
        });
        findViewById(R.id.btn_show_edit_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogEdit.show();

            }
        });
        mDialogEdit.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                onDialogShow();
            }
        });
        mDialogEdit.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onDialogDismiss();
            }
        });
    }

    private int mLayoutBottomLocation = 0;
    private void onDialogShow() {
        mDialogHeight = mDialogEdit.getDialogRootView().getMeasuredHeight();
        mDialogEdit.getDialogRootView().addOnLayoutChangeListener(mOnLayoutChangeListener);
    }

    private View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            View view = mDialogEdit.getDialogRootView();
            int[] dialogRootViewLocations = new int[2];
            view.getLocationOnScreen(dialogRootViewLocations);
            int dialogHeight = view.getMeasuredHeight();
            int dialogBottom = dialogHeight + dialogRootViewLocations[1];
            String dialogMsg = String.format(Locale.getDefault(), "locY:%s; dialogHeight:%s; bottom:%s", dialogRootViewLocations[1], dialogHeight, dialogBottom);
            Log.e(TAG, dialogMsg);

            int[] rootViewLocations = new int[2];
            mLayoutRootView.getLocationOnScreen(rootViewLocations);
            int layoutBottom = mLayoutRootView.getMeasuredHeight() + rootViewLocations[1];
            mLayoutBottomLocation = layoutBottom;
            String rootMsg = String.format(Locale.getDefault(), "rootLocY:%s, rootViewHeight;%s; layoutBottom=%s", rootViewLocations[1], mLayoutRootView.getMeasuredHeight(), layoutBottom);
            Log.e(TAG, rootMsg);

            if (dialogBottom < layoutBottom) {
                int scrollY = (layoutBottom - dialogBottom)/2;
                Log.e(TAG, "scrollY = " + scrollY);
                mLayoutRootView.scrollTo(0, scrollY);
            } if (dialogBottom == layoutBottom) {
                mLayoutRootView.scrollTo(0, 0);
            }

        }
    };

    private void onDialogDismiss() {
//        mLayoutRootView.scrollBy(0, -mDialogHeight);
        mDialogEdit.getDialogRootView().removeOnLayoutChangeListener(mOnLayoutChangeListener);
        mLayoutRootView.scrollTo(0, 0);
    }

    private void requestByRetrofit() {
        RetrofitRestClient requestClient = new RetrofitRestClient();
        requestClient.createClient();
        requestClient.request(0, 10);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_emoji;
    }
}
