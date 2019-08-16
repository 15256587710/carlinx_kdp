package com.ecarxclub.app.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.SplashPresenter;
import com.ecarxclub.app.presenter.SplashView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/6/5 14:28.
 */
public class SplashActivity extends BaseActivityP<SplashPresenter> implements SplashView {

    //图片资源文件
    private int[] images = {R.mipmap.splash_1, R.mipmap.splash_2,
            R.mipmap.splash_3, R.mipmap.splash_4};

    @BindView(R.id.vp)
    ViewPager viewPager;
    // 放4个小灰点的线性布局
    @BindView(R.id.ll)
    LinearLayout linearLayout;
    @BindView(R.id.lan_Iv)
    ImageView lan_Iv;
    @BindView(R.id.btn)
    ImageView btn;
    //图片放置
    private List<ImageView> iamgeList=new ArrayList<>();
    //小点之间的距离
    private int pointWidth;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initClick() {
        RxView.clicks(btn).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(SplashActivity.this,MainActivity.class);
            }
        });

    }

    @Override
    public void initView() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iamgeList.add(imageView);

            // 根据图片的个数去放置相应数量的小灰点
            ImageView huiImageView = new ImageView(this);
            huiImageView.setImageResource(R.mipmap.circle_red);
            LinearLayout.LayoutParams layoutParams=
                    new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            huiImageView.setLayoutParams(layoutParams);

            if(i>0){
                //给除了第一个小点的其它小点左边距
                layoutParams.leftMargin=20;
            }

            linearLayout.addView(huiImageView);
        }

        /*我们的代码现在都写在onCreate方法中，onCreate在调用的时候，界面底层的绘制工作还没有完成。所以，如果我们直接在onCreate方法里去
         * 拿距离是拿不到
         * dOnGlobalLayoutListener：在视图树全部都绘制完成的时候调用*/
        lan_Iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //视图全部绘制完成时，计算得到小点之间的距离
                pointWidth=linearLayout.getChildAt(1).getLeft()-linearLayout.getChildAt(0).getLeft();
//                System.out.println(pointWidth);
            }
        });
        //绑定适配器
        viewPager.setAdapter(new MyAdapter());
        //设置图片切换的监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //让滑倒最后一页显示按钮
                if(position==images.length-1){
                    btn.setVisibility(View.VISIBLE);
                }else {
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            //在Viewpger的界面不断滑动的时候会触发
            //position：当前滑到了第几页从0开始计数
            public void onPageScrolled(int position, float offset, int arg2) {
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)lan_Iv.getLayoutParams();
                //滑动的百分比乘上小点之间的距离，再加上当前位置乘上距离，即为蓝色小点的左边距
                layoutParams.leftMargin=(int)(pointWidth*offset+position*pointWidth);
                lan_Iv.setLayoutParams(layoutParams);
//                System.out.println("当前滑动的百分比   "+offset);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
        //设置滑动特效（可加可不加）
        viewPager.setPageTransformer(true, new DepthPageTransformer());

    }

    @Override
    public void onGetImg(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }


    class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) {
                view.setAlpha(0);

            } else if (position <= 0) {
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) {
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else {
                view.setAlpha(0);
            }
        }
    }


    class MyAdapter extends PagerAdapter {
        @Override
        // 返回ViewPager里面有几页
        public int getCount() {
            // TODO Auto-generated method stub
            return images.length;
        }

        @Override
        // 用于判断是否有对象生成界面
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = iamgeList.get(position);
            imageView.setImageResource(images[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

    }


}
