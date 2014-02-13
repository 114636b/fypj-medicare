package com.example.supertest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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





import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CareGiverSignUp extends Activity {
	EditText NRIC=null;
    EditText pin=null;
    EditText name=null;
    EditText phone = null;
	Button btn;
	Intent myIntent;
	OnClickListener myContext;
	private Uri mImageUrl;
	ImageView mImageView;
	double latitude;
	double longitude;
	TextView nric;
	Button btnBack;
	
	private static final int CAMERA_REQUEST=1888;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caregiversignup);
		
		 
		NRIC = (EditText) findViewById(R.id.txtNric);
		pin = (EditText) findViewById(R.id.txtPassword);
		name = (EditText) findViewById(R.id.txtName);
		phone = (EditText) findViewById(R.id.txtPhone);
		 GPSTracker tracker = new GPSTracker(this);
		 if (tracker.canGetLocation() == false) {
		        tracker.showSettingsAlert();
		    } else {
		        latitude = tracker.getLatitude();
		        longitude = tracker.getLongitude();
		    }
		 String testing = new Double(latitude).toString();
		 String testing2 = new Double(longitude).toString();

		 //nric=(TextView)findViewById(R.id.txtNRICProfile);
		 //nric.setText(testing+","+testing2);
		myIntent= new Intent(this, CareGiverAccess.class);
		btn = (Button) findViewById(R.id.btnRegister);
		btnBack = (Button)findViewById(R.id.btnBack);
		mImageView = (ImageView) findViewById(R.id.imageView1);
		Intent intent = getIntent();
				String imageString = intent.getStringExtra("image");
		if (imageString != null && !imageString.equals("")) {
			byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);			  
		    mImageView.setImageBitmap(
		            BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
		    );
		}
		}
	
	
	public void popDialog(View v){
	  
		new AlertDialog.Builder(this).setTitle("Confirmation")
		.setMessage("Are you sure you want to register?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	//Check and insert database code here
		        	
		        	
		        	
		        	
		        	
		        	NRIC = (EditText) findViewById(R.id.txtNric);
		    		pin = (EditText) findViewById(R.id.txtPassword);
		    		name = (EditText) findViewById(R.id.txtName);
		    		phone = (EditText) findViewById(R.id.txtPhone);
		    		EditText secondpin = (EditText)findViewById(R.id.editText1);
		    		String secondpinvalue = (String)secondpin.getText().toString();
		             String nricvalue = (String)NRIC.getText().toString();
		             String pinvalue = (String)pin.getText().toString();
		             String namevalue = (String)name.getText().toString();
		             String phonevalue = (String)phone.getText().toString();
		             String retrievedStatus1 = "";
		             String retrievedStatus2 = "";
		        	
		             if(!pinvalue.equals(secondpinvalue)){
		            	 showWrongPw();
		             }
		             
		             else{
		        	DefaultHttpClient hc=new DefaultHttpClient();
		        	ResponseHandler <String> res=new BasicResponseHandler();
		        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/supertestservlet");
		        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		        	nameValuePairs.add(new BasicNameValuePair("nric", nricvalue));  
		        	nameValuePairs.add(new BasicNameValuePair("pin", pinvalue)); 
		        	nameValuePairs.add(new BasicNameValuePair("phone", phonevalue)); 
		        	nameValuePairs.add(new BasicNameValuePair("name", namevalue)); 
				try {
					postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				try {
					HttpResponse response=hc.execute(postMethod);
					Scanner scanner = new Scanner(response.getEntity().getContent());
	    			String json = scanner.nextLine() ;
	    					    			
	    			JSONArray jsonArray = new JSONArray(json);
	    			List<String> list = new ArrayList<String>();
	    			for (int i = 0; i < jsonArray.length(); i++) {
	    			    list.add(jsonArray.getString(i));
	    			}
	    			retrievedStatus1 = (String)list.get(0);
	    			try{
	    			retrievedStatus2 = (String)list.get(1);
	    					
	    			showOk();
	    			}catch(Exception E){
	    				showFail();
	}
					
					
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	.setMessage("Registration successful")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	        	startActivity(myIntent);
	      
	        	
	        	
	        	
	        	
	        }})
	      
			     .show();
				;	
			}


public void showFail(){
	
	
	new AlertDialog.Builder(this).setTitle("NRIC Already in used")
	.setMessage("NRIC already in used, Please change it")
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	      
	        	
	        	
	        	
	        	
	        }})
	      
			     .show();
				;	
			}

public void showWrongPw(){
	
	
	new AlertDialog.Builder(this).setTitle("Password Error")
	.setMessage("Password does not match")
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        	
	      
	        	
	        	
	        	
	        	
	        }})
	      
			     .show();
				;	
			}


	public void back(View v){
		this.startActivity(myIntent);
	}


static{
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
StrictMode.setThreadPolicy(policy);  
}	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	        Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	        mImageView.setImageBitmap(photo);
		try {
			//handle result from gallary select
			if (requestCode == 987) {
				Uri currImageURI = data.getData();
				this.mImageUrl = currImageURI;
				//Set the image view's image by using imageUri
				mImageView.setImageURI(currImageURI);
			}
		} catch (Exception ex) {
			Log.e("TodoDetailsActivity", "Error in onActivityResult: " + ex.getMessage());
		}
	}
	}}

