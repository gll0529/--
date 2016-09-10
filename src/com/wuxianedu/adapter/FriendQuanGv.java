package com.wuxianedu.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.R.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FriendQuanGv extends BaseAdapter{
	private Context context;
	private List<String> list;
	public FriendQuanGv(Context context,List<String> list) {
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
		Viewhonder viewhonder = null;
		String str = list.get(position);
		if(viewhonder == null){
			viewhonder = new Viewhonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.iten_friendquan_gv, null);
			viewhonder.imageView = (ImageView) convertView.findViewById(R.id.friend_quan_gv_iv);
			convertView.setTag(viewhonder);
		}else {
			viewhonder = (Viewhonder) convertView.getTag();
		}
		imageView(viewhonder.imageView,str);
		return convertView;
		
	}
	class Viewhonder{
		ImageView imageView;
	}
	/**
	 * 利用xutil从网络上加载图片
	 */
	private void imageView(final ImageView imageView,String str){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Drawable arg0) {
				// TODO Auto-generated method stub
				imageView.setImageDrawable(arg0);
			}

		
			
		});
	}
	
	
}
