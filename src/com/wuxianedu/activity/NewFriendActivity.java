package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.adapter.NewFriendAdapter;
import com.wuxianedu.been.NewFriend;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class NewFriendActivity extends BaseActivity{
	private ListView listView;
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_newfriend;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitleTv("新朋友");
		listView = (ListView) findViewById(R.id.newfriend_lv);
		View view = LayoutInflater.from(NewFriendActivity.this).inflate(R.layout.item_newfriend_top, null);
		listView.addHeaderView(view,null,false);
		listView.setHeaderDividersEnabled(false);
		
		new contactTask().execute("addFriends.js");
		
	/*	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Button newFriendbt = (Button) view.findViewById(R.id.newfriend_lv_bt);
				Log.e("---", 123+"");
				newFriendbt.setOnClickListener(new View.OnClickListener() {
				
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						newFriendbt.setText("已接受");
						newFriendbt.setBackgroundResource(R.drawable.d_newfriend_bt_press);
//						viewHonder.newFriendbt.setClickable(false);
					}
				});
			}
		});*/
		
	}
	class contactTask extends AsyncTask<String, Void, List<NewFriend>> {

		
		private String name;
		
		

		@Override
		protected void onPreExecute() {
			showdialog(NewFriendActivity.this,R.string.dialog);
			super.onPreExecute();
		}

		@Override
		protected List<NewFriend> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(NewFriendActivity.this, params[0]);
			return json(str);
			
		}

		@Override
		protected void onPostExecute(List<NewFriend> result) {
			hideDialog();
			FileLocalCache.setSerializableData(NewFriendActivity.this, result, DateFile.NEWFRIEND);
			NewFriendAdapter adapter = new NewFriendAdapter(NewFriendActivity.this,result);
			listView.setAdapter(adapter);
			super.onPostExecute(result);
		}

		private List<NewFriend> json(String str) {
			List<NewFriend> list = new ArrayList<NewFriend>();
			try {
				JSONObject jsonObject = new JSONObject(str);
				int status = jsonObject.getInt("status");
				if (status == 1) {
					return null;
				}
				JSONArray jArray = jsonObject.getJSONArray("contacts");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject contactjson = jArray.getJSONObject(i);
					NewFriend contact = new NewFriend();
					name = contactjson.getString("name");
					contact.setName(name);
					contact.setArea(contactjson.getString("area"));
					contact.setWeCode(contactjson.getInt("weCode"));
					contact.setHead(contactjson.getString("head"));
					contact.setAdd(contactjson.getBoolean("isAdd"));
					contact.setAutograph(contactjson.getString("autograph"));
					contact.setLastStr(contactjson.getString("lastStr"));
					contact.setNewsNum(contactjson.getInt("newsNum"));
					contact.setLastTime(contactjson.getString("lastTime"));
					JSONArray jsonArray = contactjson.getJSONArray("images");
					List<String> image = new ArrayList<String>();
					for (int j = 0; j < jsonArray.length(); j++) {
						String head = jsonArray.getString(j);
						image.add(head);
					}
					contact.setImages(image);
					list.add(contact);
					Log.e("---", contact.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}
}
