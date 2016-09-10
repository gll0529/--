package com.wuxianedu.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.widget.NewFriendTextView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class DetailsGvAdapter extends BaseAdapter{
	private Context context;
	private List<String> list;
	public DetailsGvAdapter(Context context,List<String> list) {
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
		if(convertView == null){
			viewHonder = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_details_gv, null);
			viewHonder.image = (ImageView) convertView.findViewById(R.id.details_photo_gv);
			convertView.setTag(viewHonder);
		}else {
			viewHonder = (ViewHonder) convertView.getTag();
		}
		lade(list.get(position),viewHonder.image);
		return convertView;
	}
	class ViewHonder{
		ImageView image;
	}
	private void lade(String str,final ImageView image){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				image.setImageDrawable(arg0);
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
