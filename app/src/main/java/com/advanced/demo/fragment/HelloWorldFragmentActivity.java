package com.advanced.demo.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.advanced.demo.app.DemoApplication;
import com.advanced.demo.rxjava.RxPublisherDemo;
import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author by morton_ws on 2017/12/19.
 */

public class HelloWorldFragmentActivity extends BaseActivity {
    private final static String TAG = HelloWorldFragmentActivity.class.getSimpleName();

    private String url = "http://iflyad.bj.openstorage.cn/gnometest/beer/6784228128b8e00a9cbeee6b11dbffda.jpg";
    private ImageView imgView;
    private String jdDeepLink = "openapp.jdmobile://virtual?params=%7B%22des%22%3A%22getCoupon%22%2C%22m%5Fparam%22%3A%7B%22jdv%22%3A%22238571484%7Cxunfei%7Ct%5F301476161%5F207944%5F1712%7Ctuiguang%7C%5F2%5Fapp%5F0%5F1f3de5d787da4ca38cc9c5bf5bc577f3%2Dp%5F1712%22%7D%2C%22url%22%3A%22http%3A%5C%2F%5C%2Fccc%2Ex%2Ejd%2Ecom%5C%2Fdsp%5C%2Fnc%3Fext%3DY2xpY2sudW5pb24uamQuY29tL0pkQ2xpY2svP3VuaW9uSWQ9MzAxNDc2MTYxJnNpdGVpZD0yMDc5NDRfMTcxMiZ1dG10ZXJtX2V4dD1wXzE3MTImdG89aHR0cDovL3JlLm0uamQuY29tL2xpc3QvaXRlbS8xMTk3Ny0xNzk2MDQ4Lmh0bWw%5FcmVfZGNwPTQ1bFJUYWdfWDRrM2hQMFBDc1dBS1BLemtwZUVPdWk1aV8wNldXSGpXS3NmVDNaU3JldjZObmthd3ZMQkRtakpyM2l2dHpjNGJhUmZhYloxeHpOUHBCdWpsQUMyVWQwU1JrV0tRd1F5TXZGNF81OTZ4SVhETnFDbGRtSEJCVWJpLUxMWlBDT2Y4d3pX%26log%3DpcPUpdkdJVkdIsvCYwuMNRXHvw0lqr5e%5FZiwaFWnRpLnT4BgD%2DUP3WvS2uf8gx1JzQ4XCU8BQS01n0Qn%5FRiv0tuc5OVGbUp46X8ZIgxbqulKXT0k%5FtCQdwaOj53B8%5Fb5obfGslUzV5UGEmgdelNjqUvTRfVt%2DZiPgDKE7f8a9RgYgUyjxXqxIHdj6Ldcx9CuFDCQ62CXJaihyZcNMks2BVX1FKcUHkWWlBNkpNtAEscgF6sv3TK5hlULO982IEb1ZZzdGNdTSajQ%2DFCJ%5FVPfjuIMJ80iepp%2DyAyOaErGblPS4lyrMnwoOYvajrjBMubWKDg0gukaQBlDFqHn%2Dk5zn2MWqIV41Xv31tp51gZb%2DrTKZjOr4GoS56jNmv6Hq10R9GH2%5FjX3w0wZV1r4qAMiwbC7vzcdo%2Dbdcusoyvmp2dTwVLMSbfKCoCwYiwpFGVZEfvEIkZOGk8RSV0V5Pu4%2DHIF5f1itKb7QqYcrUD%2DvoEo%26v%3D404%26SE%3D1%22%2C%22SE%22%3A%22ADC%5FY6SIXa18K2tkEf3rTu3WtF6IGQzLuCviuZPJDbwzQnzR6WO8Lj30BRIA6fPhZOp2qTAjzNPR35nB4C3%5C%2FdNo4VMlqzdJzXzx19JJqJRbl8Sx4TYVqJcHZQoiVs2IGl4Suap4kuQYxcWkAypugEMAovQ%3D%3D%22%2C%22category%22%3A%22jump%22%2C%22action%22%3A%22to%22%2C%22sourceType%22%3A%22adx%22%2C%22sourceValue%22%3A%22xunfei%5F52%22%7D";


    private Button mBtnStartJd;
    private Intent mIntent = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void initView() {
        super.initView();
        imgView = findViewById(R.id.img_view);
        mBtnStartJd = findViewById(R.id.btn_start_jd);

        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jdDeepLink));
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        WebView webView = new WebView(DemoApplication.sAppContext);
        WebSettings webSettings = webView.getSettings();
        String webViewUa = webSettings.getUserAgentString();
        Log.e(TAG, "default UA = " + webViewUa);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnStartJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (hasInstallApp()) {
//                    startActivity(mIntent);
//                } else {
//                    Log.e(TAG, "app not install");
//                }
                Log.e(TAG, "btn send publish msg");
                RxPublisherDemo.getInstance().updateContent(HelloWorldFragmentActivity.class.getSimpleName());
            }
        });
    }

    private boolean hasInstallApp() {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mIntent, 0);
        return !activities.isEmpty();
    }

    @Override
    protected void initPages() {
        super.initPages();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.contain_view, new HelloWorldFragment()).commit();
        Glide.with(mContext).load(url).skipMemoryCache(true).into(imgView);
        Disposable disposable = RxPublisherDemo.getInstance().register(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Log.e(TAG, "accept = " + s);
            }
        });
        compositeDisposable.add(disposable);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "send publish msg");
                RxPublisherDemo.getInstance().updateContent(HelloWorldFragmentActivity.class.getSimpleName());
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fragment_activity;
    }
}
