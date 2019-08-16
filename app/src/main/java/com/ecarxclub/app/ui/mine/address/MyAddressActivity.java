package com.ecarxclub.app.ui.mine.address;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.mine.MyAddressEaseRcvAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.presenter.mine.address.MyAddressPresenter;
import com.ecarxclub.app.presenter.mine.address.MyAddressView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/23 15:03.
 * 我的地址
 */
@BindEventBus
public class MyAddressActivity extends BaseActivityP<MyAddressPresenter> implements MyAddressView{
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.erv_mine_address)
    EasyRecyclerView easyRecyclerView;


    private MyAddressEaseRcvAdapter myAddressEaseRcvAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
//    private int pagesize = 10;
    private int indexPage;
    private int selectPostion = -1;//当前点击的item postion

    private List<MyAddressRes.DataBean.ListBean> listAddress=new ArrayList<>();

    private String mStatus="";//1兑换详情进入

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_myaddress;
    }
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarRight.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.homepage_icon_add), null, null, null);
        tvToolbarRight.setText("添加地址");
        tvToolbarTitle.setText("我的地址");
        initEasyRecyclerView();
    }

    @Override
    public void initData() {
        if(getIntent().getExtras()!=null){
            mStatus=getIntent().getExtras().getString("status");
        }
        ToastUtils.i("initdata","+++++="+mStatus);
        onGetAddressList();
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //添加地址
        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(MyAddressActivity.this,AddAddressActivity.class);
            }
        });
    }

    //获取我的地址列表
    public void onGetAddressList(){
        presenter.getForgetCode(true,pageindex,Constant.GET_LIST_SIZE);
    }

    //地址列表
    @Override
    public void onMyAddressList(MyAddressRes myAddressRes) {
        if (myAddressRes.success){
//            listAddress=myAddressRes.data.list;
            listAddress.addAll(myAddressRes.data.list);
            indexPage=myAddressRes.data.pages;
            if(pageindex==1){
                if(myAddressRes.data.list.size()==0){
                    easyRecyclerView.showEmpty();
                }else {
                    myAddressEaseRcvAdapter.clear();
                    myAddressEaseRcvAdapter.addAll(myAddressRes.data.list);
                }
            }else {
                myAddressEaseRcvAdapter.addAll(myAddressRes.data.list);
            }
        }else {
            easyRecyclerView.showError();
        }
    }

//    设置默认地址
    @Override
    public void onSetDefaultAddress(BaseMsgRes baseMsgRes) {
        ToastUtils.i("设置默认地址","11");
        if(baseMsgRes.isSuccess()){
            pageindex = 1;
            listAddress.clear();
            onGetAddressList();
            EventBus.getDefault().post(new EventMessage(Constant.USER_INFO_SUCCESS,""));
        }
    }
//删除地址
    @Override
    public void onDeleteAddress(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            pageindex = 1;
            listAddress.clear();
            onGetAddressList();
        }
    }

    @Override
    protected MyAddressPresenter createPresenter() {
        return new MyAddressPresenter(this);
    }

    /**
     * EasyRecyclerView
     */
    private void initEasyRecyclerView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(MyAddressActivity.this));

        easyRecyclerView.setAdapterWithProgress(myAddressEaseRcvAdapter = new MyAddressEaseRcvAdapter(MyAddressActivity.this));
        myAddressEaseRcvAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.i("onclick___",""+position);
                selectPostion = position;
                if("1".equals(mStatus)){
                    EventBus.getDefault().post(new EventMessage(Constant.EXCHANGE_DETAILS_ADDRESS,listAddress.get(position)));
                    finish();
                }
            }
        });
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.i("刷新","2222222222");
                pageindex = 1;
                listAddress.clear();
                onGetAddressList();
            }
        });
        myAddressEaseRcvAdapter.setOnViewClicklistener(new MyAddressEaseRcvAdapter.onViewClicklistener() {
            @Override
            public void onCheckBoxClick(int postion) {
                presenter.setDefaultAddress(listAddress.get(postion).id);
            }

            @Override
            public void onEditClick(int postion) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("editAddress", listAddress.get(postion));
                startIntent(MyAddressActivity.this,AddAddressActivity.class,bundle);
            }

            @Override
            public void onDeleteClick(int postion) {
                presenter.deleteAddress(listAddress.get(postion).id);
            }
        });
        myAddressEaseRcvAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                ToastUtils.i("moreshow","zzzzzzzzzzzzzzzzzzzzzzz+++++++"+pageindex+"____"+indexPage);
                if (pageindex <= indexPage) {
                    ToastUtils.i("moreshow","3333333+++++++"+pageindex+"____"+indexPage);
                    onGetAddressList();
                } else {
                    myAddressEaseRcvAdapter.stopMore();
                    myAddressEaseRcvAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }

            @Override
            public void onMoreClick() {

            }
        });
//        TextView view = easyRecyclerView.getEmptyView().findViewById(R.id.tv_empty_title);
//        view.setText("暂无采购商品哦");
//        TextView btn = easyRecyclerView.getEmptyView().findViewById(R.id.tv_empty_add);
//        btn.setText("重新加载");
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pageindex = 1;
//                refreshList = 0;
//                listDetails2.clear();
//                getOrderList(pageindex);
//            }
//        });
//        TextView btnError = easyRecyclerView.getErrorView().findViewById(R.id.btn_status_error);
//        btnError.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pageindex = 1;
//                refreshList = 0;
//                listDetails2.clear();
//                getOrderList(pageindex);
//            }
//        });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusAddressAdd(EventMessage eventMessage) {
        ToastUtils.e("action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.MINE_ADD_ADDRESS:
                if (eventMessage.object.equals(Constant.MINE_ADD_ADDRESS_CONTENT)) {
                    pageindex = 1;
                    listAddress.clear();
                    onGetAddressList();
                }
                break;
        }
    }

}
