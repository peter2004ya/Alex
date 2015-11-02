package com.example.positionfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StoresActivity extends Activity {

	private Button logout;
	private TextView text1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stores);
		
		text1 = (TextView) findViewById(R.id.textView1);
		logout  = (Button) findViewById(R.id.button1);
		
		logout.setOnClickListener(mlogoutListener);
		
		String Name = getIntent().getStringExtra("resName");
		text1.setText(Name);
	}

	//©±®aµn¥X
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
