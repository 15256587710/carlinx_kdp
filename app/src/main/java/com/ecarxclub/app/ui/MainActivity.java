package com.ecarxclub.app.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.AppManager;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.fragment.CardFragment;
import com.ecarxclub.app.fragment.HomeFragment;
import com.ecarxclub.app.fragment.MineFragment;
import com.ecarxclub.app.fragment.ShopFragment;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.presenter.main.MainPresenter;
import com.ecarxclub.app.presenter.main.MainView;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.ui.shop.ShopDetailsActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.views.TabFragmentHost;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * B7E7A9D051051CF2ECFC13441309B47D20733AF43D6E6C357F56E355ECAB1985
 */


@BindEventBus
public class MainActivity extends BaseActivityP<MainPresenter> implements MainView {
    @BindView(R.id.img_main_advert)
    ImageView imgAdvert;
    private TabFragmentHost mTabHost;
    // 标签
    private String[] TabTag = {"tab1", "tab2", "tab3", "tab4"};//
    // 自定义tab布局显示文本和顶部的图片
    private Integer[] ImgTab = {R.layout.tab_main_home, R.layout.tab_main_shop,
            R.layout.tab_main_card, R.layout.tab_main_mine};
    // tab 选中的activity
    private Class[] ClassTab = {HomeFragment.class, ShopFragment.class, CardFragment.class, MineFragment.class};
    // tab选中背景 drawable 样式图片 背景都是同一个,背景颜色都是 白色。。。  R.color.white,
    private Integer[] StyleTab = {R.color.white, R.color.white, R.color.white, R.color.white};
    private long exitTime = 0;
    private String btnIndex = "";
    //声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mLocationClient;
//    private double lat;
//    private double lon;
    private int mTabIndex=1;//点击哪个tab
    @Override
    public void initView() {
        setupView();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
        showGPSContacts();
    }

    @Override
    protected boolean initSwipeBack() {
        return false;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        presenter.onGetShowAdvert("3");
        onGetUserInfo();
    }

    @Override
    public void initClick() {
//        ToastUtils.i("友盟来源 +++", "" + CommonUtils.getMetaData(YxbApplication.getContext(), "UMENG_CHANNEL"));
    }


    private void setupView() {
        // 实例化framentTabHost
        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);
        InitTabView();
    }

    // 初始化 tab 自定义的选项卡 ///////////////
    private void InitTabView() {
        // 可以传递参数 b;传递公共的userid,version,sid
        Bundle b = new Bundle();
        // 循环加入自定义的tab
        for (int i = 0; i < TabTag.length; i++) {
            if (i == 1) {
//                b.putString("location",mLocation);
            }
            // 封装的自定义的tab的样式
            View indicator = getIndicatorView(i);
            mTabHost.addTab(mTabHost.newTabSpec(TabTag[i]).setIndicator(indicator), ClassTab[i], b);// 传递公共参数
        }
        mTabHost.getTabWidget().setDividerDrawable(R.color.black);

        mTabHost.setCurrentTabByTag("tab1");
//        tabs = (TabWidget) findViewById(android.R.id.tabs);
    }


    // 设置tab自定义样式:注意 每一个tab xml子布局的linearlayout 的id必须一样
    private View getIndicatorView(int i) {
        // 找到tabhost的子tab的布局视图
        View v = getLayoutInflater().inflate(ImgTab[i], null);
        RelativeLayout tv_lay = (RelativeLayout) v.findViewById(R.id.tab_layout_back);
        if (i == 2) {
//            tv_lay.getLayoutParams().height=80;
//            TextView textView1 = (TextView) v.findViewById(R.id.tab_text);
//            textView1.getLayoutParams().height=80;
//            textView1.setHeight(80);
//            Log.i("cwp","-----"+i+"==="+textView1.getHeight()+textView1.getText());
//            if (Apps.isPro) {
//                textView1.setText("最新上架");
//            } else {
//                textView1.setText("最新揭晓");
//            }
        }
        tv_lay.setBackgroundResource(StyleTab[i]);
        return v;
    }

    public void setTabIndex(int index) {
        switch (index) {
            case 1:
                mTabHost.setCurrentTabByTag("tab1");
                break;
            case 2:
                mTabHost.setCurrentTabByTag("tab2");
                break;
            case 3:
                mTabHost.setCurrentTabByTag("tab3");
                break;
            case 4:
                mTabHost.setCurrentTabByTag("tab4");
                break;
        }
    }

    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {//已开启定位权限
            setUpMap();//开始定位
        }
    }

    /**
     * 配置定位参数
     */
    private void setUpMap() {

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);


        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//                    lat = amapLocation.getLatitude();
//                    lon = amapLocation.getLongitude();

                    EventBus.getDefault().post(new EventMessage(Constant.USER_LOCATION_SUCCESS, amapLocation.getCity()));
