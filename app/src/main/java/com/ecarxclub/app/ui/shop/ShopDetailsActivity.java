package com.ecarxclub.app.ui.shop;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.shop.ShopDetailsBannerRes;
import com.ecarxclub.app.model.shop.ShopDetailsRes;
import com.ecarxclub.app.presenter.shop.ShopDetailsPresenter;
import com.ecarxclub.app.presenter.shop.ShopDetailsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.banner.demo.NetworkImageHolderView;
import com.ecarxclub.app.utils.banner.holder.CBViewHolderCreator;
import com.ecarxclub.app.utils.banner.listener.OnItemClickListener;
import com.ecarxclub.app.utils.banner.view.ConvenientBanner;
import com.ecarxclub.app.views.ScrollViewForToolBar;
import com.jakewharton.rxbinding.view.RxView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/22 17:02.
 * 商品详情
 */
public class ShopDetailsActivity extends BaseActivityP<ShopDetailsPresenter> implements ShopDetailsView, OnItemClickListener, ScrollViewForToolBar.OnScrollChangedListener {
    @BindView(R.id.ll_toobar)
    LinearLayout llToobar;
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.cb_card)
    ConvenientBanner convenientBanner;
    @BindView(R.id.tv_shopd_name)
    TextView tvName;
    @BindView(R.id.tv_shopd_title)
    TextView tvTitle;
    @BindView(R.id.tv_shopd_scroe)
    TextView tvScroe;
    @BindView(R.id.tv_shopd_content)
    TextView tvContent;
    @BindView(R.id.btn_shopdetails_exchange)
    Button btnExchange;
    @BindView(R.id.sc_shopdetails)
    ScrollViewForToolBar scrollViewForToolBar;

    @BindView(R.id.rcy_shop_tag)
    RecyclerView recyclerView;
    private int imgHeight;

    private List<String> listImg = new ArrayList<>();

    private ShopDetailsRes.DataBean dataBean;
    private String mShopId;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_details;
    }

    @Override
    public void initView() {
        tvToolbarTitle.setText("商品详情");
        tvToolbarTitle.setTextColor(getResources().getColor(R.color.tab_y));
        tvToolbarTitle.setTextColor(Color.argb( 0, 0, 0, 0));
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        scrollViewForToolBar.setOnScrollChangedListener(this);

    }

    @Override
    public void initData() {
        if (getIntent().getExtras() != null) {
            mShopId=getIntent().getExtras().getString("shopid");
            presenter.onShopDetails(mShopId);
            presenter.onGetBanner(mShopId);
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

        //兑换商品
        RxView.clicks(btnExchange).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainCarId!=null) {//是否绑定车辆
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("shopdetails",dataBean);
                        startIntent(ShopDetailsActivity.this, ExchangeDetailsActivity.class,bundle);
                    }else {
                        YxbContent.goBindCarAct(ShopDetailsActivity.this);
                    }
                }else {
                    YxbContent.goLoginAct(ShopDetailsActivity.this);
                }
            }
        });
    }

    private void setPages(List<String> listImg) {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, listImg)
                .setPageIndicator(new int[]{R.mipmap.indicator_select, R.mipmap.indicator_unselect})
                .setOnItemClickListener(this);
    }

    @Override
    protected ShopDetailsPresenter createPresenter() {
        return new ShopDetailsPresenter(this);
    }


    @Override
    public void onBannerDetails(ShopDetailsBannerRes baseMsgRes) {
        if (baseMsgRes.success) {
            for (int i = 0; i < baseMsgRes.data.size(); i++) {
                listImg.add(baseMsgRes.data.get(i).filePath);
            }
            setPages(listImg);
        }
    }

    @Override
    public void onShopDetails(ShopDetailsRes shopDetailsRes) {
        if(shopDetailsRes.success){
            dataBean=shopDetailsRes.data;
            tvName.setText(shopDetailsRes.data.productName);
            tvTitle.setText(shopDetailsRes.data.productCover);
            tvScroe.setText(shopDetailsRes.data.integral + "积分+" + shopDetailsRes.data.money + "元");
            tvContent.setText(Html.fromHtml(shopDetailsRes.data.productRemark));
            if (shopDetailsRes.data.tags!=null&&shopDetailsRes.data.tags.size()>0){
                recyclerView.setLayoutManager(new GridLayoutManager(ShopDetailsActivity.this,shopDetailsRes.data.tags.size()));
                recyclerViewAdapter=new RecyclerViewAdapter<ShopDetailsRes.DataBean.TagsBean>(ShopDetailsActivity.this
                        ,shopDetailsRes.data.tags,R.layout.item_shop_tag) {
                    @Override
                    public void convert(RecyclerViewHolder holder, ShopDetailsRes.DataBean.TagsBean item,int pos) {
                        holder.setText(R.id.item_tv_tag,item.tag.tagName);
                    }
                };
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        int[] location = new int[2];
        tvName.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        ToastUtils.i("" + x, y + "gaotu  " + convenientBanner.getHeight() + "_________" + tvName.getHeight());
    }

    @Override
    public void onSelectChoose(int postion) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (convenientBanner != null) {
            convenientBanner.startTurning(3000);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    imgHeight = convenientBanner.getHeight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (convenientBanner != null) {
            convenientBanner.stopTurning();
        }
        if (listImg!=null){
            listImg.clear();
            listImg=null;
        }
        if (recyclerViewAdapter!=null){
            recyclerViewAdapter=null;
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
//        ToastUtils.i((int)(imgHeight*0.7)+"ttttttt", imgHeight+"______________-" + t);
        if (t <= 0) {
            llToobar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));//AGB由相关工具获得，或者美工提供
            tvToolbarTitle.setTextColor(Color.argb((int) 0, 0, 0, 0));
            imgToolbarLeft.setImageResource(R.mipmap.icon_back);
        } else if (t > 0 && t <= (int)(imgHeight*0.7)) {
            float scale = (float) t / (int)(imgHeight*0.7);
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            llToobar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            tvToolbarTitle.setTextColor(Color.argb((int) alpha, 0, 0, 0));
            imgToolbarLeft.setImageResource(R.mipmap.nav_icon_back);
        } else {
            llToobar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
            tvToolbarTitle.setTextColor(Color.argb((int) 255, 0, 0, 0));
            imgToolbarLeft.setImageResource(R.mipmap.nav_icon_back);
        }

    }
}
