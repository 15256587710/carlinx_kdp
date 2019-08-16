package com.ecarxclub.app.ui.card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.card.CardTagsFragment;
import com.ecarxclub.app.model.card.CardCouponTypeRes;
import com.ecarxclub.app.presenter.card.CardTagBasePresenter;
import com.ecarxclub.app.presenter.card.CardTagBaseView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.androidkun.xtablayout.XTabLayout;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/25 11:35.
 * 卡券分类
 */
public class CardTagsActivity extends BaseActivityP<CardTagBasePresenter> implements CardTagBaseView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.vp_mine_coupon)
    ViewPager viewPager;
    @BindView(R.id.xTabLayout_mine_coupon)
    XTabLayout xTabLayout;
    //    private String[] mallTitles = {"待使用", "已使用", "已过期"};
    private List<String> listTag = new ArrayList<>();
    private ArrayList<CardTagsFragment> listFragment = new ArrayList<>();

    private int selectIndex;

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("卡券分类");
    }

    @Override
    public void initData() {
        selectIndex = getIntent().getExtras().getInt("tabSelect");
        presenter.getCardTags();
    }

    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_tags;
    }

    @Override
    protected CardTagBasePresenter createPresenter() {
        return new CardTagBasePresenter(this);
    }

    @Override
    public void onGetCardTags(CardCouponTypeRes cardCouponTypeRes) {
        if (cardCouponTypeRes.success) {
            for (int i = -1; i < cardCouponTypeRes.data.size(); i++) {
                if (i == -1) {
                    listTag.add("全部");
                    listFragment.add(CardTagsFragment.newInstance(""));//0
                } else {
                    listTag.add(cardCouponTypeRes.data.get(i).typeName);
                    listFragment.add(CardTagsFragment.newInstance(cardCouponTypeRes.data.get(i).id));//0
                }
            }
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(CardTagsActivity.this.getSupportFragmentManager());
//            listFragment.add(CardTagsFragment.newInstance("1"));
//            listFragment.add(CardTagsFragment.newInstance("2"));//0
//            listFragment.add(CardTagsFragment.newInstance("-1"));//0
            viewPagerAdapter.setTitles(listTag);
            viewPagerAdapter.setFragments(listFragment);
            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(viewPagerAdapter);//给ViewPager设置适配器
            xTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
            xTabLayout.getTabAt(selectIndex).select();
        }
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        //        private String[] titles;
        private List<String> list;
        private ArrayList<CardTagsFragment> mallFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //        public void setTitles(String[] titles) {
//            this.titles = titles;
//        }
        public void setTitles(List<String> list) {
            this.list = list;
        }

        public void setFragments(ArrayList<CardTagsFragment> mallFragments) {
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
//            return titles[position];
            return list.get(position);
        }
    }

}
