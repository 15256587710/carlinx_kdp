package com.ecarxclub.app.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.ShareModel;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.shop.integral.TaskListRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;
import com.ecarxclub.app.presenter.shop.IntegralCenterPresenter;
import com.ecarxclub.app.presenter.shop.IntegralCenterView;
import com.ecarxclub.app.share.ShareUtils;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.ui.home.MyCarActivity;
import com.ecarxclub.app.ui.mine.BindWechatSetActivity;
import com.ecarxclub.app.ui.mine.EditUserInfoActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.BottomAlertDialog;
import com.ecarxclub.app.views.WaterView;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/11 10:21.
 * 积分中心
 */
@BindEventBus
public class IntegralCenterActivity extends BaseActivityP<IntegralCenterPresenter> implements IntegralCenterView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.wv_ic)
    WaterView waterView;
    @BindView(R.id.tv_ic_integral_all)
    TextView tvIntegralAll;//总积分
    @BindView(R.id.tv_ic_timetxt)
    TextView tvTimeTxt;
    @BindView(R.id.rcv_ic_everyday)
    RecyclerView recyclerViewEveryday;//每日任务
    @BindView(R.id.rcv_ic_new)
    RecyclerView recyclerViewNewTask;//新手任务

    RecyclerViewAdapter recyclerViewAdapter;
    private String mShare="";//1分享里程
    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_center;
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //积分记录
        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(IntegralCenterActivity.this,IntegralRecordActivity.class);
            }
        });
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        tvToolbarTitle.setText("积分中心");
        tvToolbarRight.setText("积分记录");
        tvToolbarRight.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void initData() {
        getWaterInfo();
        getTaskList();
    }

    private void getWaterInfo(){//气泡  积分
        presenter.getWaterPoint();
    }

    private void getTaskList(){//任务列表
        presenter.getTaskListPre();
    }

    @Override
    protected IntegralCenterPresenter createPresenter() {
        return new IntegralCenterPresenter(this);
    }

    @Override
    public void onGetPointList(WaterRes waterRes) {
        if (waterRes.success) {
            tvIntegralAll.setText((int) waterRes.data.integral + "");
            tvTimeTxt.setText(waterRes.data.tip);
            waterView.setWaters(waterRes.data.bubble);
            waterView.setOnViewClickListener(new WaterView.onViewClickListener() {
                @Override
                public void onViewClick(WaterRes.DataBean.BubbleBean dataBean) {
                    ToastUtils.i("水滴 ", dataBean.integral + "____" + dataBean.remark);
                    presenter.getPointId(dataBean.id);
                }
            });
        }
    }

    @Override
    public void onGetTaskList(TaskListRes taskListRes) {
        if (taskListRes.success){
            //每日任务
            if (taskListRes.data.todayTask!=null&&taskListRes.data.todayTask.size()>0){
                recyclerViewEveryday.setLayoutManager(new LinearLayoutManager(IntegralCenterActivity.this));
                recyclerViewEveryday.setNestedScrollingEnabled(false);
                recyclerViewAdapter = new RecyclerViewAdapter<TaskListRes.DataBean.TodayTaskBean>(IntegralCenterActivity.this,
                        taskListRes.data.todayTask, R.layout.item_integral_task) {
                    @Override
                    public void convert(RecyclerViewHolder holder, final TaskListRes.DataBean.TodayTaskBean item,int pos) {
                        TextView tvTitle=holder.getView(R.id.item_tv_task_title);
                        if (item.taskType==2){//邀请好友
                            if (item.requireCount>1){
                                tvTitle.setText(item.taskName+"("+item.finishCount+"/"+item.requireCount+")");
                            }else {
                                tvTitle.setText(item.taskName);
                            }
                        }else {
                            tvTitle.setText(item.taskName);
                        }
                        LinearLayout llContent=holder.getView(R.id.item_ll_task_content);
                        TextView tvContent=holder.getView(R.id.item_tv_task_content);
                        if (item.taskType==1){//签到  不显示积分
                            llContent.setVisibility(View.GONE);
                        }else {
                            llContent.setVisibility(View.VISIBLE);
                            tvContent.setText("+"+(int)item.integral+item.unit);
                        }
                        holder.setImageFormUrl(R.id.item_img_task, item.imgPath);
                        TextView tvTag=holder.getView(R.id.item_tv_task_tag);
                        TextView tvSure=holder.getView(R.id.item_tv_task_go);
                        tvTag.setVisibility(View.GONE);
                        if (item.taskType==1){//签到
                            if (item.finish){
                                tvSure.setText("已签到");
                                tvSure.setTextColor(getResources().getColor(R.color.tab_n));
                                tvSure.setBackgroundResource(R.drawable.lay_bggry_radius12);
                            }else {
                                tvSure.setText("去签到");
                                tvSure.setTextColor(getResources().getColor(R.color.white));
                                tvSure.setBackgroundResource(R.drawable.lay_bgred_radius12);
                            }
                        }else if (item.taskType==4){//行驶里程
                            if (item.requireCount>1){
                                tvTag.setVisibility(View.VISIBLE);
                                tvTag.setText("("+item.finishCount+"/"+item.requireCount+")");
                            }
                            if (item.finish){
                                tvSure.setText("已完成");
                                tvSure.setTextColor(getResources().getColor(R.color.tab_n));
                                tvSure.setBackgroundResource(R.drawable.lay_bggry_radius12);
                            }else {
                                tvSure.setText("未完成");
                                tvSure.setTextColor(getResources().getColor(R.color.white));
                                tvSure.setBackgroundResource(R.drawable.lay_bgred_radius12);
                            }
                        }else {
                            if (item.finish){
                                tvSure.setText("已完成");
                                tvSure.setTextColor(getResources().getColor(R.color.tab_n));
                                tvSure.setBackgroundResource(R.drawable.lay_bggry_radius12);
                            }else {
                                tvSure.setText("去完成");
                                tvSure.setTextColor(getResources().getColor(R.color.white));
                                tvSure.setBackgroundResource(R.drawable.lay_bgred_radius12);
                            }
                        }
                        tvSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item.taskType==1){//去签到
                                    if (YxbContent.getIsBindCar()) {//是否绑定车辆
                                        startIntent(IntegralCenterActivity.this,SignInActivity.class);
                                    }else {
                                        YxbContent.goBindCarAct(IntegralCenterActivity.this);
                                    }
                                }else if (item.taskType==2){//邀请好友
                                    mShare="";
                                    Bundle bundle=new Bundle();//"http://192.168.0.107:8080/#/invite?token="+   item.interLink
                                    bundle.putString("web_url", item.interLink+"?token="+SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN,"").toString()+"&wrap=android");
                                    ShareModel shareModel=new ShareModel();
                                    shareModel.shareTitle=item.shareTitle;
                                    shareModel.shareContent=item.shareContent;
                                    shareModel.shareImage=item.shareImage;
                                    shareModel.shareInterLink=item.shareInterLink;
                                    bundle.putSerializable("model",shareModel);
                                    startIntent(IntegralCenterActivity.this,WebViewActivity.class,bundle);
                                }else if (item.taskType==3){//分享里程
                                    if (YxbContent.getIsBindCar()) {//是否绑定车辆
                                        onShare(item);
                                    }else {
                                        YxbContent.goBindCarAct(IntegralCenterActivity.this);
                                    }
                                }else if (item.taskType==4){//行驶里程

                                }else if (item.taskType==5){//玩游戏
                                    Bundle bundle=new Bundle();//"http://192.168.0.107:8080/#/?token="
                                    bundle.putString("web_url",item.interLink+ "?token="+SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN,"").toString()+"&wrap=android");
                                    bundle.putString("welcome","integral");
                                    startIntent(IntegralCenterActivity.this,WebViewActivity.class,bundle);
                                }
                            }
                        });
                    }
                };
                recyclerViewEveryday.setAdapter(recyclerViewAdapter);
            }

            //新手任务
            if (taskListRes.data.newTask!=null&&taskListRes.data.newTask.size()>0){
                recyclerViewNewTask.setLayoutManager(new LinearLayoutManager(IntegralCenterActivity.this));
                recyclerViewNewTask.setNestedScrollingEnabled(false);
                recyclerViewAdapter = new RecyclerViewAdapter<TaskListRes.DataBean.NewTaskBean>(IntegralCenterActivity.this,
                        taskListRes.data.newTask, R.layout.item_integral_task) {
                    @Override
                    public void convert(RecyclerViewHolder holder, final TaskListRes.DataBean.NewTaskBean item,int pos) {
                        holder.setText(R.id.item_tv_task_title, item.taskName);
                        holder.setText(R.id.item_tv_task_content, "+"+(int)item.integral+item.unit);
                        holder.setImageFormUrl(R.id.item_img_task, item.imgPath);
                        TextView tvSure=holder.getView(R.id.item_tv_task_go);
                        if (item.finish){
                            tvSure.setText("已完成");
                            tvSure.setTextColor(getResources().getColor(R.color.tab_n));
                            tvSure.setBackgroundResource(R.drawable.lay_bggry_radius12);
                        }else {
                            tvSure.setText("去完成");
                            tvSure.setTextColor(getResources().getColor(R.color.white));
                            tvSure.setBackgroundResource(R.drawable.lay_bgred_radius12);
                        }
                        tvSure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!item.finish){//未完成
                                    if (item.taskType==6){//绑定车辆
                                        YxbContent.goBindCarAct(IntegralCenterActivity.this);
                                    }else if (item.taskType==7){//注册

                                    }else if (item.taskType==8){//绑定微信
                                        startIntent(IntegralCenterActivity.this, BindWechatSetActivity.class);
                                    }else if (item.taskType==9){//车辆信息完善
                                        if (YxbContent.getIsBindCar()) {//是否绑定车辆
                                            startIntent(IntegralCenterActivity.this, MyCarActivity.class);
                                        }else {
                                            YxbContent.goBindCarAct(IntegralCenterActivity.this);
                                        }
                                    }else if (item.taskType==10){//个人信息完善
                                        startIntent(IntegralCenterActivity.this, EditUserInfoActivity.class);
                                    }
                                }
                            }
                        });
                    }
                };
                recyclerViewNewTask.setAdapter(recyclerViewAdapter);
            }
        }
    }

    //收取水滴
    @Override
    public void onGetPointId(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            getWaterInfo();
        }
    }

    //分享里程
    @Override
    public void onShareSuccess(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            initData();
        }
    }

    private void onShare(final TaskListRes.DataBean.TodayTaskBean todayTaskBean){
        mShare="1";
        View view = LayoutInflater.from(IntegralCenterActivity.this).inflate(R.layout.dialog_share, null);
        LinearLayout ll_share_wechat = view.findViewById(R.id.ll_share_talk);
        LinearLayout ll_share_qq = view.findViewById(R.id.ll_share_qq);
        LinearLayout ll_share_wcFriend = view.findViewById(R.id.ll_share_friends);
        LinearLayout ll_share_weibo = view.findViewById(R.id.ll_share_xinlang);
        ll_share_weibo.setVisibility(View.GONE);
        ll_share_qq.setVisibility(View.GONE);
        TextView tvCancel=view.findViewById(R.id.tv_share_cancel);
        final BottomAlertDialog bottomAlertDialog = new BottomAlertDialog(IntegralCenterActivity.this, view, true, true);
        bottomAlertDialog.show();
        // 微信分享
        ll_share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTypeImage(todayTaskBean.shareImage,SHARE_MEDIA.WEIXIN);
                bottomAlertDialog.dismiss();
            }
        });
        // 微信朋友圈分享
        ll_share_wcFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTypeImage(todayTaskBean.shareImage,SHARE_MEDIA.WEIXIN_CIRCLE);
                bottomAlertDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomAlertDialog.dismiss();
                mShare="";
            }
        });
    }

    //分享里程
    public void shareTypeImage(String url,SHARE_MEDIA platform){
        ShareUtils.shareImg(this,url,platform);
    }

    //分享里程
//    public void shareTypeFriend(TaskListRes.DataBean.TodayTaskBean todayTaskBean,SHARE_MEDIA platform) {
//        ShareUtils.shareWeb(this, todayTaskBean.shareInterLink+"?token="+SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN,"").toString()+"&wrap=android"
//                , todayTaskBean.shareTitle
//                , todayTaskBean.shareContent, todayTaskBean.shareImage,
//                R.drawable.umeng_socialize_share_web, platform
//        );
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
        ToastUtils.e("IntegralCenterActivity  " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_SIGNIN://签到   刷新数据
                initData();
                break;
            case Constant.USER_SHARE_SUCCESS://分享里程成功  刷新数据
                if ("1".equals(mShare)){
                    presenter.getShareSuccess();
                }
                mShare="";
                break;
        }
    }

}
