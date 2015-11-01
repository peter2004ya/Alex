package com.example.positionfood;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.networkcommunication.volleymgr.NetworkManager;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

public class LoginActivty extends Activity {
	
	private ProgressDialog mProgressDialog;
	private EditText UserInput,uPasswordInput,StroesInput,sPasswordInput;
	private Button uLoginButton,cancleButton,signupButton,sLoginButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_activty);
		
		UserInput = (EditText) findViewById(R.id.editText1);
		uPasswordInput = (EditText) findViewById(R.id.editText2);
		StroesInput = (EditText) findViewById(R.id.editText3);
		sPasswordInput = (EditText) findViewById(R.id.editText4);
		
		uLoginButton  = (Button) findViewById(R.id.button1);
		cancleButton = (Button) findViewById(R.id.button2);
		signupButton = (Button) findViewById(R.id.button3);
		sLoginButton = (Button) findViewById(R.id.button4);
		
		uLoginButton.setOnClickListener(muLoginListener);
		cancleButton.setOnClickListener(mcancleListener);
		signupButton.setOnClickListener(msignupListener);
		sLoginButton.setOnClickListener(msLoginListener);
		
        mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("確認登入中...");
	}
	
	//使用者登入
    private OnClickListener muLoginListener = new OnClickListener() {
    	
		public void onClick(View v) {
			
			try {
				String strAccount = URLEncoder.encode(UserInput.getEditableText().toString(), "UTF-8");
				mProgressDialog.show();
				String url = "http://i2015server.herokuapp.com/user/login?User=" + strAccount;				
				StringRequest request = new StringRequest(Request.Method.GET, url, LoginSuccessListener, LoginErrorListener);
				NetworkManager.getInstance(LoginActivty.this).request(null, request);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	};
	
	protected Listener<String> LoginSuccessListener = new Listener<String>() {
		private String DataPassword,Name;
		
		@Override
		public void onResponse(String response) {
			try {
				JSONArray array = new JSONArray(response);
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					DataPassword = obj.getString("Password");
					Name = obj.getString("NM");
				}	
			}catch (JSONException e1) {
				e1.printStackTrace();
			}finally {
				mProgressDialog.dismiss();
			}
	
			String strPassword = (uPasswordInput.getEditableText().toString());
			
			if(DataPassword.equals(strPassword)){
				Intent intent = new Intent();
				String resturantName = ("Name");
				intent.putExtra("resName", resturantName);
				intent = new Intent(LoginActivty.this, MainActivity.class);				 
				startActivity(intent);
				LoginActivty.this.finish();        
			}else{
				Toast toast = Toast.makeText(LoginActivty.this,"密碼錯誤！",Toast.LENGTH_SHORT);
				toast.show();
				uPasswordInput.setText("");
			}
		}
	};
	protected ErrorListener LoginErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
		}
	};
	
	//店家登入
    private OnClickListener msLoginListener = new OnClickListener() {
    	
		public void onClick(View v) {
			
			try {
				String strAccount = URLEncoder.encode(StroesInput.getEditableText().toString(), "UTF-8");
	
				mProgressDialog.show();
				
				String url = "http://i2015server.herokuapp.com/stores/login?Store=" + strAccount;				
				StringRequest request = new StringRequest(Request.Method.GET, url, LoginSuccessListener1, LoginErrorListener1);
				NetworkManager.getInstance(LoginActivty.this).request(null, request);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	};
	
	protected Listener<String> LoginSuccessListener1 = new Listener<String>() {
		private String DataPassword,Name;
		
		@Override
		public void onResponse(String response) {
			try {
				JSONArray array = new JSONArray(response);
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					DataPassword = obj.getString("Password");	
					Name = obj.getString("Name");
				}	
			}catch (JSONException e1) {
				e1.printStackTrace();
			}finally {
				mProgressDialog.dismiss();
			}
	
			String strPassword = (sPasswordInput.getEditableText().toString());
			
			if(DataPassword.equals(strPassword)){
				Intent intent = new Intent();
				String resturantName = ("Name");
				intent.putExtra("resName", resturantName);
				intent = new Intent(LoginActivty.this, StoresActivity.class);				 
				startActivity(intent);
				LoginActivty.this.finish();
			}else{
				Toast toast = Toast.makeText(LoginActivty.this,"密碼錯誤！",Toast.LENGTH_SHORT);
				toast.show();
				sPasswordInput.setText("");
			}
		}
	};
	
	protected ErrorListener LoginErrorListener1 = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
		}
	};
	
	//離開
	private OnClickListener mcancleListener = new OnClickListener() {
			
			public void onClick(View v) {
				LoginActivty.this.finish(); 
			}
		};
	//註冊
	private OnClickListener msignupListener = new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivty.this, SignupActivity.class);
				startActivity(intent);
				LoginActivty.this.finish(); 
			}
		};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_activty, menu);
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
