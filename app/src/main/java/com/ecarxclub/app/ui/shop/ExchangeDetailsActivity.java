package com.ecarxclub.app.ui.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.model.pay.PayOrderRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;
import com.ecarxclub.app.model.shop.ShopDetailsRes;
import com.ecarxclub.app.presenter.shop.ExchangeDetailsPresenter;
import com.ecarxclub.app.presenter.shop.ExchangeDetailsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.mine.address.AddAddressActivity;
import com.ecarxclub.app.ui.mine.address.MyAddressActivity;
import com.ecarxclub.app.ui.pay.PaySuccessResultActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.pay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.unionpay.UPQuerySEPayInfoCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

import static com.ecarxclub.app.common.YxbApplication.WX_ID;

/**
 * Created by cwp
 * on 2019/4/29 16:53.
 * 兑换详情
 */
@BindEventBus
public class ExchangeDetailsActivity extends BaseActivityP<ExchangeDetailsPresenter> implements ExchangeDetailsView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.cb_exd_zfb)
    CheckBox cbZfb;
    @BindView(R.id.cb_exd_wx)
    CheckBox cbWx;
    @BindView(R.id.cb_exd_yl)
    CheckBox cbYl;
    //地址
    @BindView(R.id.item_tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.item_tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.item_tv_address_info)
    TextView tvAddressContent;
    //商品
    @BindView(R.id.img_ex_details)
    ImageView imgShop;
    @BindView(R.id.tv_exd_name)
    TextView tvShopName;
    @BindView(R.id.tv_exd_type)
    TextView tvShopType;
    @BindView(R.id.tv_exd_tag)
    TextView tvShopTag;
    @BindView(R.id.tv_exd_info)
    TextView tvShopInfo;
    @BindView(R.id.tv_exd_score)
    TextView tvShopScore;
    @BindView(R.id.tv_exd_money)
    TextView tvShopMoney;
    @BindView(R.id.tv_exd_user_score)
    TextView tvUserScore;
    @BindView(R.id.ll_exd_address)
    LinearLayout llAddress;
    @BindView(R.id.btn_exd_pay)
    Button btnPay;

    @BindView(R.id.et_exd_num)
    EditText etNum;
    @BindView(R.id.tv_exd_noaddress)
    TextView tvNoAddress;

    @BindView(R.id.img_pay_phone)
    ImageView imgPhonePay;
    @BindView(R.id.tv_pay_phone)
    TextView tvPhonePay;
    @BindView(R.id.cb_exd_phone)
    CheckBox cbPhone;
    @BindView(R.id.ll_include_pay)
    LinearLayout llPayType;

    @BindView(R.id.ll_pay_phone)
    LinearLayout llPhonePay;
    @BindView(R.id.ll_pay_yl)
    LinearLayout llYl;
    @BindView(R.id.ll_pay_ailpy)
    LinearLayout llAliay;
    @BindView(R.id.ll_pay_wechat)
    LinearLayout llWechat;
    @BindView(R.id.tv_exd_sendfree)
    TextView tvSendFree;
    @BindView(R.id.tv_exd_reduce)
    TextView tvReduce;
    @BindView(R.id.tv_exd_add)
    TextView tvAdd;

    private int mShopNum;//商品数量
    private String mDefaultAddressId;//默认地址id
    private ShopDetailsRes.DataBean listBean;//商品数据
    private MyAddressRes.DataBean.ListBean listAddress;//地址
    public static String mPayMoney, mPayScroe;//支付金额，积分

    private boolean isAdd = false;//是否是添加地址过来 true是
    private static final int SDK_PAY_FLAG = 1;//支付宝支付
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private String mMode = "00", mStype = "";//mStype 手机pay支付
    private int mUserScore;//用户总积分

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange_details;
    }

    @Override
    public void initView() {
        tvToolbarTitle.setText("兑换详情");
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        checkYinlianState();
    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            listBean = (ShopDetailsRes.DataBean) getIntent().getExtras().getSerializable("shopdetails");
            tvShopName.setText(listBean.productName);
            tvShopInfo.setText(listBean.integral + "积分+" + listBean.money + "元");
            tvShopScore.setText(listBean.integral + "积分");
            tvShopMoney.setText(listBean.money + "元");
            if (listBean.sendFee > 0) {
                tvSendFree.setText("邮费：" + listBean.sendFee + "元");
            } else {
                tvSendFree.setText("免邮");
            }
            Glide.with(ExchangeDetailsActivity.this).load(listBean.productIcon).
                    apply(YxbContent.options).into(imgShop);
            if (listBean.tags != null && listBean.tags.size() > 0) {
                if (listBean.tags.size() == 1) {
                    tvShopType.setText(listBean.tags.get(0).tag.tagName);
                    tvShopTag.setVisibility(View.GONE);
                } else if (listBean.tags.size() == 2) {
                    tvShopTag.setVisibility(View.VISIBLE);
                    tvShopTag.setText(listBean.tags.get(1).tag.tagName);
                }
            } else {
                tvShopType.setVisibility(View.GONE);
                tvShopTag.setVisibility(View.GONE);
            }
            if (listBean.money > 0) {
                llPayType.setVisibility(View.VISIBLE);
            } else {
                llPayType.setVisibility(View.GONE);
            }
        }
        if (YxbApplication.userInfoRes != null && YxbApplication.userInfoRes.data != null) {
            //查看是否有默认地址
            mDefaultAddressId = YxbApplication.userInfoRes.data.mainAddressId;
        }
        onAddressList();
        presenter.getUserScore();
    }

    //地址列表
    public void onAddressList() {
        presenter.getAddressList(true, Constant.GET_LIST_INDEX, Constant.GET_LIST_SIZE);
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //商品加
        RxView.clicks(tvAdd).throttleFirst(Constant.HALF_DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mShopNum = Integer.parseInt(etNum.getText().toString());
                if (listBean != null && mShopNum < listBean.productStock) {
                    mShopNum++;
                    etNum.setText(mShopNum + "");
                    tvShopScore.setText(listBean.integral * mShopNum + "积分");
                    tvShopMoney.setText(listBean.money * mShopNum + "元");
                } else {
                    showtoast("库存不足");
                }
            }
        });
        //商品减
        RxView.clicks(tvReduce).throttleFirst(Constant.HALF_DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mShopNum = Integer.parseInt(etNum.getText().toString());
                if (mShopNum > 1) {
                    mShopNum--;
                    etNum.setText(mShopNum + "");
                    tvShopScore.setText(listBean.integral * mShopNum + "积分");
                    tvShopMoney.setText(listBean.money * mShopNum + "元");
                } else {
                    showtoast("商品数量不足");
                }
            }
        });
        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().equals("0") || s.toString().equals("")) {//0或 “”
                    etNum.setText("1");
                } else if (listBean != null && Integer.parseInt(s.toString()) > listBean.productStock) {//大于库存
                    etNum.setText(listBean.productStock + "");
                }
                mShopNum = Integer.parseInt(etNum.getText().toString());
                etNum.setSelection(etNum.getText().toString().length());
                tvShopScore.setText(listBean.integral * mShopNum + "积分");
                tvShopMoney.setText(listBean.money * mShopNum + "元");
            }
        });

        //支付宝
        RxView.clicks(llAliay).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                cbZfb.setChecked(true);
                if (cbWx.isChecked()) {
                    cbWx.setChecked(false);
                }
                if (cbYl.isChecked()) {
                    cbYl.setChecked(false);
                }
                if (cbPhone.isChecked()) {
                    cbPhone.setChecked(false);
                }
            }
        });
        //微信
        RxView.clicks(llWechat).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                cbWx.setChecked(true);
                if (cbZfb.isChecked()) {
                    cbZfb.setChecked(false);
                }
                if (cbYl.isChecked()) {
                    cbYl.setChecked(false);
                }
                if (cbPhone.isChecked()) {
                    cbPhone.setChecked(false);
                }
            }
        });
        //银联
        RxView.clicks(llYl).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                cbYl.setChecked(true);
                if (cbWx.isChecked()) {
                    cbWx.setChecked(false);
                }
                if (cbZfb.isChecked()) {
                    cbZfb.setChecked(false);
                }
                if (cbPhone.isChecked()) {
                    cbPhone.setChecked(false);
                }
            }
        });
        //手机pay支付
        RxView.clicks(llPhonePay).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                cbPhone.setChecked(true);
                if (cbWx.isChecked()) {
                    cbWx.setChecked(false);
                }
                if (cbZfb.isChecked()) {
                    cbZfb.setChecked(false);
                }
                if (cbYl.isChecked()) {
                    cbYl.setChecked(false);
                }
            }
        });
        //选择地址
        RxView.clicks(llAddress).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "1");
                startIntent(ExchangeDetailsActivity.this, MyAddressActivity.class, bundle);
            }
        });

        //添加地址
        RxView.clicks(tvNoAddress).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "1");
                startIntent(ExchangeDetailsActivity.this, AddAddressActivity.class, bundle);
            }
        });

        //支付
        RxView.clicks(btnPay).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (listAddress != null) {
                    if (listBean != null && listBean.money > 0) {//需要支付
                        if (cbZfb.isChecked() || cbWx.isChecked() || cbYl.isChecked() || cbPhone.isChecked()) {
                            upOrder();
                        } else {
                            showtoast("请选择支付类型");
                        }
                    } else {//积分兑换
                        upOrder();
                    }
                } else {
                    showtoast("请填写收货地址");
                }
            }
        });
    }

    private void upOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", listBean.id);
        map.put("amount", etNum.getText().toString());
        map.put("addressId", listAddress.id);
        presenter.getUpOrder(map);
    }

    @Override
    public void onDataResult(BaseMsgRes baseMsgRes) {

    }

    //获取我的地址列表
    @Override
    public void onMyAddressList(MyAddressRes myAddressRes) {
        if (myAddressRes.success && myAddressRes.data.list != null) {
            if (isAdd) {
                listAddress = myAddressRes.data.list.get(0);
                setAddressTxt();
            } else {
                if (myAddressRes.data.list.size() > 0) {
                    if (mDefaultAddressId != null && mDefaultAddressId.length() > 0) {//有默认地址
                        for (int i = 0; i < myAddressRes.data.list.size(); i++) {
                            if (myAddressRes.data.list.get(i).id.equals(mDefaultAddressId)) {
                                listAddress = myAddressRes.data.list.get(i);
                            }
                        }
                    } else {//没有默认地址  默认选择第一个
                        listAddress = myAddressRes.data.list.get(0);
                    }
                    setAddressTxt();
                } else {//没有地址
                    llAddress.setVisibility(View.GONE);
                    tvNoAddress.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //用户积分
    @Override
    public void onGetUserScore(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            mUserScore = (int) Float.parseFloat(baseMsgRes.getData().toString());
            tvUserScore.setText(mUserScore + "积分");
        }
    }

    //提交订单
    @Override
    public void onUpOrder(PayOrderRes payOrderRes) {
        if (payOrderRes.success) {
            mPayMoney = payOrderRes.data.money + "";
            mPayScroe = payOrderRes.data.integral + "";
            if (listBean != null && listBean.money > 0) {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", payOrderRes.data.orderId);
                if (cbZfb.isChecked()) {
                    map.put("payType", "1");//支付类型 1,支付宝  2,微信
                } else if (cbWx.isChecked()) {
                    map.put("payType", "2");//支付类型 1,支付宝  2,微信
                } else if (cbYl.isChecked() || cbPhone.isChecked()) {
                    ToastUtils.i("yinlian   ", "");
                    map.put("payType", "3");//支付类型 1,支付宝  2,微信 3 银联
                } else {
                    showtoast("请选择支付类型");
                    return;
                }
                presenter.getPayOrderr(map);
            } else {//积分
                gotoPaySuccess();
            }

        }
    }

    //支付
    @Override
    public void onPayOrder(PayOrderSuccessRes payOrderSuccess) {
        if (payOrderSuccess.success) {
            if (cbZfb.isChecked()) {
                onPayAliply(payOrderSuccess.data.payStr);
            } else if (cbWx.isChecked()) {
                onWeiXin(payOrderSuccess);
            } else if (cbYl.isChecked() || cbPhone.isChecked()) {
                if (cbYl.isChecked()) {
                    onYinLian(payOrderSuccess.data.tn, "");
                } else {
                    onYinLian(payOrderSuccess.data.tn, mStype);
                }
            }
        }
    }

    @Override
    protected ExchangeDetailsPresenter createPresenter() {
        return new ExchangeDetailsPresenter(this);
    }

    //银联  手机pay
    private void checkYinlianState() {
        //当判断用户手机上已安装银联Apk，商户客户端可以做相应个性化处理
        if (UPPayAssistEx.checkWalletInstalled(context)) {

        }
        ToastUtils.i("yinlian " + UPPayAssistEx.checkWalletInstalled(context), "");
        //检查手机Pay状态接口调用
        UPQuerySEPayInfoCallback callback = new UPQuerySEPayInfoCallback() {
            @Override
            public void onResult(String SEName, String seType, int cardNumbers, Bundle reserved) {
                // 该方法在手机Pay正常情况下回调
                // SEName —— 手机 pay 名称，如表 1
                // seType —— 与手机 pay 名称对应的类别，如表 1
                // cardNumbers —— 卡数量
                // reserved —— 保留字段
//                SEName seType
//                Samsung Pay 02
//                Huawei Pay 04
//                Meizu Pay 27
//                Le Pay 30
//                ZTE Pay 21
//                Mi Pay 25
//                vivo Pay 33
//                Smartisan Pay 32
                ToastUtils.i("onResult " + SEName, "___" + seType + "====" + cardNumbers);
                mStype = seType;
                if (llPhonePay != null) {
                    llPhonePay.setVisibility(View.VISIBLE);
                    if ("02".equals(seType)) {
                        imgPhonePay.setBackgroundResource(R.mipmap.img_samsung);
                        tvPhonePay.setText(SEName);
                    } else if ("04".equals(seType)) {
                        imgPhonePay.setBackgroundResource(R.mipmap.img_huawei);
                        tvPhonePay.setText(SEName);
                    } else if ("27".equals(seType)) {
                        imgPhonePay.setBackgroundResource(R.mipmap.img_meizu);
                        tvPhonePay.setText(SEName);
                    } else if ("25".equals(seType)) {
                        imgPhonePay.setBackgroundResource(R.mipmap.img_xiaomi);
                        tvPhonePay.setText(SEName);
                    }
                }
            }

            @Override
            public void onError(String SEName, String seType, String errorCode, String errorDesc) {
                // 该方法在手机Pay异常情况下回调  errorCode
                // 1.XXpay 未安装或 TSM 控件版本低  ERROR_NOT_SUPPORT = "01"
                // 2.硬件不支持 XXpay ERROR_NOT _SUPPORT = "01"
                // 3.未开通 XXpay ERROR_NOT_READY = "02"
                // 4.可用卡数为 0  ERROR_NOT_READY = "02"
                // 5.检测未安装 TSM 控件 ERROR_NOT_SUPPORT = "04"
                // 6.接口超时 ERROR_ TIMEOUT = "10"
                mStype = "";
                ToastUtils.i(llPhonePay + " onError    " + SEName, "___" + seType + "====" + errorCode);
                if (llPhonePay != null) {
                    llPhonePay.setVisibility(View.GONE);
                }
            }
        };
        UPPayAssistEx.getSEPayInfo(context, callback);
    }


    private void onYinLian(String tn, String seType) {
        ToastUtils.i("" + seType, "tn ____" + tn);
        if ("".equals(seType)) {
        /* spId —— 保留使用，这里输入null
                sysProvider —— 保留使用，这里输入null
                orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
                mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起
                        交易*/
            UPPayAssistEx.startPay(ExchangeDetailsActivity.this, null, null, tn, mMode);
        } else {
            //        context —— 用于获取启动支付控件的活动对象的context
//        spId —— 保留使用，这里输入null
//        sysProvider —— 保留使用，这里输入null
//        orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
//        mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起 交易
//        seType —— 手机pay支付类别，见表1 返回值： 0
            UPPayAssistEx.startSEPay(ExchangeDetailsActivity.this, null, null, tn, mMode, seType);
        }

    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        showtoast("支付成功");
                        gotoPaySuccess();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.i("支付宝支付成功", payResult + "+++++++" + resultInfo);
                    } else {
                        showtoast("支付失败");
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.e("支付宝支付失败", payResult + "_____" + resultInfo);
                    }
                    break;
            }
        }
    };

    public void gotoPaySuccess() {
        EventBus.getDefault().post(new EventMessage(Constant.USER_SCORE_PAY, ""));
        mUserScore = mUserScore - ((int) Float.parseFloat(mPayScroe));
        tvUserScore.setText(mUserScore + "积分");
        Bundle bundle = new Bundle();
        bundle.putString("money", mPayMoney);
        bundle.putString("score", mPayScroe);
        startIntent(ExchangeDetailsActivity.this, PaySuccessResultActivity.class, bundle);
    }

    //支付宝支付
    public void onPayAliply(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ExchangeDetailsActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //微信支付
    public void onWeiXin(PayOrderSuccessRes payOrderSuccessRes) {
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(getApplicationContext(), WX_ID, true);
        mWxApi.registerApp(WX_ID);
        if (!mWxApi.isWXAppInstalled()) {
            //提醒用户没有按照微信
            showtoast("没有安装微信,请先安装微信!");
            return;
        }
        PayReq req = new PayReq();
        req.appId = WX_ID;// 微信开放平台审核通过的应用APPID
        req.partnerId = payOrderSuccessRes.data.partnerid;
        req.prepayId = payOrderSuccessRes.data.prepayid;
        req.nonceStr = payOrderSuccessRes.data.noncestr;
        req.timeStamp = String.valueOf(payOrderSuccessRes.data.timestamp);
        req.packageValue = "Sign=WXPay";
        req.sign = payOrderSuccessRes.data.sign;
        mWxApi.sendReq(req);
    }


    public void setAddressTxt() {
        llAddress.setVisibility(View.VISIBLE);
        tvNoAddress.setVisibility(View.GONE);
        if (listAddress != null) {
            tvAddressName.setText("收货人：" + listAddress.addrUserName);
            tvAddressPhone.setText(listAddress.addrUserMobile);
            tvAddressContent.setText(listAddress.province +
                    listAddress.city +
                    listAddress.region +
                    listAddress.address);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusExdChangeAddress(EventMessage eventMessage) {
        ToastUtils.e("ExchangeDetailsActivity_____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.EXCHANGE_DETAILS_ADDRESS://选择地址
                listAddress = (MyAddressRes.DataBean.ListBean) eventMessage.object;
                if (listAddress != null) {
                    setAddressTxt();
                }
                break;
            case Constant.EXCHANGE_DETAILS_ADDRESS_ADD://添加地址
                isAdd = true;
                onAddressList();
                break;
            case Constant.USER_PAY_WEICHATE://微信支付回调
                gotoPaySuccess();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        ToastUtils.i("" + str, "___onresult " + data.toString());
        if (str.equalsIgnoreCase("success")) {

            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            // result_data结构见c）result_data参数说明
//            if (data.hasExtra("result_data")) {
//                String result = data.getExtras().getString("result_data");
//                try {
//                    JSONObject resultJson = new JSONObject(result);
//                    String sign = resultJson.getString("sign");
//                    String dataOrg = resultJson.getString("data");
//                    // 此处的verify建议送去商户后台做验签
//                    // 如要放在手机端验，则代码必须支持更新证书
//                    boolean ret = verify(dataOrg, sign, mMode);
//                    if (ret) {
//                        // 验签成功，显示支付结果
//                        msg = "支付成功！";
//                    } else {
//                        // 验签失败
//                        msg = "支付失败！";
//                    }
//                } catch (JSONException e) {
//                }
//            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            msg = "支付成功！";
            gotoPaySuccess();
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }
        ToastUtils.showTextShort(msg);
//        AlertDialog.Builder builder = new AlertDialog.Builder(ExchangeDetailsActivity.this);
//        builder.setTitle("支付结果通知");
//        builder.setMessage(msg);
//        builder.setInverseBackgroundForced(true);
//        // builder.setCustomTitle();
//        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }

}
