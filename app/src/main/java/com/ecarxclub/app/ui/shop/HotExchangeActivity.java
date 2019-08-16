package com.ecarxclub.app.ui.shop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.fragment.shop.HotExchangeFragment;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.presenter.shop.HotExchangeBasePresenter;
import com.ecarxclub.app.presenter.shop.HotExchangeBaseView;
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
 * on 2019/4/29 11:18.
 * 商品分类
 */
public class HotExchangeActivity extends BaseActivityP<HotExchangeBasePresenter> implements HotExchangeBaseView{
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.vp_mine_coupon)
    ViewPager viewPager;
    @BindView(R.id.xTabLayout_mine_coupon)
    XTabLayout xTabLayout;
    private List<String> listTag=new ArrayList<>();
    private ArrayList<HotExchangeFragment> listFragment = new ArrayList<>();


    private ShopTagsRes shopTagsRes;
    private int strChooseIndex;
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("商品分类");
    }

    @Override
    public void initData() {
        shopTagsRes= (ShopTagsRes) getIntent().getExtras().getSerializable("hotTag");
        strChooseIndex=getIntent().getExtras().getInt("changeTag");
//        presenter.getCardTags();
        if (shopTagsRes!=null&&shopTagsRes.data!=null){
            for (int i=-1;i<shopTagsRes.data.size();i++){
                if(i==-1){
                    listTag.add("全部");
                    listFragment.add(HotExchangeFragment.newInstance("",i));//-1
                }else {
                    listTag.add(shopTagsRes.data.get(i).tagName);
                    listFragment.add(HotExchangeFragment.newInstance(shopTagsRes.data.get(i).id,i));//0
                }
            }
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(HotExchangeActivity.this.getSupportFragmentManager());
//            listFragment.add(CardTagsFragment.newInstance("1"));
//            listFragment.add(CardTagsFragment.newInstance("2"));//0
//            listFragment.add(CardTagsFragment.newInstance("-1"));//0
        viewPagerAdapter.setTitles(listTag);
        viewPagerAdapter.setFragments(listFragment);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(viewPagerAdapter);//给ViewPager设置适配器
        xTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来
        xTabLayout.getTabAt(strChooseIndex).select();
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
    public void onGetResult(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected HotExchangeBasePresenter createPresenter() {
        return new HotExchangeBasePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_tags;
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        //        private String[] titles;
        private List<String> list;
        private ArrayList<HotExchangeFragment> mallFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //        public void setTitles(String[] titles) {
//            this.titles = titles;
//        }
        public void setTitles(List<String> list) {
            this.list = list;
        }
        public void setFragments(ArrayList<HotExchangeFragment> mallFragments) {
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
