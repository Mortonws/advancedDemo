package com.advanced.demo.retrofit.viewHolder;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.advanced.demo.R;
import com.advanced.demo.retrofit.RetrofitRequestUtils;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class MovieHeaderViewHolder extends MovieBaseViewHolder {
    private Button btnMovieRequest;
    private EditText movieStart;
    private EditText movieCount;
    public MovieHeaderViewHolder(Context context, View view) {
        super(context, view);
        btnMovieRequest = (Button) view.findViewById(R.id.btn_retrofit_request);
        movieStart = (EditText) view.findViewById(R.id.request_start);
        movieCount = (EditText) view.findViewById(R.id.request_count);
    }

    @Override
    public void onShow() {
        btnMovieRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start = 0;
                int count = 10;
                String startContent = movieStart.getText().toString();
                String countContent = movieCount.getText().toString();
                if (!TextUtils.isEmpty(startContent)) {
                    start = Integer.valueOf(startContent);
                }
                if (!TextUtils.isEmpty(countContent)) {
                    count = Integer.valueOf(countContent);
                }
                Message msg = new Message();
                msg.what = RetrofitRequestUtils.EVENT_REQUEST_MOVIE;
                msg.arg1 = start;
                msg.arg2 = count;
                RetrofitRequestUtils.getInstance().sendMsg(msg);
            }
        });
    }
}