//                    ToastUtils.e("getAccuracy", "" + amapLocation.getAccuracy() + " 米");//获取精度信息
//                    ToastUtils.e("joe", "lat :-- " + lat + " lon :--" + lon);
//                    ToastUtils.e("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    ToastUtils.e("joe", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码  定位
                if (grantResults != null && grantResults.length > 0) {
                    ToastUtils.i("onRequestPermissionsResult " + grantResults[0], "____" + PackageManager.PERMISSION_GRANTED);
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                        setUpMap();//开始定位
                    } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                        Toast.makeText(MainActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 100: //存储权限
                if (grantResults .length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EventBus.getDefault().post(new EventMessage(Constant.USER_SAVE_PERMISS,""));
                } else {
                    ToastUtils.showTextShort("未授权");
                }
                break;
            default:
                break;
        }
    }

    public void onGetUserInfo() {
//        presenter.onGetUserInfo();
    }

    @Override
    public void onUserInfo(UserInfoRes baseMsgRes) {
        if (baseMsgRes.success) {
            YxbApplication.userInfoRes = baseMsgRes;
        }
    }

    @Override
    public void onGetPopShowType(final AdvertisementRes advertisementRes) {
        if (advertisementRes.success) {
            if (advertisementRes.data != null && advertisementRes.data.size() > 0) {
                btnIndex = advertisementRes.data.get(0).buttonPosition;
                Glide.with(MainActivity.this).load(advertisementRes.data.get(0).posterIcon).apply(YxbContent.options_pop).into(imgAdvert);
                if ("1".equals(btnIndex)) {
                    imgAdvert.setVisibility(View.VISIBLE);
                } else {
                    imgAdvert.setVisibility(View.GONE);
                }
                //我的消息
                RxView.clicks(imgAdvert).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (advertisementRes.data != null && advertisementRes.data.size() > 0) {
                            if (advertisementRes.data.get(0).jump == 2 && advertisementRes.data.get(0).interLink.length() > 0) {//h5
                                presenter.onClickAdvert(CommonUtils.getMacAddress(), advertisementRes.data.get(0).id);
                                Bundle bundle = new Bundle();
                                bundle.putString("web_type", "advert");
                                bundle.putSerializable("advert", advertisementRes);
                                startIntent(MainActivity.this, WebViewActivity.class, bundle);
                            } else if (advertisementRes.data.get(0).jump == 3) {//首页
                                setTabIndex(1);
                            } else if (advertisementRes.data.get(0).jump == 4) {//商城
                                setTabIndex(2);
                            } else if (advertisementRes.data.get(0).jump == 5) {//卡券
                                setTabIndex(3);
                            } else if (advertisementRes.data.get(0).jump == 6) {//我的
                                setTabIndex(4);
                            } else if (advertisementRes.data.get(0).jump == 7) {//商品详情
                                Bundle bundle = new Bundle();
                                bundle.putString("shopid", advertisementRes.data.get(0).itemId);
                                startIntent(MainActivity.this, ShopDetailsActivity.class, bundle);
                            } else if (advertisementRes.data.get(0).jump == 8) {//卡券详情
                                Bundle bundle = new Bundle();
                                bundle.putString("cardid", advertisementRes.data.get(0).itemId);
                                startIntent(MainActivity.this, CardDetailsActivity.class, bundle);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClickAdvert(BaseMsgRes baseMsgRes) {

    }

    public void setAdvertBtn(String btnPos, int index) {
        if (btnPos.equals((index + ""))) {
            imgAdvert.setVisibility(View.VISIBLE);
        } else {
            imgAdvert.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusClick(EventMessage eventMessage) {
//        ToastUtils.e("Mainactivity______action " + eventMessage.action, "  obj " + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.MAIN_TAB_CHANGE:
                if (eventMessage.object.equals(Constant.MAIN_TAB_CHANGE_MAME)) {//1
                    QMUIStatusBarUtil.setStatusBarDarkMode(this);//白色
                    setAdvertBtn(btnIndex, 1);
                    mTabIndex=1;
                } else if (eventMessage.object.equals(Constant.MAIN_TAB_CHANGE_MAME2)) {
                    QMUIStatusBarUtil.setStatusBarDarkMode(this);
                    setAdvertBtn(btnIndex, 2);
                    mTabIndex=2;
                } else if (eventMessage.object.equals(Constant.MAIN_TAB_CHANGE_MAME3)) {
                    QMUIStatusBarUtil.setStatusBarLightMode(this);
                    setAdvertBtn(btnIndex, 3);
                    mTabIndex=3;
                } else if (eventMessage.object.equals(Constant.MAIN_TAB_CHANGE_MAME4)) {
                    if(SharedPreferencesUtils.getParam(YxbApplication.getContext(),
                            UrlOkHttpUtils.YXB_USER_TOKEN, "").toString().length()>0){
                        QMUIStatusBarUtil.setStatusBarDarkMode(this);
                        setAdvertBtn(btnIndex, 4);
                    }else {
                        setTabIndex(mTabIndex);
                        YxbContent.goLoginAct(MainActivity.this);
                    }
                    mTabIndex=4;
                }
                break;
            //登录信息过期
            case Constant.UN_LOGIN:
                YxbContent.goLoginAct(MainActivity.this);
                SharedPreferencesUtils.setParam(MainActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN,"");
                break;
            case Constant.LOGIN_SUCCESS://登录成功
                onGetUserInfo();
                if (mTabIndex==4){
                    setTabIndex(1);
                }else {
                    setTabIndex(mTabIndex);
                }
                showGPSContacts();
                break;
            case Constant.LOGIN_OUT://退出登录
                setTabIndex(1);
                break;
            case Constant.USER_CHANGE_TAB://切换tab
                int index = (int) eventMessage.object;
                setTabIndex(index);
                break;
            case Constant.USER_UPDATE_LOCATION://从新定位
                if (mLocationClient!=null){
                    //启动定位
                    mLocationClient.startLocation();
                }
                break;
        }
    }

    /**
     * 再按一次推出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showtoast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.getAppManager().finishAllActivity();
//                ActivityManager.removeAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
