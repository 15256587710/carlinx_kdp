package com.ecarxclub.app.ui.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.car.CarInfoRes;
import com.ecarxclub.app.model.home.car.CarStyleRes;
import com.ecarxclub.app.presenter.home.ImprovingCarPresenter;
import com.ecarxclub.app.presenter.home.ImprovingCarView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.mine.EditUserInfoActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.TimeUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.BottomAlertDialog;
import com.ecarxclub.app.utils.picker.DatePicker;
import com.ecarxclub.app.utils.picker.adapter.ArrayWheelAdapter;
import com.ecarxclub.app.utils.picker.common.LineConfig;
import com.ecarxclub.app.utils.picker.widget.WheelView;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/22 13:49.
 * 完善车辆信息
 */
public class ImprovingCarActivity extends BaseActivityP<ImprovingCarPresenter> implements ImprovingCarView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.ll_impcar_color)
    LinearLayout llColor;
    @BindView(R.id.tv_impcar_color)
    TextView tvColor;
    @BindView(R.id.tv_impcar_carprovince)
    TextView tvProvince;
    @BindView(R.id.et_impcar_carnum)
    EditText etCarNum;//车牌号码
    @BindView(R.id.et_impcar_enginenum)
    EditText etCarEngineNum;//发动机号码
    @BindView(R.id.et_impcar_vinnum)
    EditText etCarVinNum;//车架号码

    @BindView(R.id.ll_impcar_choosecar)
    LinearLayout llChooseCar;//车型
    @BindView(R.id.tv_impcar_car)
    TextView tvCarStyle;
    @BindView(R.id.ll_impcar_time)
    LinearLayout llBuyTime;
    @BindView(R.id.tv_impcar_time)
    TextView tvBuyTime;

    RecyclerViewAdapter recyclerViewAdapter;
    private List<String> listColor = new ArrayList<>();//车辆颜色
    private List<String> listProvince = new ArrayList<>();//省
    String str[] = {"京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖"
            , "鲁", "新", "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙"
            , "陕", "吉", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼"};
    private String mCarId,mCarStyleId;//车辆id   mCarStyleId 车型id
    private String mCarStyle, mCarColor, mCarNum, mCarEngine, mCarVinNum, mCarTime;
    private List<String> listStyle = new ArrayList<>();//车型
    private List<CarStyleRes.DataBean> dataBeanStyle;//车型
    private int carStyleIndex = -1, carColorIndex = -1;
    private String[] mStrBirthday;
    private CarInfoRes.DataBean mCarinfoRes;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_improving_car;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("完善车辆信息");
        tvToolbarRight.setText("保存");
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            mCarId = getIntent().getExtras().getString("carid");
            presenter.getCarInfos(mCarId);
        }
        for (int i = 0; i < str.length; i++) {
            listProvince.add(str[i]);
        }
        presenter.getCarStyle();
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //购买时间
        RxView.clicks(llBuyTime).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getTimes();
            }
        });

        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mCarStyle = tvCarStyle.getText().toString();
                mCarNum = etCarNum.getText().toString();
                Map<String, String> map = new HashMap<>();
                map.put("id", mCarId);
                if (mCarinfoRes.carStyleOrigin!=1&&mCarStyle.length()>0 && !"未选择".equals(mCarStyle)) {
                    map.put("carStyle", mCarStyle);
                    map.put("carStyleCode", carStyleIndex==-1?mCarStyleId:dataBeanStyle.get(carStyleIndex).id);
                }
                mCarColor = tvColor.getText().toString();
                if (mCarinfoRes.carColorOrigin!=1&&mCarColor.length()>0  && !"未选择".equals(mCarColor)) {
                    map.put("carColor", mCarColor);
                }
                if (mCarinfoRes.licenseOrigin!=1&&mCarNum.length() > 0 && !"未获取车牌".equals(mCarNum) && tvProvince.getText().toString().length() > 0) {
                    map.put("license", tvProvince.getText().toString() + mCarNum);
                }
                mCarEngine = etCarEngineNum.getText().toString();
                if (mCarinfoRes.engineSerialNoOrigin!=1&&mCarEngine.length() > 0 && !"后六位".equals(mCarEngine)) {
                    map.put("engineSerialNo", mCarEngine);
                }
                mCarVinNum = etCarVinNum.getText().toString();
                if (mCarinfoRes.vinOrigin!=1&&mCarVinNum.length() > 0 && !"后六位".equals(mCarVinNum)) {
                    map.put("vin", mCarVinNum);
                }
                mCarTime = tvBuyTime.getText().toString();
                if (mCarinfoRes.buyTimeOrigin!=1&&mCarTime != null && !"未选择".equals(mCarTime)) {
                    map.put("buyTime", mCarTime);
                }
                presenter.updateCarInfo(map);
            }
        });
        //车牌号
        RxView.clicks(tvProvince).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                View view = LayoutInflater.from(ImprovingCarActivity.this).inflate(R.layout.dialog_province, null);
                final BottomAlertDialog bottomAlertDialog = new BottomAlertDialog(ImprovingCarActivity.this, view, true, true);
                bottomAlertDialog.show();
                RecyclerView recyclerView = view.findViewById(R.id.dialog_rcv);
                recyclerView.setLayoutManager(new GridLayoutManager(ImprovingCarActivity.this, 9));
                recyclerViewAdapter = new RecyclerViewAdapter<String>(ImprovingCarActivity.this,
                        listProvince, R.layout.item_text) {
                    @Override
                    public void convert(RecyclerViewHolder holder, final String item, int pos) {
                        TextView tvTitle = holder.getView(R.id.item_tv_province);
                        if (item.equals(tvProvince.getText().toString())) {
                            tvTitle.setTextColor(getResources().getColor(R.color.color_tab_line));
                        } else {
                            tvTitle.setTextColor(getResources().getColor(R.color.tab_y));
                        }
                        tvTitle.setText(item);
                    }
                };
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
                    @Override
                    public void OnRecycleItemClick(View view, int position) {
                        tvProvince.setText(listProvince.get(position));
                        bottomAlertDialog.dismiss();
                    }

                    @Override
                    public void onRecycleItemLongClick(View view, int position) {

                    }
                });
            }
        });

        //车型
        RxView.clicks(llChooseCar).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                View view = LayoutInflater.from(ImprovingCarActivity.this).inflate(R.layout.dialog_wheelview, null);
                final BottomAlertDialog bottomAlertDialog = new BottomAlertDialog(ImprovingCarActivity.this, view, true, true);
                bottomAlertDialog.show();
                bottomAlertDialog.setCancelable(false);
                final WheelView wheelView = view.findViewById(R.id.dialog_wheel);
                wheelView.setLayoutParams(new LinearLayout.LayoutParams(300, 400));
                wheelView.setTextSize(16);
                wheelView.setSelectedTextColor(0XFF1F40C1);
                wheelView.setUnSelectedTextColor(0XFFFF5900);
                wheelView.setLineConfig(new LineConfig());
                wheelView.setCanLoop(false);
                wheelView.setAdapter(new ArrayWheelAdapter<>(listStyle));
                wheelView.setCurrentItem(carStyleIndex == -1 ? 2 : carStyleIndex);
                TextView tvCancel = view.findViewById(R.id.dialog_wheel_cancel);
                TextView tvSure = view.findViewById(R.id.dialog_wheel_sure);
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wheelView.onItemPicked();
                        carStyleIndex = wheelView.getCurrentPosition();
                        tvCarStyle.setText(wheelView.getCurrentItem());
                        tvCarStyle.setTextColor(getResources().getColor(R.color.tab_y));
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
        });

        //颜色
        RxView.clicks(llColor).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (carStyleIndex == -1) {
                    if (mCarStyleId!=null&&mCarStyleId.length()>0){
                        presenter.getCarColor(mCarStyleId);
                    }else {
                        showtoast("请选择车辆车型");
                    }
                } else {
                    presenter.getCarColor(dataBeanStyle.get(carStyleIndex).id);
                }
            }
        });
    }

    @Override
    protected ImprovingCarPresenter createPresenter() {
        return new ImprovingCarPresenter(this);
    }


    @Override
    public void onCarInfo(CarInfoRes carInfoRes) {
        if (carInfoRes.success) {
            mCarinfoRes=carInfoRes.data;
            mCarStyleId=carInfoRes.data.carStyleCode;
            if (carInfoRes.data.buyTime != null) {//购买日期
                tvBuyTime.setText(carInfoRes.data.buyTime);
                tvBuyTime.setTextColor(getResources().getColor(R.color.tab_y));
                mStrBirthday = carInfoRes.data.buyTime.split("-");
            } else {
                tvBuyTime.setText("未选择");
                tvBuyTime.setTextColor(getResources().getColor(R.color.color_c1));
            }
            //是否可更改
            if (mCarinfoRes.buyTimeOrigin==1){
                llBuyTime.setEnabled(false);
            }else {
                llBuyTime.setEnabled(true);
            }

            if (carInfoRes.data.vin != null) {//车架号
                etCarVinNum.setText(carInfoRes.data.vin);
            }
            //是否可更改
            if (mCarinfoRes.vinOrigin==1){
                etCarVinNum.setEnabled(false);
            }else {
                etCarVinNum.setEnabled(true);
            }

            if (carInfoRes.data.engineSerialNo != null) {//发动机号码
                etCarEngineNum.setText(carInfoRes.data.engineSerialNo);
            }
            //是否可更改
            if (mCarinfoRes.engineSerialNoOrigin==1){
                etCarEngineNum.setEnabled(false);
            }else {
                etCarEngineNum.setEnabled(true);
            }

            if (carInfoRes.data.license != null) {//车牌号
                tvProvince.setText(carInfoRes.data.license.substring(0, 1));
                etCarNum.setText(carInfoRes.data.license.substring(1, carInfoRes.data.license.length()));
            }
            //是否可更改
            if (mCarinfoRes.licenseOrigin==1){
                tvProvince.setEnabled(false);
                etCarNum.setEnabled(false);
            }else {
                tvProvince.setEnabled(true);
                etCarNum.setEnabled(true);
            }

            if (carInfoRes.data.carStyle != null) {//车型
                tvCarStyle.setText(carInfoRes.data.carStyle);
                tvCarStyle.setTextColor(getResources().getColor(R.color.tab_y));
            } else {
                tvCarStyle.setText("未选择");
                tvCarStyle.setTextColor(getResources().getColor(R.color.color_c1));
            }
            //是否可更改
            if (mCarinfoRes.carStyleOrigin==1){
                llChooseCar.setEnabled(false);
            }else {
                llChooseCar.setEnabled(true);
            }

            if (carInfoRes.data.carColor != null) {//颜色
                tvColor.setText(carInfoRes.data.carColor);
                tvColor.setTextColor(getResources().getColor(R.color.tab_y));
            } else {
                tvColor.setText("未选择");
                tvColor.setTextColor(getResources().getColor(R.color.color_c1));
            }
            //是否可更改
            if (mCarinfoRes.carColorOrigin==1){
                llColor.setEnabled(false);
            }else {
                llColor.setEnabled(true);
            }
        }
    }

    @Override
    public void onUpdateInfo(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            showtoast("修改成功");
            EventBus.getDefault().post(new EventMessage(Constant.USER_UPDATE_CAR, ""));
        }
    }

    //车辆车型
    @Override
    public void onCarStyle(CarStyleRes carStyleRes) {
        if (carStyleRes.success && carStyleRes.data.size() > 0) {
            dataBeanStyle = carStyleRes.data;
            for (int i = 0; i < carStyleRes.data.size(); i++) {
                listStyle.add(carStyleRes.data.get(i).styleName);
            }
        }
    }

    //车辆颜色
    @Override
    public void onCarColor(CarStyleRes carStyleRes) {
        if (carStyleRes.success) {
            if (carStyleRes.data != null && carStyleRes.data.size() > 0) {
                listColor.clear();
                for (int i = 0; i < carStyleRes.data.size(); i++) {
                    listColor.add(carStyleRes.data.get(i).styleColor);
                }
                View view = LayoutInflater.from(ImprovingCarActivity.this).inflate(R.layout.dialog_wheelview, null);
                final BottomAlertDialog bottomAlertDialog = new BottomAlertDialog(ImprovingCarActivity.this, view, true, true);
                bottomAlertDialog.show();
                bottomAlertDialog.setCancelable(false);
                final WheelView wheelView = view.findViewById(R.id.dialog_wheel);
                wheelView.setLayoutParams(new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT));
                wheelView.setTextSize(20);
                wheelView.setSelectedTextColor(0XFF1F40C1);
                wheelView.setUnSelectedTextColor(0XFFFF5900);
                wheelView.setLineConfig(new LineConfig());
                wheelView.setCanLoop(false);
                wheelView.setAdapter(new ArrayWheelAdapter<>(listColor));
                wheelView.setCurrentItem(carColorIndex == -1 ? 2 : carColorIndex);
                TextView tvCancel = view.findViewById(R.id.dialog_wheel_cancel);
                TextView tvSure = view.findViewById(R.id.dialog_wheel_sure);
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wheelView.onItemPicked();
                        carColorIndex = wheelView.getCurrentPosition();
                        ToastUtils.i("onClick " + wheelView.getCurrentPosition(), "_______" + wheelView.getCurrentItem());
                        tvColor.setText(wheelView.getCurrentItem());
                        tvColor.setTextColor(getResources().getColor(R.color.tab_y));
                        bottomAlertDialog.dismiss();
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomAlertDialog.dismiss();
                    }
                });
            } else {
                showtoast("暂无该车型颜色");
            }

        }
    }

    //时间
    private void getTimes() {
        final DatePicker picker = new DatePicker(ImprovingCarActivity.this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setTopHeight(50);
        picker.setRangeStart(1920, 1, 1);
        picker.setRangeEnd(Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[0]), Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[1]), Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[2]));
        picker.setSelectedItem(mStrBirthday!=null&&mStrBirthday.length == 3 ? Integer.parseInt(mStrBirthday[0]) : 2019,
                mStrBirthday!=null&&mStrBirthday.length == 3 ? Integer.parseInt(mStrBirthday[1]) : 01,
                mStrBirthday!=null&&mStrBirthday.length == 3 ? Integer.parseInt(mStrBirthday[2]) : 01);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvBuyTime.setText(year + "-" + month + "-" + day);
                tvBuyTime.setTextColor(getResources().getColor(R.color.tab_y));
//                presenter.changeBirthday(tvBirthday.getText().toString());
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
}
