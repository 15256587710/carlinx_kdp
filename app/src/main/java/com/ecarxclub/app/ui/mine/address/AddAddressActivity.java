package com.ecarxclub.app.ui.mine.address;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.presenter.mine.address.AddAddressPresenter;
import com.ecarxclub.app.presenter.mine.address.AddAddressView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.picker.model.Province;
import com.ecarxclub.app.utils.picker.widget.AddressPickTask;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/23 15:54.
 * 添加地址
 */
public class AddAddressActivity extends BaseActivityP<AddAddressPresenter> implements AddAddressView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_mine_add_sure)
    Button btnAdd;

    @BindView(R.id.et_mine_add_name)
    EditText etAddressName;
    @BindView(R.id.et_mine_add_phonenum)
    EditText etAddressPhone;
    @BindView(R.id.et_mine_add_address)
    EditText etAddressInfo;
    @BindView(R.id.ll_mine_address)
    LinearLayout llChooseAddress;
    @BindView(R.id.tv_mine_address_city)
    TextView tvAddressCity;
    private String mStrPhone, mStrName, mStrInfo,mStrProvince,mStrCity,mStrArea="";


    private String deal = "";

    private MyAddressRes.DataBean.ListBean listBean;

    private String mStutas="";//  1兑换详情添加地址进入

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_add_address;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
    }

    @Override
    public void initData() {
        if(getIntent().getExtras()!=null){
            listBean= (MyAddressRes.DataBean.ListBean) getIntent().getExtras().getSerializable("editAddress");
            mStutas=getIntent().getExtras().getString("status");
            if(listBean!=null){
                tvAddressCity.setText(listBean.province+listBean.city+listBean.region);
                etAddressName.setText(listBean.addrUserName);
                etAddressPhone.setText(listBean.addrUserMobile);
                etAddressInfo.setText(listBean.address);
                mStrProvince=listBean.province;
                mStrCity=listBean.city;
                mStrArea=listBean.region;
            }
            tvToolbarTitle.setText("编辑地址");
        }else {
            tvToolbarTitle.setText("添加地址");
        }
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        RxView.clicks(btnAdd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrName = etAddressName.getText().toString();
                mStrPhone = etAddressPhone.getText().toString();
                mStrInfo = etAddressInfo.getText().toString();
                if (mStrName != null && mStrName.length() > 0) {
                    if (CommonUtils.isPhoneNumber(mStrPhone)) {
                        if (mStrInfo != null && mStrInfo.length() > 0) {
                            if(mStrProvince!=null&&mStrProvince.length()>0){
                                onAddressUp();
                            }else {
                                showtoast("请选择所在城市");
                            }
                        } else {
                            showtoast("请输入地址信息");
                        }
                    } else {
                        showtoast("请输入手机号码");
                    }
                } else {
                    showtoast("请输入收货人姓名");
                }
            }
        });

        RxView.clicks(llChooseAddress).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setAddressPick();
            }
        });
    }

    public void onAddressUp(){
        Map<String, String> map = new HashMap<>();
        map.put("addrUserName", mStrName);
        map.put("addrUserMobile", mStrPhone);
        map.put("province", mStrProvince);
        map.put("city", mStrCity);
        map.put("region", mStrArea);
        map.put("address", mStrInfo);
        if(listBean!=null){//编辑
            map.put("id",listBean.id);
            presenter.onEditAddress(map);
        }else {
            presenter.onAddAddress(map);
        }
    }

    //添加地址
    @Override
    public void onAddResult(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            if("1".equals(mStutas)){//兑换详情  添加地址
                EventBus.getDefault().post(new EventMessage(Constant.EXCHANGE_DETAILS_ADDRESS_ADD, Constant.MINE_ADD_ADDRESS_CONTENT));
            }else {
                EventBus.getDefault().post(new EventMessage(Constant.MINE_ADD_ADDRESS, Constant.MINE_ADD_ADDRESS_CONTENT));
            }
            finish();
        }
    }
//    编辑地址
    @Override
    public void onUpdateResult(BaseMsgRes baseMsgRes) {
        ToastUtils.i("编辑地址","");
        if (baseMsgRes.isSuccess()) {
            finish();
            EventBus.getDefault().post(new EventMessage(Constant.MINE_ADD_ADDRESS, Constant.MINE_ADD_ADDRESS_CONTENT));
        }
    }

    /**
     * 地址选择器
     */
    private void setAddressPick() {
        AddressPickTask task = new AddressPickTask(AddAddressActivity.this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
            }
            @Override
            public void onAddressPicked(Province province, Province.City city, Province.City.County county) {
                mStrProvince=province.getRegion_name();
                mStrCity=city.getRegion_name();
                if (county == null) {
                    tvAddressCity.setText(province.getRegion_name() + city.getRegion_name());
//                    addr_region_id = city.region_id;//地区ID
                } else {
                    mStrArea=county.getRegion_name();
                    tvAddressCity.setText(province.getRegion_name() + " " + city.getRegion_name() + " " + county.getRegion_name());
                    if (county.getRegion_id() == null) {
//                        addr_region_id = "-1";
                    } else {
//                        addr_region_id = String.valueOf(county.getRegion_id());
                    }
                }
            }
        });

        if (listBean!=null) {
//            if (mGetAddressInfoRes.body().data.addr_region_name.split(" ").length == 1) {
//                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0]);
//            } else if (mGetAddressInfoRes.body().data.addr_region_name.split(" ").length == 2) {
//                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[1]);
//            } else {
//                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[1], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[2]);
//            }
            task.execute(listBean.province,listBean.city,listBean.region);
        } else {
            task.execute("浙江", "杭州", "滨江");
        }
    }


    @Override
    protected AddAddressPresenter createPresenter() {
        return new AddAddressPresenter(this);
    }


}
