package com.advanced.demo.cameraPreview;

import android.graphics.Rect;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/10/16.
 */

public class CameraPreviewActivity extends BaseActivity {
    private final static String TAG = "CameraPreview";

    private RecyclerView mDataList;
    private GridLayoutManager mLayoutManager;
    private StaggeredAdapter mAdapter;
    private int mDataRecyclerViewWidth;

    private RecyclerView mListAppraiseStar;
    private AppraiseStarAdapter mAppraiseStarAdapter;

    @Override
    protected void initView() {
        super.initView();
        mDataList = (RecyclerView) findViewById(R.id.data_list);
        mListAppraiseStar = (RecyclerView) findViewById(R.id.list_appraise_star);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mAdapter = new StaggeredAdapter(mContext);
        mDataRecyclerViewWidth = getResources().getDimensionPixelSize(R.dimen.grid_data_list_width);

        mLayoutManager = new GridLayoutManager(mContext, 2);
        mDataList.setLayoutManager(mLayoutManager);
        mDataList.setAdapter(mAdapter);
        mDataList.setItemAnimator(new DefaultItemAnimator());

        mAdapter.addData();

        mAppraiseStarAdapter = new AppraiseStarAdapter(mContext);
        mListAppraiseStar.setAdapter(mAppraiseStarAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mListAppraiseStar.setLayoutManager(layoutManager);
        mListAppraiseStar.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                String data = mAdapter.getData(position);
                int dataTextWidth = getTextWidth(data);
                int targetWidth = mDataRecyclerViewWidth * 3 / 8;
                if (dataTextWidth >= targetWidth) {
                    return 2;
                }
                return 1;
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_camera_preview;
    }


    public int getTextWidth(String textContent) {
        Rect bounds = new Rect();
        TextPaint paint;

        TextView textView = new TextView(mContext);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        paint = textView.getPaint();
        paint.getTextBounds(textContent, 0, textContent.length(), bounds);
        return bounds.width();
    }
}
