package com.wuxianedu.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.activity.DetailsActivity;
import com.wuxianedu.activity.GroupChatActivity;
import com.wuxianedu.activity.MainActivity;
import com.wuxianedu.activity.NewFriendActivity;
import com.wuxianedu.adapter.ContactAdapter;
import com.wuxianedu.been.Contact;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;
import com.wuxianedu.util.PingYinUtil;
import com.wuxianedu.util.TVUtil;
import com.wuxianedu.widget.SearchEditText;
import com.wuxianedu.widget.SideBar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ContactFragment extends Fragment {
	private View view;
	private MainActivity mainactivity;
	private ListView listview;
	private View headView;
	private boolean isAdd = true;
	private ContactAdapter adapter;
	private List<Contact> list;
	private List<Contact> serchList = new ArrayList<Contact>();
	private List<Contact> serchSubList = new ArrayList<Contact>();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_contact, null);
			mainactivity = (MainActivity) getActivity();

		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if (isAdd) {
			init();
			listview.addHeaderView(headView, null, false);
			listview.setHeaderDividersEnabled(false);
			isAdd = false;
		}
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}

	/**
	 * 初始化控件,绑定布局
	 */
	private void init() {
		listview = (ListView) view.findViewById(R.id.contact_lv);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mainactivity,DetailsActivity.class);
				//intent.putExtra("postion", position);
				intent.putExtra("contact", (Serializable)list.get(position-1));
				mainactivity.startActivity(intent);
				
			}
		});
		headView = LayoutInflater.from(mainactivity).inflate(R.layout.item_contact_top, null);
		new contactTask().execute("contacts.js");
		SideBar sideBar = (SideBar) view.findViewById(R.id.side_side);
		TextView textView = (TextView) view.findViewById(R.id.side_text);
		sideBar.setTextView(textView);
		sideBar.setListView(listview);
		TextView newFriend = (TextView) headView.findViewById(R.id.contact_top_addfriend);
		newFriend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mainactivity,NewFriendActivity.class);
				mainactivity.startActivity(intent);
			}
		});
		
		TextView groupchat = (TextView) headView.findViewById(R.id.contact_top_groupchat);
		groupchat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mainactivity,GroupChatActivity.class);
				Log.e("---", "跳转");
				mainactivity.startActivity(intent);
			}
		});
		
		
		SearchEditText edit = (SearchEditText) headView.findViewById(R.id.contact_top_sousuo);
		edit.addTextChangedListener(new Myedit());
	}
	class Myedit implements TextWatcher{
		
		private View topview;
		

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			topview = headView.findViewById(R.id.contact_top_text);
			
	
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String str = s.toString();
			serchSubList.clear();
			serchList.clear();
			
			if(TVUtil.isEmpty(str)){
				adapter.setList(list);
				topview.setVisibility(View.VISIBLE);
			}
			if(TVUtil.isChinese(str)){
				
				for (int i = 0; i < list.size(); i++) {
					Contact contact = list.get(i);
					String name =contact.getName();
					if (name.startsWith(str)) {
						serchList.add(contact);
					}else if (name.contains(str)) {
						topview.setVisibility(View.GONE);
						serchSubList.add(contact);
					}
				}
			}else{
				topview.setVisibility(View.GONE);
				for (int j = 0; j < list.size(); j++) {
					Contact contact = list.get(j);
					String nFrist =contact.getnFrist();
					String nameFrist = contact.getNameFrist();
					if (nFrist.startsWith(str.toUpperCase())) {
						serchList.add(contact);
					}else if (nameFrist.contains(str.toUpperCase())) {
						serchSubList.add(contact);
						topview.setVisibility(View.GONE);
					
						
					}
				}
			}
			serchList.addAll(serchSubList);
			adapter.setList(serchList);
		}
	}
	class contactTask extends AsyncTask<String, Void, List<Contact>> {

		private Contact contact;
		private String name;
		
		

		@Override
		protected void onPreExecute() {
			mainactivity.showdialog(mainactivity,R.string.dialog);
			super.onPreExecute();
		}

		@Override
		protected List<Contact> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(mainactivity, params[0]);
			return json(str);
		}

		@Override
		protected void onPostExecute(List<Contact> result) {
			mainactivity.hideDialog();
			FileLocalCache.setSerializableData(mainactivity, result, DateFile.CONTACT);
			adapter = new ContactAdapter(mainactivity, result);
			listview.setAdapter(adapter);
			

			super.onPostExecute(result);
		}

		private List<Contact> json(String str) {
			list = new ArrayList<Contact>();
			try {
				JSONObject jsonObject = new JSONObject(str);
				int status = jsonObject.getInt("status");
				if (status == 1) {
					return null;
				}
				JSONArray jArray = jsonObject.getJSONArray("contacts");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject contactjson = jArray.getJSONObject(i);
					contact = new Contact();
					name = contactjson.getString("name");
					contact.setName(name);
					contact.setArea(contactjson.getString("area"));
					contact.setWeCode(contactjson.getInt("weCode"));
					contact.setHead(contactjson.getString("head"));
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
					contact.setPinYinName(PingYinUtil.getPingYin(name));
					String nameFirst = PingYinUtil.converterToFirstSpell(name);
					contact.setNameFrist(nameFirst);
					String nFrist = nameFirst.substring(0, 1);
					if (TVUtil.isEnglish(PingYinUtil.getPingYin(name))) {
						contact.setnFrist(nFrist);
						
					} else {
						//Log.e("---", "dsafsd");
						contact.setnFrist("#");
						
					}
					/*
					 * Collections.sort(list, new Comparator() {
					 * 
					 * @Override public int compare(Object lhs, Object rhs) { //
					 * TODO Auto-generated method stub
					 * 
					 * String str = PingYinUtil.getPingYin(name); String before
					 * = PingYinUtil.getPingYin(name); return
					 * str.compareTo(before); } });
					 */
					//Log.e("---", contact.toString());
					list.add(contact);
				}
				Collections.sort(list, new Comparator<Contact>() { // 对首字母进行排序

					@Override
					public int compare(Contact lhs, Contact rhs) {
						String befoer = lhs.getNameFrist();
						String after = rhs.getNameFrist();
						return befoer.compareTo(after); // 对字符串进行比较

					}
				});

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}
}
