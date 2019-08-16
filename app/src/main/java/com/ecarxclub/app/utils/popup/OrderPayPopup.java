package com.ecarxclub.app.utils.popup;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.utils.popup.base.BasePopupWindow;


/**
 * 作者：wecent
 * 时间：2018/5/2
 * 描述：
 */

public class OrderPayPopup extends BasePopupWindow {

    private OnSelectListener onSelectListener;
    private Activity mActivity;
    private RelativeLayout poppuShadow;
    private TextView tvCancel,tvPhonePay;
    private ImageView imgPhonePay;
    private LinearLayout llAlipay,llWechat,llYinlian,llPhonePay;

    public OrderPayPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mActivity = context;
    }

    @Override
    public View getPopupView() {
        View view = getPopupViewById(R.layout.popup_order_pay);
        poppuShadow = view.findViewById(R.id.popup_shadow);
        poppuShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvCancel=view.findViewById(R.id.pop_orderpay_cancel);
        llAlipay=view.findViewById(R.id.pop_ll_orderpay_alipay);
        llWechat=view.findViewById(R.id.pop_ll_orderpay_wechat);
        llYinlian=view.findViewById(R.id.pop_ll_orderpay_yl);
        llPhonePay=view.findViewById(R.id.ll_pay_phone);
        tvPhonePay=view.findViewById(R.id.tv_pay_phone);
        imgPhonePay=view.findViewById(R.id.img_pay_phone);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onAlipayClick();
                dismiss();
            }
        });
        llWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onWechatClick();
                dismiss();
            }
        });
        llYinlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onYinlianClick();
                dismiss();
            }
        });
        llPhonePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onPhonePayClick();
                dismiss();
            }
        });
        return view;
    }

    public void setData(String mstype,String  msname){
        if (!mstype.equals("")){
            llPhonePay.setVisibility(View.VISIBLE);
            if ("02".equals(mstype)) {
                imgPhonePay.setBackgroundResource(R.mipmap.img_samsung);
                tvPhonePay.setText(msname);
            } else if ("04".equals(mstype)) {
                imgPhonePay.setBackgroundResource(R.mipmap.img_huawei);
                tvPhonePay.setText(msname);
            } else if ("27".equals(mstype)) {
                imgPhonePay.setBackgroundResource(R.mipmap.img_meizu);
                tvPhonePay.setText(msname);
            } else if ("25".equals(mstype)) {
                imgPhonePay.setBackgroundResource(R.mipmap.img_xiaomi);
                tvPhonePay.setText(msname);
            }
        }else {
            llPhonePay.setVisibility(View.GONE);
        }
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }

    public interface OnSelectListener {
        void onAlipayClick();
        void onWechatClick();
        void onYinlianClick();
        void onPhonePayClick();
    }
}
