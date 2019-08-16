package com.ecarxclub.app.fragment.shop;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.mine.coupon.MyCouponAdapter;
import com.ecarxclub.app.adapter.shop.IntegralRecordAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.BaseFragment;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.MyCouponRes;
import com.ecarxclub.app.model.shop.IntegralRecordRes;
import com.ecarxclub.app.presenter.mine.coupon.MyCouPonView;
import com.ecarxclub.app.presenter.mine.coupon.MyCouponPresenter;
import com.ecarxclub.app.presenter.shop.fragment.IntegralRecodeFgPresenter;
import com.ecarxclub.app.presenter.shop.fragment.IntegralRecodeFgView;
import com.ecarxclub.app.ui.mine.coupon.CouponDetailsActivity;
import com.ecarxclub.app.ui.shop.IntegralRecordActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by cwp
 * on 2019/4/24 14:20.
 * 我的卡券
 */
public class IntegralRecodeFragment extends BaseFragment<IntegralRecodeFgPresenter> implements IntegralRecodeFgView {
    @BindView(R.id.easyrv_list)
    EasyRecyclerView easyRecyclerView;

    private String status;//1全部 2获取 3使用

    private IntegralRecordAdapter integralRecordAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
    //    private int pagesize = 10;
    private boolean indexPage=false;
    private String mData="";
    private List<IntegralRecordRes.DataBean.RecordBean.ListBean> listBeans=new ArrayList<>();
    private List<IntegralRecordRes.DataBean.RecordBean.ListBean> lstData=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list;
    }

    @Override
    public void initView() {
        initEaseRecycleView();
    }

    @Override
    public void initData() {
        status=getArguments().getString("status");
        onGetRecordList();
    }
    public void onGetRecordList(){
        presenter.onRecordList(status,pageindex,Constant.GET_LIST_SIZE);
    }

    /**
     * 传入需要的参数，设置给arguments
     */
    public static IntegralRecodeFragment newInstance(String statu) {
        Bundle bundle = new Bundle();
        bundle.putString("status", statu);
        IntegralRecodeFragment integralRecodeFragment = new IntegralRecodeFragment();
        integralRecodeFragment.setArguments(bundle);
        return integralRecodeFragment;
    }


    @Override
    protected IntegralRecodeFgPresenter createPresenter() {
        return new IntegralRecodeFgPresenter(this);
    }

    public void initEaseRecycleView(){
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(integralRecordAdapter=new IntegralRecordAdapter(getActivity()));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listBeans.clear();
                mData="";
                ToastUtils.i("onRefresh","+++++++"+pageindex+"____"+indexPage+"----------------"+listBeans.size());
                pageindex=1;
                onGetRecordList();
            }
        });
        integralRecordAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                ToastUtils.i("moreshow","zzzzzzzzzzzzzzzzzzzzzzz+++++++"+pageindex+"____"+indexPage);
                if (indexPage) {
                    ToastUtils.i("moreshow","3333333+++++++"+pageindex+"____"+indexPage);
                    onGetRecordList();
                } else {
                    integralRecordAdapter.stopMore();
                    integralRecordAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }
            @Override
            public void onMoreClick() {

            }
        });
        ImageView imageView=easyRecyclerView.getEmptyView().findViewById(R.id.img_erv_empty);
        imageView.setBackgroundResource(R.mipmap.pic_03);
        TextView textView=easyRecyclerView.getEmptyView().findViewById(R.id.tv_erv_empty);
        textView.setText("暂无卡积分记录");
    }


    @Override
    public void onReordList(IntegralRecordRes integralRecordRes) {
        if (integralRecordRes.success){
            EventBus.getDefault().post(new EventMessage(Constant.USER_INTEGRAL,(int)integralRecordRes.data.integral));
            if(integralRecordRes.data!=null&&integralRecordRes.data.record.list!=null){
                lstData=integralRecordRes.data.record.list;
                for (int i=0;i<lstData.size();i++){
                    if (mData.equals(lstData.get(i).createTime.substring(0, 7))){
                        lstData.get(i).isShow=false;
                    }else {
                        lstData.get(i).isShow=true;
                    }
                    mData=lstData.get(i).createTime.substring(0, 7);
                }
                listBeans.addAll(lstData);
                indexPage=(integralRecordRes.data.record.list.size()==Constant.GET_LIST_SIZE)?true:false;
                if(pageindex==1){
                    if(integralRecordRes.data.record.list.size()==0){
                        easyRecyclerView.showEmpty();
                    }else {
                        integralRecordAdapter.clear();
                        integralRecordAdapter.addAll(lstData);
                    }
                }else {
                    integralRecordAdapter.addAll(lstData);
                }
            }else {
                easyRecyclerView.showError();
            }
        }
    }
}
