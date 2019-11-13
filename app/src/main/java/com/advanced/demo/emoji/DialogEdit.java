package com.advanced.demo.emoji;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2019-11-07.
 */
public class DialogEdit extends Dialog {
    private final static String TAG = DialogEdit.class.getSimpleName();
    private EditText mEditText;

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
        mEditText = findViewById(R.id.edittext);
        mEditText.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e(TAG , "[onLayoutChange]");
            }
        });
        getEditText().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e(TAG, "[onGlobalLayout]");
            }
        });


//        setOnShowListener(new OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                mEditText.requestFocus();
//                showSoftKeyBoard(getContext(), mEditText);
//            }
//        });
    }

    @Override
    public void show() {
        super.show();

    }

    public EditText getEditText() {
        return mEditText;
    }

    public RelativeLayout getDialogRootView() {
        return findViewById(R.id.dialog_root);
    }

    /**
     * 显示软键盘
     * @param ctx
     * @param v
     */
    public static void showSoftKeyBoard(Context ctx, View v){
        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v,0);
    }
}
