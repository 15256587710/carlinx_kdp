package com.ecarxclub.app.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.downfile.DownFileCallback;
import com.ecarxclub.app.downfile.DownLoadManager;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.model.home.GetVersionRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.home.OtherServiceRes;
import com.ecarxclub.app.model.home.WeatherRes;
import com.ecarxclub.app.presenter.main.home.HomePresenter;
import com.ecarxclub.app.presenter.main.home.HomeView;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.ui.home.DrivingRecordActivity;
import com.ecarxclub.app.ui.home.MyCarActivity;
import com.ecarxclub.app.ui.home.ScanCodeActivity;
import com.ecarxclub.app.ui.shop.ShopDetailsActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.TimeUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.home.HomeAdvertisementDialog;
import com.ecarxclub.app.utils.dialog.home.HomeUpdateDialog;
import com.andview.refreshview.XRefreshView;
import com.jakewharton.rxbinding.view.RxView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * 首页
 */
@BindEventBus
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {
    @BindView(R.id.tv_home_car)
    TextView tvCarNumber;
    @BindView(R.id.tv_home_driving)
    TextView tvDriving;//行驶记录
    @BindView(R.id.tv_home_weather)
    TextView tvWeather;
    @BindView(R.id.rl_home_message)
    RelativeLayout rlMessage;
    @BindView(R.id.ll_home_no_dring)
    LinearLayout llNoDring;//无行驶记录页面
    @BindView(R.id.cv_home_main)
    CardView cardView;
    @BindView(R.id.rl_home_service)
    RecyclerView recyclerView;
    @BindView(R.id.xRefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.ll_home_other)
    LinearLayout llOther;
    @BindView(R.id.ll_home_nobindcar)
    LinearLayout llBindCar;
    @BindView(R.id.ll_home_more)
    LinearLayout llMoreService;
    @BindView(R.id.rl_home_bingcar)
    RelativeLayout rlBindCar;
    @BindView(R.id.tv_main_driving_length)
    TextView tvDrivingLength;
    @BindView(R.id.tv_home_driving_time)
    TextView tvDrivingTime;
    @BindView(R.id.tv_home_driving_startarea)
    TextView tvStartArea;
    @BindView(R.id.tv_home_driving_startaddress)
    TextView tvStartAddress;
    @BindView(R.id.tv_home_driving_endarea)
    TextView tvEndArea;
    @BindView(R.id.tv_home_driving_endaddress)
    TextView tvEndAddress;
    @BindView(R.id.tv_home_driving_havetime)
    TextView tvHaveTime;
    @BindView(R.id.tv_home_driving_havelength)
    TextView tvHaveLength;
    @BindView(R.id.tv_home_driving_oil)
    TextView tvHaveOil;
    @BindView(R.id.tv_home_oil)
    TextView tvOil;
    @BindView(R.id.tv_home_oillength)
    TextView tvOilLength;
    @BindView(R.id.pb_home_oil)
    ProgressBar progressBarOil;
    @BindView(R.id.pb_home_length)
    ProgressBar progressBarLength;
    @BindView(R.id.tv_home_timeinfo)
    TextView tvTimeMsg;

    //刷新
    public static long lastRefreshTime;
    RecyclerViewAdapter recyclerViewAdapter;
    private String  mCarId="";//行驶记录详情id
    private String citys = "";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String mLoadPath = "",mSavePath="";//下载路径  保存路径
    private boolean isLogin;//登录成功
    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabmain_home;
    }

    @Override
    public void initData() {
        getAdvertisement();
        getVersion();
        onInitWeather(citys);
        getData();
//        ToastUtils.showTextShort(CommonUtils.getMetaData(YxbApplication.getContext(), "UMENG_CHANNEL"));//渠道
    }

    private void getData() {
        if(YxbContent.getLoginToken()){//登录
            onGetUserInfos();
        }else {
            YxbApplication.userInfoRes=null;
            tvDrivingLength.setText("——");
            tvOil.setText("--");
            tvOilLength.setText("--");
            tvCarNumber.setText("未获取车牌");
            progressBarOil.setProgress(0);
            progressBarLength.setProgress(0);
            tvTimeMsg.setVisibility(View.GONE);
            llBindCar.setVisibility(View.VISIBLE);
            llNoDring.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            llMoreService.setVisibility(View.GONE);
            xRefreshView.stopRefresh();
        }
    }

    @Override
    public void initView() {
        initXRefreshView();
//        showAdvertisement();
//        showUpdate();
    }

    public void onInitWeather(String city) {
        ToastUtils.i("地点  ", "" + city);
        if (city != null && city.length() > 0) {
            presenter.onGetWeather(city);
        }
    }

    public void onGetUserInfos() {
        presenter.onGetUserInfo();
    }

    //第三方服务
    public void onServices() {
        presenter.onGetServices();
    }

    @Override
    public void initClick() {
        //行驶记录
        RxView.clicks(tvDriving).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Bundle bundle = new Bundle();
                bundle.putString("carId", mCarId);
                startIntent(getActivity(), DrivingRecordActivity.class, bundle);//DrivingRecordActivity  DrivingDetailsActivity
            }
        });

        //扫码
        RxView.clicks(rlMessage).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    startIntent(getActivity(), ScanCodeActivity.class);
