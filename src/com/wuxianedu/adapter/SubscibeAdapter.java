package com.wuxianedu.adapter;

import java.util.Date;
import java.util.List;

import com.wuxianedu.R;
import com.wuxianedu.adapter.WeiXinAdapter.ViewHodler;
import com.wuxianedu.been.WeChat;
import com.wuxianedu.util.DateUtil;
import com.wuxianedu.widget.RoundImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubscibeAdapter extends BaseAdapter{
	private Context context;
	private List<WeChat> list;
	public SubscibeAdapter(Context context,List<WeChat> list) {
		this.context = context;
		this.list = list;
	}
	
	public List<WeChat> getList() {
		return list;
	}

	public void setList(List<WeChat> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHonder viewHodler;
		if(convertView == null){
			viewHodler = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_subscibe, null);
			viewHodler.date = (TextView) convertView.findViewById(R.id.subscibe_lv_date);
			viewHodler.name = (TextView) convertView.findViewById(R.id.subscibe_lv_name);
			viewHodler.lastStr = (TextView) convertView.findViewById(R.id.subscibe_lv_sign);
			viewHodler.newNum = (TextView) convertView.findViewById(R.id.subscibe_lv_quanquan);
			convertView.setTag(viewHodler);
		}else{
			viewHodler = (ViewHonder) convertView.getTag();
		}
		WeChat weChat = list.get(position);
		String time = weChat.getLastTime();
		Date date = DateUtil.getDate(DateUtil.df7, time);
		viewHodler.date.setText(DateUtil.getDay(date));
		
		viewHodler.name.setText(weChat.getName());
		viewHodler.lastStr.setText(weChat.getLastStr());
		if (weChat.getNewsNum() == 0) {
			viewHodler.newNum.setVisibility(View.GONE);
		}else{
			viewHodler.newNum.setVisibility(View.VISIBLE);
			viewHodler.newNum.setText(String.valueOf(weChat.getNewsNum()));
		}
		return convertView;
	}
	class ViewHonder{
		TextView name;
		TextView lastStr;
		TextView newNum;
		TextView date;
	}

}
