package com.ecarxclub.app.fragment.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.mine.order.MyOrderAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.BaseLazyLoadFragment;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.event.EventMessagePay;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;
import com.ecarxclub.app.presenter.mine.order.OrderFgPresenter;
import com.ecarxclub.app.presenter.mine.order.OrderFgView;
import com.ecarxclub.app.ui.mine.order.MyOrderActivity;
import com.ecarxclub.app.ui.mine.order.OrderDetailsActivity;
import com.ecarxclub.app.ui.pay.PaySuccessResultActivity;
import com.ecarxclub.app.ui.shop.ExchangeDetailsActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.pay.PayResult;
import com.ecarxclub.app.utils.popup.OrderPayPopup;
import com.ecarxclub.app.utils.popup.base.BasePopupWindow;
import com.alipay.sdk.app.PayTask;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
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

import butterknife.BindView;

import static com.ecarxclub.app.common.YxbApplication.WX_ID;

/**
 * Created by cwp
 * on 2019/4/26 12:03.
 * 订单列表
 */
@BindEventBus
public class MyOrderFragment extends BaseLazyLoadFragment<OrderFgPresenter> implements OrderFgView {
    @BindView(R.id.easyrv_list)
    EasyRecyclerView easyRecyclerView;

//    private int indexPage;
    private boolean isIndexMore;
    private int pageindex = Constant.GET_LIST_INDEX;
    private int selectPostion = -1;//当前点击的item postion
    private MyOrderAdapter myOrderAdapter;
    private List<MyOrderListRes.DataBean.ListBean> listBeans = new ArrayList<>();
    private String mStatus;

    private OrderPayPopup orderPayPopup;
    private String mPayType;//1支付宝  2微信

