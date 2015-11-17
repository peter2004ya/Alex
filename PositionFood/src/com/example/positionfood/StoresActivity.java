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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class StoresActivity extends Activity {

	private Button logout;
	private TextView text1;
	private int StoreID;
	private ListView mListView;
	private ArrayList<Photo2> mDatas;
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final Photo2 photo = mDatas.get(position);
			final CharSequence[] items = { photo.menu, photo.total + "元"};
			
			AlertDialog.Builder b = new AlertDialog.Builder(StoresActivity.this);
			b.setTitle(photo.client + "的訂單");
			b.setItems(items, null);
			b.setPositiveButton("確認完畢", new DialogInterface.OnClickListener() {
				
				//刪除訂單
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			b.setNegativeButton("取消", null);
			b.show();
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stores);
		
		text1 = (TextView) findViewById(R.id.textView1);
		logout  = (Button) findViewById(R.id.button1);
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setOnItemClickListener(mOnItemClickListener );
		
		logout.setOnClickListener(mlogoutListener);
		
		String Name = getIntent().getStringExtra("resName");
		text1.setText(Name);
		
		//抓取店家ID
		try {
			String strAccount = URLEncoder.encode(Name, "UTF-8");
			String url = "http://i2015server.herokuapp.com/store/id?name=" + strAccount;				
			StringRequest request = new StringRequest(Request.Method.GET, url, pullidCompleteListener, pullidErrorListener);
			NetworkManager.getInstance(StoresActivity.this).request(null, request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			}
		//透過店家ID取訂單
		String url = "http://i2015server.herokuapp.com/order?rid=" + StoreID;				
		StringRequest request = new StringRequest(Request.Method.GET, url, pushpullCompleteListener, pushpullErrorListener);
		NetworkManager.getInstance(StoresActivity.this).request(null, request);
		
	}
	//Pull ID
	protected Listener<String> pullidCompleteListener = new Listener<String>() {
		
		@Override
		public void onResponse(String response) {
			try {
				JSONArray array = new JSONArray(response);
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					StoreID = obj.getInt("rid");	
				}
			}catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	};
	protected ErrorListener pullidErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
		}
	};
	
	//PushPull ID
	protected Listener<String> pushpullCompleteListener = new Listener<String>() {
		
		@Override
		public void onResponse(String response) {
			try {
				
				mDatas = new ArrayList<Photo2>();
				
				JSONArray array = new JSONArray(response);
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					
					Photo2 photo = new Photo2();
					
					photo.rid = obj.getString("rid");
					photo.client = obj.getString("client_id");
					photo.menu = obj.getString("menu_id");
					photo.total = obj.getString("total");
					mDatas.add(photo);
				}
				
				ListViewAdapter_Store adapter = new ListViewAdapter_Store(mDatas);
				mListView.setAdapter(adapter);
				
			}catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	};
	public class Photo2 {
		public String rid;
		public String client;
		public String menu;
		public String total;
		
		@Override
		public String toString() {
			return client;
		}
		
	}
	
	protected ErrorListener pushpullErrorListener = new ErrorListener() {
	
		@Override
		public void onErrorResponse(VolleyError err) {
		}
	};
	
	//店家登出
	private OnClickListener mlogoutListener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(StoresActivity.this, LoginActivty.class);
			startActivity(intent);
			StoresActivity.this.finish(); 
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stores, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
