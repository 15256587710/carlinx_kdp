package com.ecarxclub.app.ui.mine.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.mine.message.MessageAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.mine.MessageRes;
import com.ecarxclub.app.presenter.mine.message.MessagePresenter;
import com.ecarxclub.app.presenter.mine.message.MessageView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/26 13:51.
 * 消息通知
 */
public class MyMessageActivity extends BaseActivityP<MessagePresenter> implements MessageView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.erv_driving_list)
    EasyRecyclerView easyRecyclerView;

    private MessageAdapter messageAdapter;
    private int pageindex = Constant.GET_LIST_INDEX;
    private boolean isMore;
    private List<MessageRes.DataBean.ListBean> lstMsg=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_drivingrecord;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("消息通知");
        tvToolbarRight.setText("全部已读");
        initEaseRecycleView();
        mSwipeBackLayout.setSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }
            @Override
            public void onEdgeTouch(int edgeFlag) {
            }
            @Override
            public void onScrollOverThreshold() {
            }
        });
    }

    @Override
    public void initData() {
        onMsgList();
    }

    public void onMsgList(){
        presenter.onMessageList(pageindex, Constant.GET_LIST_SIZE);
    }
    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                setFinishAct();
                finish();
            }
        });
        RxView.clicks(tvToolbarRight).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.onReadMsg(null);
            }
        });
    }

    public void setFinishAct(){
        EventBus.getDefault().post(new EventMessage(Constant.USER_INFO_SUCCESS, "1"));
        finish();
    }

    @Override
    public void onGetMessageList(MessageRes messageRes) {
        if(messageRes.success&&messageRes.data.list!=null){
            lstMsg=messageRes.data.list;
            isMore=(messageRes.data.list.size()==Constant.GET_LIST_SIZE)?true:false;
            if(pageindex==1){
                if(messageRes.data.list.size()==0){
                    easyRecyclerView.showEmpty();
                }else {
                    messageAdapter.clear();
                    messageAdapter.addAll(messageRes.data.list);
                }
            }else {
                messageAdapter.addAll(messageRes.data.list);
            }
        }
    }

    //读取消息
    @Override
    public void onReadMsg(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            lstMsg.clear();
            pageindex=1;
            onMsgList();
        }
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(this);
    }


    public void initEaseRecycleView(){
        easyRecyclerView.setRefreshingColorResources(R.color.btn_yellow_bar);
        easyRecyclerView.setAdapter(messageAdapter=new MessageAdapter(MyMessageActivity.this));
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this));
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lstMsg.clear();
                pageindex=1;
                onMsgList();
            }
        });
        messageAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("msg",lstMsg.get(position));
                startIntent(MyMessageActivity.this,MessageDetailsActivity.class,bundle);
                if (!lstMsg.get(position).read){//未读消息
                    presenter.onReadMsg(lstMsg.get(position).id);
                }
            }
        });
        messageAdapter.setMore(R.layout.content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pageindex++;
                if (isMore) {
                    onMsgList();
                } else {
                    messageAdapter.stopMore();
                    messageAdapter.setNoMore(R.layout.content_erv_nomore);
                }
            }
            @Override
            public void onMoreClick() {

            }
        });
    }

    /**
     * 再按一次推出程序
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
////            setFinishAct();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setFinishAct();
    }
}
