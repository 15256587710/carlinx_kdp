package com.ecarxclub.app.fragment.card;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.card.CardBaseAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.BaseFragment;
import com.ecarxclub.app.model.card.CardListRes;
import com.ecarxclub.app.presenter.card.CardTagFgPresenter;
import com.ecarxclub.app.presenter.card.CardTagsFgView;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by cwp
 * on 2019/4/25 13:45.
 * 卡券分类
 */
public class CardTagsFragment extends BaseFragment<CardTagFgPresenter> implements CardTagsFgView {
    @BindView(R.id.easyrv_list)
    EasyRecyclerView easyRecyclerView;

    CardBaseAdapter cardBaseAdapter;
    private List<CardListRes.DataBean.ListBean> listBeans=new ArrayList<>();
    private String cardId;
    private int indexPage;
    private int pageindex = Constant.GET_LIST_INDEX;
    //    private int pagesize = 10;
    @Override
    protected CardTagFgPresenter createPresenter() {
        return new CardTagFgPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list;
    }

    @Override
    public void initClick() {
        super.initClick();
    }


    @Override
    public void initView() {
        initEasyRecycleView();
    }

    //  https://app.carlinx.cn/yxb/api/seller/coupon?typeId=293088549065068670&pageIndex=1&pageSize=10
    //  https://app.carlinx.cn/yxb/api/seller/coupon
    @Override
    public void initData() {
        cardId = getArguments().getString("status");
        ToastUtils.i("", "_____" + cardId);
        onGetCardList();
    }

    private void onGetCardList(){
        Map<String, String> map = new HashMap<>();
        if (!"".equals(cardId)) {
            map.put("typeId", "" + cardId);
        }
        map.put("pageIndex", ""+pageindex);
        map.put("pageSize", Constant.GET_LIST_SIZE+"");
        presenter.getCardList(map);
    }

    @Override
    public void onCardTagList(CardListRes cardListRes) {
        if (cardListRes.success) {
            ToastUtils.i("sizelist    ", "" + cardListRes.data.list.size());
            listBeans.addAll(cardListRes.data.list);
            indexPage=cardListRes.data.pages;
            if(pageindex==1){
                if(cardListRes.data.list.size()==0){
                    easyRecyclerView.showEmpty();
                }else {
                    cardBaseAdapter.clear();
                    cardBaseAdapter.addAll(listBeans);
                }
            }else {
                cardBaseAdapter.addAll(listBeans);
            }
        }else {
            easyRecyclerView.showError();
        }
    }

    /**
     * 传入需要的参数，设置给arguments
     */
    public static CardTagsFragment newInstance(String statu) {
        Bundle bundle = new Bundle();
        bundle.putString("status", statu);
        CardTagsFragment myCouponFragment = new CardTagsFragment();
        myCouponFragment.setArguments(bundle);
        return myCouponFragment;
    }

    private void initEasyRecycleView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(cardBaseAdapter = new CardBaseAdapter(getActivity()));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                listBeans.clear();
                onGetCardList();
            }
        });
        cardBaseAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putString("cardid",listBeans.get(position).id);
                startIntent(getActivity(), CardDetailsActivity.class,bundle);
            }
        });
        cardBaseAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                ToastUtils.i("moreshow", "zzzzzzzzzzzzzzzzzzzzzzz+++++++" + pageindex + "____" + indexPage);
                if (pageindex <= indexPage) {
                    ToastUtils.i("moreshow", "3333333+++++++" + pageindex + "____" + indexPage);
//                    onGetAddressList();
                } else {
                    cardBaseAdapter.stopMore();
                    cardBaseAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }

            @Override
            public void onMoreClick() {

            }
        });
        ImageView imageView=easyRecyclerView.getEmptyView().findViewById(R.id.img_erv_empty);
        imageView.setBackgroundResource(R.mipmap.pic_03);
        TextView textView=easyRecyclerView.getEmptyView().findViewById(R.id.tv_erv_empty);
        textView.setText("暂无卡券");
    }


}
