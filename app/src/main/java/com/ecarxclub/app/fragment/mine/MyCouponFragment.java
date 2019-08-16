package com.ecarxclub.app.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.mine.coupon.MyCouponAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.BaseFragment;
import com.ecarxclub.app.model.mine.MyCouponRes;
import com.ecarxclub.app.presenter.mine.coupon.MyCouPonView;
import com.ecarxclub.app.presenter.mine.coupon.MyCouponPresenter;
import com.ecarxclub.app.ui.mine.coupon.CouponDetailsActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by cwp
 * on 2019/4/24 14:20.
 * 我的卡券
 */
public class MyCouponFragment extends BaseFragment<MyCouponPresenter> implements MyCouPonView {
    @BindView(R.id.easyrv_list)
    EasyRecyclerView easyRecyclerView;

    private String status;//1有效 2已使用 -1已过期

//    private int indexPage;
    private boolean isMore;
    private int pageindex = Constant.GET_LIST_INDEX;
    private int selectPostion = -1;//当前点击的item postion
    private MyCouponAdapter myCouponAdapter;
    private List<MyCouponRes.DataBean.ListBean> listBeans=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list;
    }

    @Override
    public void initView() {
        initEasyRecycleView();
    }

    @Override
    public void initData() {
        status=getArguments().getString("status");
        onGetCouponList();
    }
    public void onGetCouponList(){
        presenter.onGetCouponList(pageindex,Constant.GET_LIST_SIZE,true,status);
    }

    /**
     * 传入需要的参数，设置给arguments
     */
    public static MyCouponFragment newInstance(String statu) {
        Bundle bundle = new Bundle();
        bundle.putString("status", statu);
        MyCouponFragment myCouponFragment = new MyCouponFragment();
        myCouponFragment.setArguments(bundle);
        return myCouponFragment;
    }


    @Override
    protected MyCouponPresenter createPresenter() {
        return new MyCouponPresenter(this);
    }

    @Override
    public void onListResult(MyCouponRes couponRes) {
        if(couponRes.success){
            if (couponRes.data.list!=null&&couponRes.data.list.size()>0){
                listBeans.addAll(couponRes.data.list);
                isMore=(couponRes.data.list.size()==Constant.GET_LIST_SIZE)?true:false;
                if(pageindex==1){
                    if(couponRes.data.list.size()==0){
                        easyRecyclerView.showEmpty();
                    }else {
                        myCouponAdapter.clear();
                        myCouponAdapter.addAll(listBeans);
                    }
                }else {
                    myCouponAdapter.addAll(listBeans);
                }
            }else {
                easyRecyclerView.showEmpty();
            }
        }else {
            easyRecyclerView.showError();
        }
    }




    private void initEasyRecycleView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(myCouponAdapter = new MyCouponAdapter(getActivity()));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                listBeans.clear();
                onGetCouponList();
            }
        });
        myCouponAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPostion=position;
                Bundle bundle=new Bundle();
//                bundle.putString("couponid",listBeans.get(position).couponId);
                bundle.putSerializable("couponInfo", listBeans.get(position));
                startIntent(getActivity(), CouponDetailsActivity.class,bundle);
            }
        });

        myCouponAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                if (isMore) {
                    onGetCouponList();
                } else {
                    myCouponAdapter.stopMore();
                    myCouponAdapter.setNoMore(R.layout.content_erv_nomore);
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
