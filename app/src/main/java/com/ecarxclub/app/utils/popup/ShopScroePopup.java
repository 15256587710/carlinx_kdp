package com.ecarxclub.app.utils.popup;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.utils.popup.base.BasePopupWindow;


/**
 * 作者：wecent
 * 时间：2018/5/2
 * 描述：
 */

public class ShopScroePopup extends BasePopupWindow {

    private OnSelectListener onSelectListener;
    private Activity mActivity;
    private LinearLayout poppuShadow;
//    private TextView tvCancel,tvSure;
    private TextView tvNum;
//    private EditText etShare;

    public ShopScroePopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mActivity = context;
    }

    @Override
    public View getPopupView() {
        View view = getPopupViewById(R.layout.popup_shop_score);
        poppuShadow = (LinearLayout) view.findViewById(R.id.popup_ll_shop);
        poppuShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvNum=view.findViewById(R.id.popup_tv_score);

//        tvSure=view.findViewById(R.id.tv_pop_cartsure);
//        tvCancel=view.findViewById(R.id.tv_pop_cartshare);
//        etShare=view.findViewById(R.id.et_pop_cart_share);
//        tvNum=view.findViewById(R.id.tv_pop_cart_num);
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        tvSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSelectListener.onClick(etShare.getText().toString());
//            }
//        });
        return view;
    }

    public void setData(String scroe){
        tvNum.setText(scroe);
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
        void onClick(String str);
    }
}
