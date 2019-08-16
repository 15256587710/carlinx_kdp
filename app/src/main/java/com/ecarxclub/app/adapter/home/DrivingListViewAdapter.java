package com.ecarxclub.app.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.home.DrivingListRes;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/18 15:27.
 * 不用
 */
public class DrivingListViewAdapter extends BaseAdapter {
    private List<DrivingListRes.DataBean.TravelBean> list;
    private Context context;
    ViewHolderDriving viewHolderDriving;
    public DrivingListViewAdapter(List<DrivingListRes.DataBean.TravelBean> list,Context context){
        this.context=context;
        this.list=list;
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
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_driving_rcylist,parent,false);
            viewHolderDriving=new ViewHolderDriving();
            viewHolderDriving.tvCity=convertView.findViewById(R.id.item_dd_list_city);
            viewHolderDriving.tvTime=convertView.findViewById(R.id.item_dd_list_time);
            viewHolderDriving.tvEndAddress=convertView.findViewById(R.id.item_dd_list_endaddress);
            viewHolderDriving.tvStartAddress=convertView.findViewById(R.id.item_dd_list_stataddress);
            convertView.setTag(viewHolderDriving);
        }else {
            viewHolderDriving= (ViewHolderDriving) convertView.getTag();
        }
        viewHolderDriving.tvCity.setText(list.get(position).city);
        viewHolderDriving.tvTime.setText(list.get(position).startTime);
        viewHolderDriving.tvStartAddress.setText(list.get(position).startLocation);
        viewHolderDriving.tvEndAddress.setText(list.get(position).endLocation);
        return convertView;
    }

    static class ViewHolderDriving{
        TextView tvTime,tvCity,tvStartAddress,tvEndAddress;
    }

}
