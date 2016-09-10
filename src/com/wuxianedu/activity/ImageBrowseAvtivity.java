package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.ImageUtil;
import com.wuxianedu.widget.TouchImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImageBrowseAvtivity extends BaseActivity{
	private List<View> viewList;
	private boolean getIsFriend;
	private int getPostion;
	private List<String> getImage;
	private TextView imageBrowse;
	private ViewPager pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleTv("图片浏览");
		getImage = new ArrayList<String>();
		Intent intent = getIntent();
		//传过来的布尔值,确定是不是从朋友圈传来的
		getIsFriend = intent.getBooleanExtra("getIsFriend", false);
		//传过来的postion 确定点击的是第几个
		getPostion = intent.getIntExtra("getPostion", 0);
		//传过来的图片集合
		getImage = (List<String>) intent.getSerializableExtra("getImage");
		//Log.e("---", getImage.toString());
		inits();
		set();
	}
	
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_imagebrowse;
	}
	private void inits() {
		imageBrowse = (TextView) findViewById(R.id.imagebrowse_test);
		pager = (ViewPager) findViewById(R.id.imagebrowse_image);
		//将穿过来的图片分别绑定为试图
		viewList = new ArrayList<View>();
		for (int i = 0; i < getImage.size(); i++) {
			View view=  LayoutInflater.from(ImageBrowseAvtivity.this).inflate(R.layout.item_imagebrowse_vg, null);
			viewList.add(view);
		}
	}
	private void set(){
		imageBrowse.setText(getPostion+1+"/"+getImage.size());
		pager.setAdapter(new pagerAdapter());
		pager.setCurrentItem(getPostion);
		pager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				imageBrowse.setText(arg0+1+"/"+getImage.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	class pagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(viewList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			//将每个试图加入到ViewGroup中
			container.addView(viewList.get(position));
			//为每个View中的TouchImageView绑定控件
			TouchImageView imageView = (TouchImageView) viewList.get(position).findViewById(R.id.imagebrowse_gv_image);
			//Log.e("--", getImage.get(position));
			if (getIsFriend) {
				lodeimage(getImage.get(position),imageView);
			}else{
				imageView.setImageBitmap(ImageUtil.getSmallBitmap(getImage.get(position), 300, 400));
			}
			
			return viewList.get(position);
		}
		
	}
	private void lodeimage(String str,final TouchImageView image){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				// TODO Auto-generated method stub
				image.setImageDrawable(arg0);
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
