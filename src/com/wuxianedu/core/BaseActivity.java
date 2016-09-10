package com.wuxianedu.core;

import com.wuxianedu.R;
import com.wuxianedu.util.CoreUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity{
	protected TextView titleTv,rightTv;
	private ImageView leftIv,rightIv;
	private OnClickListener click;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(getTileId());
		init();
		CoreUtil.addToActivityList(this);
	}
	protected abstract int getTileId();
	
	protected void init(){
		titleTv = (TextView) findViewById(R.id.title);
		rightTv = (TextView) findViewById(R.id.title_right_tv);
		leftIv = (ImageView) findViewById(R.id.title_left);
		rightIv = (ImageView) findViewById(R.id.title_right);
		leftIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(click == null){
					CoreUtil.removeActivity(BaseActivity.this);
					finish();
				}else{
					click.onClick(v);
				}
			}
			
		});
		
		
	}
	protected void setOnclick(OnClickListener click){
		this.click=click;
	}
	
	protected void setLeftIv(){
		leftIv.setVisibility(View.GONE);
		
	}
	protected void setLeftIvshow(){
		leftIv.setVisibility(View.VISIBLE);
		
	}
	protected void setTitleTv( int resId){
		titleTv.setText(getString(resId));
	}
	protected void setTitleTv( String string){
		titleTv.setText(string);
	}
	protected void setRightIv(){
		rightIv.setVisibility(View.GONE);
	}
	protected void setrightTv(String resId,OnClickListener click){
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setText(resId);
		rightTv.setOnClickListener(click);
	}
	protected void setrightIv(int resId,OnClickListener click){
		rightIv.setVisibility(View.VISIBLE);
		rightIv.setImageResource(resId);
		rightIv.setOnClickListener(click);
	}
	public void showdialog(Context context,int resId){
		if(dialog == null){
			dialog = new ProgressDialog(context);
			dialog.setMessage(getString(resId));
			dialog.setCancelable(false);
			if(dialog.isShowing()){
				return;
			}else{
				dialog.show();
			}
		}
	}
	
	public void hideDialog(){
		if(dialog != null&&dialog.isShowing() == true){
			dialog.dismiss();
		}
	}
	
}
