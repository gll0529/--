package com.wuxianedu.activity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.been.Chat;
import com.wuxianedu.been.User;
import com.wuxianedu.constact.DateFile;
import com.wuxianedu.core.BaseActivity;
import com.wuxianedu.util.DateUtil;
import com.wuxianedu.util.ExpressionUtil;
import com.wuxianedu.util.FileLocalCache;
import com.wuxianedu.util.FileUtil;
import com.wuxianedu.util.ImageUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends BaseActivity{
	private int[] image = {R.drawable.chat_tool_photo,R.drawable.chat_tool_camera,R.drawable.chat_tool_location,
			R.drawable.chat_tool_send_file,R.drawable.chat_tool_audio,R.drawable.chat_tool_video};
	private String[] string = {"图片","拍照","位置","文件","视频通话","语音通话"};
	private int[] biaoqingbao = {R.drawable.f_static_000,R.drawable.f_static_001,R.drawable.f_static_002,
			R.drawable.f_static_003,R.drawable.f_static_004,R.drawable.f_static_005,
			R.drawable.f_static_006,R.drawable.f_static_007,R.drawable.f_static_008,
			R.drawable.f_static_009,R.drawable.f_static_010,R.drawable.f_static_011,
			R.drawable.f_static_012,R.drawable.f_static_013,R.drawable.f_static_014,
			R.drawable.f_static_015,R.drawable.f_static_016,R.drawable.f_static_017,
			R.drawable.f_static_018,R.drawable.f_static_019,R.drawable.f_static_020,
			R.drawable.f_static_021,R.drawable.f_static_022,R.drawable.f_static_023,
			R.drawable.f_static_024,R.drawable.f_static_025,R.drawable.f_static_026,
			R.drawable.f_static_027,R.drawable.f_static_028,R.drawable.f_static_029,
			R.drawable.f_static_030,R.drawable.f_static_031,R.drawable.f_static_032,
			R.drawable.f_static_033,R.drawable.f_static_034,R.drawable.f_static_035,
			R.drawable.f_static_036,R.drawable.f_static_037};
	private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> biaoqinglist = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> tupianlist = new ArrayList<Map<String,Object>>();
	private boolean isFrist = true;
	private boolean isCheck = true;
	private boolean biaoQingCheck= true;
	private ImageView jiaohao;
	private ImageView yuyin;
	private ImageView jianpan;
	private Button luyin;
	private EditText shuru;
	private Button fasong;
	private ImageView biaoqing;
	private SimpleAdapter adapter,yuyinAdapter;
	private ListView listView;
	private GridView gridView;
	private GridView biaoqinggridView;
	private View yuyinshuruView;
	private TextView wenzi;
	private ImageView  yuyinIv;
	private ImageView  photo;
	private View talk;
	private AnimationDrawable aDrawable;
	private TextView hintTv;
	private MediaRecorder recoder;
	private String recoderPath; //音频名称
	private long startRecorder; //开时录音的时刻
	private boolean isSend = true; //是否发送语音 
	private MediaPlayer player = new MediaPlayer();;
	private static final int IMAGE = 0 ;  //发送图片
	private static final int PHOTO = 1 ;  //发送照片
	private String imagePath;
	private String path;
	private String lastPath;
	private List <String> imageList = new ArrayList<String>();
	
	@Override
	protected int getTileId() {
		// TODO Auto-generated method stub
		return R.layout.activity_chat;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		gridView();
		inits();
		biaoqingClick();
		jiaoHaoClick();
		yuyinClick();
		shuruClick();
		luYinClick();
		girdViewClick();
		getListViewDate();
	}
	

	private void inits() {
		Intent intent = getIntent();
		setTitleTv(intent.getStringExtra("name"));
		setrightIv(R.drawable.icon_chat_user, null);
		yuyinshuruView = findViewById(R.id.yuyintishi);
		jiaohao = (ImageView)findViewById(R.id.chat_jiahao);
		yuyin = (ImageView)findViewById(R.id.chat_yuyin);
		jianpan = (ImageView) findViewById(R.id.chat_jianpan);
		luyin = (Button) findViewById(R.id.chat_luyin);
		shuru = (EditText) findViewById(R.id.chat_shuru);
		fasong = (Button) findViewById(R.id.chat_fasong);
		biaoqing = (ImageView) findViewById(R.id.chat_biaoqing);
		
		
		talk = findViewById(R.id.yuyintishi);
		View mic = talk.findViewById(R.id.yuyintishi_mic);
		aDrawable = (AnimationDrawable) mic.getBackground();
		hintTv = (TextView) talk.findViewById(R.id.yuyintishi_tishi);
		
		
		listView = (ListView) findViewById(R.id.chat_lv);
		
		adapter = new SimpleAdapter(this, list, R.layout.item_chat_lv,
				new String[]{"date","contact","head","path"},
				new int[]{R.id.chat_lv_date,R.id.chat_lv_contact,R.id.chat_lv_head,R.id.chat_lv_contact_image})
		{
			
			
			
			int i = 0;
			
			@Override
			public View getView(final int position, View convertView, android.view.ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				wenzi = (TextView) v.findViewById(R.id.chat_lv_contact);
				yuyinIv = (ImageView) v.findViewById(R.id.chat_lv_contact_yuyin);
				photo = (ImageView) v.findViewById(R.id.chat_lv_contact_image);
				Map<String,Object> map = list.get(position);
				lastPath = (String) map.get("lastPath");
				if (lastPath != null) {
					
					imageList.add(lastPath);
			
					photo.setImageBitmap(ImageUtil.getSmallBitmap(lastPath, 100, 160));
				}
				final String recoderPath1 = (String) map.get("recoderPath");
				Log.e("---", recoderPath1+"hfjfhjhj");
				if(wenzi.getText() == " "){
					yuyinIv.setVisibility(View.VISIBLE);
					wenzi.setVisibility(View.GONE);
					photo.setVisibility(View.GONE);
				}else if(photo.getDrawable() != null ){
					yuyinIv.setVisibility(View.GONE);
					wenzi.setVisibility(View.GONE);
					photo.setVisibility(View.VISIBLE);
					
				}else if(wenzi.getText() != null){
					yuyinIv.setVisibility(View.GONE);
					wenzi.setVisibility(View.VISIBLE);
					photo.setVisibility(View.GONE);
				
				}
				String contact = (String) wenzi.getText();
				SpannableString  str= ExpressionUtil.decorateFaceInStr(ChatActivity.this,contact);
				wenzi.setText(str);
				
				final AnimationDrawable animationDrawable = (AnimationDrawable) yuyinIv.getDrawable();
				
				animationDrawable.stop();
				
				yuyinIv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						
						if (player.isPlaying()) {
							stopPlayer();
							animationDrawable.stop();
						}else {
							animationDrawable.start();
							playVoice(recoderPath1, animationDrawable);
							
						}
					}
				});
				
				photo.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ChatActivity.this,ImageBrowseAvtivity.class);
						// position = chatList.indexOf(chat);
						
						int positions = imageList.indexOf(lastPath);
						
						intent.putExtra("getPostion", positions);
						intent.putExtra("getImage", (Serializable)imageList);
						//imageList.clear();
						startActivity(intent);
					}
				});
				
				return v;
			};
		};
		
		listView.setAdapter(adapter);
	}
	/**
	 * playVoice:语音播放的方法
	 * @date: 2016年8月1日 上午10:59:39 
	 * @author yifeng.Zhang
	 */
	private void playVoice(String string,final AnimationDrawable animationDrawable){
		
		if (player != null) {
			
			try {
				player.reset(); 
				player.setDataSource(string);
				player.prepare();
				player.start();
				player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						animationDrawable.stop();
					}
				});
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
				
			} catch (SecurityException e) {
				
				e.printStackTrace();
				
			} catch (IllegalStateException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
		}
	}
	
	/**
	 * stopPlayer:停止播放的方法
	 * @date: 2016年8月1日 上午11:00:11 
	 * @author yifeng.Zhang
	 */
	private void stopPlayer(){
		if (player != null && player.isPlaying()) {
		}
	}
	private void jiaoHaoClick(){
		jiaohao.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (isFrist) {
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.VISIBLE);
					biaoqinggridView.setVisibility(View.GONE);
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					isFrist = false;
					biaoQingCheck = true;
					hintKb();
				}else{
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.GONE);
					
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					isFrist = true;
				}
				
			}
		});
	}
	
	
	private void biaoqingClick(){
		biaoqing.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (biaoQingCheck) {
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.VISIBLE);
					gridView.setVisibility(View.GONE);
					biaoqinggridView.setVisibility(View.VISIBLE);
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					biaoQingCheck = false;
					isFrist = true;
					hintKb();
				}else{
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.GONE);
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					biaoQingCheck = true;
				}
				
			}
		});
	}
	
	private void shuruClick(){
		shuru.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view = findViewById(R.id.chat_view);
				view.setVisibility(View.GONE);
				View view2 = findViewById(R.id.chat_rela);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				view2.setLayoutParams(params);
				biaoQingCheck = true;
				isFrist = true;
			}
		});
	    TextWatcher mTextWatcher; mTextWatcher = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
				
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
				
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if(s.length()>=1){
				fasong.setVisibility(View.VISIBLE);
				jiaohao.setVisibility(View.GONE);
					
			}else{
				fasong.setVisibility(View.GONE);
				jiaohao.setVisibility(View.VISIBLE);
			}
		}
	 };
	 shuru.addTextChangedListener(mTextWatcher);
	 }
	
	
	private List<Map<String,Object>> getListViewDate(){
		
		fasong.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String contact = shuru.getText().toString();
				
				String date = DateUtil.parseLongToString(DateUtil.getCurrentTime(), DateUtil.SDF);
				User user = (User) FileLocalCache.getSerializableData(ChatActivity.this, DateFile.USERNAME);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("date", date);
				map.put("contact", contact);
				map.put("head", R.drawable.img_12);
				
				list.add(map);
				adapter.notifyDataSetChanged();
				shuru.setText("");
				listView.setSelection(list.size()-1);
				
				
			}
		});
		luyin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String contact = " ";
				String date = DateUtil.parseLongToString(DateUtil.getCurrentTime(), DateUtil.SDF);
				User user = (User) FileLocalCache.getSerializableData(ChatActivity.this, DateFile.USERNAME);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("date", date);
				map.put("contact", contact);
				map.put("head", R.drawable.img_12);
				map.put("recoderPath", recoderPath);
				list.add(map);
				adapter.notifyDataSetChanged();
				listView.setSelection(list.size()-1);
				
				
			}
		});
		gridView.setOnItemClickListener(new TypeGvClick());
		return list;
	}
	
	
	private void yuyinClick(){

		yuyin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isCheck){
					hintKb();
					shuru.setText("");
					yuyin.setImageResource(R.drawable.chatting_setmode_keyboard_btn_normal);
					shuru.setVisibility(View.GONE);
					luyin.setVisibility(View.VISIBLE);
					biaoqing.setVisibility(View.GONE);
					isCheck = false;
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.GONE);
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					biaoQingCheck = true;
					isFrist = true;
				}else {
					
					yuyin.setImageResource(R.drawable.chatting_setmode_voice_btn_normal);
					shuru.setVisibility(View.VISIBLE);
					luyin.setVisibility(View.GONE);
					biaoqing.setVisibility(View.VISIBLE);
					isCheck = true;
					View view = findViewById(R.id.chat_view);
					view.setVisibility(View.GONE);
					View view2 = findViewById(R.id.chat_rela);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams ) view2.getLayoutParams();
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					view2.setLayoutParams(params);
					biaoQingCheck = true;
					isFrist = true;
				}
					
			}	
		});
	}
	
	
	private void gridView() {
		
		gridView = (GridView) findViewById(R.id.chat_gv);
		SimpleAdapter adapter = new SimpleAdapter(ChatActivity.this, getDateGv(), R.layout.iten_chat_gv, 
				new String[]{"image","string"}, new int[]{R.id.chat_gv_image,R.id.chat_gv_name});
		gridView.setAdapter(adapter);
		biaoqinggridView = (GridView) findViewById(R.id.chat_gv_biaoqing);
		SimpleAdapter biaoqingadapter = new SimpleAdapter(ChatActivity.this, getbDateGvBiaoqing(), R.layout.item_chat_gv_biaoqing, 
				new String[]{"image"}, new int[]{R.id.chat_gv_biaoqing_image});
		biaoqinggridView.setAdapter(biaoqingadapter);
		
	}
	
	
	private List<Map<String,Object>> getDateGv(){
		tupianlist=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<image.length;i++){
			Map<String,Object> map=new HashMap<String,Object>();	
			map.put("image", image[i]);
			map.put("string", string[i]);
			tupianlist.add(map);
		}
		return tupianlist;
	}
	
	private List<Map<String,Object>> getbDateGvBiaoqing(){
		biaoqinglist = new ArrayList<Map<String,Object>>();
		for(int i=0;i<biaoqingbao.length;i++){
			Map<String,Object> map1=new HashMap<String,Object>();	
			map1.put("image", biaoqingbao[i]);
			biaoqinglist.add(map1);
		}
		return biaoqinglist;
	}
	
	private void luYinClick(){
		luyin.setOnTouchListener(new MyRecorder());
	
	}
	
	private void hintKb() {
		 InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);            
		 if(imm.isActive()&&getCurrentFocus()!=null){
		    if (getCurrentFocus().getWindowToken()!=null) {
		    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		    }             
		 }
	}
	class MyRecorder implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: //摁下
				talk.setVisibility(View.VISIBLE);  //录音提示布局显示
				startRecorder();
				hintTv.setText("向上滑动,取消发送");
				aDrawable.start();   //开时播放动画
				//startRecorder(); //开始录音
				//isSend = true; //是否发送
				break;
			case MotionEvent.ACTION_MOVE: //移动
				if (event.getY() < -50 ) {  //向上滑动
					hintTv.setText("松开手指,取消发送");
					//isSend = false;
				}else {
					hintTv.setText("向上滑动,取消发送");
				//	isSend = true;
				}
				
				break;
			case MotionEvent.ACTION_UP: //抬起
				stopRecorder();
				talk.setVisibility(View.GONE);
				aDrawable.stop();
			//	stopRecorder();
				/*if (isSend) {  //为true的时候发送语言
					
				}*/
				break;
			}
			return false;
		}
	}
	
	
	private void girdViewClick(){
		biaoqinggridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			List<Map<String,Object>> list = ExpressionUtil.buildExpressionsList(ChatActivity.this);
			//获取表情存放集合
			Map<String, Object> map = list.get(position);
			//获取字符串型的表情ID
			String faceString = (String) map.get("drawableId");
			//转换成符合文本
			faceString = ExpressionUtil.drawableIdToFaceName.get(faceString);
			//获取文字选中的位置
			int select = shuru.getSelectionStart();
			//将文字进行转换成 复合文本
			String str = shuru.getText().toString();
			StringBuffer sBuffer = new StringBuffer(str);
			//insert能在字符串的任意位置添加，而append只能在末尾.
			str = sBuffer.insert(select,faceString).toString();
			SpannableString  ss = ExpressionUtil.decorateFaceInStr(ChatActivity.this,str);//必须直接赋给textView,否则无用
			Log.e("---", ss.toString());
			//将转换过的文字设置到输入框
			shuru.setText(ss);
			//设置光标选中的位置
			shuru.setSelection(select+faceString.length());
			}
		});
	}
	
	/**
	 * startRecorder:开始录音
	 * @date: 2016年7月30日 下午4:50:51 
	 * @author yifeng.Zhang
	 */
	private void startRecorder(){
		if (recoder != null) {
			recoder.reset(); //重置录音设备
		}else {
			recoder = new MediaRecorder();
		}
		recoder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录音源为麦克风
		recoder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);//设置输出格式
		recoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置解码合适
		
		String string = FileUtil.getSDCardPath()+"weChat/voice/"; //创建银屏存放的目录
		FileUtil.checkDir(string); //检查目录是否存在,不存在则创建
		recoderPath = string +"voice" + System.currentTimeMillis() + ".amr";
		//Log.e("---",recoderPath);
		recoder.setOutputFile(recoderPath);  //设置音频输出路劲
		
		try {
			recoder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		startRecorder = System.currentTimeMillis();  //开时录音的时刻
		
		recoder.start();  //开始录音
	}
	
	/**
	 * stopRecorder:结束录音
	 * @date: 2016年7月30日 下午5:03:20 
	 * @author yifeng.Zhang
	 */
	private void stopRecorder(){
		recoder.stop(); //结束录音
		
		if (System.currentTimeMillis() - startRecorder < 1000) {  //录音时长小于1秒
			isSend = false;
			File file = new File(recoderPath);  //得到音频所在文件
			Toast.makeText(this, "录音时间过短,请重录.....", Toast.LENGTH_SHORT).show();
			if (file.exists()) {  //检查文件是否存在
				file.delete();  //存在则删除
			}
		}
		
		if (recoder != null) {
			recoder.release();  //释放资源
			recoder = null;  //把引用置为空
			System.gc();  //通知垃圾回收机制回收
		}
	}
	/**
	 * @ClassName: TypeGvClick 
	 * @Function: 类型监听事件
	 * @date: 2016年7月30日 下午3:01:10 
	 * @author yifeng.Zhang
	 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
	 */
	class TypeGvClick implements OnItemClickListener{
		
		

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case IMAGE: //图片
				Intent intent;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4以上
					intent = new Intent(Intent.ACTION_PICK,Media.EXTERNAL_CONTENT_URI);
				}else{
					intent = new Intent(Intent.ACTION_GET_CONTENT,null); //4.4以下及4.4
				}
				intent.setDataAndType(Media.EXTERNAL_CONTENT_URI,"image/*");
				startActivityForResult(intent, IMAGE);
				break;
			case PHOTO: //照片
				Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机
				String photoPath = FileUtil.getSDCardPath()+"weChat/photo/";
				FileUtil.checkDir(photoPath);
				String imageName = photoPath + "image" + System.currentTimeMillis() + ".jpg";
				Uri uri = Uri.fromFile(new File(imageName));
				imagePath = imageName;
				//将路径设置给相机
				photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
				startActivityForResult(photoIntent, PHOTO);
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		
		super.onActivityResult(arg0, arg1, arg2);
		String lastPath ; 
		switch (arg0) {
		case IMAGE:  //图片
			if (arg2 != null) {
				Uri uri = arg2.getData();
				path = ImageUtil.getRealFilePath(this, uri);
			}
			String date = DateUtil.parseLongToString(DateUtil.getCurrentTime(), DateUtil.SDF);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("date", date);
			lastPath = path;
			map.put("head", R.drawable.img_12);
			map.put("lastPath",lastPath);
			list.add(map);
			lastPath = null;
			Log.e("---", "sadfsadfasdfasdfasdfa");
			//adapter.notifyDataSetChanged();
			listView.setSelection(list.size()-1);
			break;
		case PHOTO:  //照片
			if (arg1 == -1) {
				
				String date1 = DateUtil.parseLongToString(DateUtil.getCurrentTime(), DateUtil.SDF);
				Map<String,Object> map1 = new HashMap<String, Object>();
				lastPath = imagePath;
				map1.put("date", date1);
				map1.put("head", R.drawable.img_12);
				map1.put("lastPath",lastPath);
				list.add(map1);
				lastPath = null;
				//adapter.notifyDataSetChanged();
				listView.setSelection(list.size()-1);
				
			}
			break;
		default:
			break;
		}
	}
	private void load(String str , final ImageView image){
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				// TODO Auto-generated method stub
				image.setImageDrawable(arg0);
			}
			
			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
