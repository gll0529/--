package com.wuxianedu.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.CoreUtil;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;
import com.wuxianedu.util.TVUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText passwoed,user;
	private ProgressDialog dialog;
	private String userstr, passwordstr;
	private TextView problem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleTv(R.string.login);//将标题改变为登录
		
		inits();	
	
		
	}

	
	
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_login;
	}
	/**
	 * 初始化控件
	 * 
	 */
	private void inits(){
		user = (EditText) findViewById(R.id.login_phone);//用户名
		passwoed = (EditText) findViewById(R.id.login_psd);//密码
		problem = (TextView) findViewById(R.id.login_problem);//登录遇到的问题
		
		dialog  = new ProgressDialog(this);//进度条对话框
		dialog.setMessage("正在加载.....");
		findViewById(R.id.login_bt).setOnClickListener(this);//绑定登录按钮的点击事件
		findViewById(R.id.login_signin_bt).setOnClickListener(this);//绑定注册按钮的点击事件
		findViewById(R.id.login_problem).setOnClickListener(this);//绑定遇到问题的点击事件
	}
	
	/**
	 * 判断登录名是否正确
	 */
	private void panduan(){
		userstr = user.getText().toString();
		passwordstr = passwoed.getText().toString();
		if (TVUtil.isEmpty(userstr)) {//登录名为空
			Toast.makeText(this, "你输入的用户名为空", Toast.LENGTH_SHORT).show();;
			return;
		}
		if (userstr.length() != 11) {//登录名为11位
			Toast.makeText(this, "你输入的用户名非法", Toast.LENGTH_SHORT).show();;
			return;
		}

		if (TVUtil.isEmpty(passwordstr)) {//密码不为空
			Toast.makeText(this, "你输入的密码为空", Toast.LENGTH_SHORT).show();;
			return;
		}
		
		if (passwordstr.length()<6) {//密码不能少于6位
			Toast.makeText(this, "你输入的密码非法", Toast.LENGTH_SHORT).show();;
			return;
		}
		new Mytask().execute("userLogin.js");//解析json文件,读取用户信息
	}
	
	/**
	 * 判断是否读取到用户信息
	 * */
	private void login(User user) {

		if (user == null) {
			Toast.makeText(this, "登录异常....", Toast.LENGTH_SHORT).show();;
			return;
		}
		if(userstr.equals(user.getUserPhoneNum().toString())){//判断输入的用户名是否与解析到的用户名相等
			//new User().getUserPhoneNum())
			//Log.e("---", new User().getUserPhoneNum()+"----------");
			FileLocalCache.setSerializableData(this, user, DateFile.USERNAME);//将读取到的用户信息写到本地
			//关闭之前打开的Activity
			CoreUtil.finishActivityList(); 
			Intent intent = new Intent(this,MainActivity.class);//判断正确后跳转到主界面
			startActivity(intent);
			finish();
		}else{//如果输入的用户名与解析到的用户名相等输出"用户名不存在"
			Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();;
		}
		
		
	}
	/**
	 *  异步,创建子进程,解析数据
	 * */
	
	class Mytask extends AsyncTask<String, Void, User>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.show();//show进度条对话框
			super.onPreExecute();
		}
		
		@Override
		protected User doInBackground(String... params) {
			// TODO Auto-generated method stub
			String str = GetJsonString.getJsonString(LoginActivity.this, params[0]);//将json数据转化成字符串
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json(str);//解析数据
		}


		protected void onPostExecute(User result) {
			// TODO Auto-generated method stub
		
			super.onPostExecute(result);
			dialog.dismiss();//关闭进度条对话框
			login(result);//调用判断方法
		}
		
		/***
		 * 解析数据
		 * */
		private User json(String str) {
			JSONObject jsonObject;
			User users = new User();
			try {
				jsonObject = new JSONObject(str);
				
				int statu = jsonObject.getInt("status");
				if(statu == 1){
					return null;
				}
				JSONObject json = jsonObject.getJSONObject("userInfor");
				users.setUserId(json.getString("userId"));
				users.setUserName(json.getString("userName"));
				users.setUserPhoneNum(json.getLong("userPhoneNum"));
				users.setHead(json.getString("head"));
				
				//Log.e("---", users.getUserPhoneNum()+"----------ajsdlf-------");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return users;
		}
	}
	/***
	 * 点击事件
	 * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_bt://点击登录按钮时跳转主界面
			panduan();//调用panduan()方法
			break;
		case R.id.login_signin_bt://点击登录界面时跳转注册界面
			Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
			startActivity(intent);
			break;
		case R.id.login_problem://点击跳转遇到问题界面
			Uri uri = Uri.parse("http://weixin.qq.com/");//得到网址
			Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);//跳转到该网址
			startActivity(intent1);
			break;
		default:
			break;
		}
	}
}
