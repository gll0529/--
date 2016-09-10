package com.wuxianedu.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.been.Contact;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GroupchatHlvAdapter extends BaseAdapter{
	private Context context;
	private List<Contact> list;
	
	public GroupchatHlvAdapter(Context context,List<Contact> list) {
		this.context = context;
		this.list = list;
		//Log.e("---", list.get(0).getHead()+"2");
		notifyDataSetChanged();
	}
	public List<Contact> getList() {
		return list;
	}

	public void setList(List<Contact> list) {
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
		// TODO Auto-generated method stub
		ViewHonder viewHonder;
		if(convertView == null){
			viewHonder = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_groupchat_hlv, null);
			viewHonder.imageView = (ImageView) convertView.findViewById(R.id.groupchat_hlv_im);
			convertView.setTag(viewHonder);
		}else {
			viewHonder = (ViewHonder) convertView.getTag();
		}
		loadImage(list.get(position).getHead(),viewHonder.imageView);
		viewHonder.imageView.setAlpha(list.get(position).getAlpha());
		return convertView;
	}
	class ViewHonder{
		ImageView imageView;
	}

	private void loadImage(String str,final ImageView imageView){
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
				imageView.setImageDrawable(arg0);
			}
		});
	}
		
		
}
