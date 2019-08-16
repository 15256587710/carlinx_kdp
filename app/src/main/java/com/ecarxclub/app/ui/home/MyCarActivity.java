package com.ecarxclub.app.ui.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.car.BindCarRes;
import com.ecarxclub.app.presenter.home.MyCarPresenter;
import com.ecarxclub.app.presenter.home.MyCarView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.card.CardPagerAdapter;
import com.ecarxclub.app.utils.card.ShadowTransformer;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.jakewharton.rxbinding.view.RxView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/6 11:09.
 * 我的车辆
 */
@BindEventBus
public class MyCarActivity extends BaseActivityP<MyCarPresenter> implements MyCarView {
//    @BindView(R.id.gd_map)
    private MapView mapView;
    @BindView(R.id.tv_toolbar_left)
    ImageView tvToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.viewPager_card)
    ViewPager mViewPager;
    @BindView(R.id.btn_mycar_change)
    Button btnChange;
    private ShadowTransformer mCardShadowTransformer;
    private CardPagerAdapter mCardAdapter;
    //声明AMapLocationClient类对象
//    public AMapLocationClientOption mLocationOption = null;
//    private AMapLocationClient mLocationClient;

//    private double lat;
//    private double lon;
    private AMap aMap;//地图控制器对象
    private UiSettings mUiSettings;

//    View infoWindow = null;
//    LatLng latLng = new LatLng(30.2937384, 120.2083779);//30.2937384, 120.2083779
//    private LatLng mStartPoint = new LatLng(30.2937384, 120.2083779);//起点，116.335891,39.942295   E120.2083779, N30.2937384
//    private LatLng mEndPoint = new LatLng(30.206811, 120.221008);
    //    List<LatLng> latLngs=new ArrayList<>();
    private boolean isBind;
    private String mCarId="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_add), null, null, null);
        tvToolbarRight.setText("添加车辆");
        tvToolbarTitle.setText("我的车辆");
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
        mapView= (MapView) findViewById(R.id.gd_map);
        mapView.onCreate(savedInstanceState);
        init();
//        showGPSContacts();

        onMyCarList();
    }

    @Override
    public void initClick() {
        RxView.clicks(tvToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                YxbContent.goBindCarAct(MyCarActivity.this);
            }
        });
        //切换绑定
        RxView.clicks(btnChange).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mCarId.length()>0){
                    presenter.ongBindCar(mCarId);
                }else {
                    ToastUtils.showTextShort("该车辆状态暂不可绑定");
                }
            }
        });
    }

    private void onMyCarList() {
        presenter.ongetAllCar();
    }

    private void initViewpager(final List<BindCarRes.DataBean> listBean) {
        mCardAdapter = new CardPagerAdapter(MyCarActivity.this);
        mCardAdapter.addCardItem(listBean);
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                ToastUtils.i("", "select   " + i);
                mCarId=listBean.get(i).carId;
                addMarkersToMap(listBean.get(i).endLat,listBean.get(i).endLongitude,listBean.get(i).endLocation);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onGetMyCar(BindCarRes myCarList) {
        if (myCarList.success) {
            if (myCarList.data != null && myCarList.data.size() > 0) {
                initViewpager(myCarList.data);
                for (int i=0;i<myCarList.data.size();i++){
                    if (myCarList.data.get(i).bind){//是否 绑定了车辆
                        isBind=true;
                        addMarkersToMap(myCarList.data.get(i).endLat,myCarList.data.get(i).endLongitude,myCarList.data.get(i).endLocation);
                        mCarId=myCarList.data.get(i).carId;
                    }
                }
                if (!isBind){//没有绑定
                    addMarkersToMap(myCarList.data.get(0).endLat,myCarList.data.get(0).endLongitude,myCarList.data.get(0).endLocation);
                    mViewPager.setCurrentItem(0);
                    mCarId=myCarList.data.get(0).carId;
                }
            }
        }
    }

    //选择绑定车辆
    @Override
    public void onChooseV(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            EventBus.getDefault().post(new EventMessage(Constant.USER_CHANGE_CAR,""));
            ToastUtils.showTextShort("切换绑定成功");
        }
    }

    /**
     * * 初始化AMap类对象 aMap 地图控制器
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();//地图控制器对象
            mUiSettings = aMap.getUiSettings();
        }
        //设置logo位置
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);//高德地图标志的摆放位置
        mUiSettings.setZoomControlsEnabled(true);//地图缩放控件是否可见
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//地图缩放控件的摆放位置
        //aMap  为地图控制器对象
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//地图的定位标志是否可见
        aMap.setMyLocationEnabled(true);//地图定位标志是否可以点击
//        setUpMap();
//        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(CameraPosition cameraPosition) {
//                ToastUtils.i("onCameraChange+++" + cameraPosition.target, "" + cameraPosition.zoom);
//            }
//
//            @Override
//            public void onCameraChangeFinish(CameraPosition cameraPosition) {
////                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), cameraPosition.zoom));
//                ToastUtils.i("onCameraChangeFinish+++" + cameraPosition.target + "____" + cameraPosition.toString(),
//                        ">>>>>>>" + cameraPosition.describeContents() + "---________" + cameraPosition.isAbroad + "+++++" + cameraPosition.zoom);
//            }
//        });


//        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                if (infoWindow == null) {
//                    ToastUtils.i("弹窗_______________________________________________  ", "");
//                    infoWindow = LayoutInflater.from(MyCarActivity.this).inflate(
//                            R.layout.pop_map_infowindow, null);
//                }
//                render(marker, infoWindow);
//                return infoWindow;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });
    }


    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(double l1, double l2,String title) {
//        latLngs.add(mEndPoint);
        LatLng mEndPoint = new LatLng(l1, l2);
        aMap.clear();
//        for (int i=0;i<latLng.size();i++){
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
//                    .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.gps_point)))
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))//HUE_GREEN  HUE_AZURE
                .position(mEndPoint)
                .title("地址")
                .snippet(title)
                .draggable(true);
        Marker marker = aMap.addMarker(markerOption);
        // 设置显示的焦点，即当前地图显示为当前位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mEndPoint, 12));
        marker.showInfoWindow();
//        }

    }

    /**
     * 自定义infowinfow窗口
     */
