package com.advanced.demo.fragmentAdapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2019-06-17.
 */
public class UserViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        mViewPager = findViewById(R.id.user_view_pager);
    }

    @Override
    protected void initPages() {
        super.initPages();
        for (int index = 0; index < 5; index++) {
            mFragmentList.add(UserFragment.createFragment(index));
        }

        FragmentStatePagerAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_user_activity;
    }


    private class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
