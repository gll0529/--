package com.wuxianedu.activity;

import com.wuxianedu.R;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.fragment.ContactFragment;
import com.wuxianedu.fragment.FindFragment;
import com.wuxianedu.fragment.MeFragment;
import com.wuxianedu.fragment.WeiXinFragment;
import com.wuxianedu.util.CoreUtil;
import com.wuxianedu.util.DensityUtil;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends BaseActivity  {
	private RadioGroup radioGroup;
	private ViewPager viewPager;
	private Fragment[] fragments = new Fragment[4];//初始化Framents数组
	private RadioButton[] radio = new RadioButton[4];//初始radio数组
	private ImageView imageView;
	private View view;
	private PopupWindow popupWindow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inits();//调用inits()方法;
		setLeftIv();//隐藏标题左边的图片
		setrightIv(R.drawable.icon_add, new View.OnClickListener() {//显示并设置右边的图片,设置点击事件
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopup();//调用popupWindow对话框
				tiaozhuan();//点击popupWindow对话框进行跳转
			}
		});
	}

	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
		
	}
	
	/**
	 * 初始化frament,radio,viewPager
	 * */
	private void inits(){
		imageView = (ImageView) findViewById(R.id.title_right);
		fragments[0] = new WeiXinFragment();//分别为framents数组赋值
		fragments[1] = new ContactFragment();
		fragments[2] = new FindFragment();
		fragments[3] = new MeFragment();
		
		radio[0] = (RadioButton) findViewById(R.id.main_weixin_bt);//分别为radio数组赋值
		radio[1] = (RadioButton) findViewById(R.id.main_contact_bt);
		radio[2] = (RadioButton) findViewById(R.id.main_find_bt);
		radio[3] = (RadioButton) findViewById(R.id.main_me_bt);
		
		radioGroup = (RadioGroup) findViewById(R.id.main_radio_group);//绑定radioGroup控件
		viewPager = (ViewPager) findViewById(R.id.viewPager);//绑定viewPager控件
		FragmentAdapter adpter = new FragmentAdapter(getSupportFragmentManager());//获取fragments的适配器
		viewPager.setAdapter(adpter);//更改viewpager的适配器
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {//设置viewPager的点击事件
			
			@Override
			public void onPageSelected(int arg0) {//viewpager选择时
				radio[arg0].setChecked(true);//当viewpager显示的是第几个就让radio选定为第几个.使viewpager与radio同步
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {//当viewpager滚动的时候
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//radioGroup的点击事件
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				check(checkedId);//调用check方法
			}
		});
		
	}
	
	/**
	 * 当点击radio时使viewPager跳转到相同界面,实现联动
	 * */
	private void check(int checkedId){
		int arge = 0;//定义一个int型数值,作为viewpager的数组下标
		switch (checkedId) {
		case R.id.main_weixin_bt://当radio下标为 R.id.main_weixin_bt时,跳转到weixinfragment界面
			arge = 0;//将0赋给arge
			setTitleTv(R.string.weixin);//改变标题中的文字
			setLeftIv();//隐藏左侧的图片
			setrightIv(R.drawable.icon_add, new View.OnClickListener() {//设置右侧图片,设置点击事件
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showPopup();//show出show出popupwindow对话框
					tiaozhuan();//点击popupwindow对话框跳转到相应位置
				}
			});
			
			break;
		case R.id.main_contact_bt://当radio下标为R.id.main_contact_b时	跳转到contactfragment界面		
			arge = 1;//将1赋给arge
			setTitleTv(R.string.contact);//将标题中间的文字改为通讯录
			setLeftIv();//隐藏左侧的图片
			setrightIv(R.drawable.icon_menu_addfriend, null);//设置右侧的图片
			break;
		case R.id.main_find_bt://当radio下标为R.id.main_find_bt时	跳转到findfragment界面
			arge = 2;//将2赋给arge作为数组下标
			setTitleTv(R.string.find);//将标题中间的位子改为发现
			setRightIv();//隐藏右侧的图片
			setLeftIv();//隐藏左侧的图片
			break;
		case R.id.main_me_bt://当radio下标为R.id.main_me_bt时	跳转到mefragment界面
			arge = 3;//将3赋给arge作为数组下标
			setTitleTv(R.string.me);//将标题文字改为我的
			setRightIv();//隐藏右边图片
			setLeftIv();//隐藏左侧图片
			break;

		default:
			break;
		}
		viewPager.setCurrentItem(arge, false);//将选择第几个付给viewpager,让viewpager与radio产生联动
	}
	class FragmentAdapter extends FragmentPagerAdapter{//建立viewpager的适配器

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {//得到的现在显示的是第几张
			// TODO Auto-generated method stub
			return fragments[arg0];
		}

		@Override
		public int getCount() {//一共有几张view
			// TODO Auto-generated method stub
			return fragments.length;
		}
		
	}
	
	private void showPopup(){//建立popupwindow对话框
		int width = (int) (DensityUtil.getDisplay(this)[0] * 0.5);//得到宽度为屏幕的一般
		view = LayoutInflater.from(this).inflate(R.layout.item_main_popup, null);//绑定将要作为背景的试图,
		//第一个参数:背景试图
		//第二个参数:宽度
		//第三个参数 高度
		//第四个参数:点击别处,是否可以收起
		popupWindow = new PopupWindow(view, width, WindowManager.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setAnimationStyle(R.style.popup_style);//设置进入出去动画动画
		popupWindow.setBackgroundDrawable(new BitmapDrawable());//版本在5.0之前的必须加,否则无法收回
		popupWindow.showAsDropDown(imageView);//显示popupwindow对话框,必须放在最后
		
	}
	
	/**
	 * 点击popupwindow的跳转事件
	 * */
	private void tiaozhuan(){
		//绑定popupwindow中的textview添加朋友 控件
		TextView addfriend = (TextView) view.findViewById(R.id.popup_addfriend);
		addfriend.setOnClickListener(new View.OnClickListener() {//设置添加朋友的点击事件
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,NewFriendActivity.class);
				popupWindow.dismiss();//跳转到新朋友界面,并关闭对话框
				startActivity(intent);
			}
		});
		TextView groupchat = (TextView) view.findViewById(R.id.popup_groupchat);//绑定popupwindow中的群聊控件
		groupchat.setOnClickListener(new View.OnClickListener() {//设置群聊的点击事件
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,GroupChatActivity.class);//跳转到群聊界面
				popupWindow.dismiss();//关闭对话框
				startActivity(intent);
			}
		});
	}
	
	
}
