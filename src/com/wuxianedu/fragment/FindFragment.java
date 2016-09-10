package com.wuxianedu.fragment;

import com.wuxianedu.R;
import com.wuxianedu.activity.FriendQuanActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FindFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_find, null);
		
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		firendQuanOnclick(view);
	}
	
	private void firendQuanOnclick(View view){
		TextView friendquan = (TextView) view.findViewById(R.id.find_friend_quan);
		friendquan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),FriendQuanActivity.class);
				startActivity(intent);
			}
		});
	}
}
