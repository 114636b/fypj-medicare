package com.example.supertest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import sessionManager.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewEmergency extends Activity {
	EditText EName=null;
	//EditText name;
	//EditText Phone;
	//EditText address;
    EditText EPhone=null;
    EditText EAddress = null;
	Button btn;
	Intent myIntent;
	OnClickListener myContext;
	String nric;
	String name;
	 String namevalue;
     String phonevalue;
     String addressvalue;
     SessionManager session;
     HashMap<String, String> Elder ;
	private static final int CAMERA_REQUEST=1888;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergencyinfo);
		
		 session = new SessionManager(getApplicationContext()); 
	        HashMap<String, String> user = session.getElderDetails();
		EName = (EditText) findViewById(R.id.txtName);
		EPhone = (EditText) findViewById(R.id.txtPhone);
		EAddress = (EditText) findViewById(R.id.txtAddress);
		nric = user.get(SessionManager.KEY_ENRIC);
        name = user.get(SessionManager.KEY_ENAME);
        
//myIntent= new Intent(this, ViewEmergency.class);
		
        //retrieve session
        session = new SessionManager(getApplicationContext());  

        String name = "";
        String Phone = "";
        String address = "";
        
        
        HashMap<String, String> Elder = session.getEInfoDetails();
        name = Elder.get(SessionManager.KEY_EENAME);
        Phone = Elder.get(SessionManager.KEY_EEPHONE);
        address = Elder.get(SessionManager.KEY_EEADDRESS);
        EName = (EditText) findViewById(R.id.txtName);
		EPhone = (EditText) findViewById(R.id.txtPhone);
		EAddress = (EditText) findViewById(R.id.txtAddress);
       EName.setText(name);
       EPhone.setText(Phone);
       EAddress.setText(address);

		View.OnClickListener handler = new View.OnClickListener(){
            public void onClick(View v) {
                
                switch (v.getId()) {

                    case R.id.save:
                        popDialog();
                        break;
                    
                    
                }
            }
        };
		
    findViewById(R.id.save).setOnClickListener(handler);
		/*Intent intent = getIntent();
				String imageString = intent.getStringExtra("image");
		if (imageString != null && !imageString.equals("")) {
			byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);			  
		    mImageView.setImageBitmap(
		            BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
		    );
		}*/
		}
	
	
	public void popDialog(){
	
		new AlertDialog.Builder(this).setTitle("Confirmation")
		.setMessage("Are you sure you want to save as emergency contact?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	//Check and insert database code here
		        	
		        	
		        	
		        	
		             
		             namevalue = (String)EName.getText().toString();
		             phonevalue = (String)EPhone.getText().toString();
		            addressvalue = (String)EAddress.getText().toString();
		             String retrievedStatus1 = "";
		             String retrievedStatus2 = "";
		        	
		        	DefaultHttpClient hc=new DefaultHttpClient();
		        	ResponseHandler <String> res=new BasicResponseHandler();
		        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/EmergencyInfoservlet");
		        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		        	nameValuePairs.add(new BasicNameValuePair("NRIC", nric)); 
		        	nameValuePairs.add(new BasicNameValuePair("Name", name)); 
		        	nameValuePairs.add(new BasicNameValuePair("EPhone", phonevalue)); 
		        	nameValuePairs.add(new BasicNameValuePair("EName", namevalue)); 
		        	nameValuePairs.add(new BasicNameValuePair("EAddress", addressvalue));
				try {
					postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				try {
					HttpResponse response=hc.execute(postMethod);
					
					session.storeEmergencyInfo(namevalue,phonevalue,addressvalue);    			
	    			
	    			showOk();
	    			}catch(Exception E){
	    				showFail();
	}
					
					
					
					
				
				
		        	
		        	
		        	
		        	
		        	
		        }})
		        .setNegativeButton("No", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
		        
			        }})
				     .show();
					;	
				}

public void showOk(){
	
	
	new AlertDialog.Builder(this).setTitle("Successful")
	.setMessage("Successfully updated!")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	        	Intent myIntent = new Intent(getApplicationContext(),elder_main.class);
	        	startActivity(myIntent);
	        	
	      
	        	
	        	
	        	
	        	
	        }})
	      
			     .show();
				;	
			}


public void showFail(){
	
	
	new AlertDialog.Builder(this).setTitle("Contact already updated")
	.setMessage("Please change name of emergency contact")
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	      
	        	
	        	
	        	
	        	
	        }})
	      
			     .show();
				;	
			}


	


static{
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
StrictMode.setThreadPolicy(policy);  
}	
	





	public void uploadImage(View v){
		
	
	}
	
	
}