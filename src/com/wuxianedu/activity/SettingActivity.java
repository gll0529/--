package com.wuxianedu.activity;

import com.wuxianedu.R;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.CoreUtil;
import com.wuxianedu.util.FileLocalCache;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class SettingActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleTv(R.string.shezhi);
		btOnclick();
		//CoreUtil.addToActivityList(this);
		
	}
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting;
	}
	public void btOnclick(){
		findViewById(R.id.me_tuichu).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builer= new AlertDialog.Builder(SettingActivity.this);
				builer.setItems(new String[]{"退出登陸", "關閉程序"},new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which == 0){
							Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
							startActivity(intent);
							CoreUtil.finishActivityList();
							FileLocalCache.delSerializableData(SettingActivity.this, DateFile.USERNAME);
						}else{
							CoreUtil.exitApp();
						}
					}
				});
				builer.show();
			}
		});
	}
	
	
}
