package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.adapter.SubscribeInformationAdapter;
import com.wuxianedu.been.SubscribeInformation;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.GetJsonString;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SubscibeInformationActivity extends BaseActivity{

	private ListView listView;

	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_subscibe_information;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inits();
	}
	private void inits() {
		listView = (ListView) findViewById(R.id.subinformation_lv);
		Intent intent = getIntent();
		setTitleTv(intent.getStringExtra("name"));
		/*View view = LayoutInflater.from(this).inflate(R.layout.item_subscibe, null);
		TextView newNum = (TextView) view.findViewById(R.id.subscibe_lv_quanquan);
		newNum.setVisibility(View.GONE);*/
		new SubscibeInformationAsyncTask().execute("subscribeDetails.js");
	}
	
	class SubscibeInformationAsyncTask extends AsyncTask<String, Void, List<SubscribeInformation>>{
		
		@Override
		protected void onPreExecute() {
			showdialog(SubscibeInformationActivity.this, R.string.dialog);
			super.onPreExecute();
		}
		
		@Override
		protected List<SubscribeInformation> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(SubscibeInformationActivity.this, params[0]);
			
			return mySubJson(str);
		}

		@Override
		protected void onPostExecute(List<SubscribeInformation> result) {
			hideDialog();
			super.onPostExecute(result);
			SubscribeInformationAdapter adapter = new SubscribeInformationAdapter(SubscibeInformationActivity.this, result);
			listView.setAdapter(adapter);
		}
		
		private List<SubscribeInformation> mySubJson(String str){
			List<SubscribeInformation> list = new ArrayList<SubscribeInformation>();
			try {
				JSONObject jsonObject = new JSONObject(str);
				int status = jsonObject.getInt("status");
				//Log.e("---", "salkjfd");
				if(status == 1){
					return null;
				}
				
				JSONArray jsonArray = jsonObject.getJSONArray("subscribeDetails");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					SubscribeInformation sub = new SubscribeInformation();
					
					sub.setContent(json.getString("content"));
					sub.setImage(json.getString("image"));
					sub.setTime(json.getString("time"));
					sub.setTitle(json.getString("title"));
					list.add(sub);
					
					Log.e("---", sub.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}
}
