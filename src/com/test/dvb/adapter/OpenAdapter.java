package com.test.dvb.adapter;

import java.util.Vector;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.dvb.R;
import com.test.dvb.units.Item;

public class OpenAdapter extends ArrayAdapter<Item> {
	private Activity ctx;
	private int idLayout;
	private Vector<Item> myVector = new Vector<Item>();

	public OpenAdapter(Activity context, int resource, Vector<Item> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.idLayout = R.layout.item_listview_open;
		this.myVector = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = ctx.getLayoutInflater().inflate(idLayout, null);
		}
		if (myVector.size() > 0 && position >= 0) {
			ImageView img_icon = (ImageView) convertView
					.findViewById(R.id.imageView_icon);
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.textView_name);
			
			Item tmp = myVector.elementAt(position);
			tv_name.setText(tmp.getName());
			
			if(tmp.isFile()){
				if(tmp.getName().endsWith(".pdf")){
					img_icon.setImageResource(R.drawable.file_pdf);
				}else{
					img_icon.setImageResource(R.drawable.file_un);
				}
			}else{
				img_icon.setImageResource(R.drawable.folder);
			}

		}
		return convertView;
	}
}
