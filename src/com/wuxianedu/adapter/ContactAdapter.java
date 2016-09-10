package com.wuxianedu.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;

import com.wuxianedu.R;
import com.wuxianedu.been.Contact;
import com.wuxianedu.util.PingYinUtil;
import com.wuxianedu.widget.RoundImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter implements SectionIndexer{
	private Context context;
	private List<Contact> list;

	public ContactAdapter(Context context, List<Contact> list) {
		this.context = context;
		this.list = list;
	}
	public void setList(List<Contact> list){
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHonder honder = null;
		if (convertView == null) {
			honder = new ViewHonder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_lv, null);
			honder.zimuTv = (TextView) convertView.findViewById(R.id.contact_lv_zimu);
			honder.nameTV = (TextView) convertView.findViewById(R.id.contact_lv_name);
			honder.headIv = (RoundImageView) convertView.findViewById(R.id.contact_lv_head);
			convertView.setTag(honder);
		} else {
			honder = (ViewHonder) convertView.getTag();
		}
		Contact contact = list.get(position);
		String name = contact.getName();
		honder.nameTV.setText(name);
		lodeImage(contact.getHead(), honder.headIv);

		String nameFrist = PingYinUtil.converterToFirstSpell(name);
		String nFrist = contact.getnFrist();
		if (position == 0) {
			honder.zimuTv.setVisibility(View.VISIBLE);
			honder.zimuTv.setText(nFrist);

		} else if (nFrist.equals(list.get(position-1).getnFrist())) {

			honder.zimuTv.setVisibility(View.GONE);
		} else {
			honder.zimuTv.setVisibility(View.VISIBLE);
			honder.zimuTv.setText(nFrist);
		}

		return convertView;
	}

	class ViewHonder {
		TextView zimuTv, nameTV;
		RoundImageView headIv;
	}

	private void lodeImage(String str, final RoundImageView headIv) {
		x.image().loadDrawable(str, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {

			@Override
			public void onSuccess(Drawable arg0) {
				// TODO Auto-generated method stub
				headIv.setImageDrawable(arg0);
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
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			Contact contact = list.get(i);
			String str = contact.getnFrist();
			char c = str.charAt(0);
			if(c == sectionIndex){
				return i;
			}
		}
		return sectionIndex;
		
	}
	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
