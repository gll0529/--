/**
 * @Project Name:WeChat
 * @File Name:CoreUtil.java
 * @Package Name:com.wuxainedu.util
 * @Date:2016年7月6日下午4:58:24
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * @ClassName:CoreUtil 
 * @Function: 管理Activity的工具类 
 * @Date:     2016年7月6日 下午4:58:24 
 * @author   yifeng.Zhang
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class CoreUtil {

	static List<AppCompatActivity> list = new ArrayList<AppCompatActivity>();
	
	/**
	 * 集合所有打开的activity
	 * @param fActivity
	 */
	public static void addToActivityList(AppCompatActivity fActivity){
		if(!list.contains(fActivity)){
			list.add(fActivity);
		}
	}
	
	/**
	 * 关闭activity集合
	 */
	public static void finishActivityList(){
		L.e("activity集合大小-----》》"+list.size());
		/*for (FragmentActivity fragmentActivity : list) {
			L.e("被关闭fragmentActivity-----》》"+fragmentActivity);
			fragmentActivity.finish();
		}*/
		for (int i = 0; i < list.size(); i++) {
			L.e("被关闭fragmentActivity-----》》"+list.get(i));
			list.get(i).finish();
		}
		list.clear();
	}
	
	/**
	 * 关闭activity 并杀死进程。
	 */
	public static void exitApp(){
		for (AppCompatActivity activity : list) {
			activity.finish();
			L.e("exitApp:-----》》"+activity);
		}
		list.clear();
		L.e("android.os.Process.killProcess(android.os.Process.myPid());");
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
	/**
	 * 移除关闭的activity
	 * 在调用 finish方法的时候 同时调用这个方法。不要在onDestroy()方法中调用，因为在 遍历集合关闭时也会调用
	 */
	public static void removeActivity(Context context){
		list.remove(context);
	}
}

