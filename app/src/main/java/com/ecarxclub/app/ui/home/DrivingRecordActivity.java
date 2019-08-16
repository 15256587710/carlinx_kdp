package com.ecarxclub.app.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.home.DrivingListAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.home.MyDrivingListRes;
import com.ecarxclub.app.presenter.home.DrivingPresenter;
import com.ecarxclub.app.presenter.home.DrivingView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/22 16:05.
 * 行驶记录
 */
public class DrivingRecordActivity extends BaseActivityP<DrivingPresenter> implements DrivingView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.erv_driving_list)
    EasyRecyclerView easyRecyclerView;

    private String carId;
    private DrivingListAdapter drivingListAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
    private boolean isMore = false;
    private List<MyDrivingListRes.DataBean.ListBean> listDriving = new ArrayList<>();//总的
    private List<MyDrivingListRes.DataBean.ListBean> myDrivingListRes = new ArrayList<>(); //整合数据
    private String mData="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_drivingrecord;
    }

//    private final Handler mHandler = new Handler();
    @Override
    public void initView() {
        tvToolbarTitle.setText("行驶记录");
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        initEaseRecycleView();
//        //模拟内存泄露
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 3 * 60 * 1000);
//        finish();
    }

    @Override
    public void initData() {
        carId = getIntent().getExtras().getString("carId");
        onGetDrivingList();
    }

    public void onGetDrivingList() {
        presenter.onGetCarDetails(carId, pageindex, 10);
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    @Override
    public void onGetList(MyDrivingListRes baseMsgRes) {
        if (baseMsgRes.success && baseMsgRes.data != null) {
            myDrivingListRes=baseMsgRes.data.list;
            for (int i=0;i<myDrivingListRes.size();i++){
                if (mData.equals(myDrivingListRes.get(i).endDate)){
                    myDrivingListRes.get(i).isShow=false;
                }else {
                    myDrivingListRes.get(i).isShow=true;
                }
                mData=myDrivingListRes.get(i).endDate;
            }
            listDriving.addAll(myDrivingListRes) ;
            isMore = (baseMsgRes.data.list.size() == Constant.GET_LIST_SIZE) ? true : false;
            if (pageindex == 1) {
                if (baseMsgRes.data.list.size() == 0) {
                    easyRecyclerView.showEmpty();
                } else {
                    drivingListAdapter.clear();
                    drivingListAdapter.addAll(myDrivingListRes);
                }
            } else {
                drivingListAdapter.addAll(myDrivingListRes);
            }
        }
    }

    public void initEaseRecycleView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(drivingListAdapter = new DrivingListAdapter(DrivingRecordActivity.this));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(DrivingRecordActivity.this));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listDriving.clear();
                pageindex = 1;
                onGetDrivingList();
            }
        });
        drivingListAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        drivingListAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                ToastUtils.i("加载更多", "___" + isMore);
                if (isMore) {
                    onGetDrivingList();
                } else {
                    drivingListAdapter.stopMore();
                    drivingListAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }

            @Override
            public void onMoreClick() {

            }
        });
    }

    @Override
    protected DrivingPresenter createPresenter() {
        return new DrivingPresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.i("lis  "+listDriving.size()," res "+myDrivingListRes.size());
        if (listDriving!=null){
            listDriving.clear();
            listDriving=null;
        }
        if (myDrivingListRes!=null){
            myDrivingListRes.clear();
            myDrivingListRes=null;
        }
        if (drivingListAdapter!=null){
            drivingListAdapter.clear();
            drivingListAdapter=null;
        }
        System.gc();
    }
}