    private static final int SDK_PAY_FLAG = 1;//支付宝支付
    private MyOrderActivity myOrderActivity;
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private String mMode = "00";
    private String mStype="",mSName="";
    private boolean isPhonePay;
    public void initData() {
        if (!isFirst) {//懒加载  basefragment
            isFirst = true;
        }
        onOrderList();
    }
    @Override
    public void initView() {
        myOrderActivity= (MyOrderActivity) getActivity();
        mStatus = getArguments().getString("status");
        initEasyRecycleView();
        checkYinlianState();
        ToastUtils.i("订单列表mStatus","__"+mStatus);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) { //可见，并且是第一次加载
            initData();
        } else {
            if (mIsVisable) {//加载当前可以见页面
                pageindex=1;
                selectPostion=0;
                listBeans.clear();
                initData();
            }
        }
    }

    public void onOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageindex + "");
        map.put("pageSize", Constant.GET_LIST_SIZE + "");
        if (mStatus != null && mStatus.length() > 0) {
            map.put("orderStatus", mStatus);
        }
        presenter.onGetOrderList(map);
    }


    /**
     * 传入需要的参数，设置给arguments
     */
    public static MyOrderFragment newInstance(String statu) {
        Bundle bundle = new Bundle();
        bundle.putString("status", statu);
        MyOrderFragment myOrderFragment = new MyOrderFragment();
        myOrderFragment.setArguments(bundle);
        return myOrderFragment;
    }

    @Override
    protected OrderFgPresenter createPresenter() {
        return new OrderFgPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list;
    }

    @Override
    public void onOrderList(MyOrderListRes myOrderListRes) {
        if (myOrderListRes.success) {
            listBeans.addAll(myOrderListRes.data.list);
//            indexPage = myOrderListRes.data.pages;
            isIndexMore=(myOrderListRes.data.list.size()==Constant.GET_LIST_SIZE)?true:false;
            if (pageindex == 1) {
                if (myOrderListRes.data.list.size() == 0) {
                    easyRecyclerView.showEmpty();
                } else {
                    myOrderAdapter.clear();
                    myOrderAdapter.addAll(myOrderListRes.data.list);
                }
            } else {
                myOrderAdapter.addAll(myOrderListRes.data.list);
            }
        } else {
            easyRecyclerView.showError();
        }
    }

    //支付成功
    @Override
    public void onPayOrder(PayOrderSuccessRes payOrderSuccess) {
        if (payOrderSuccess.success) {
            if("1".equals(mPayType)){
                onPayAliply(payOrderSuccess.data.payStr);
            }else if("2".equals(mPayType)){
                onWeiXin(payOrderSuccess);
            }else if ("3".equals(mPayType)){
                if (isPhonePay){
                    onYinLian(payOrderSuccess.data.tn,mStype);
                }else {
                    onYinLian(payOrderSuccess.data.tn,"");
                }
            }
        }
    }


    private void initEasyRecycleView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(myOrderAdapter = new MyOrderAdapter(getActivity()));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefrushList();
            }
        });
        myOrderAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPostion = position;
                Bundle bundle=new Bundle();
                bundle.putSerializable("orderdetails",listBeans.get(position));
                bundle.putString("indexs",mStatus);
                startIntent(getActivity(), OrderDetailsActivity.class,bundle);
            }
        });

        myOrderAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                if (isIndexMore) {//pageindex <= indexPage
                    ToastUtils.i("moreshow", "3333333+++++++" + pageindex + "____");
                    onOrderList();
                } else {
                    myOrderAdapter.stopMore();
                    myOrderAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }
            @Override
            public void onMoreClick() {

            }
        });

        myOrderAdapter.setOnViewClickListener(new MyOrderAdapter.onViewClickListener() {
            @Override
            public void onBtnPay(final int postion) {
                selectPostion = postion;
                ToastUtils.i("onbtnPay ", listBeans.get(postion).id+"___"+postion);
                if(orderPayPopup==null){
                    orderPayPopup = new OrderPayPopup(getActivity());
//                    orderPayPopup.setDataPrice(goodsBean.goodsPriceDtoList);
                }
                orderPayPopup.showPopupWindow();
                orderPayPopup.setData(mStype,mSName);
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.5f;
                getActivity().getWindow().setAttributes(lp);
                orderPayPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                orderPayPopup.setOnSelectListener(new OrderPayPopup.OnSelectListener() {
                    @Override
                    public void onAlipayClick() {//支付宝
                        isPhonePay=false;
                        mPayType="1";
                        onPayOrder(listBeans.get(postion).items.get(0).orderId);
                    }
                    @Override
                    public void onWechatClick() {//微信
                        isPhonePay=false;
                        mPayType="2";
                        onPayOrder(listBeans.get(postion).items.get(0).orderId);
                    }

                    @Override
                    public void onYinlianClick() {
                        isPhonePay=false;
                        mPayType="3";
                        onPayOrder(listBeans.get(postion).items.get(0).orderId);
                    }

                    @Override
                    public void onPhonePayClick() {
                        mPayType="3";
                        isPhonePay=true;
                        onPayOrder(listBeans.get(postion).items.get(0).orderId);
                    }
                });
            }
        });

        ImageView imageView = easyRecyclerView.getEmptyView().findViewById(R.id.img_erv_empty);
        imageView.setBackgroundResource(R.mipmap.pic_04);
        TextView textView = easyRecyclerView.getEmptyView().findViewById(R.id.tv_erv_empty);
        textView.setText("暂无订单");
    }


    //去支付
    public void onPayOrder(String orderid){
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderid);
        map.put("payType", mPayType);//支付类型 1,支付宝  2,微信
        presenter.getPayOrderr(map);
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
                mSName=SEName;
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
                ToastUtils.i("onError    " + SEName, "___" + seType + "====" + errorCode);
            }
        };
        UPPayAssistEx.getSEPayInfo(context, callback);
    }

    private void onYinLian(String tn, String seType) {
        ToastUtils.i("" + seType, "tn ____" + tn);
        if ("".equals(seType)) {
            ToastUtils.i("","tn ____"+tn);
        /* spId —— 保留使用，这里输入null
                sysProvider —— 保留使用，这里输入null
                orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
                mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起
                        交易*/
//        String serverMode = "00";
            UPPayAssistEx.startPay (myOrderActivity, null, null, tn, mMode);
        } else {
            //        context —— 用于获取启动支付控件的活动对象的context
//        spId —— 保留使用，这里输入null
//        sysProvider —— 保留使用，这里输入null
//        orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
//        mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起 交易
//        seType —— 手机pay支付类别，见表1 返回值： 0
            UPPayAssistEx.startSEPay(myOrderActivity, null, null, tn, mMode, seType);
        }
        EventBus.getDefault().post(new EventMessagePay(Constant.USER_PAY_YINLIAN_STATE,mStatus));
    }


