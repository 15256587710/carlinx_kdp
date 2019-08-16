package com.ecarxclub.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.CommonAdapter;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.adapter.card.CardBaseAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.model.card.CardBannerRes;
import com.ecarxclub.app.model.card.CardCouponTypeRes;
import com.ecarxclub.app.model.card.CardListRes;
import com.ecarxclub.app.presenter.card.CardFgPresenter;
import com.ecarxclub.app.presenter.card.CardFgView;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.ui.card.CardTagsActivity;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.ViewHolderCommon;
import com.ecarxclub.app.utils.banner.demo.NetworkImageHolderView;
import com.ecarxclub.app.utils.banner.holder.CBViewHolderCreator;
import com.ecarxclub.app.utils.banner.listener.OnItemClickListener;
import com.ecarxclub.app.utils.banner.view.ConvenientBanner;
import com.andview.refreshview.XRefreshView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 *
 * 卡券中心
 */
public class CardFragment extends BaseFragment<CardFgPresenter> implements CardFgView,OnItemClickListener {
    @BindView(R.id.xRefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.cb_card)
    ConvenientBanner convenientBanner;
    @BindView(R.id.gv_card_conpon)
    GridView gridView;
    @BindView(R.id.tv_card_more)
    TextView tvMore;
    @BindView(R.id.rv_card_list)
    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    //刷新
    public static long lastRefreshTime;
    //banner图片
    private List<String> lstImg=new ArrayList<>();
    private List<CardBannerRes.DataBean.ListBean> listBanner=new ArrayList<>();
    private CommonAdapter commonAdapter;
//    private int pageindex = Constant.GET_LIST_INDEX;

    @Override
    protected CardFgPresenter createPresenter() {
        return new CardFgPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_tabmain_card;
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
                initData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {//加载跟多
            }
        });
    }
    //banner 接口获取结果
    @Override
    public void onGetBannerResult(CardBannerRes cardBannerRes) {
        xRefreshView.stopRefresh();
        lstImg.clear();
        if(cardBannerRes.success){
            if(cardBannerRes.data!=null&&cardBannerRes.data.list!=null){
                listBanner=cardBannerRes.data.list;
                for (int i=0;i<cardBannerRes.data.list.size();i++){
                    lstImg.add(cardBannerRes.data.list.get(i).imgPath);
                }
                setPages(lstImg);
            }
        }
    }
    //卡券分类
    @Override
    public void onCouponType(CardCouponTypeRes cardCouponTypeRes) {
        if (cardCouponTypeRes.success&&cardCouponTypeRes.data!=null){
            onCardConponType(cardCouponTypeRes.data);
        }
    }

    @Override
    public void onCouponList(final CardListRes cardListRes) {
        xRefreshView.stopRefresh();
        if(cardListRes.success&&cardListRes.data.list!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewAdapter=new RecyclerViewAdapter<CardListRes.DataBean.ListBean>(getActivity(),cardListRes.data.list,R.layout.item_erv_card_list) {
                @Override
                public void convert(RecyclerViewHolder holder, CardListRes.DataBean.ListBean item,int pos) {
                    holder.setText(R.id.item_erv_card_name,item.couponName);
                    holder.setText(R.id.item_erv_card_title,item.couponTitle);
                    holder.setText(R.id.item_tv_look,"查看\n详情");
                    holder.setText(R.id.item_tv_card_type,item.type.typeName);
                    holder.setText(R.id.item_tv_card_tag,item.targs.tagName);
                    holder.setImageFormUrl(R.id.item_img_card,item.seller.sellerAppLogo);
                }
            };
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerViewAdapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
                @Override
                public void OnRecycleItemClick(View view, int position) {
                    Bundle bundle=new Bundle();
                    bundle.putString("cardid",cardListRes.data.list.get(position).id);
                    startIntent(getActivity(), CardDetailsActivity.class,bundle);
                }
                @Override
                public void onRecycleItemLongClick(View view, int position) {

                }
            });
        }
    }

    @Override
    public void initView() {
        initXRefreshView();
    }

    @Override
    public void initClick() {
        //更多   卡券分类
        RxView.clicks(tvMore).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goToCardTagsAct(0);
            }
        });
    }

    private void goToCardTagsAct(int tabIndex){
        Bundle bundle=new Bundle();
        bundle.putInt("tabSelect",tabIndex);
        startIntent(getActivity(), CardTagsActivity.class,bundle);
    }

    @Override
    public void initData() {
        presenter.getCardBanner();
        presenter.getCardType();
        Map<String,String> map=new HashMap<>();
        map.put("pageIndex","1");
        map.put("pageSize","100");
        presenter.getCardList(map);
    }

    private void setPages(List<String> listImg){
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },listImg)
                .setPageIndicator(new int[]{R.mipmap.indicator_select, R.mipmap.indicator_unselect})
                .setOnItemClickListener(this)
        ;
    }


    public void onCardConponType(List<CardCouponTypeRes.DataBean> list){
        commonAdapter=new CommonAdapter<CardCouponTypeRes.DataBean>(getActivity(),list,R.layout.item_card_coupontype) {
            @Override
            public void convert(ViewHolderCommon helper, CardCouponTypeRes.DataBean item) {
                helper.setText(R.id.item_tv_conpon_name,item.typeName);
                helper.setImageFormUrl(R.id.item_img_conpontop,item.typeIcon);
            }
        };
        gridView.setAdapter(commonAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToCardTagsAct(position+1);
            }
        });
    }


//    banner点击事件
    @Override
    public void onItemClick(int position) {
        ToastUtils.i("banner点击 ",listBanner.size()+"____"+position);
        if ("1".equals(listBanner.get(position).jump)){//不跳转

        }else if ("2".equals(listBanner.get(position).jump)){//H5
            Bundle bundle=new Bundle();
            bundle.putString("web_url",listBanner.get(position).navPath);
            startIntent(getActivity(), WebViewActivity.class,bundle);
        }else if ("3".equals(listBanner.get(position).jump)){//卡券详情
            Bundle bundle=new Bundle();
            bundle.putString("cardid",listBanner.get(position).itemId+"");
            startIntent(getActivity(), CardDetailsActivity.class,bundle);
        }
    }

    @Override
    public void onSelectChoose(int postion) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (convenientBanner!=null){
            convenientBanner.startTurning(3000);
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
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
