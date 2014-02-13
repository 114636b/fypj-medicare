package com.example.supertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ElderlyInfo extends Activity {
	
	Button btnAbout;
	Intent myIntent;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	setContentView(R.layout.elderlyinfo);
	
	
	
	btnAbout=(Button)findViewById(R.id.btnAbout);
	myIntent= new Intent(this, AboutElderly.class);
	
	btnAbout.setOnClickListener(new OnClickListener(){
		
		@Override
		public void onClick(View v)
		{
			startActivity(myIntent);
		}
	});

}
}
