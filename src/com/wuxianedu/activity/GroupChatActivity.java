package com.wuxianedu.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.R;
import com.wuxianedu.adapter.GroupChatAdapter;
import com.wuxianedu.adapter.GroupchatHlvAdapter;
import com.wuxianedu.been.Contact;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.DensityUtil;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.GetJsonString;
import com.wuxianedu.util.PingYinUtil;
import com.wuxianedu.util.TVUtil;
import com.wuxianedu.widget.ClearEditText;
import com.wuxianedu.widget.HorizontalListView;
import com.wuxianedu.widget.SideBar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GroupChatActivity extends BaseActivity{
	private List<Contact> serchList = new ArrayList<Contact>();
	private List<Contact> serchSubList = new ArrayList<Contact>();
	private ListView group;
	private View headView;
	private GroupChatAdapter adapter;
	private List<Contact> list;
	private boolean ischeck;
	private List<Contact> groupList = new ArrayList<Contact>();
	private GroupchatHlvAdapter hlvadapter;
	private HorizontalListView hListView;
	private CheckBox checkBox;
	private Contact contact1;
	

	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_groupchat;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setrightTv("确定", null);
		setTitleTv("群聊");
		inits();
	}

	private void inits() {
		group = (ListView) findViewById(R.id.groupchat_lv);
		hListView = (HorizontalListView) findViewById(R.id.groupchat_hlv);
		hlvadapter = new GroupchatHlvAdapter(this,groupList);
		//Log.e("---", "sadfasd");
		headView = LayoutInflater.from(this).inflate(R.layout.item_group_top,null);
		
		group.addHeaderView(headView, null, false);
		group.setHeaderDividersEnabled(false);
		
		new contactTask().execute("contacts.js");
		//Log.e("---", "sadfasd");
		SideBar sideBar = (SideBar)findViewById(R.id.groupchat_side);
		TextView textView = (TextView)findViewById(R.id.groupchat_side_text);
		sideBar.setTextView(textView);
		sideBar.setListView(group);
		ClearEditText edit = (ClearEditText) findViewById(R.id.groupchat_serch);
		edit.addTextChangedListener(new MyGroupedit());
		group.setOnItemClickListener(new OnItemClickListener() {

			

		

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				contact1 = adapter.getList().get(position-1);
				checkBox = (CheckBox) view.findViewById(R.id.groupchat_lv_check);
				ischeck = !checkBox.isChecked(); //设置CheckBox状态
				checkBox.setChecked(ischeck);
				if(ischeck){
					addContact(contact1);
				}else{
					removeContact(contact1);
				}
			}
		});
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Contact contact = groupList.get(position);
				removeContact(contact);
				
				
				
			}
		});
	}
	
	private void addContact(Contact contact){
		contact.setCheck(ischeck);
		groupList.add(contact);
		//Log.e("---", contact.getHead()+"1");
		hlvadapter.setList(groupList);
		hListView.setAdapter(hlvadapter);
		//hlvadapter.setList(groupList);
		setwidth();
		if(groupList.size() == 0){
			setrightTv("确定", null);
		}else {
			setrightTv("确定("+groupList.size()+")", null);
		}
		

	}
	
	private void removeContact(Contact contact){
		contact.setCheck(false);
		groupList.remove(contact);
		hlvadapter.setList(groupList);
		//Log.e("---", contact.getHead()+"1");
		hListView.setAdapter(hlvadapter);
		contact.setAlpha(1.0f);
		//hlvadapter.setList(groupList);
		setwidth();
		if(groupList.size() == 0){
			setrightTv("确定", null);
		}else {
			setrightTv("确定("+groupList.size()+")", null);
		}
		group.setAdapter(adapter);

	}
	private void setwidth(){
		View view = findViewById(R.id.groupchat_serch_icon);
		int size = groupList.size(); //水平ListView的图片张数
		if (size == 0) {
			view.setVisibility(View.VISIBLE);
			return;
		}
		
		RelativeLayout.LayoutParams params =  (LayoutParams) hListView.getLayoutParams();
		int newWidth = size * DensityUtil.dp2px(this, 50);
		int maxWidth = DensityUtil.getDisplay(this)[0]-DensityUtil.dp2px(this, 80);
		
		if (newWidth > maxWidth) {
			params.width = maxWidth;
		}else {
			params.width = newWidth;
		}
		
		view.setVisibility(View.GONE);
		hListView.setSelection(groupList.size()-1);
		hListView.setLayoutParams(params);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.e("---", keyCode+"");
		
		if (keyCode == 4) {  //摁下的是删除键的时候
			if (groupList.size() == 0) {
				return super.onKeyDown(keyCode, event);
			}else {
				if (groupList.get(groupList.size() - 1 ).getAlpha() == 1.0f) { //透明度为1.0
					groupList.get(groupList.size() - 1 ).setAlpha(0.5f);
					hlvadapter.setList(groupList);
					//hListView.setAdapter(hlvadapter);
					return false;
				}else {
					removeContact(groupList.get(groupList.size() - 1 ));
				}
			}
		}
		//更新视图
		
		//adapter.setList(list);
		return false;
		
	}
class MyGroupedit implements TextWatcher{
		
		private View topview;
		

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			topview = headView.findViewById(R.id.groupchat_top_tv);
			
	
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
			super.onPreExecute();
			showdialog(GroupChatActivity.this,R.string.dialog);
		}

		@Override
		protected List<Contact> doInBackground(String... params) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String str = GetJsonString.getJsonString(GroupChatActivity.this, params[0]);
			
			return json(str);
		}

		@Override
		protected void onPostExecute(List<Contact> result) {
			super.onPostExecute(result);
			hideDialog();
			FileLocalCache.setSerializableData(GroupChatActivity.this, result, DateFile.GROUP);
			adapter = new GroupChatAdapter(GroupChatActivity.this, result);
			
			group.setAdapter(adapter);
			

			
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
