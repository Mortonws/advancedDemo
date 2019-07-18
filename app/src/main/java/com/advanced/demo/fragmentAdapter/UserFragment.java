package com.advanced.demo.fragmentAdapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.advanced.demo.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author by morton_ws on 2019-06-17.
 */
public class UserFragment extends Fragment {
    public final static String EXTRA_FRAGMENT_INDEX = "com.advanced.demo.fragmentAdapter.extra_index";
    private final static String TAG = UserFragment.class.getSimpleName();
    private Disposable mDisposable;
    private int mContentIndex = -1;
    private TextView mLabelCountingDown;

    public static UserFragment createFragment(int index) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mContentIndex = bundle.getInt(EXTRA_FRAGMENT_INDEX, -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        TextView content = view.findViewById(R.id.user_name);
        String contentUser = String.format(Locale.getDefault(), "这是第%s个用户", (mContentIndex + 1));
        content.setText(contentUser);

        mLabelCountingDown = view.findViewById(R.id.label_counting_down);

        createCountingDown();
    }

    private long mCountTime = 5;
    private Flowable mFlowable;
    private void createCountingDown() {
        mFlowable = Flowable.intervalRange(0, mCountTime + 1, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        long result = mCountTime - aLong;
                        long min = result/60;
                        long sec = result % 60;
                        StringBuilder sb = new StringBuilder();
                        if (min > 0) {
                            sb.append(min).append("分");
                        }
                        sb.append(sec).append("秒后有大红包哦");
                        mLabelCountingDown.setText(sb.toString());
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() {
                        mLabelCountingDown.setText("倒计时到");
                    }
                });
        mDisposable = mFlowable.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume; index = " + (mContentIndex + 1));
        if (mDisposable != null && mDisposable.isDisposed()) {
            mCountTime++;
            mDisposable = mFlowable.subscribe();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause; index = " + (mContentIndex + 1));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop; index = " + (mContentIndex + 1));
    }
}