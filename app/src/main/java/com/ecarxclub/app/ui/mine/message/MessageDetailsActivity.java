package com.ecarxclub.app.ui.mine.message;

import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.mine.MessageRes;
import com.ecarxclub.app.ui.BaseActivity;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/6/10 14:24.
 * 消息详情
 */
public class MessageDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_message_title)
    TextView tvTitle;
    @BindView(R.id.tv_message_time)
    TextView tvTime;
    @BindView(R.id.tv_message_content)
    TextView tvContent;

    private MessageRes.DataBean.ListBean listBean;
    @Override
    public int initContenttView() {
        return R.layout.activity_mine_messagedetails;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("消息详情");
    }

    @Override
    public void initData() {
        if (getIntent().getExtras()!=null){
            listBean= (MessageRes.DataBean.ListBean) getIntent().getExtras().getSerializable("msg");
            tvTitle.setText(listBean.title);
            tvTime.setText(listBean.createTime);
            tvContent.setText(listBean.content);
        }
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
}
