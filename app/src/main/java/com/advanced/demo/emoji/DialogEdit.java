package com.advanced.demo.emoji;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2019-11-07.
 */
public class DialogEdit extends Dialog {

    public DialogEdit(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);//设置dialog显示居中
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = getContext().getResources().getDisplayMetrics().widthPixels;// 设置dialog宽度为屏幕的4/5
            getWindow().setAttributes(lp);
        }
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
    }

    public RelativeLayout getDialogRootView() {
        return findViewById(R.id.dialog_root);
    }
}