//                    startIntent(getActivity(), MyMessageActivity.class);//MyMessageActivity  AlertDialogActivity
                }else {
                    YxbContent.goLoginAct(getActivity());
                }
            }
        });
        //车牌号
        RxView.clicks(tvCarNumber).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainCarId!=null) {//是否绑定车辆
                        startIntent(getActivity(), MyCarActivity.class);
                    }else {
                        YxbContent.goBindCarAct(getActivity());
                    }
                }else {
                    YxbContent.goLoginAct(getActivity());
                }
            }
        });

        //绑定车辆
        RxView.clicks(rlBindCar).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    YxbContent.goBindCarAct(getActivity());
                }else {
                    YxbContent.goLoginAct(getActivity());
                }
            }
        });
    }

    //广告
    private void getAdvertisement() {
        presenter.onGetShowAdvert("2");
    }

    //更新
    private void getVersion() {
        //1560912556417====1560930547004
        long haveTime = (long) SharedPreferencesUtils.getParam(getActivity(), UrlOkHttpUtils.YXB_UPDATE_APP, (long) 0);
        ToastUtils.i("gengxin ", System.currentTimeMillis() + "====" + haveTime);
        if (haveTime > 0) {//设置了暂不更新
            if (System.currentTimeMillis() > haveTime) {// 过了暂不更新时间   则调用接口
                presenter.onVersion(CommonUtils.getAppVersionName(getActivity()));
            }
        } else {
            presenter.onVersion(CommonUtils.getAppVersionName(getActivity()));
        }
    }

    public void initXRefreshView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setPinnedTime(1000);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {//刷新
                EventBus.getDefault().post(new EventMessage(Constant.USER_UPDATE_LOCATION,""));
                getData();
            }
            @Override
            public void onLoadMore(boolean isSilence) {//加载跟多
            }
        });
    }

    @Override
    public void onUserInfo(UserInfoRes baseMsgRes) {
        xRefreshView.stopRefresh();
        if (baseMsgRes.success) {
            YxbApplication.userInfoRes = baseMsgRes;
            if (isLogin){
                EventBus.getDefault().post(new EventMessage(Constant.USER_LOGIN_SUCCESS_HOME,""));
            }
            if (baseMsgRes.data.mainCarId != null) {
                presenter.onDrivingRecode(baseMsgRes.data.mainCarId);
                onServices();
            } else {//没有绑定车辆
                llBindCar.setVisibility(View.VISIBLE);
                llNoDring.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                llMoreService.setVisibility(View.GONE);
                tvDrivingLength.setText("——");
                tvOil.setText("--");
                tvOilLength.setText("--");
                progressBarOil.setProgress(0);
                progressBarLength.setProgress(0);
            }
//            setMsgNum();
            isLogin=false;
        }
    }

    //    {"success":true,"msg":"success","data":{"city":"杭州","temperature":"20℃~27℃","wash_car":"不宜","weather":"小雨","wind":"东北风微风"}}
    @Override
    public void onGetWeater(WeatherRes weatherRes) {
        xRefreshView.stopRefresh();
        if (weatherRes.success) {
            if (weatherRes.data.city != null) {
                tvWeather.setText(weatherRes.data.city + " " + weatherRes.data.weather + " " + weatherRes.data.temperature + " " + weatherRes.data.wash_car + "洗车");
            } else {
                tvWeather.setText("——");
            }

        }
    }

    //  广告
    @Override
    public void onGetPopShowType(AdvertisementRes advertisementRes) {
        if (advertisementRes.success) {
            if (advertisementRes.data != null && advertisementRes.data.size() > 0) {
                showAdvertisement(advertisementRes);
            }
        }
    }

    @Override
    public void onClickAdvert(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {

        }
    }

    //更新
    @Override
    public void onVersion(GetVersionRes getVersionRes) {
        xRefreshView.stopRefresh();
        if (getVersionRes.success) {
            if (getVersionRes.data.grade.equals("2") || getVersionRes.data.grade.equals("3")) {//2提示更新  3强制更新
                mLoadPath = getVersionRes.data.interLink;
                showUpdate(getVersionRes.data);
            }
        }
    }

    @Override
    public void onServices(final OtherServiceRes baseMsgRes) {
        if (baseMsgRes.success) {
            if (baseMsgRes.data.size() > 0) {
                llMoreService.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerViewAdapter = new RecyclerViewAdapter<OtherServiceRes.DataBean>(getActivity(), baseMsgRes.data, R.layout.item_home_servicelist) {
                    @Override
                    public void convert(RecyclerViewHolder holder, OtherServiceRes.DataBean item,int pos) {
                        holder.setText(R.id.item_home_tv_title, item.title);
                        holder.setText(R.id.item_home_tv_content, item.illustration);
                        holder.setImageFormUrl(R.id.item_home_img, item.logoIcon);
                    }
                };
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
                    @Override
                    public void OnRecycleItemClick(View view, int position) {
                        if (baseMsgRes.data.get(position).interLink!=null&&baseMsgRes.data.get(position).interLink.length()>0){
                            Bundle bundle=new Bundle();
                            bundle.putString("web_url",baseMsgRes.data.get(position).interLink);
                            startIntent(getActivity(),WebViewActivity.class,bundle);
                        }
                    }
                    @Override
                    public void onRecycleItemLongClick(View view, int position) {

                    }
                });
            } else {
                recyclerView.setVisibility(View.GONE);
                llOther.setVisibility(View.GONE);
                llMoreService.setVisibility(View.GONE);
            }
        }
    }

    //车辆行驶记录
    @Override
    public void onDrivingRecodeV(HomeDrivingInfoRes baseMsgRes) {
        ToastUtils.i("onDrivingRecodeV___",""+baseMsgRes.success);
        if (baseMsgRes.success) {
            if (baseMsgRes.data!=null){
                if (baseMsgRes.data.travel != null) {
                    llBindCar.setVisibility(View.GONE);
                    llNoDring.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                    tvDrivingTime.setText(baseMsgRes.data.travel.createTime);
                    tvHaveTime.setText("耗时：" + baseMsgRes.data.travel.minute + "min");
                    tvHaveLength.setText("距离：" + baseMsgRes.data.travel.distance + "Km");
                    tvHaveOil.setText("油耗：" + baseMsgRes.data.travel.oilConsumption + "L");
                    tvStartArea.setText(baseMsgRes.data.travel.startDistrict);
                    tvStartAddress.setText(baseMsgRes.data.travel.startLocation);
                    tvEndArea.setText(baseMsgRes.data.travel.endDistrict);
                    tvEndAddress.setText(baseMsgRes.data.travel.endLocation);
                    mCarId = baseMsgRes.data.travel.carId;
                } else {
                    setNoDring();
                }
                if (baseMsgRes.data.car != null) {
                    if (baseMsgRes.data.car.totalMileage == 0.0) {
                        tvDrivingLength.setText("——");
                    } else {
                        tvDrivingLength.setText(baseMsgRes.data.car.totalMileage + "");
                    }
                    if (baseMsgRes.data.car.license != null) {
                        tvCarNumber.setText(baseMsgRes.data.car.license + " >");
                    } else {
                        tvCarNumber.setText("未获取车牌");
                    }
                    tvTimeMsg.setText("以上信息仅供参考 数据同步时间 " + baseMsgRes.data.car.createTime);
                    if (baseMsgRes.data.car.innage == 0.0 || baseMsgRes.data.car.totalTankOil == 0.0) {
                        progressBarOil.setProgress(0);
                        tvOil.setText("--");
                    } else {
                        tvOil.setText(baseMsgRes.data.car.innage + "");
                        progressBarOil.setProgress((int)((baseMsgRes.data.car.innage / baseMsgRes.data.car.totalTankOil)*100));
                    }
                    if (baseMsgRes.data.car.journeyMileage == 0.0 || baseMsgRes.data.car.fullMileage == 0.0) {
                        progressBarLength.setProgress(0);
                        tvOilLength.setText("--");
                    } else {
                        tvOilLength.setText(baseMsgRes.data.car.journeyMileage + "");
                        progressBarLength.setProgress((int) ((baseMsgRes.data.car.journeyMileage / baseMsgRes.data.car.fullMileage)*100));
                    }
                }else {
                    tvDrivingLength.setText("——");
                    progressBarOil.setProgress(0);
                    tvOil.setText("--");
                    progressBarLength.setProgress(0);
                    tvOilLength.setText("--");
                    tvCarNumber.setText("未获取车牌");
                    tvTimeMsg.setText("暂未获得数据");
                }
            }else {//没有行驶记录
                setNoDring();
            }
        }
    }

    public void setNoDring() {
        llBindCar.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        llNoDring.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
        ToastUtils.e("homeFragment_____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.LOGIN_SUCCESS://登录成功
                isLogin=true;
                getData();
                break;
            case Constant.LOGIN_OUT://退出登录
                getData();
                break;
            case Constant.USER_BIND_WECHAT://绑定微信成功
                getData();
                break;
            case Constant.USER_LOCATION_SUCCESS://天气
                citys = (String) eventMessage.object;
                onInitWeather(citys);
                break;
            case Constant.USER_INFO_SUCCESS://修改用户信息
                String isMessageChange = (String) eventMessage.object;
                if ("1".equals(isMessageChange)) {
                    onGetUserInfos();
                }
                break;
            case Constant.USER_CHANGE_CAR://切换车辆
                onGetUserInfos();
                break;
            case Constant.USER_SAVE_PERMISS://下载app存储 权限回调
                downloadFile(mLoadPath);
                break;
        }
    }


    private void showAdvertisement(final AdvertisementRes advertisementRes) {
        final HomeAdvertisementDialog addressListDialog = new HomeAdvertisementDialog(getActivity()).builder();
        addressListDialog.setData(advertisementRes.data.get(0).posterIcon);
        addressListDialog.setCancelable(false);
        addressListDialog.setOnBtnClickListener(new HomeAdvertisementDialog.onButtonClickListener() {
            @Override
            public void onClickListener() {//广告点击
                if (advertisementRes.data != null && advertisementRes.data.size() > 0) {
                    if (advertisementRes.data.get(0).jump == 2 && advertisementRes.data.get(0).interLink != null) {//h5
                        presenter.onClickAdvert(CommonUtils.getMacAddress(), advertisementRes.data.get(0).id);
                        Bundle bundle = new Bundle();
                        bundle.putString("web_type", "advert");
                        bundle.putSerializable("advert", advertisementRes);
                        startIntent(getActivity(), WebViewActivity.class, bundle);
                    } else if (advertisementRes.data.get(0).jump == 4) {//商城
                        EventBus.getDefault().post(new EventMessage(Constant.USER_CHANGE_TAB, 2));
                    } else if (advertisementRes.data.get(0).jump == 5) {//卡券
                        EventBus.getDefault().post(new EventMessage(Constant.USER_CHANGE_TAB, 3));
                    } else if (advertisementRes.data.get(0).jump == 6) {//我的
                        EventBus.getDefault().post(new EventMessage(Constant.USER_CHANGE_TAB, 4));
                    } else if (advertisementRes.data.get(0).jump == 7) {//商品详情
                        Bundle bundle = new Bundle();
                        bundle.putString("shopid", advertisementRes.data.get(0).itemId);
                        startIntent(getActivity(), ShopDetailsActivity.class, bundle);
                    } else if (advertisementRes.data.get(0).jump == 8) {//卡券详情
                        Bundle bundle = new Bundle();
                        bundle.putString("cardid", advertisementRes.data.get(0).itemId);
                        startIntent(getActivity(), CardDetailsActivity.class, bundle);
                    }
                }
                addressListDialog.dissmiss();
            }
        });
        addressListDialog.show();
    }

    HomeUpdateDialog homeUpdateDialog;

    //更新
    private void showUpdate(final GetVersionRes.DataBean dataBean) {
        homeUpdateDialog = new HomeUpdateDialog(getActivity()).builder();
        homeUpdateDialog.setData(dataBean);
        homeUpdateDialog.setCancelable(false);
        homeUpdateDialog.setOnBtnClickListener(new HomeUpdateDialog.onButtonClickListener() {
            @Override
            public void onClickListener(String mGrade) {//3强制更新  2暂不更新
                if (mGrade.equals("3")) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 100);
                        } else {
                            downloadFile(dataBean.interLink);
                        }
                    } else {
                        downloadFile(dataBean.interLink);
                    }
                } else {
                    long mm = TimeUtils.getHours(dataBean.delay) * 1000;
                    long nowTime = System.currentTimeMillis();
                    ToastUtils.i("时间", "__" + (mm + nowTime));
                    SharedPreferencesUtils.setParam(getActivity(), UrlOkHttpUtils.YXB_UPDATE_APP, (mm + nowTime));
                    homeUpdateDialog.dissmiss();
                }
