package com.ecarxclub.app.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.home.DrivingDetailsRes;
import com.ecarxclub.app.presenter.home.DrivingDetailsPresenter;
import com.ecarxclub.app.presenter.home.DrivingDetailsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.map_gaode.DrivingRouteOverLay;
import com.ecarxclub.app.views.ImaginaryLineView;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.jakewharton.rxbinding.view.RxView;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/6 15:23.
 * 行驶记录  详情
 */
public class DrivingDetailsActivity extends BaseActivityP<DrivingDetailsPresenter> implements DrivingDetailsView,
        AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

//    @BindView(R.id.gd_driving_map)
    private MapView mapView;
    @BindView(R.id.tv_dd_starttime)
    TextView tvStartTime;
    @BindView(R.id.tv_dd_endtime)
    TextView tvEndTime;
    @BindView(R.id.tv_dd_startaddress)
    TextView tvStartAddress;
    @BindView(R.id.tv_dd_startarea)
    TextView tvStartArea;
    @BindView(R.id.tv_dd_endaddress)
    TextView tvEndAddress;
    @BindView(R.id.tv_dd_endarea)
    TextView tvEndArea;
    @BindView(R.id.tv_dd_startlength)
    TextView tvStartLength;
    @BindView(R.id.tv_dd_endlength)
    TextView tvEndLength;
    @BindView(R.id.tv_dd_oil)
    TextView tvOil;
    @BindView(R.id.tv_dd_speed)
    TextView tvSpeed;
    @BindView(R.id.tv_dd_alllength)
    TextView tvAllLenght;
    @BindView(R.id.ilv_dott)
    ImaginaryLineView imaginaryLineView;

    private AMap aMap;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    //    private LatLonPoint mStartPoint = new LatLonPoint(30.2937384, 120.2083779);//起点，116.335891,39.942295   E120.2083779, N30.2937384
//    private LatLonPoint mEndPoint = new LatLonPoint(30.206811, 120.221008);
    private LatLonPoint mStartPoint;//起点，116.335891,39.942295   E120.2083779, N30.2937384
    private LatLonPoint mEndPoint;
    private final int ROUTE_TYPE_DRIVE = 2;

    //    private RelativeLayout mBottomLayout, mHeadLayout;
//    private TextView mRotueTimeDes, mRouteDetailDes;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private String drvingId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_driving_details;//activity_driving_details
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        tvToolbarTitle.setText("行驶记录");
        mContext = this.getApplicationContext();
        mapView= (MapView) findViewById(R.id.gd_driving_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        imaginaryLineView.setLineAttribute(getResources().getColor(R.color.colorBottomLine), 4, new float[]{4, 4, 4, 4});
        init();
    }

    @Override
    public void initData() {
        drvingId = getIntent().getExtras().getString("drvId");
        presenter.onGetCarDetails(drvingId);
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(DrivingDetailsActivity.this);
        aMap.setOnMarkerClickListener(DrivingDetailsActivity.this);
        aMap.setOnInfoWindowClickListener(DrivingDetailsActivity.this);
        aMap.setInfoWindowAdapter(DrivingDetailsActivity.this);

    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        registerListener();
        mRouteSearch = new RouteSearch(DrivingDetailsActivity.this);
        mRouteSearch.setRouteSearchListener(this);
//        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
//        mHeadLayout = (RelativeLayout)findViewById(R.id.routemap_header);
//        mRotueTimeDes = (TextView) findViewById(R.id.firstline);
//        mRouteDetailDes = (TextView) findViewById(R.id.secondline);
//        mHeadLayout.setVisibility(View.GONE);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            showtoast("定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            showtoast("终点未设置");
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(DrivingDetailsActivity.this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        aMap.addMarker(new MarkerOptions()
                .position(convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }


    @Override
    public void onGetDrivingDetails(DrivingDetailsRes baseMsgRes) {
        if (baseMsgRes.success) {
            tvStartTime.setText(baseMsgRes.data.startTime);
            tvEndTime.setText(baseMsgRes.data.endTime);
            tvStartAddress.setText(baseMsgRes.data.startLocation);
            tvStartArea.setText(baseMsgRes.data.startAddress);
            tvEndAddress.setText(baseMsgRes.data.endLocation);
            tvEndArea.setText(baseMsgRes.data.endAddress);

            tvStartLength.setText(baseMsgRes.data.startMileage + "km");
            tvEndLength.setText(baseMsgRes.data.endMileage + "km");
            tvOil.setText(baseMsgRes.data.oilConsumption+"L");
            tvSpeed.setText(baseMsgRes.data.averageSpeed + "km/h");
            tvAllLenght.setText(baseMsgRes.data.distance + "km");
            mStartPoint = new LatLonPoint(baseMsgRes.data.startLat, baseMsgRes.data.startLongitude);//起点，116.335891,39.942295   E120.2083779, N30.2937384
            mEndPoint = new LatLonPoint(baseMsgRes.data.endLat, baseMsgRes.data.endLongitude);
            setfromandtoMarker();
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
        }
    }

    @Override
    protected DrivingDetailsPresenter createPresenter() {
        return new DrivingDetailsPresenter(this);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            mContext, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
//                    mBottomLayout.setVisibility(View.VISIBLE);
//                    int dis = (int) drivePath.getDistance();
//                    int dur = (int) drivePath.getDuration();
//                    String des = getFriendlyTime(dur)+"("+getFriendlyLength(dis)+")";
//                    mRotueTimeDes.setText(des);
//                    mRouteDetailDes.setVisibility(View.VISIBLE);
//                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
//                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
//                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ToastUtils.e("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv","");
////                            Intent intent = new Intent(mContext,
////                                    DriveRouteDetailActivity.class);
////                            intent.putExtra("drive_path", drivePath);
////                            intent.putExtra("drive_result",
////                                    mDriveRouteResult);
////                            startActivity(intent);
//                        }
//                    });
                } else if (result != null && result.getPaths() == null) {
                    ToastUtils.showTextShort("对不起，没有搜索到相关数据");
                }

            } else {
                ToastUtils.showTextShort("对不起，没有搜索到相关数据");
            }
        } else {
            ToastUtils.showTextShort("" + errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


//    public String getFriendlyTime(int second) {
//        if (second > 3600) {
//            int hour = second / 3600;
//            int miniate = (second % 3600) / 60;
//            return hour + "小时" + miniate + "分钟";
//        }
//        if (second >= 60) {
//            int miniate = second / 60;
//            return miniate + "分钟";
//        }
//        return second + "秒";
//    }
//    public static String getFriendlyLength(int lenMeter) {
//        if (lenMeter > 10000) // 10 km
//        {
//            int dis = lenMeter / 1000;
//            return dis + ChString.Kilometer;
//        }
//        if (lenMeter > 1000) {
//            float dis = (float) lenMeter / 1000;
//            DecimalFormat fnum = new DecimalFormat("##0.0");
//            String dstr = fnum.format(dis);
//            return dstr + ChString.Kilometer;
//        }
//        if (lenMeter > 100) {
//            int dis = lenMeter / 50 * 50;
//            return dis + ChString.Meter;
//        }
//        int dis = lenMeter / 10 * 10;
//        if (dis == 0) {
//            dis = 10;
//        }
//        return dis + ChString.Meter;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.i("onDestroy ",aMap+"________"+mapView);
        if (mapView!=null){
            mapView.onDestroy();
        }
//        if (aMap!=null){
//            aMap.clear();
//        }
        mStartPoint=null;
        mEndPoint=null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView!=null){
            mapView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView!=null){
            mapView.onResume();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView!=null){
            mapView.onSaveInstanceState(outState);
        }

    }
}
