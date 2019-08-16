package com.ecarxclub.app.adapter.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.utils.ToastUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的地址列表
 */

public class MyAddressEaseRcvAdapter extends RecyclerArrayAdapter<MyAddressRes.DataBean.ListBean> {

    private Context context;
    private onViewClicklistener onViewClicklistener;
    private int position;

    public MyAddressEaseRcvAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        ToastUtils.e("OnCreateViewHolder ", "utype ");
        return new AddressViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        this.position = position;
        super.OnBindViewHolder(holder, position);
    }

    class AddressViewHolder extends BaseViewHolder<MyAddressRes.DataBean.ListBean> {
        @BindView(R.id.item_tv_address_name)
        TextView tvName;
        @BindView(R.id.item_tv_address_phone)
        TextView tvPhone;
        @BindView(R.id.item_tv_address_info)
        TextView tvAddress;
        @BindView(R.id.item_tv_address_edit)
        TextView tvEdit;
        @BindView(R.id.item_tv_address_delete)
        TextView tvDelete;
        @BindView(R.id.item_cb_address_default)
        CheckBox checkBox;

        public AddressViewHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_mine_addresslist);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(final MyAddressRes.DataBean.ListBean data) {
            super.setData(data);
            tvName.setText(data.addrUserName);
            tvPhone.setText(data.addrUserMobile);
            tvAddress.setText(data.province+data.city+data.region+data.address);
            if(YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainAddressId!=null&&YxbApplication.userInfoRes.data.mainAddressId.equals(data.id)){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
            if (data.bind){
                checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
            checkBox.setTag(position);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        onViewClicklistener.onCheckBoxClick((Integer) checkBox.getTag());
                    }else {
                        checkBox.setChecked(true);
                        ToastUtils.i("选中","");
                    }
                }
            });

            tvEdit.setTag(position);
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClicklistener.onEditClick((Integer) tvEdit.getTag());
                }
            });

            tvDelete.setTag(position);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClicklistener.onDeleteClick((Integer) tvDelete.getTag());
                }
            });
        }
    }


    public void setOnViewClicklistener(onViewClicklistener onImgAddClicklistener) {
        this.onViewClicklistener = onImgAddClicklistener;
    }

    public interface onViewClicklistener {//
        void onCheckBoxClick(int postion);//
        void onEditClick(int postion);
        void onDeleteClick(int postion);
    }

}
