package com.wuxianedu.widget;

import com.wuxianedu.util.DensityUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Drawable leftdrawable = getCompoundDrawables()[0];
		Drawable rightdrawable = getCompoundDrawables()[3];
		int sizeleft = DensityUtil.dp2px(context, 24);
		int sizeright = DensityUtil.dp2px(context, 10);
		leftdrawable.setBounds(0, 0, sizeleft, sizeleft);
		rightdrawable.setBounds(0, 0, sizeright, (int) (sizeright*(26.0/15.0)));
		setCompoundDrawables(leftdrawable, null, rightdrawable, null);
		
	}

}
