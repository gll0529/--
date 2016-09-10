package com.wuxianedu.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;
import com.wuxianedu.util.TVUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends BaseActivity {
	private Button button,buttonyanzhengma;
	private Notification notification;
	private NotificationManager manager;
	private EditText editPhone,edityanzhengma,editpsd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		setTitleTv(R.string.signin);//将标题该为注册
		//Log.e("---", "lksajflas");
		new Mytask().execute("userReg.js");//解析"userReg.js"文件
		inits();//调用inits()方法;
	}

	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_signin;
	}
	
	/**
	 * 初始化控件
	 * */
	private void inits() {
		// TODO Auto-generated method stub
		editPhone = (EditText) findViewById(R.id.signin_phone);//绑定注册用户名
		editpsd = (EditText) findViewById(R.id.signin_psd);//绑定注册密码
		edityanzhengma = (EditText) findViewById(R.id.signin_yanzhengba);//绑定验证码
		button = (Button) findViewById(R.id.signin_bt);//绑定注册按钮
		buttonyanzhengma = (Button) findViewById(R.id.signin_yzm);//绑定发送验证码按钮
		buttonyanzhengma.setOnClickListener(new View.OnClickListener() {//绑定验证码的点击事件
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendmessage();//发送信息
				manager.notify(0, notification);//发送通知
			}
		});
		button.setOnClickListener(new View.OnClickListener() {//绑定注册按钮的点击事件
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				User user = new User();//新建一个user对象
				
				panduan();//调用panduan()方法
			}
		});
	}
	
	/**
	 * 发送通知
	 * */
	private void sendmessage() {
		// TODO Auto-generated method stub
		Notification.Builder builder=new Notification.Builder(this);
		//设置声音震动 灯光  
		builder.setDefaults(Notification.DEFAULT_ALL);
		//通知点击之后是否删除
		builder.setAutoCancel(false);
		//设置通知标题
		builder.setTicker("你有新消息");
		//设置内容标题
		builder.setContentTitle("微信验证码");
		//设置通知内容
		builder.setContentText("209834");
		//设置通知图像
		builder.setSmallIcon(R.drawable.f_static_036);
		//builder.setContentIntent(pendingIntent);
		notification = builder.getNotification();//创建通知对象
		//获得通知服务
		manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE) ;
	}
	
	/**
	 * 判断输入的用户名,密码,验证码是否符合格式,是否正确
	 * */
	private void panduan(){
		if (TVUtil.isEmpty(editPhone.getText().toString())) {//判断用户名是否为空
			Toast.makeText(SignInActivity.this, "用户名不为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TVUtil.isEmpty(edityanzhengma.getText().toString())) {//判断验证码是否为空
			Toast.makeText(SignInActivity.this, "验证码不为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TVUtil.isEmpty(editpsd.getText().toString())) {//验证密码是否为空
			Toast.makeText(SignInActivity.this, "密码不为空", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(SignInActivity.this,MainActivity.class);
		startActivity(intent);//如果判断正确后跳转到主界面
		finish();
	}
	
	/**
	 * 异步,创建子线程解析数据
	 * */
	class Mytask extends AsyncTask<String, Void, User>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showdialog(SignInActivity.this, R.string.dialog);//show出进度条对话框
		}
		
		@Override
		protected User doInBackground(String... params) {
			//Log.e("---", "doInBackground");
			String str = GetJsonString.getJsonString(SignInActivity.this, params[0]);//接json文件解析成数据
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return json(str);//结束文件
		}


		protected void onPostExecute(User result) {
			hideDialog();//结束进度条对话框
			FileLocalCache.setSerializableData(SignInActivity.this, result, DateFile.USERNAME);//将json到的数据写入本地
			super.onPostExecute(result);
		}
		
		/**
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
				
				Log.e("---", users.getUserPhoneNum()+"----------ajsdlf-------");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return users;
		}
	}
}
