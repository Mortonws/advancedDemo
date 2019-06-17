package com.advanced.demo.eventBus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author by WuSang on 2019/5/9.
 */
public class EventBusActivity extends BaseActivity {

    private final static String TAG = EventBusActivity.class.getSimpleName();

    private Button mBtnSendEvent;
    private TextView mEventContent;
    private static int EVENT_FLAG = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPages() {
        super.initPages();
        EventBus.getDefault().register(this);
        Glide.with(mContext).load("");
    }

    @Override
    protected void initView() {
        super.initView();
        mBtnSendEvent = findViewById(R.id.btn_send_event);
        mEventContent = findViewById(R.id.event_content);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnSendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent event = new MessageEvent();
                String msg = String.format("Msg Content %s", ++EVENT_FLAG);
                event.setMessage(msg);
                EventBus.getDefault().post(event);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        Log.e(TAG, "[onEvent] receive Message Event = " + event.toString());
        mEventContent.setText(event.getMessage());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_event_bus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
