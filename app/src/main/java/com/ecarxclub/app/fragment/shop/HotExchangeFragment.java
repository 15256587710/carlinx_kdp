package com.ecarxclub.app.fragment.shop;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.shop.ShopFgAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.BaseFragment;
import com.ecarxclub.app.fragment.BaseLazyLoadFragment;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.presenter.shop.fragment.HotFgPresenter;
import com.ecarxclub.app.presenter.shop.fragment.HotFgView;
import com.ecarxclub.app.ui.shop.HotExchangeActivity;
import com.ecarxclub.app.ui.shop.ShopDetailsActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.recycle.DividerGridItemDecoration;
import com.ecarxclub.app.utils.recycle.GridDividerItemDecoration;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by cwp
 * on 2019/4/29 11:26.
 */
public class HotExchangeFragment extends BaseLazyLoadFragment<HotFgPresenter> implements HotFgView {
    @BindView(R.id.easyrv_list)
    EasyRecyclerView easyRecyclerView;
    private String hotId;
    private int pageindex = Constant.GET_LIST_INDEX;
    private List<ShopListRes.DataBean.ListBean> listBeans=new ArrayList<>();
    private int indexPage = 1;
    private ShopFgAdapter shopFgAdapter;

    private int fgIndex;
    public void initData() {
        if (!isFirst) {//懒加载  basefragment
            isFirst = true;
        }
        onShopData();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        ToastUtils.i("onFragmentVisibleChange "+mIsVisable,"_____"+isVisible+"+++++++"+isFirst+"===="+fgIndex);
        if (isVisible) { //可见，并且是第一次加载
            initData();
        } else {
            if (mIsVisable) {//加载当前可以见页面
                listBeans.clear();
                pageindex = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(300);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ToastUtils.w("onResume","++++++++++++++++++++++++++"+fgIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pageindex=1;
        listBeans.clear();
        ToastUtils.w("onDestroy","================================================"+fgIndex);
    }

    @Override
    public void initView() {
        hotId=getArguments().getString("status");
        fgIndex=getArguments().getInt("ints");
        ToastUtils.w("initView",hotId+"_____________"+fgIndex);
        initEaseRecycleView();
    }

    public void onShopData(){
        Map<String ,String> map=new HashMap<>();
        if(!"".equals(hotId)){
            map.put("tagIds",hotId);
        }
        map.put("pageIndex",pageindex+"");
        map.put("pageSize","10");
        presenter.getShopList(map);
    }

    public void initEaseRecycleView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        easyRecyclerView.addItemDecoration(new GridDividerItemDecoration(10,getResources().getColor(R.color.blue)));
        easyRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        easyRecyclerView.setAdapter(shopFgAdapter = new ShopFgAdapter(getActivity()));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listBeans.clear();
                pageindex = 1;
                onShopData();
            }
        });
        shopFgAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putString("shopid",listBeans.get(position).id);
                startIntent(getActivity(), ShopDetailsActivity.class,bundle);
            }
        });
        shopFgAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                if (pageindex <= indexPage) {
//                    onGetAddressList();
                } else {
                    shopFgAdapter.stopMore();
//                    shopFgAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }
            @Override
            public void onMoreClick() {

            }
        });

        TextView view = easyRecyclerView.getEmptyView().findViewById(R.id.tv_erv_empty);
        view.setText("暂无兑换商品");
        ImageView imageView=easyRecyclerView.getEmptyView().findViewById(R.id.img_erv_empty);
        imageView.setBackgroundResource(R.mipmap.pic_05);
//        TextView btnError = easyRecyclerView.getErrorView().findViewById(R.id.btn_status_error);
//        btnError.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }


    @Override
    protected HotFgPresenter createPresenter() {
        HotFgPresenter hotFgPresenter=new HotFgPresenter(this, getActivity());
        return hotFgPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list;
    }

    @Override
    public void onGetHotList(ShopListRes shopListRes) {
        if(shopListRes.success&&shopListRes.data.list!=null){
            listBeans=shopListRes.data.list;
            indexPage=shopListRes.data.pages;
            if(pageindex==1){
                if(shopListRes.data.list.size()==0){
                    easyRecyclerView.showEmpty();
                }else {
                    shopFgAdapter.clear();
                    shopFgAdapter.addAll(listBeans);
                }
            }else {
                shopFgAdapter.addAll(listBeans);
            }
        }else {
            easyRecyclerView.showError();
        }
    }


    /**
     * 传入需要的参数，设置给arguments
     */
    public static HotExchangeFragment newInstance(String statu,int s) {
        Bundle bundle = new Bundle();
        bundle.putString("status", statu);
        bundle.putInt("ints",s);
        HotExchangeFragment myCouponFragment = new HotExchangeFragment();
        myCouponFragment.setArguments(bundle);
        return myCouponFragment;
    }
}
