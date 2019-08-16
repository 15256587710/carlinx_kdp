package com.ecarxclub.app.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.model.ShareModel;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.shop.ShopBannerRes;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.presenter.shop.fragment.ShopFgPresenter;
import com.ecarxclub.app.presenter.shop.fragment.ShopFgView;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.ui.shop.HotExchangeActivity;
import com.ecarxclub.app.ui.shop.IntegralCenterActivity;
import com.ecarxclub.app.ui.shop.ShopDetailsActivity;
import com.ecarxclub.app.ui.shop.SignInActivity;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.banner.demo.NetworkImageHolderView;
import com.ecarxclub.app.utils.banner.holder.CBViewHolderCreator;
import com.ecarxclub.app.utils.banner.listener.OnItemClickListener;
import com.ecarxclub.app.utils.banner.view.ConvenientBanner;
import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.ecarxclub.app.utils.recycle.DividerGridItemDecoration;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

@BindEventBus
public class ShopFragment extends BaseFragment<ShopFgPresenter> implements ShopFgView, OnItemClickListener {
    @BindView(R.id.xRefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.rv_shop_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_shop_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_shop_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_shop_tag3)
    TextView tvTag3;
    @BindView(R.id.tv_shop_tag4)
    TextView tvTag4;
    @BindView(R.id.img_shop_tag1)
    ImageView imgTag1;
    @BindView(R.id.img_shop_tag2)
    ImageView imgTag2;
    @BindView(R.id.img_shop_tag3)
    ImageView imgTag3;
    @BindView(R.id.img_shop_tag4)
    ImageView imgTag4;
    @BindView(R.id.tv_shop_more)
    TextView tvMore;
    @BindView(R.id.cb_shop)
    ConvenientBanner convenientBanner;
    @BindView(R.id.rcv_shop_tag)
    RecyclerView recyclerViewIcon;
    //刷新
    public static long lastRefreshTime;
    RecyclerViewAdapter recyclerViewAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
    private ShopTagsRes shopBean;
    //banner图片
    private List<String> lstImg = new ArrayList<>();
    private List<ShopBannerRes.DataBean.BannerBean> listBanner = new ArrayList<>();

    @Override
    protected ShopFgPresenter createPresenter() {
        return new ShopFgPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabmain_shop;
    }

    @Override
    public void initView() {
        initXRefreshView();
    }

    public void initXRefreshView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setPinnedTime(1000);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {//刷新
                setData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {//加载跟多
                ToastUtils.i("onLoadMore", " " + isSilence);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.i("11111111111111", " ");
                                    xRefreshView.stopLoadMore();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void initClick() {
        //热门兑换
        RxView.clicks(tvMore).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToHotExchangeAct(0);
            }
        });

        //汽车周边
        RxView.clicks(imgTag1).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToHotExchangeAct(1);
            }
        });
        //手机数码
        RxView.clicks(imgTag2).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToHotExchangeAct(2);
            }
        });
        //家居生活
        RxView.clicks(imgTag3).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToHotExchangeAct(3);
            }
        });
        //家用电器
        RxView.clicks(imgTag4).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToHotExchangeAct(4);
            }
        });

        //绑定车辆
