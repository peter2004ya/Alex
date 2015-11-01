package com.example.positionfood;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;

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

public class SignupActivity extends Activity {
	
	private EditText mEmail, mUser, mPassword, mMP, mNM;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup_activty);
		
		Button signupButton = (Button) findViewById(R.id.button1);
		Button returnButton = (Button) findViewById(R.id.button2);
		
		signupButton.setOnClickListener(msignupListener);
		returnButton.setOnClickListener(mreturnListener);
		
		mNM = (EditText) findViewById(R.id.editText1);
		mMP = (EditText) findViewById(R.id.editText2);
		mEmail = (EditText) findViewById(R.id.editText3);
		mUser = (EditText) findViewById(R.id.editText4);
		mPassword = (EditText) findViewById(R.id.editText5);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Wait...");
	}
	
	private OnClickListener msignupListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			try {
				String strNM = URLEncoder.encode(mNM.getEditableText().toString(), "UTF-8");
				String strMP = URLEncoder.encode(mMP.getEditableText().toString(), "UTF-8");
				String strEmail = URLEncoder.encode(mEmail.getEditableText().toString(), "UTF-8");
				String strUser = URLEncoder.encode(mUser.getEditableText().toString(), "UTF-8");
				String strPassword = URLEncoder.encode(mPassword.getEditableText().toString(), "UTF-8");
				mProgressDialog.show();
				
				String url = "http://i2015server.herokuapp.com/user/signup?NM=" + strNM + "&MP=" + strMP + "&Email=" + strEmail + "&User=" + strUser + "&Password=" + strPassword;
				StringRequest request = new StringRequest(Request.Method.GET, url, mOnAddSuccessListener, mOnErrorListener);
				NetworkManager.getInstance(SignupActivity.this).request(null, request);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	protected Listener<String> mOnAddSuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			mProgressDialog.dismiss();
			mNM.setText("");
			mMP.setText("");
			mEmail.setText("");
			mUser.setText("");
			mPassword.setText("");
			Toast.makeText(SignupActivity.this, "µù¥U¦¨¥\", Toast.LENGTH_LONG).show();
		}
	};
	
	protected ErrorListener mOnErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
			Toast.makeText(SignupActivity.this, "Err " + err.toString(), Toast.LENGTH_LONG).show();
		}
	};
	
	private OnClickListener mreturnListener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(SignupActivity.this, LoginActivty.class);
			startActivity(intent);
			SignupActivity.this.finish(); 
		}
	};



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
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