//                downLoadApk(dataBean.interLink);
//                DownLoadManager.getInstance().pause(dataBean.interLink);
            }
        });
        homeUpdateDialog.show();
    }



    private void downloadFile(String url) {
        if (url.substring((url.length() - 3), url.length()).contains("apk")) {
            DownLoadManager.getInstance().downFile(url, new DownFileCallback() {
                @Override
                public void onSuccess(String path) {
                    mSavePath=path;
                    ToastUtils.i("onSuccess", "___________" + path + "++++++");
                    showtoast("下载成功，path=" + path);
//                    homeUpdateDialog.dissmiss();
                    installAPK(new File(path));
                }

                @Override
                public void onFail(String msg) {
                    ToastUtils.i("onFail", "___________" + msg);
                }

                @Override
                public void onProgress(final long totalSize, final long downSize) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int progress = (int) (downSize * 100 / totalSize);
                            homeUpdateDialog.setProgress(progress);
                            ToastUtils.i("down  ", "___" + progress);
                        }
                    });
                }
            });
        } else {
            ToastUtils.showTextShort("下载路径有误");
        }
    }
    private void initApk(File appFile) {
        ToastUtils.i("安装1111 " + Build.VERSION.SDK_INT, " " + appFile);
        //判读版本是否在7.0以上
        Uri apkUri = FileProvider.getUriForFile(context, "com.ecarxclub.app.fileprovider", appFile);//在AndroidManifest中的android:authorities值
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(install);
    }

    //8.0安装新版本APK权限
    private void peimissionO() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = getActivity().getPackageManager().canRequestPackageInstalls(); //检测是否允许安装apk
            ToastUtils.w("apkkkkkkkkk ", "" + b);
            if (b) {
                initApk(new File(mSavePath));
                //安装apk
            } else {
                ToastUtils.w("apkkkkkkkkk22222222222222222222  " + getActivity().getPackageName(), "" + b);
                //请求安装权限
                Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                startActivityForResult(intent, 10);
            }
        }
    }


    // 更换新的APK(android版本低于8.0)
    public void installAPK(File file) {
        //跳转安装
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//  8.0
            peimissionO();//检查安装权限
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//  7.0
                initApk(file);
            } else {//7.0以下
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtils.i("onActivityResultsssttttt " + requestCode, "___onresult " + resultCode);
        switch (requestCode) {
            case 10:
                if (resultCode == getActivity().RESULT_OK) {
                    initApk(new File(mSavePath));
                    Toast.makeText(getActivity(), "安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showTextShort("授权失败");
                }
                break;
        }
    }


}