//        RxView.clicks(tvBindcar).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
//            @Override
//            public void call(Void aVoid) {
//                if (YxbContent.getLoginToken()) {
//                    startIntent(getActivity(), BindCarActivity.class);//BindCarActivity  ChooseCarActivity
//                } else {
//                    YxbContent.goLoginAct(getActivity());
//                }
//            }
//        });
    }

    private void goToHotExchangeAct(int tag) {//热门兑换
        Bundle bundle = new Bundle();
        bundle.putInt("changeTag", tag);
        bundle.putSerializable("hotTag", shopBean);
        startIntent(getActivity(), HotExchangeActivity.class, bundle);
    }

    @Override
    public void initData() {
        setData();
    }

    //通用接口
    private void unLoginView() {
        getShopTags();
        onShopData();
        presenter.getShopBannerP();
    }

    private void getShopTags() {//热门兑换
        presenter.getShopTag();
    }

    private void setData() {
        unLoginView();
//        if (YxbContent.getLoginToken()) {//是否登录
//            //是否绑定车辆
//            if (YxbApplication.userInfoRes != null && YxbApplication.userInfoRes.data != null && YxbApplication.userInfoRes.data.mainCarId != null) {
//                presenter.onDrivingRecode(YxbApplication.userInfoRes.data.mainCarId);
//            } else {
//                tvBindcar.setVisibility(View.VISIBLE);
////                rlUserInfo.setVisibility(View.GONE);
////                presenter.getClockDays();//获取连续签到天数 {"success":true,"msg":"success","data":1}
//            }
//        } else {//未登录
//            tvBindcar.setVisibility(View.VISIBLE);
////            rlUserInfo.setVisibility(View.GONE);
////            setCllockDays("0");
//        }
    }

    public void onShopData() {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageindex + "");
        map.put("pageSize", "10");
        presenter.getShopList(map);
    }


    private void setPages(List<String> listImg) {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, listImg)
                .setPageIndicator(new int[]{R.mipmap.indicator_select, R.mipmap.indicator_unselect})
                .setOnItemClickListener(this)
        ;
    }


    //标签
    @Override
    public void onGetShopTag(ShopTagsRes baseMsgRes) {
        if (baseMsgRes.success && baseMsgRes.data != null) {
            shopBean = baseMsgRes;
            tvTag1.setText(baseMsgRes.data.get(0).tagName);
            Glide.with(getActivity()).load(baseMsgRes.data.get(0).tagIcon).apply(YxbContent.options_pop_tags).into(imgTag1);
            tvTag2.setText(baseMsgRes.data.get(1).tagName);
            Glide.with(getActivity()).load(baseMsgRes.data.get(1).tagIcon).apply(YxbContent.options_pop_tags).into(imgTag2);
            tvTag3.setText(baseMsgRes.data.get(2).tagName);
            Glide.with(getActivity()).load(baseMsgRes.data.get(2).tagIcon).apply(YxbContent.options_pop_tags).into(imgTag3);
            tvTag4.setText(baseMsgRes.data.get(3).tagName);
            Glide.with(getActivity()).load(baseMsgRes.data.get(3).tagIcon).apply(YxbContent.options_pop_tags).into(imgTag4);
        }
    }

    @Override
    public void onGetShopList(final ShopListRes shopTagsRes) {
        xRefreshView.stopRefresh();
        if (shopTagsRes.success && shopTagsRes.data.list != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerViewAdapter = new RecyclerViewAdapter<ShopListRes.DataBean.ListBean>(getActivity(),
                    shopTagsRes.data.list, R.layout.item_shop_fglist) {
                @Override
                public void convert(RecyclerViewHolder holder, ShopListRes.DataBean.ListBean item, int pos) {
                    ToastUtils.w("RecyclerViewHolder", "" + item.money);
                    holder.setText(R.id.item_tv_shoplist_score, item.integral + "积分" + item.money + "元");
                    holder.setText(R.id.item_tv_shoplist_title, item.productName);
                    holder.setImageFormUrl(R.id.item_img_shoplist, item.productIcon);
                }
            };
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
                @Override
                public void OnRecycleItemClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shopid", shopTagsRes.data.list.get(position).id);
                    startIntent(getActivity(), ShopDetailsActivity.class, bundle);
                }

                @Override
                public void onRecycleItemLongClick(View view, int position) {

                }
            });
        }
    }

