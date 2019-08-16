package com.ecarxclub.app.ui.mine.order;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.mine.MyOrderFragment;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.event.EventMessagePay;
import com.ecarxclub.app.presenter.mine.order.MyOrderBasePresenter;
import com.ecarxclub.app.presenter.mine.order.MyOrderBaseView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.androidkun.xtablayout.XTabLayout;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/26 11:58.
 * 我的订单
 */
@BindEventBus
public class MyOrderActivity extends BaseActivityP<MyOrderBasePresenter> implements MyOrderBaseView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.vp_mine_coupon)
    ViewPager viewPager;
    @BindView(R.id.xTabLayout_mine_coupon)
    XTabLayout xTabLayout;
    private String[] mallTitles = {"全部", "待付款", "待发货","已发货","已签收"};
    private ArrayList<MyOrderFragment> listFragment = new ArrayList<>();
    private String mState="";
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("我的订单");
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
    public void initData() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MyOrderActivity.this.getSupportFragmentManager());
        listFragment.add(MyOrderFragment.newInstance(""));
        listFragment.add(MyOrderFragment.newInstance("0"));
        listFragment.add(MyOrderFragment.newInstance("1"));//0
        listFragment.add(MyOrderFragment.newInstance("2"));//0
        listFragment.add(MyOrderFragment.newInstance("5"));//0
        viewPagerAdapter.setTitles(mallTitles);
        viewPagerAdapter.setFragments(listFragment);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(viewPagerAdapter);//给ViewPager设置适配器
        xTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
//        xTabLayout.getTabAt(0).select();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_coupon;
    }
    @Override
    public void onMyOrderResult(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected MyOrderBasePresenter createPresenter() {
        return new MyOrderBasePresenter(this);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;
        private ArrayList<MyOrderFragment> mallFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public void setFragments(ArrayList<MyOrderFragment> mallFragments) {
            this.mallFragments = mallFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mallFragments.get(position);
        }

        @Override
        public int getCount() {
            return mallFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ToastUtils.i("activityActivityResult "+mState,"___onresult "+resultCode+"___"+data.getExtras().getString("pay_result"));
        super.onActivityResult(requestCode,resultCode,data);
        EventBus.getDefault().post(new EventMessage(Constant.USER_PAY_YINLIAN,data,mState));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessagePay eventMessage) {
        ToastUtils.e("EventMessagePay  " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_PAY_YINLIAN_STATE:// 银联支付  传过来fragment的index
                mState= (String) eventMessage.object;
                break;
        }
    }
}
