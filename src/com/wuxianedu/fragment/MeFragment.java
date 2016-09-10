package com.wuxianedu.fragment;


import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.activity.SettingActivity;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.util.FileLocalCache;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MeFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_me, null);
		
	}
	@Override
	public void onViewCreated(View view , Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		onClick(view);
		setMeUsername(view,getActivity());
		super.onViewCreated(view, savedInstanceState);
		
	}
	
	private void onClick(View view){
		view.findViewById(R.id.me_setting).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SettingActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void setMeUsername(View view,Context context){
		final ImageView head = (ImageView) view.findViewById(R.id.me_head);
		TextView username = (TextView) view.findViewById(R.id.me_username);
		TextView userphpne = (TextView) view.findViewById(R.id.me_userphonenum);
		User user = (User) FileLocalCache.getSerializableData(context, DateFile.USERNAME);
		String str = user.getUserName();
		userphpne.setText("微信号:"+user.getUserPhoneNum());
		username.setText(str);
		x.image().loadDrawable(user.getHead(), ImageOptions.DEFAULT, new CommonCallback<Drawable>() {

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
				head.setImageDrawable(arg0);
			}

		
			
		});
	}
}
