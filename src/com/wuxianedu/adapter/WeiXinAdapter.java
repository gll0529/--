package com.wuxianedu.adapter;

import java.util.Date;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.R.layout;
import com.wuxianedu.been.WeChat;
import com.wuxianedu.util.DateUtil;
import com.wuxianedu.widget.RoundImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeiXinAdapter extends BaseAdapter{
	private Context context;
	private List<WeChat> list;
	public WeiXinAdapter(Context context, List<WeChat> list) {
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
		ViewHodler viewHodler = null;
		if (viewHodler == null) {
			viewHodler = new ViewHodler();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_weixin_wechat, null);
			viewHodler.headIv = (RoundImageView) convertView.findViewById(R.id.weixin_lv_head);
			viewHodler.dateTv = (TextView) convertView.findViewById(R.id.weixin_lv_date);
			viewHodler.nameTv = (TextView) convertView.findViewById(R.id.weixin_lv_name);
			viewHodler.signTv = (TextView) convertView.findViewById(R.id.weixin_lv_sign);
			viewHodler.quanquanTv = (TextView) convertView.findViewById(R.id.weixin_lv_quanquan);
			convertView.setTag(viewHodler);
		}else{
			viewHodler = (ViewHodler) convertView.getTag();
		}
		WeChat weChat = list.get(position);
		String time = weChat.getLastTime();
		Date date = DateUtil.getDate(DateUtil.df7, time);
		viewHodler.dateTv.setText(DateUtil.getDay(date));
		
		viewHodler.nameTv.setText(weChat.getName());
		viewHodler.signTv.setText(weChat.getLastStr());
		lodeImage(weChat.getHead(),viewHodler.headIv);
		if (weChat.getNewsNum() == 0) {
			viewHodler.quanquanTv.setVisibility(View.GONE);
		}else{
			viewHodler.quanquanTv.setVisibility(View.VISIBLE);
			viewHodler.quanquanTv.setText(String.valueOf(weChat.getNewsNum()));
		}
		return convertView;
	}
	class ViewHodler{
		RoundImageView headIv;
		TextView nameTv,signTv,dateTv,quanquanTv;
	}
	private void lodeImage(String str,final RoundImageView headIv){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				headIv.setImageDrawable(arg0);
				
			}
			
			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
