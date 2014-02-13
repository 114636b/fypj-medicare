package com.example.supertest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import sessionManager.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DoSomething extends Activity {
long time;
SessionManager session;
String nric,name;
URL url;
Intent p;
String year,month,day,hour,min,medname,medremarks,meddosage,medimage;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  super.onCreate(savedInstanceState);
	setContentView(R.layout.medack);
    session = new SessionManager(getApplicationContext()); 
    HashMap<String, String> user = session.getElderDetails();
    nric = user.get(SessionManager.KEY_ENRIC);
    name = user.get(SessionManager.KEY_ENAME);
  TextView tvmedname = (TextView)findViewById(R.id.textView2);
 TextView tvDosage =(TextView)findViewById(R.id.textView4);
 TextView tvRemarks = (TextView)findViewById(R.id.textView6);
p = new Intent(this,elder_main.class);
  ImageView i = (ImageView)findViewById(R.id.imageView1);
  Intent myIntent =getIntent();
  time = myIntent.getLongExtra("time", 0);
  year = myIntent.getStringExtra("year");
  month = myIntent.getStringExtra("month");
  day = myIntent.getStringExtra("day");
  hour = myIntent.getStringExtra("hr");
  min= myIntent.getStringExtra("min");
  medname = myIntent.getStringExtra("MN");
  medremarks = myIntent.getStringExtra("MR");
  meddosage = myIntent.getStringExtra("MD");
  medimage = myIntent.getStringExtra("MI");
  Calendar c = Calendar.getInstance(); 
  tvmedname.setText(medname);
  tvDosage.setText(meddosage);
  tvRemarks.setText(medremarks);
//Set time in milliseconds

try {
	url = new URL(medimage);
} catch (MalformedURLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
Bitmap image = null;
try {
	image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

i.setImageBitmap(image);

  
  
  
  Button ack = (Button)findViewById(R.id.button1);
  
  ack.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 DefaultHttpClient hc=new DefaultHttpClient();
	    	ResponseHandler <String> res=new BasicResponseHandler();
	    	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/MedAckServlet");
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    	nameValuePairs.add(new BasicNameValuePair("NRIC", nric));  
	    	nameValuePairs.add(new BasicNameValuePair("elderName", name));    
	    	nameValuePairs.add(new BasicNameValuePair("day", day)); 
	    	nameValuePairs.add(new BasicNameValuePair("month", month)); 
	    	nameValuePairs.add(new BasicNameValuePair("year", year)); 
	    	nameValuePairs.add(new BasicNameValuePair("hour", hour)); 
	    	nameValuePairs.add(new BasicNameValuePair("min", min)); 
		
	    	try {
				postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
				try {
					HttpResponse response=hc.execute(postMethod);
					showOk();
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}});
 
  
 
  
  
  
  
 }
 
 public void showOk(){
		
		
		new AlertDialog.Builder(this).setTitle("Successful")
		.setMessage("You have successfuly acknowledge the reminder.")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	
		        	
		      startActivity(p);
		        	
		        	
		        	
		        	
		        }})
		      
				     .show();
					;	
				}
 
 static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
}
