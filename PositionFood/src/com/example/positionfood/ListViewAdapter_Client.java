package com.example.positionfood;

import java.util.ArrayList;

import com.example.positionfood.MenuActivity.Photo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter_Client extends BaseAdapter {
	
	private ArrayList<Photo> mData;
	
	public ListViewAdapter_Client(ArrayList<Photo> data) {
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_client, null);
		}
		TextView title = (TextView) convertView.findViewById(R.id.textView1);
		TextView author = (TextView) convertView.findViewById(R.id.textView2);
		
	    Photo photo = (Photo) getItem(position);
		
	    title.setText(photo.name);
		author.setText(photo.price);
		return convertView;
	}

}
