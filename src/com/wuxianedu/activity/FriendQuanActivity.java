package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.adapter.FriendQuanAdapter;
import com.wuxianedu.been.FriendQuan;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.DateUtil;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;
import com.wuxianedu.widget.RoundImageView;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FriendQuanActivity extends BaseActivity{
	private ListView listView;
	private String backGround;
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_friend_quan;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleTv(R.string.friendquan);
		inits();
		//Log.e("---", "onCreate");
		
	}
	
	private void inits() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.friend_quan);
		new FriendTask().execute("userFriendsCircle.js");
		
	}
	class FriendTask extends AsyncTask<String, Void, List<FriendQuan>>{
		
		@Override
		protected void onPreExecute() {
			showdialog(FriendQuanActivity.this, R.string.dialog);
			super.onPreExecute();
			
		}
		
		@Override
		protected List<FriendQuan> doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(FriendQuanActivity.this, params[0]);
		//	Log.e("---", "doInBackground");
			return json(str);
		}

		@Override
		protected void onPostExecute(List<FriendQuan> result) {
			hideDialog();
			super.onPostExecute(result);
			FriendQuanAdapter adapter = new FriendQuanAdapter(FriendQuanActivity.this, result);
			listView.setAdapter(adapter);
			View view = LayoutInflater.from(FriendQuanActivity.this).inflate(R.layout.item_friendquan_top, null);
			//listView.addHeaderView(view);
			listView.addHeaderView(view, null, false);
			User user = (User) FileLocalCache.getSerializableData(FriendQuanActivity.this, DateFile.USERNAME);
			RoundImageView roundImageView = (RoundImageView) view.findViewById(R.id.friend_quan_top_head);
			ImageView imageView = (ImageView) view.findViewById(R.id.friend_quan_top_image);
			TextView textView = (TextView) view.findViewById(R.id.friend_quan_top_usernaem);
			textView.setText(user.getUserName());
			friendquanheadIVhead(roundImageView,user.getHead());
			friendquanheadIVhead(imageView,backGround);
			
			
		}
		
		private List<FriendQuan> json(String str){
			
			List<FriendQuan> list = new ArrayList<FriendQuan>();
			
			try {
				JSONObject json = new JSONObject(str);
				int status = json.getInt("status");
			//	Log.e("---", "json");
				if(status == 1){
					return null;
				}
				backGround = json.getString("backGround");
				JSONArray  jsonarray = json.getJSONArray("friends");
				for(int i=0;i<jsonarray.length();i++){
					FriendQuan friendQuan = new FriendQuan();
					JSONObject jsonObject = jsonarray.getJSONObject(i);
					friendQuan.setHead(jsonObject.getString("head"));
					friendQuan.setContent(jsonObject.getString("content"));
					friendQuan.setTime(jsonObject.getString("time"));
					friendQuan.setUsername(jsonObject.getString("userName"));
					List<String> list1 = new ArrayList<String>();
					JSONArray jArray = jsonObject.getJSONArray("images");
					for(int j=0;j<jArray.length();j++){
						String image = jArray.getString(j);
						list1.add(image);
					}
					friendQuan.setList(list1);
					list.add(friendQuan);
				//	Log.e("---", "----");
				}
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list; 
		}
		
		private List<FriendQuan> parserJson(String string){
			List<FriendQuan> list = new ArrayList<FriendQuan>();
			
			try {
				JSONObject jsonObject = new JSONObject(string);
				int status = jsonObject.getInt("status");
				if(status != 0){
					return null;   //请求结果错误   返回null
				}
				//返回结果正常，进一步进行信息解析
				JSONArray jsonArray = jsonObject.getJSONArray("friends");
				
				//获得背景
				backGround = jsonObject.getString("backGround");
				
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					FriendQuan friendsCircle = new FriendQuan();
					friendsCircle.setUsername(jsonObj.getString("userName"));
					friendsCircle.setHead(jsonObj.getString("head"));
					friendsCircle.setContent(jsonObj.getString("content"));
					friendsCircle.setTime(jsonObj.getString("time"));
					List<String> imageList = new ArrayList<String>();
					JSONArray images = jsonObj.getJSONArray("images");
					for (int j = 0; j < images.length(); j++) {
						Log.e("---", "----");
						String imageUrl = images.getString(j);
						imageList.add(imageUrl);
					}
					friendsCircle.setList(imageList);
					list.add(friendsCircle);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		private void friendquanheadIVhead(final RoundImageView friendquanheadIV,String str){
			x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {

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
					friendquanheadIV.setImageDrawable(arg0);
				}

			
				
			});

		}
		
		private void friendquanheadIVhead(final ImageView imageView,String str){
			x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {

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
					imageView.setImageDrawable(arg0);
				}

			
				
			});

		}
		
	}
}
