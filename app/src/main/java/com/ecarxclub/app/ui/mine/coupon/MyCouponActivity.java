package com.ecarxclub.app.ui.mine.coupon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.mine.MyCouponFragment;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.mine.coupon.BaseCouponPresenter;
import com.ecarxclub.app.presenter.mine.coupon.BaseCouponView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.androidkun.xtablayout.XTabLayout;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/24 14:00.
 * 我的卡券
 */
public class MyCouponActivity extends BaseActivityP<BaseCouponPresenter> implements BaseCouponView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.vp_mine_coupon)
    ViewPager viewPager;
    @BindView(R.id.xTabLayout_mine_coupon)
    XTabLayout xTabLayout;
    private String[] mallTitles = {"待使用", "已使用", "已过期"};
    private ArrayList<MyCouponFragment> listFragment = new ArrayList<>();

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("我的卡券");
    }

    @Override
    protected BaseCouponPresenter createPresenter() {
        return new BaseCouponPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_coupon;
    }

    @Override
    public void onCouponDetails(BaseMsgRes baseMsgRes) {

    }

    @Override
    public void initData() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MyCouponActivity.this.getSupportFragmentManager());
        listFragment.add(MyCouponFragment.newInstance("1"));
        listFragment.add(MyCouponFragment.newInstance("2"));//0
        listFragment.add(MyCouponFragment.newInstance("-1"));//0
        viewPagerAdapter.setTitles(mallTitles);
        viewPagerAdapter.setFragments(listFragment);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);//给ViewPager设置适配器
        xTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
//        xTabLayout.getTabAt(0).select();
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


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;
        private ArrayList<MyCouponFragment> mallFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public void setFragments(ArrayList<MyCouponFragment> mallFragments) {
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
}
