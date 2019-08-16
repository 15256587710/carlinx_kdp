package com.ecarxclub.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.shop.MallIntegralRes;

import java.util.List;

/**
 * Created by cwp
 * on 2019/4/22 13:30.
 */
public class MallGvAdapter extends BaseAdapter {
    private Context context;
    private List<MallIntegralRes> list;
    private int clickPostion=0;

    public MallGvAdapter(Context context, List<MallIntegralRes> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickPostion(int postion){
        this.clickPostion=postion;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMall vh;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_gv_mall_integral,null);
            vh=new ViewHolderMall();
            vh.tvName=convertView.findViewById(R.id.item_tv_mall_name);
            vh.tvNum=convertView.findViewById(R.id.item_tv_mall_code);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolderMall) convertView.getTag();
        }
        vh.tvName.setText(list.get(position).getItgName());
        vh.tvNum.setText(list.get(position).getItgCode());
        if(clickPostion==position){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
        }else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.color_mall));
        }
        return convertView;
    }


    class ViewHolderMall{
        TextView tvName,tvNum;
    }

}
