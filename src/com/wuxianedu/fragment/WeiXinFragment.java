package com.wuxianedu.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.activity.ChatActivity;
import com.wuxianedu.activity.MainActivity;
import com.wuxianedu.activity.SubscibeActivity;
import com.wuxianedu.adapter.WeiXinAdapter;
import com.wuxianedu.been.WeChat;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class WeiXinFragment extends Fragment{
	private View view;
	private MainActivity mActivity;
	private ListView listView;
	private List<WeChat> list;
	private WeiXinAdapter weiXinAdapter;
	private boolean isFrist = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view =  inflater.inflate(R.layout.fragment_weixin, null);
			mActivity =(MainActivity) getActivity();
		}
		listView = (ListView) view.findViewById(R.id.weixin_lv);
		
		return view;
		
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		init();
		if(isFrist){
			new WeChatTask().execute("wechat.js");
			isFrist = false;
		}
		
	}
	private void init() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if(position == 0){
					Intent intent = new Intent(getActivity(),SubscibeActivity.class);
					View newNumView = LayoutInflater.from(getActivity()).inflate(R.layout.item_weixin_wechat, null);
					TextView newNum = (TextView) newNumView.findViewById(R.id.weixin_lv_quanquan);
					list.get(position).setNewsNum(0);
					list.add(list.get(position));
					weiXinAdapter.setList(list);
					listView.setAdapter(weiXinAdapter);
					startActivity(intent);
				}else{
					Intent intent = new Intent(getActivity(),ChatActivity.class);
					String name = list.get(position).getName();
					intent.putExtra("name", name);
					View newNumView = LayoutInflater.from(getActivity()).inflate(R.layout.item_weixin_wechat, null);
					TextView newNum = (TextView) newNumView.findViewById(R.id.weixin_lv_quanquan);
					list.get(position).setNewsNum(0);
					list.add(list.get(position));
					weiXinAdapter.setList(list);
					listView.setAdapter(weiXinAdapter);
					startActivity(intent);
				}
			}
		});
		
	}
	class WeChatTask extends AsyncTask<String, Void, List<WeChat>>{
		@Override
		protected void onPreExecute() {
			mActivity.showdialog(mActivity,R.string.dialog);
			super.onPreExecute();
		}
		
		@Override
		protected List<WeChat> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(getActivity(), params[0]);
			return json(str);
		}

		@Override
		protected void onPostExecute(List<WeChat> result) {
			mActivity.hideDialog();
			FileLocalCache.setSerializableData(mActivity, result, DateFile.WECHAT);
			
			weiXinAdapter = new WeiXinAdapter(mActivity, result);
			listView.setAdapter(weiXinAdapter);
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
				JSONArray jArray = jsonObject.getJSONArray("contacts");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject contactjson = jArray.getJSONObject(i);
					WeChat weChat = new WeChat();
					weChat.setName(contactjson.getString("name"));;
					
					weChat.setArea(contactjson.getString("area"));
					weChat.setWeCode(contactjson.getInt("weCode"));
					weChat.setHead(contactjson.getString("head"));
					weChat.setAutograph(contactjson.getString("autograph"));
					weChat.setLastStr(contactjson.getString("lastStr"));
					weChat.setNewsNum(contactjson.getInt("newsNum"));
					weChat.setLastTime(contactjson.getString("lastTime"));
					JSONArray jsonArray = contactjson.getJSONArray("images");
					List<String> image = new ArrayList<String>();
					for (int j = 0; j < jsonArray.length(); j++) {
						String head = jsonArray.getString(j);
						image.add(head);
					}
					weChat.setList(image);
					
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
