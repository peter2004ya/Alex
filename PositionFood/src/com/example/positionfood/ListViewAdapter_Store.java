package com.example.positionfood;

import java.util.ArrayList;

import com.example.positionfood.StoresActivity.Photo2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter_Store extends BaseAdapter{

	private ArrayList<Photo2> mData;
	
	public  ListViewAdapter_Store(ArrayList<Photo2> data) {
		mData = data;
	}
	
	@Override
	public int getCount() {
		return (mData != null) ? mData.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_store, null);
		}
		TextView title = (TextView) convertView.findViewById(R.id.textView1);
		
		Photo2 photo = (Photo2) getItem(position);
		
	    title.setText(photo.client);
		return convertView;
	}

}
