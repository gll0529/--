package com.wuxianedu.adapter;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.activity.ImageBrowseAvtivity;
import com.wuxianedu.been.FriendQuan;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.util.DateUtil;
import com.wuxianedu.widget.GridViewForScroll;
import com.wuxianedu.widget.RoundImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendQuanAdapter extends BaseAdapter{
	private Context context;
	private List<FriendQuan> list;
	
	public FriendQuanAdapter(Context context,List<FriendQuan> list) {
		this.context = context;
		this.list = list;
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
		FriendQuan friendQuan = list.get(position);
		Viewhonder viewhonder = null;
		if(viewhonder == null){
			viewhonder = new Viewhonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_friendquan_lv, null);
			viewhonder.friendquandateTv = (TextView) convertView.findViewById(R.id.friend_quan_date);
			viewhonder.friendquanheadIV = (RoundImageView) convertView.findViewById(R.id.friend_quan_hend);
			viewhonder.friendquanusernameTv = (TextView) convertView.findViewById(R.id.friend_quan_name);
			viewhonder.friendquansignTv = (TextView) convertView.findViewById(R.id.friend_quan_sign);
			viewhonder.friendquanimageIv = (GridViewForScroll) convertView.findViewById(R.id.friend_quan_image);
			convertView.setTag(viewhonder);
		}else {
			viewhonder = (Viewhonder) convertView.getTag();
		}
		
		Date time = DateUtil.getDate(DateUtil.df7, friendQuan.getTime());
		viewhonder.friendquandateTv.setText(DateUtil.getDay(time));
		friendquanheadIVhead(viewhonder.friendquanheadIV,friendQuan.getHead());
		viewhonder.friendquansignTv.setText(friendQuan.getContent());
		viewhonder.friendquanusernameTv.setText(friendQuan.getUsername());
		FriendQuanGv adapter = new FriendQuanGv(context, friendQuan.getList());
		final List<String> image = friendQuan.getList();
		viewhonder.friendquanimageIv.setAdapter(adapter);
		viewhonder.friendquanimageIv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,ImageBrowseAvtivity.class);
				//intent.setAction("com.wuxianedu.activity.ImageBrowseAvtivity");
				intent.putExtra("getIsFriend", true);
				intent.putExtra("getPostion", position);
				intent.putExtra("getImage", (Serializable)image);
				Log.e("---", image.toString());
				context.startActivity(intent);
				
				
				
				
			}
		});
	
		return convertView;
	}
	class Viewhonder{
		GridViewForScroll friendquanimageIv;
		TextView friendquanusernameTv,friendquansignTv,friendquandateTv;
		RoundImageView friendquanheadIV;
		
	}
	private void friendquanheadIVhead(final RoundImageView friendquanheadIV,String str){
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
				friendquanheadIV.setImageDrawable(arg0);
			}

		
			
		});
	}
}


