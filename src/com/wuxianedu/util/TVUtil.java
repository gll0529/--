/**
 * @Project Name:WeChat
 * @File Name:TVUtil.java
 * @Package Name:com.wuxainedu.util
 * @Date:2016年7月6日下午5:51:54
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName:TVUtil 
 * @Function: 字符串工具类
 * @Date:     2016年7月6日 下午5:51:54 
 * @author   yifeng.Zhang
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class TVUtil {

	/**
	 * 判断字符串是否为空
	 * @param string  字符串
	 * @return     返回结果
	 */
	public static boolean isEmpty(String string){
		if (string == null || string.trim().length()==0) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 判断是否为字母
     * @param str
     * @return
     */
    public static boolean isEnglish(String fstrData){  
    	//根据首字母进行判断,是否为英文
        char   c   =   fstrData.charAt(0);   
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))){   
              return   true;   
        }else{   
              return   false;   
        }
    }
    
    /**
     * 判断是否为中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
    	//正则表达式
        Pattern p=Pattern.compile("^[\u4e00-\u9fa5]*$");
        Matcher m=p.matcher(str);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    } 
}