//    /**
//     * 得到根Fragment
//     *
//     * @return
//     */
//    private Fragment getRootFragment() {
//        Fragment fragment = getParentFragment();
//        while (fragment.getParentFragment() != null) {
//            fragment = fragment.getParentFragment();
//        }
//        return fragment;
//
//    }

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



    //支付宝支付
    public void onPayAliply(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(myOrderActivity);
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
    public void onWeiXin(PayOrderSuccessRes payOrderSuccessRes){
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(myOrderActivity, WX_ID, true);
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

    //刷新列表
    public void onRefrushList(){
        pageindex = 1;
        listBeans.clear();
        selectPostion=0;
        onOrderList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pageindex=1;
        listBeans.clear();
        selectPostion=0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ToastUtils.i("fragmentonActivityResult "+requestCode,"___onresult "+resultCode);
//        /*************************************************
//         * 步骤3：处理银联手机支付控件返回的支付结果
//         ************************************************/
//        if (data == null) {
//            return;
//        }
//
//        String msg = "";
//        /*
//         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
//         */
//        String str = data.getExtras().getString("pay_result");
//        ToastUtils.i(""+str,"___onresult "+data.toString());
//        if (str.equalsIgnoreCase("success")) {
//
//            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
//            // result_data结构见c）result_data参数说明
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
//            // 结果result_data为成功时，去商户后台查询一下再展示成功
//            msg = "支付成功！";
//        } else if (str.equalsIgnoreCase("fail")) {
//            msg = "支付失败！";
//        } else if (str.equalsIgnoreCase("cancel")) {
//            msg = "用户取消了支付";
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    public void gotoPaySuccess(){
        Bundle bundle=new Bundle();
        bundle.putString("money",listBeans.get(selectPostion).expendMoney+"");
        bundle.putString("score",listBeans.get(selectPostion).expendIntegral+"");
        startIntent(getActivity(), PaySuccessResultActivity.class,bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusView(EventMessage eventMessage) {
        ToastUtils.e("myOrderFragment _____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.ORDER_LIST://更新列表
                String mIndex= (String) eventMessage.object;
                if(mIndex.equals(mStatus)){
                    onRefrushList();
                }
                break;
                case Constant.USER_PAY_YINLIAN://银联支付回调
                    String states= (String) eventMessage.status;
                    if (mStatus.equals(states)){
                        Intent data= (Intent) eventMessage.object;
                        if (data == null) {
                            return;
                        }
                        String msg = "";
                        /*
                         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
                         */
                        String str = data.getExtras().getString("pay_result");
                        if (str.equalsIgnoreCase("success")) {

                            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
                            // result_data结构见c）result_data参数说明
//                            if (data.hasExtra("result_data")) {
//                                String result = data.getExtras().getString("result_data");
//                                try {
//                                    JSONObject resultJson = new JSONObject(result);
//                                    String sign = resultJson.getString("sign");
//                                    String dataOrg = resultJson.getString("data");
//                                    // 此处的verify建议送去商户后台做验签
//                                    // 如要放在手机端验，则代码必须支持更新证书
//                                    boolean ret = verify(dataOrg, sign, mMode);
//                                    if (ret) {
//                                        // 验签成功，显示支付结果
//                                        msg = "支付成功！";
//                                    } else {
//                                        // 验签失败
//                                        msg = "支付失败！";
//                                    }
//                                } catch (JSONException e) {
//                                }
//                            }
                            // 结果result_data为成功时，去商户后台查询一下再展示成功
                            msg = "支付成功！";
                            gotoPaySuccess();
                        } else if (str.equalsIgnoreCase("fail")) {
                            msg = "支付失败！";
                        } else if (str.equalsIgnoreCase("cancel")) {
                            msg = "用户取消了支付";
                        }
                        ToastUtils.showTextShort(msg);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("支付结果通知");
//                        builder.setMessage(msg);
//                        builder.setInverseBackgroundForced(true);
//                        // builder.setCustomTitle();
//                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.create().show();
                    }
                    break;
            case Constant.USER_PAY_WEICHATE://微信支付回调
                gotoPaySuccess();
                break;
        }
    }





}
