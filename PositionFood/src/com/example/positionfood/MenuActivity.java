package com.example.positionfood;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	private ListView mListView;
	private ArrayList<Photo> mDatas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		TextView text1 = (TextView) findViewById(R.id.textView1);
		Button ReturnButton  = (Button) findViewById(R.id.button1);
		mListView = (ListView) findViewById(R.id.listView1);
		ReturnButton.setOnClickListener(ReturnListener);
		
		String Name = getIntent().getStringExtra("resName"); 
		text1.setText(Name);
		
		try {
			String strAccount = URLEncoder.encode(Name, "UTF-8");
			String url = "http://i2015server.herokuapp.com/store/menu?name=" + strAccount;				
			StringRequest request = new StringRequest(Request.Method.GET, url, menuCompleteListener, menuErrorListener);
			NetworkManager.getInstance(MenuActivity.this).request(null, request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private Listener<String> menuCompleteListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			try {
				mDatas = new ArrayList<Photo>();
				JSONArray array0 = new JSONArray(response);
				JSONObject obj = array0.getJSONObject(0);
				JSONArray array1 = obj.getJSONArray("menu");
				
				for (int i = 0; i < array1.length(); i++) {
					JSONObject jsonPhoto = array1.getJSONObject(i);
					
					Photo photo = new Photo();
					
					photo.id = jsonPhoto.getString("id");
					photo.name = jsonPhoto.getString("name");
					photo.price = jsonPhoto.getString("price");
					mDatas.add(photo);
				}
				
				ListViewAdapter_Client adapter = new ListViewAdapter_Client(mDatas);
				mListView.setAdapter(adapter);
				
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	private ErrorListener menuErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			
		}
	};
	
	public class Photo {
		public String id;
		public String name;
		public String price;
		public String name() {
			return name;
		}
	}
	
	//ªð¦^
	private OnClickListener ReturnListener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuActivity.this, La_carte_menu.class);
			startActivity(intent);
			MenuActivity.this.finish(); 
		}
	};
	
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
