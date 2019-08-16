package com.ecarxclub.app.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.home.ChooseCarAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.home.car.BindCarRes;
import com.ecarxclub.app.presenter.home.ChooseCarPresenter;
import com.ecarxclub.app.presenter.home.ChooseCarView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/6/12 17:15.
 */
public class ChooseCarActivity extends BaseActivityP<ChooseCarPresenter> implements ChooseCarView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
//    @BindView(R.id.tv_toolbar_right)
//    TextView tvToolbarRight;
    @BindView(R.id.erv_mine_choosecar)
    EasyRecyclerView easyRecyclerView;
    @BindView(R.id.btn_choose_bind)
    Button btnBind;
    @BindView(R.id.ll_choose_phone)
    LinearLayout llPhone;

    private ChooseCarAdapter chooseCarAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
    private int selectPostion;
    private boolean isMore;
    private List<BindCarRes.DataBean> listBeans = new ArrayList<>();
    private String mCheckCarId="";//有数据  选中了车辆

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_car;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("选择车辆");
        initEasyRecyclerView();
    }

    @Override
    public void initData() {
        presenter.ongetAllCar();
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        RxView.clicks(llPhone).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                YxbContent.getCallPhone(ChooseCarActivity.this);
            }
        });
        //绑定车辆
        RxView.clicks(btnBind).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(mCheckCarId.length()>0){
                    presenter.ongBindCar(mCheckCarId);
                }else {
                    ToastUtils.showTextShort("请选择绑定车辆");
                }
            }
        });
    }

    private void setCheckCar(int pos) {
        for (int i = 0; i < listBeans.size(); i++) {
            if (pos==i) {
                if(listBeans.get(i).bind){
                    mCheckCarId=listBeans.get(i).carId;
                }else {
                    mCheckCarId="";
                }
            }else {
                listBeans.get(i).bind=false;
            }
            ToastUtils.i("车辆id  ", "" + listBeans.get(i).bind);
        }
        chooseCarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChooseV(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            ToastUtils.showTextShort("绑定成功");
            EventBus.getDefault().post(new EventMessage(Constant.USER_CHANGE_CAR,""));
            startIntent(ChooseCarActivity.this, MainActivity.class);
        }
    }

    @Override
    public void onGetAllCarV(BindCarRes bindCarRes) {
        if (bindCarRes.success) {
            listBeans = bindCarRes.data;
            isMore = (bindCarRes.data.size() == Constant.GET_LIST_SIZE) ? true : false;
            if (pageindex == 1) {
                if (bindCarRes.data.size() == 0) {
                    easyRecyclerView.showEmpty();
                } else {
                    chooseCarAdapter.clear();
                    chooseCarAdapter.addAll(bindCarRes.data);
                }
            } else {
                chooseCarAdapter.addAll(bindCarRes.data);
            }
        }
    }

    @Override
    protected ChooseCarPresenter createPresenter() {
        return new ChooseCarPresenter(this);
    }


    /**
     * EasyRecyclerView
     */
    private void initEasyRecyclerView() {
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseCarActivity.this));
        easyRecyclerView.setAdapterWithProgress(chooseCarAdapter = new ChooseCarAdapter(ChooseCarActivity.this));
        chooseCarAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.i("onclick___", "" + position);
            }
        });

        chooseCarAdapter.setOnViewClickListener(new ChooseCarAdapter.onViewClickListenre() {
            @Override
            public void onViewClick(int index) {
                setCheckCar(index);
            }
        });
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.i("刷新", "2222222222");
                pageindex = 1;
                listBeans.clear();
                initData();
            }
        });
        chooseCarAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                if (isMore) {
                    initData();
                } else {
                    chooseCarAdapter.stopMore();
                    chooseCarAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }

            @Override
            public void onMoreClick() {

            }
        });
        TextView view = easyRecyclerView.getEmptyView().findViewById(R.id.tv_erv_empty);
        view.setText("暂无选择车辆");
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

}
