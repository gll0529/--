package com.wuxianedu.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.been.NewFriend;
import com.wuxianedu.widget.RoundImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NewFriendAdapter extends BaseAdapter{
	private Context context;
	private List<NewFriend> list;
	
	public NewFriendAdapter(Context context, List<NewFriend> list) {
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
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHonder viewHonder;
		if (convertView == null) {
			viewHonder = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_newfriend_lv, null);
			viewHonder.newFriendHead = (RoundImageView) convertView.findViewById(R.id.newfriend_lv_head_view);
			viewHonder.newFriendname = (TextView) convertView.findViewById(R.id.newfriend_lv_name);
			viewHonder.newFriendbt = (Button) convertView.findViewById(R.id.newfriend_lv_bt);
			convertView.setTag(viewHonder);
		}else {
			viewHonder = (ViewHonder) convertView.getTag();
		}
		final NewFriend newFriend = list.get(position);
		viewHonder.newFriendname.setText(newFriend.getName());
		lode(newFriend.getHead(),viewHonder.newFriendHead);
		final boolean isadd = newFriend.isAdd();
		if(isadd){
			viewHonder.newFriendbt.setText("已接受");
			viewHonder.newFriendbt.setBackgroundResource(R.drawable.d_newfriend_bt_press);
			//viewHonder.newFriendbt.setClickable(false);
			
		}else{
			viewHonder.newFriendbt.setText("接受");
			viewHonder.newFriendbt.setBackgroundResource(R.drawable.d_newfriend_bt);
			
		}
		
		viewHonder.newFriendbt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!isadd){
					Log.e("---", "dsff");
					viewHonder.newFriendbt.setText("已接受");
					viewHonder.newFriendbt.setBackgroundResource(R.drawable.d_newfriend_bt_press);
					//viewHonder.newFriendbt.setClickable(false);
					newFriend.setAdd(true);
				}
			}		
			});
		
		return convertView;
	}
	class ViewHonder{
		TextView newFriendname;
		RoundImageView newFriendHead;
		Button newFriendbt;
	}
	private void lode(String str,final RoundImageView newFriendHead){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				newFriendHead.setImageDrawable(arg0);
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
