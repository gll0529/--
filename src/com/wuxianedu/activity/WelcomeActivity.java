package com.wuxianedu.activity;

import com.wuxianedu.R;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.util.FileLocalCache;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity{
	private Handler handler;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);//绑定布局
		user = (User) FileLocalCache.getSerializableData(this, DateFile.USERNAME);//读取储存在sd卡中的数据
		handler();
		handler.sendEmptyMessageDelayed(200, 3000);//3秒后发送通知
		
		
	}
	private void handler(){
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what ==200){
					if(user == null){//判断是否登录过,没有跳转登录界面
						Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{//登录过直接跳转主界面
						Intent intent1 = new Intent(WelcomeActivity.this,MainActivity.class);
						startActivity(intent1);
						finish();
					}
				}	
				
			}};
			
		
	}
	
}
