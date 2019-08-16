package com.ecarxclub.app.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.ShareModel;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.presenter.MyWebView;
import com.ecarxclub.app.presenter.MyWebViewPresenter;
import com.ecarxclub.app.share.ShareUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.BottomAlertDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import org.greenrobot.eventbus.EventBus;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/21 11:14.
 * 通用web
 */
public class WebViewActivity extends BaseActivityP<MyWebViewPresenter> implements MyWebView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.ll_container)
    LinearLayout llTopLayout;
    private AdvertisementRes advertisementRes;
    private AgentWeb mAgentWeb;

    private String web_url="",web_type="",web_welcome;//welcome引导页进入 返回跳转到主页   regist 注册用户协议进入  integral 积分中心
    private ShareModel shareModel;//邀请好友
    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }


    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        mSwipeBackLayout.setSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }
            @Override
            public void onEdgeTouch(int edgeFlag) {
            }
            @Override
            public void onScrollOverThreshold() {
                setFinishAct();
            }
        });
    }

    @Override
    public void initData() {
        if(getIntent().getExtras()!=null){
            web_url=getIntent().getExtras().getString("web_url");
            web_welcome=getIntent().getExtras().getString("welcome");
            if ("regist".equals(web_welcome)){
                tvToolbarTitle.setText("咖豆派平台用户服务协议");
            }else {
                tvToolbarTitle.setText("网页");
            }
            if (getIntent().getExtras().getString("web_type")!=null&&getIntent().getExtras().getString("web_type").length()>0){
                web_type=getIntent().getExtras().getString("web_type");
            }else {
                web_type="web_url";
            }
            shareModel= (ShareModel) getIntent().getSerializableExtra("model");
        }
        ToastUtils.i("type "+web_type,"___url "+web_url);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llTopLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor()// 使用默认进度条颜色
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .interceptUnkownScheme() //拦截找不到相关页面的Scheme
                .createAgentWeb()//
                .ready()
                .go(getWebUrl(web_type));
//        String ua = mAgentWeb.getAgentWebSettings().getWebSettings().getUserAgentString();
//        mAgentWeb.getAgentWebSettings().getWebSettings().setUserAgentString(ua.replace("Android", "ibestry_yshu_android/1_0_0"));
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, this));
        }
    }

    public String getWebUrl(String type) {
        switch (type) {
            case "web_url":
                return web_url;
            case "advert":
                advertisementRes = (AdvertisementRes) getIntent().getExtras().getSerializable("advert");
                tvToolbarRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_share), null, null, null);
                return advertisementRes.data.get(0).interLink;
                default:
                    return "";
        }
    }

    //返回上一页
    private void setFinishAct(){
        if (!mAgentWeb.back()) {
            if("welcome".equals(web_welcome)){
                startIntent(WebViewActivity.this,MainActivity.class);
            }else if ("integral".equals(web_welcome)){
                EventBus.getDefault().post(new EventMessage(Constant.USER_SIGNIN,""));
            }
            finish();
        }
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setFinishAct();
            }
        });

        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(advertisementRes!=null){
                    View view = LayoutInflater.from(WebViewActivity.this).inflate(R.layout.dialog_share, null);
                    LinearLayout ll_share_wechat = view.findViewById(R.id.ll_share_talk);
                    LinearLayout ll_share_qq = view.findViewById(R.id.ll_share_qq);
                    LinearLayout ll_share_wcFriend = view.findViewById(R.id.ll_share_friends);
                    LinearLayout ll_share_weibo = view.findViewById(R.id.ll_share_xinlang);
                    TextView tvCancel=view.findViewById(R.id.tv_share_cancel);
                    final BottomAlertDialog bottomAlertDialog = new BottomAlertDialog(WebViewActivity.this, view, true, true);
                    bottomAlertDialog.show();
                    // 微信分享
                    ll_share_wechat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareTypeAdvert(SHARE_MEDIA.WEIXIN);
                            bottomAlertDialog.dismiss();
                        }
                    });
                    // 微信朋友圈分享
                    ll_share_wcFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareTypeAdvert(SHARE_MEDIA.WEIXIN_CIRCLE);
                            bottomAlertDialog.dismiss();
                        }
                    });
                    // qq分享
                    ll_share_qq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareTypeAdvert(SHARE_MEDIA.QQ);
                            bottomAlertDialog.dismiss();
                        }
                    });
                    // 微博分享
                    ll_share_weibo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareTypeAdvert(SHARE_MEDIA.SINA);
                            bottomAlertDialog.dismiss();
                        }
                    });
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomAlertDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    //分享广告
    public void shareTypeAdvert(SHARE_MEDIA platform) {
        ShareUtils.shareWeb(this, advertisementRes.data.get(0).interLink, advertisementRes.data.get(0).title
                , advertisementRes.data.get(0).illustration, advertisementRes.data.get(0).logoIcon,
                R.drawable.umeng_socialize_share_web, platform
        );
    }

    //分享邀请好友
    public void shareTypeFriend(SHARE_MEDIA platform) {
        ShareUtils.shareWeb(this, shareModel.shareInterLink+"?token="+SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN,"").toString()+"&wrap=android"
                , shareModel.shareTitle
                , shareModel.shareContent, shareModel.shareImage,
                R.drawable.umeng_socialize_share_web, platform
        );
    }


    public class AndroidInterface {
//        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agentWeb;
        private Activity activity;

        public AndroidInterface(AgentWeb web, Activity activity) {
            this.agentWeb = web;
            this.activity = activity;
        }

        @JavascriptInterface
        public void onShareWechat(String tag) {//tag  1微信   2朋友圈
            ToastUtils.i("onShareWechat ","tag_ "+tag);
            if ("1".equals(tag)){
                shareTypeFriend(SHARE_MEDIA.WEIXIN);
            }else {
                shareTypeFriend(SHARE_MEDIA.WEIXIN_CIRCLE);
            }

        }
    }

    @Override
    public void onResult(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected MyWebViewPresenter createPresenter() {
        return new MyWebViewPresenter(this);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "WebViewActivity onPageStarted");
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            //handler.cancel(); // Android默认的处理方式
            //handleMessage(Message msg); // 进行其他处理
            handler.proceed();  // 接受所有网站的证书
        }
    };



    //cwp
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 1;// 表单的结果回调
    // 1.设置WebChromeClient，重写文件上传回调
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if ("regist".equals(web_welcome)){
                tvToolbarTitle.setText("咖豆派平台用户服务协议");
            }else {
                tvToolbarTitle.setText(title);
            }
        }
        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
    };

    // 2.回调方法触发本地选择文件
    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    // 4. 选择内容回调到Html页面
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || mUploadCallbackAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }
    //cwp


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtils.i("requestCode " + requestCode, "  +++" + resultCode);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }
    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }


    /**
     * 再按一次推出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setFinishAct();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