//    public void render(Marker marker, View view) {
//        //如果想修改自定义Infow中内容，请通过view找到它并修改
//        String title = marker.getTitle();
//        ToastUtils.i("title  ", "" + title);
//        TextView titleUi = ((TextView) view.findViewById(R.id.pop_infowin_title));
//        titleUi.setText(title);
//        String snippet = marker.getSnippet();
//        TextView snippetUi = ((TextView) view.findViewById(R.id.pop_infowin_content));
//        snippetUi.setText(snippet);
//    }

    /**
     * 配置定位参数
     */

/*    private void setUpMap() {

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
                    ToastUtils.e("getLocationType", "" + amapLocation.getLocationType());
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();

                    ToastUtils.e("getAccuracy", "" + amapLocation.getAccuracy() + " 米");//获取精度信息
                    ToastUtils.e("joe", "lat :-- " + lat + " lon :--" + lon);
                    ToastUtils.e("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                    //清空缓存位置
                    aMap.clear();


                    // 设置显示的焦点，即当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 18));
                    //aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));


                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("我的位置");
                    markerOptions.visible(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
//                            .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.gps_point));
                    markerOptions.icon(bitmapDescriptor);
                    markerOptions.draggable(true);
                    Marker marker = aMap.addMarker(markerOptions);
//                    mapView.offsetTopAndBottom();
                    marker.showInfoWindow();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    ToastUtils.e("joe", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };*/
    @Override
    protected MyCarPresenter createPresenter() {
        return new MyCarPresenter(this);
    }

    /**
     * 重新绘制加载地图
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 暂停地图的绘制
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 保存地图当前的状态方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        ToastUtils.i("onSaveInstanceState ",outState+"___"+mapView);
    }

    /**
     * 销毁地图
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.i("onDestroy ",""+mapView);
        if (mapView != null) {
            mapView.onDestroy();
        }
//        if (mLocationClient != null) {
//            mLocationClient.stopLocation();
//            mLocationClient.onDestroy();
//        }
//        mLocationClient = null;
    }


    /**
     * 检测GPS、位置权限是否开启
     */
//    public void showGPSContacts() {
//        if (ContextCompat.checkSelfPermission(MyCarActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
//            //开启定位权限,200是标识码
//            ActivityCompat.requestPermissions(MyCarActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
//        } else {//已开启定位权限
//            init();//开始定位
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 200://刚才的识别码
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
//                    init();//开始定位
//                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
//                    Toast.makeText(MyCarActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
//                }
//                break;
//            default:
//                break;
//        }
//
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
        ToastUtils.e("MyCarActivity____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_UPDATE_CAR://完善车辆信息
                onMyCarList();
                break;
        }
    }

}
