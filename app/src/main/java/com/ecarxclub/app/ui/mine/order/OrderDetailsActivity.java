package com.ecarxclub.app.ui.mine.order;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.card.CardListRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.ConfirmRecriceRes;
import com.ecarxclub.app.model.mine.LogistiesListRes;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;
import com.ecarxclub.app.presenter.pay.OrderDetailsPresenter;
import com.ecarxclub.app.presenter.pay.OrderDetailsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.pay.PaySuccessResultActivity;
import com.ecarxclub.app.ui.shop.ExchangeDetailsActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.AlertDialog;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

import static com.ecarxclub.app.common.YxbApplication.WX_ID;

/**
 * Created by cwp
 * on 2019/5/8 13:45.
 * 订单详情
 */
@BindEventBus
public class OrderDetailsActivity extends BaseActivityP<OrderDetailsPresenter> implements OrderDetailsView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ll_od_header)
    LinearLayout llHeaderImg;

    @BindView(R.id.item_llcontent)
    LinearLayout llShopBg;
    @BindView(R.id.item_tv_myorder_id)
    TextView tvOrderId;
    @BindView(R.id.item_img_myorder)
    ImageView imgHead;
    @BindView(R.id.item_tv_myorder_name)
    TextView tvName;
    @BindView(R.id.item_tv_myorder_info)
    TextView tvInfo;
    @BindView(R.id.item_tv_myorder_num)
    TextView tvNum;

    @BindView(R.id.tv_od_time)
    TextView tvTime;
    @BindView(R.id.tv_od_scroe)
    TextView tvScroe;
    @BindView(R.id.tv_od_money)
    TextView tvMoney;
    @BindView(R.id.tv_od_status)
    TextView tvStatus;
    @BindView(R.id.include_pay)
    LinearLayout llPtype;//支付方式
    @BindView(R.id.ll_od_pay)
    LinearLayout llPay;//支付  按钮
    @BindView(R.id.btn_od_order_cancel)
    Button btnCancel;
    @BindView(R.id.rfsv_od_logistes)
    RecyclerView recyclerView;
    @BindView(R.id.cb_exd_zfb)
    CheckBox cbZfb;
    @BindView(R.id.cb_exd_wx)
    CheckBox cbWx;
    @BindView(R.id.cb_exd_yl)
    CheckBox cbYl;
    @BindView(R.id.btn_od_order_pay)
    Button btnPay;

    @BindView(R.id.img_pay_phone)
    ImageView imgPhonePay;
    @BindView(R.id.tv_pay_phone)
    TextView tvPhonePay;
    @BindView(R.id.cb_exd_phone)
    CheckBox cbPhone;
    @BindView(R.id.item_rv_tag_shop)
    RecyclerView recyclerViewTag;
    @BindView(R.id.tv_od_logistesname)
    TextView tvLogistName;
    @BindView(R.id.ll_od_ligistes)
    LinearLayout llLogist;//物流

    @BindView(R.id.ll_pay_phone)
    LinearLayout llPhonePay;
    @BindView(R.id.ll_pay_yl)
    LinearLayout llYl;
    @BindView(R.id.ll_pay_ailpy)
    LinearLayout llAliay;
    @BindView(R.id.ll_pay_wechat)
    LinearLayout llWechat;

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<LogistiesListRes.DataBean> listLogisties = new ArrayList<>();

    private MyOrderListRes.DataBean.ListBean listBean;
    private int mStatus;//订单状态
    private String mIndex;//fragment页面  更改订单详情列表刷新

    private static final int SDK_PAY_FLAG = 1;//支付宝支付
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private String mMode = "00", mStype = "";//mStype 手机pay支付
    private String mPoint;//确认收货的积分
    private ConfirmRecriceRes confirmRecriceRes;//确认收货数据  banner

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        tvToolbarTitle.setText("订单详情");
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        llShopBg.setBackgroundColor(Color.parseColor("#f3f3f3"));
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //取消订单
        RxView.clicks(btnCancel).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (listBean != null) {
                    mStatus = listBean.orderStatus;
                    if (mStatus == 0) {//取消订单
                        showCancelDialog();
                    }else if (mStatus==2||mStatus==5){//客服
                        YxbContent.getCallPhone(OrderDetailsActivity.this);
                    }
                }
            }
        });
        //支付
        RxView.clicks(btnPay).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (listBean != null) {
                    mStatus = listBean.orderStatus;
                    if (mStatus == 0) {//待支付
                        if (cbZfb.isChecked() || cbWx.isChecked() || cbYl.isChecked()||cbPhone.isChecked()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("orderId", listBean.id);
                            if (cbZfb.isChecked()) {
                                map.put("payType", "1");//支付类型 1,支付宝  2,微信
                            } else if (cbWx.isChecked()) {
                                map.put("payType", "2");//支付类型 1,支付宝  2,微信
                            } else if (cbYl.isChecked()||cbPhone.isChecked()) {
                                ToastUtils.i("yinlian   ", "");
                                map.put("payType", "3");//支付类型 1,支付宝  2,微信 3 银联
                            } else {
                                showtoast("请选择支付类型");
                                return;
                            }
                            presenter.getPayOrderr(map);
                        } else {
                            ToastUtils.showTextShort("请选择支付方式");
                        }
                    }else if (mStatus==2){//已发货  确认收货
                        showConfirmDialog();
                    }else if (mStatus==5){//已签收

                    }
                }
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
//手机pay
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
    }

    @Override
    public void initData() {
        listBean = (MyOrderListRes.DataBean.ListBean) getIntent().getExtras().getSerializable("orderdetails");
        mIndex = getIntent().getExtras().getString("indexs");
        if (listBean != null) {
            tvOrderId.setText("订单号：" + listBean.id);
            tvName.setText(listBean.items.get(0).product.productName);
            tvInfo.setText(listBean.expendIntegral + "积分+" + listBean.expendMoney + "元");
            tvNum.setText("x" + listBean.items.get(0).amount);
            Glide.with(OrderDetailsActivity.this).load(listBean.items.get(0).product.productIcon).
                    apply(YxbContent.options).into(imgHead);
            tvTime.setText(listBean.createTime);
            tvScroe.setText("-" + listBean.expendIntegral);
            tvMoney.setText(listBean.expendMoney + "元");
            if (listBean.items!=null&&listBean.items.get(0).product!=null&&listBean.items.get(0).product.tags.size()>0){
                recyclerViewTag.setLayoutManager(new GridLayoutManager(context,listBean.items.get(0).product.tags.size()));
                recyclerViewAdapter=new RecyclerViewAdapter<MyOrderListRes.DataBean.ListBean.ItemsBean.ProductBean.TagsBean >
                        (context,listBean.items.get(0).product.tags,R.layout.item_shop_tag) {
                    @Override
                    public void convert(RecyclerViewHolder holder, MyOrderListRes.DataBean.ListBean.ItemsBean.ProductBean.TagsBean item,int pos) {
                        holder.setText(R.id.item_tv_tag,item.tag.tagName);
                    }
                };
                recyclerViewTag.setAdapter(recyclerViewAdapter);
            }
        }
        onRefreshView();
    }

    public void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerViewAdapter = new RecyclerViewAdapter<LogistiesListRes.DataBean>(OrderDetailsActivity.this
                , listLogisties, R.layout.item_logisties_list) {
            @Override
            public void convert(RecyclerViewHolder holder, LogistiesListRes.DataBean item,int pos) {
                TextView tvCircle=holder.getView(R.id.item_tv_logist_circle);
                if (pos==0){
                    tvCircle.setBackgroundResource(R.drawable.circle_shape_red);
                }else {
                    tvCircle.setBackgroundResource(R.drawable.circle_shape_gry);
                }
                holder.setText(R.id.item_lg_title, item.value);
                holder.setText(R.id.item_lg_time, item.key);
            }
        };
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void showCancelDialog() {
        new AlertDialog(OrderDetailsActivity.this).builder().setTitle("温馨提示")
                .setMsg("确定删除该订单？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onCancelOrder(listBean.id);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    private void showConfirmDialog() {
        new AlertDialog(OrderDetailsActivity.this).builder().setTitle("是否确认订单已签收")
//                .setMsg("")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getConformRecive(listBean.id);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    public void onRefreshView() {
        if (listBean != null) {
            mStatus = listBean.orderStatus;
            if (mStatus == 0) {
                tvStatus.setText("等待用户付款...");
                llPay.setVisibility(View.VISIBLE);
                llHeaderImg.setBackgroundResource(R.mipmap.banner_04);
                llLogist.setVisibility(View.GONE);
                checkYinlianState();
            } else if (mStatus == 1) {
                tvStatus.setText("商品即将发货~");
                llPtype.setVisibility(View.GONE);
                llPay.setVisibility(View.GONE);
                llHeaderImg.setBackgroundResource(R.mipmap.banner_01);
                llLogist.setVisibility(View.GONE);
            } else if (mStatus == 2) {
                tvStatus.setText("商品已经发货！");
                llPtype.setVisibility(View.GONE);
                llPay.setVisibility(View.VISIBLE);
                btnPay.setText("确认签收");
                btnCancel.setText("联系客服");
                llHeaderImg.setBackgroundResource(R.mipmap.banner_02);
                getLogistics();
            } else if (mStatus == 3) {
                tvStatus.setText("订单正在申请取消…");
                llPtype.setVisibility(View.GONE);
                llPay.setVisibility(View.GONE);
                llLogist.setVisibility(View.GONE);
            } else if (mStatus == 4) {
                tvStatus.setText("订单已取消！");
                llPtype.setVisibility(View.GONE);
                llPay.setVisibility(View.GONE);
                llLogist.setVisibility(View.GONE);
            }else if (mStatus == 5) {
                tvStatus.setText("订单已签收！");
                llPtype.setVisibility(View.GONE);
                llPay.setVisibility(View.VISIBLE);
                btnPay.setBackgroundResource(R.drawable.lay_bgwhite_bordere9_rd4);
                btnPay.setText("已签收");
                btnPay.setTextColor(getResources().getColor(R.color.tab_n));
                llHeaderImg.setBackgroundResource(R.mipmap.banner_06);
                btnCancel.setText("联系客服");
                getLogistics();
            }
        }
    }

    private void getLogistics(){//物流
        if (listBean.shipCrop!=null&&listBean.shipCrop.length()>0){
            llLogist.setVisibility(View.VISIBLE);
            tvLogistName.setText(listBean.shipCrop+"："+listBean.shipNo);
            presenter.onLogistics(listBean.shipCrop, listBean.shipNo);
        }else {
            llLogist.setVisibility(View.GONE);
        }
    }

    @Override
    protected OrderDetailsPresenter createPresenter() {
        return new OrderDetailsPresenter(this);
    }

    @Override
    public void onCancelOrder(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            showtoast("订单取消成功");
            listBean.orderStatus = 4;
            EventBus.getDefault().post(new EventMessage(Constant.ORDER_LIST, mIndex));
            onRefreshView();
        }
    }

    //物流
    @Override
    public void onLogistiesV(LogistiesListRes baseMsgRes) {
        if (baseMsgRes.success) {
            listLogisties = baseMsgRes.data;
            setRecyclerView();
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
            } else if (cbYl.isChecked()||cbPhone.isChecked()) {
                if (cbYl.isChecked()) {
                    onYinLian(payOrderSuccess.data.tn, "");
                } else {
                    onYinLian(payOrderSuccess.data.tn, mStype);
                }
            }
        }
    }

    //确认签收
    @Override
    public void onConfirmRecive(ConfirmRecriceRes baseMsgRes) {
        if (baseMsgRes.success){
            confirmRecriceRes=baseMsgRes;
            showtoast("确认成功");
            listBean.orderStatus = 5;
            EventBus.getDefault().post(new EventMessage(Constant.ORDER_LIST, mIndex));
            onRefreshView();
            mPoint=baseMsgRes.data.integral+"";
            gotoPaySuccess();
        }
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
                if (llPhonePay!=null){
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
                if (llPhonePay!=null){
                    llPhonePay.setVisibility(View.GONE);
                }
                ToastUtils.i("onError    " + SEName, "___" + seType + "====" + errorCode);
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
            UPPayAssistEx.startPay(OrderDetailsActivity.this, null, null, tn, mMode);
        } else {
            //        context —— 用于获取启动支付控件的活动对象的context
//        spId —— 保留使用，这里输入null
//        sysProvider —— 保留使用，这里输入null
//        orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
//        mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起 交易
//        seType —— 手机pay支付类别，见表1 返回值： 0
            UPPayAssistEx.startSEPay(OrderDetailsActivity.this, null, null, tn, mMode, seType);
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
        Bundle bundle = new Bundle();
        if (listBean.orderStatus==5){//确认收货
            bundle.putSerializable("ConfrimModel", confirmRecriceRes);
        }else {
            bundle.putString("money", listBean.expendMoney + "");
            bundle.putString("score", listBean.expendIntegral + "");
        }
        startIntent(OrderDetailsActivity.this, PaySuccessResultActivity.class, bundle);
    }

    //支付宝支付
    public void onPayAliply(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailsActivity.this);
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
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrderDetailsActivity.this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusView(EventMessage eventMessage) {
        ToastUtils.e("myOrderFragment _____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_PAY_WEICHATE://微信支付回调
                gotoPaySuccess();
                break;
        }
    }

}
