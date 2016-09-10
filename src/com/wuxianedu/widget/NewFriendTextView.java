package com.wuxianedu.widget;

import com.wuxianedu.util.DensityUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class NewFriendTextView extends TextView {

	public NewFriendTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Drawable topDrawable = getCompoundDrawables()[1];
		int size = DensityUtil.dp2px(context, 40);
		topDrawable.setBounds(0, 0, (int)size, size);
		setCompoundDrawables(null, topDrawable, null, null);
	}

}