//    @Override
//    public void onDrivingRecodeV(HomeDrivingInfoRes baseMsgRes) {
//        xRefreshView.stopRefresh();
//        if (baseMsgRes.success) {
//            if (baseMsgRes.data.travel != null) {
////                onRefreshView();
//                tvBindcar.setVisibility(View.GONE);
////                rlUserInfo.setVisibility(View.VISIBLE);
//            } else {
//                tvBindcar.setVisibility(View.VISIBLE);
////                rlUserInfo.setVisibility(View.GONE);
////                setCllockDays("0");
//            }
//        }
//    }

    //banner和积分，签到等UI
    @Override
    public void onGetShopBanner(final ShopBannerRes shopBannerRes) {
        lstImg.clear();
        if (shopBannerRes.success) {
            if (shopBannerRes.data != null && shopBannerRes.data.banner != null) {
                listBanner = shopBannerRes.data.banner;
                for (int i = 0; i < shopBannerRes.data.banner.size(); i++) {
                    lstImg.add(shopBannerRes.data.banner.get(i).imgPath);
                }
                setPages(lstImg);
            }
            if (shopBannerRes.data != null && shopBannerRes.data.icon != null && shopBannerRes.data.icon.size() > 0) {
                recyclerViewIcon.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                recyclerViewAdapter = new RecyclerViewAdapter<ShopBannerRes.DataBean.IconBean>(getActivity(), shopBannerRes.data.icon, R.layout.item_shop_icon) {
                    @Override
                    public void convert(RecyclerViewHolder holder, ShopBannerRes.DataBean.IconBean item, int pos) {
                        holder.setText(R.id.item_tv_bot_txt, item.title);
                        holder.setImageFormUrl(R.id.item_img_top_icon, item.imgPath);
                    }
                };
                recyclerViewIcon.setAdapter(recyclerViewAdapter);
//                recyclerViewIcon.setNestedScrollingEnabled(false);
                recyclerViewAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
                    @Override
                    public void OnRecycleItemClick(View view, int position) {
                        if (YxbContent.getLoginToken()) {//是否登录
                            if ("1".equals(shopBannerRes.data.icon.get(position).jump)) {//签到
                                goSignInAct();
                            } else if ("2".equals(shopBannerRes.data.icon.get(position).jump)) {//积分
                                goIntegralCenterAct();
                            } else if ("3".equals(shopBannerRes.data.icon.get(position).jump)) {//游戏
                                goWebGame(shopBannerRes.data.icon.get(position).interLink);
                            } else if ("4".equals(shopBannerRes.data.icon.get(position).jump)) {//邀请好友
                                Bundle bundle = new Bundle();//"http://192.168.0.107:8080/#/invite?token="+   item.interLink
                                bundle.putString("web_url", shopBannerRes.data.icon.get(position).interLink + "?token=" + SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN, "").toString() + "&wrap=android");
                                ShareModel shareModel = new ShareModel();
                                shareModel.shareTitle = shopBannerRes.data.icon.get(position).shareTitle;
                                shareModel.shareContent = shopBannerRes.data.icon.get(position).shareContent;
                                shareModel.shareImage = shopBannerRes.data.icon.get(position).shareImage;
                                shareModel.shareInterLink = shopBannerRes.data.icon.get(position).shareInterLink;
                                bundle.putSerializable("model", shareModel);
                                startIntent(getActivity(), WebViewActivity.class, bundle);
                            }
                        } else {
                            YxbContent.goLoginAct(getActivity());
                        }
                    }

                    @Override
                    public void onRecycleItemLongClick(View view, int position) {

                    }
                });
            }
        }
    }

    //web  游戏
    private void goWebGame(String goUrl) {
        Bundle bundle = new Bundle();//"http://192.168.0.107:8080/#/?token="
        bundle.putString("web_url", goUrl + "?token=" + SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN, "").toString() + "&wrap=android");
        bundle.putString("welcome", "integral");
        startIntent(getActivity(), WebViewActivity.class, bundle);
    }

    private void goSignInAct() {//签到
        if (YxbContent.getIsBindCar()) {//是否绑定车辆
            startIntent(getActivity(), SignInActivity.class);
        } else {
            YxbContent.goBindCarAct(getActivity());
        }
    }

    public void goIntegralCenterAct() {//积分
        startIntent(getActivity(), IntegralCenterActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
        ToastUtils.e("shopFragment_____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_LOGIN_SUCCESS_HOME://home 传过来登录成功
                setData();
                break;
            case Constant.LOGIN_OUT://退出登录
                setData();
                break;
            case Constant.USER_CHANGE_CAR://切换绑定车辆
//                tvBindcar.setVisibility(View.GONE);
//                rlUserInfo.setVisibility(View.VISIBLE);
//                onRefreshView();
                break;
            case Constant.USER_SCORE_PAY://刷新积分
//                presenter.getUserScore();
                break;
        }
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (convenientBanner != null) {
            convenientBanner.startTurning(3000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public void onItemClick(int position) {
        ToastUtils.i("banner点击 ", listBanner.size() + "____" + position);
        if ("1".equals(listBanner.get(position).jump)) {//不跳转

        } else if ("2".equals(listBanner.get(position).jump)) {//H5
            Bundle bundle = new Bundle();
            bundle.putString("web_url", listBanner.get(position).navPath);
            startIntent(getActivity(), WebViewActivity.class, bundle);
        } else if ("3".equals(listBanner.get(position).jump)) {//卡券详情
            Bundle bundle = new Bundle();
            bundle.putString("cardid", listBanner.get(position).itemId + "");
            startIntent(getActivity(), CardDetailsActivity.class, bundle);
        } else if ("4".equals(listBanner.get(position).jump)) {//签到
            goSignInAct();
        } else if ("5".equals(listBanner.get(position).jump)) {//积分中心
            goIntegralCenterAct();
        } else if ("6".equals(listBanner.get(position).jump)) {//游戏
            goWebGame(listBanner.get(position).navPath);
        } else if ("7".equals(listBanner.get(position).jump)) {//排行榜
        } else if ("8".equals(listBanner.get(position).jump)) {//绑定车辆
            if (YxbContent.getLoginToken()) {
                YxbContent.goBindCarAct(getActivity());
            } else {
                YxbContent.goLoginAct(getActivity());
            }
        } else if ("9".equals(listBanner.get(position).jump)) {//会员权益
        }
    }

    @Override
    public void onSelectChoose(int postion) {

    }
}
