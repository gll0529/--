package com.wuxianedu.adapter;

import java.util.Date;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.R.layout;
import com.wuxianedu.been.SubscribeInformation;
import com.wuxianedu.util.DateUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SubscribeInformationAdapter extends BaseAdapter{
	private Context context;
	private List<SubscribeInformation> list;
	public SubscribeInformationAdapter(Context context,List<SubscribeInformation> list) {
		this.context = context;
		this.list = list;
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
		ViewHonder viewHonder;
		if (convertView == null) {
			viewHonder = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_subinformation_lv, null);
			viewHonder.titleDate = (TextView) convertView.findViewById(R.id.information_title_date);
			viewHonder.name = (TextView) convertView.findViewById(R.id.information_name);
			viewHonder.date = (TextView) convertView.findViewById(R.id.information_date);
			viewHonder.image = (ImageView) convertView.findViewById(R.id.information_image);
			viewHonder.sign = (TextView) convertView .findViewById(R.id.information_sign);
			convertView.setTag(viewHonder);
		}else {
			viewHonder = (ViewHonder) convertView.getTag();
		}
		viewHonder.name.setText(list.get(position).getTitle());
		viewHonder.sign.setText(list.get(position).getContent());
		String str = list.get(position).getTime();
		Date time = DateUtil.getDate(DateUtil.df7, str); 
		viewHonder.titleDate.setText(DateUtil.getStringDate(DateUtil.df8, time));
		viewHonder.date.setText(DateUtil.getStringDate(DateUtil.DF, time));
		loadImage(list.get(position).getImage(), viewHonder.image);
		return convertView;
	}
	class ViewHonder{
		TextView name,titleDate,date,sign;
		ImageView image;
	}
	private void loadImage(String str,final ImageView imageView){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				imageView.setImageDrawable(arg0);
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
