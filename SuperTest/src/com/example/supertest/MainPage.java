package com.example.supertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPage extends Activity {
	
	Button myBtn;
	Intent myIntent;
	Button myElderlybtn;
	Intent myElderly;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		myElderlybtn=(Button)findViewById(R.id.myElderlybtn);
		myElderly = new Intent(this, ElderlyAccess.class);
		myBtn=(Button)findViewById(R.id.myBtn);
		myIntent= new Intent(this, CareGiverAccess.class);
		
		myBtn.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				startActivity(myIntent);
			}
		});
		
		myElderlybtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				startActivity(myElderly);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
