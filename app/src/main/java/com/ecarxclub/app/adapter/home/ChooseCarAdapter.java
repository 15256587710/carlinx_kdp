package com.ecarxclub.app.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.home.car.BindCarRes;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 * 车辆选择
 */
public class ChooseCarAdapter extends RecyclerArrayAdapter<BindCarRes.DataBean> {
    private int pos;
    private onViewClickListenre onViewClickListenre;
    public ChooseCarAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopFgViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
//        ToastUtils.i("OnBindViewHolder",""+position);
        pos=position;
        super.OnBindViewHolder(holder, position);
    }

    class ShopFgViewHolder extends BaseViewHolder<BindCarRes.DataBean> {//
        private TextView tvTitle;
        private ImageView img;
        private CheckBox checkBox;

        public ShopFgViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_choose_car);
            tvTitle = $(R.id.item_tv_choosecar_title);
            img = $(R.id.item_img_car);
            checkBox=$(R.id.itme_cb_select);
        }

        @Override
        public void setData(final BindCarRes.DataBean data) {
            if(data!=null){
                tvTitle.setText(data.vin);
//                Glide.with(getContext())
//                        .load(data.productIcon)
//                        .apply(YxbContent.options)
//                        .into(img);
                if (data.bind){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }
                checkBox.setTag(pos);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()){
                            data.bind=true;
                        }else {
                            data.bind=false;
                        }
                        onViewClickListenre.onViewClick((Integer) checkBox.getTag());
                    }
                });
            }
        }
    }



    public void setOnViewClickListener(onViewClickListenre onViewClickListener){
        this.onViewClickListenre=onViewClickListener;
    }

    public interface onViewClickListenre{
        void onViewClick(int index);
    }

}
