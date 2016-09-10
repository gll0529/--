package com.wuxianedu.activity;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.hp.hpl.sparta.Text;
import com.wuxianedu.R;
import com.wuxianedu.adapter.DetailsGvAdapter;
import com.wuxianedu.been.Contact;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.widget.RoundImageView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class DetailsActivity extends BaseActivity{
	private GridView griView;
	@Override
	protected int getTileId() {
		return R.layout.activity_details;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inits();//调用inits()方法
		setTitleTv("详细资料");//将标题中间的文字改为详细资料
	}
	
	/**
	 * 初始化控件
	 * */
	private void inits() {
		griView = (GridView) findViewById(R.id.details_photo_iv);//绑定gridview控件
		RoundImageView head = (RoundImageView) findViewById(R.id.details_head); //
		TextView username = (TextView) findViewById(R.id.details_username);
		TextView userphone = (TextView) findViewById(R.id.details_userphone);
		TextView userarea = (TextView) findViewById(R.id.details_area_tv);
		TextView usersign = (TextView) findViewById(R.id.details_sign_tv);
		
		Intent intent = getIntent();
		Contact contact = (Contact) intent.getSerializableExtra("contact");
		username.setText(contact.getName());
		userphone.setText("微信号:"+contact.getWeCode());
		userarea.setText(contact.getArea());
		usersign.setText(contact.getAutograph());
		lode(contact.getHead(),head);
		DetailsGvAdapter adapter = new DetailsGvAdapter(DetailsActivity.this,contact.getImages());
		griView.setAdapter(adapter);
	}
	private void lode(String str,final RoundImageView head){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				head.setImageDrawable(arg0);
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
