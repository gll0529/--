package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.adapter.SubscibeAdapter;
import com.wuxianedu.been.WeChat;
import com.wuxianedu.constact.Constact;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.GetJsonString;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SubscibeActivity extends BaseActivity{

	private ListView listView;
	private List<WeChat> list;
	private TextView newNum;
	private SubscibeAdapter adapter;

	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_subscibe;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inits();
		setTitleTv("订阅号");
	}
	private void inits() {
		//if (Constact.isFrist) {
			listView = (ListView) findViewById(R.id.subscibe_lv);
			new WeChatTask().execute("subscribe.js");
			Constact.isFrist = false;
		//}
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SubscibeActivity.this,SubscibeInformationActivity.class);
				newNum = (TextView) view.findViewById(R.id.subscibe_lv_quanquan);
				//newNum.setVisibility(View.GONE);
				String name = list.get(position).getName();
				intent.putExtra("name", name);
				startActivity(intent);
				list.get(position).setNewsNum(0);
				list.add(list.get(position));
				adapter.setList(list);
				listView.setAdapter(adapter);
				
				
			}
		});
		
	}
	
	class WeChatTask extends AsyncTask<String, Void, List<WeChat>>{
		

		@Override
		protected void onPreExecute() {
			showdialog(SubscibeActivity.this, R.string.dialog);
			super.onPreExecute();
		}
		
		@Override
		protected List<WeChat> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(SubscibeActivity.this, params[0]);
			return json(str);
		}

		@Override
		protected void onPostExecute(List<WeChat> result) {
			hideDialog();
			adapter = new SubscibeAdapter(SubscibeActivity.this,result);
			listView.setAdapter(adapter);
			
			
			
			super.onPostExecute(result);
		}
		
		private List<WeChat> json(String str) {
			list = new ArrayList<WeChat>();
			try {
				JSONObject jsonObject = new JSONObject(str);
				int status = jsonObject.getInt("status");
				if (status == 1) {
					return null;
				}
				JSONArray jArray = jsonObject.getJSONArray("subscribe");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject contactjson = jArray.getJSONObject(i);
					WeChat weChat = new WeChat();
					weChat.setName(contactjson.getString("name"));;
					
					weChat.setWeCode(contactjson.getInt("weCode"));
					weChat.setLastStr(contactjson.getString("lastStr"));
					weChat.setNewsNum(contactjson.getInt("newsNum"));
					weChat.setLastTime(contactjson.getString("lastTime"));
					
					
					list.add(weChat);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
	}
}
