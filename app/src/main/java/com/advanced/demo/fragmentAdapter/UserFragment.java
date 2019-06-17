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

/**
 * @author by morton_ws on 2019-06-17.
 */
public class UserFragment extends Fragment {
    public final static String EXTRA_FRAGMENT_INDEX = "com.advanced.demo.fragmentAdapter.extra_index";
    private final static String TAG = UserFragment.class.getSimpleName();
    private int mContentIndex = -1;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume; index = " + (mContentIndex+1));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause; index = " + (mContentIndex+1));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop; index = " + (mContentIndex+1));
    }
}