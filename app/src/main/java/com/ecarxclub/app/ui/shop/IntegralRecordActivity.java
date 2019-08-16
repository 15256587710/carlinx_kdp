package com.ecarxclub.app.ui.shop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.shop.IntegralRecordAdapter;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.mine.MyCouponFragment;
import com.ecarxclub.app.fragment.mine.MyOrderFragment;
import com.ecarxclub.app.fragment.shop.IntegralRecodeFragment;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.event.EventMessagePay;
import com.ecarxclub.app.model.shop.IntegralRecordRes;
import com.ecarxclub.app.presenter.shop.IntegralRecordPresenter;
import com.ecarxclub.app.presenter.shop.IntegralRecordView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.mine.coupon.MyCouponActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.androidkun.xtablayout.XTabLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/28 10:23.
 * 积分记录
 */
@BindEventBus
public class IntegralRecordActivity extends BaseActivityP<IntegralRecordPresenter> implements IntegralRecordView{
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_ir_score)
    TextView tvIntegral;

    @BindView(R.id.vp_mine_coupon)
    ViewPager viewPager;
    @BindView(R.id.xTabLayout_mine_coupon)
    XTabLayout xTabLayout;
    private String[] mallTitles = {"全部", "获取", "使用"};
    private ArrayList<IntegralRecodeFragment> listFragment = new ArrayList<>();

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
    public void initView() {
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        tvToolbarTitle.setText("咖豆记录");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(IntegralRecordActivity.this.getSupportFragmentManager());
        listFragment.add(IntegralRecodeFragment.newInstance("1"));
        listFragment.add(IntegralRecodeFragment.newInstance("2"));
        listFragment.add(IntegralRecodeFragment.newInstance("3"));
        viewPagerAdapter.setTitles(mallTitles);
        viewPagerAdapter.setFragments(listFragment);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);//给ViewPager设置适配器
        xTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
//        xTabLayout.getTabAt(0).select();
    }

    @Override
    public void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.erv_list_activity;
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;
        private ArrayList<IntegralRecodeFragment> mallFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public void setFragments(ArrayList<IntegralRecodeFragment> mallFragments) {
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
    public void onReordList(IntegralRecordRes integralRecordRes) {
    }

    @Override
    protected IntegralRecordPresenter createPresenter() {
        return new IntegralRecordPresenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage eventMessage) {
        ToastUtils.e("IntegralRecordActivity  " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_INTEGRAL:// 银联支付  传过来fragment的index
                int mState= (int) eventMessage.object;
                tvIntegral.setText(mState+"");
                break;
        }
    }
}
