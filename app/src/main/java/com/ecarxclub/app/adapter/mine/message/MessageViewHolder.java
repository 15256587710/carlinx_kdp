package com.ecarxclub.app.adapter.mine.message;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.mine.MessageRes;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by cwp
 * on 2019/4/25 16:41.
 */
public class MessageViewHolder extends BaseViewHolder<MessageRes.DataBean.ListBean> {
    private TextView tvTime,tvTitle,tvContent,tvTag;
    public MessageViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_mine_message);
        tvContent=$(R.id.item_msg_content);
        tvTitle=$(R.id.item_msg_title);
        tvTime=$(R.id.item_msg_time);
        tvTag=$(R.id.item_tv_msg_circle);
    }

    @Override
    public void setData(MessageRes.DataBean.ListBean data) {
//        tvContent.setText(data.content);
        tvTitle.setText(data.title);
        tvTime.setText(data.createTime);
        if(data.read){
            tvTag.setBackgroundResource(R.drawable.circle_shape_gry);
//            tvTag.setVisibility(View.GONE);
        }else {
            tvTag.setBackgroundResource(R.drawable.circle_shape_red);
//            tvTag.setVisibility(View.VISIBLE);
        }
//        Glide.with(getContext())
//                .load(person.getFace())
//                .placeholder(R.drawable.default_image)
//                .bitmapTransform(new CropCircleTransformation(getContext()))
//                .into(mImg_face);
    }
}
