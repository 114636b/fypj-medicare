package com.example.supertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import sessionManager.ElderSession;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectedElder extends Activity {
	ElderSession session1;
	TextView tv1;
	Button btncoordinates;
	Intent myIntent;
	Button btnEmergency;
	Button btnMedication;
	Button btnMedi;
	Button btnProfile;
	ImageView profilepic;
	 String Phone;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		session1 = new ElderSession(getApplicationContext());  
		setContentView(R.layout.cgelderprofile);
		final Context context = getApplicationContext();
		 session1 = new ElderSession(context);  
	       HashMap<String, String> user1 = session1.getSelectedDetails();
	       String name = user1.get(ElderSession.KEY_ENAME);
	      Phone = user1.get(ElderSession.KEY_EPHONE);
	      String NRIC = user1.get(ElderSession.KEY_ENRIC);
	      String Email = user1.get(ElderSession.KEY_ElderEMAIL);
	     final String coordinates= user1.get(ElderSession.KEY_COORDINATES);
	     final String latitude= user1.get(ElderSession.KEY_LATITUDE);
	     String longitude =  user1.get(ElderSession.KEY_LONGITUDE);
	     String lastlogin = user1.get(ElderSession.KEY_LASTLOGIN);
	     final String emergencyName =user1.get(ElderSession.KEY_EMERGENCYNAME);
	    final String emergencyPhone =user1.get(ElderSession.KEY_EMERGENCYPHONE);
	     final String emergencyAddress = user1.get(ElderSession.KEY_EMERGENCYADDRESS);
	     myIntent= new Intent(this, ElderLocation.class);
	     final Intent o = new Intent(this,CaregiverMedication.class);
	     final Intent p = new Intent(this,SelectedMed.class);
	     tv1 = (TextView)findViewById(R.id.txtNRICProfile);
	     profilepic = (ImageView)findViewById(R.id.imageView1);
	     final Intent pic =new Intent(this,ProfilePic.class);
	       tv1.setText(name +" / " + NRIC);
	   	DefaultHttpClient hc=new DefaultHttpClient();
		ResponseHandler <String> res=new BasicResponseHandler();
		HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/ElderImageServlet");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
		nameValuePairs.add(new BasicNameValuePair("Name", name)); 
		try {
			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
				HttpResponse response = null;
				try {
					Scanner scanner;
					response = hc.execute(postMethod);
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					//scanner = new Scanner(response.getEntity().getContent());
					String json = reader.readLine() ;
					JSONArray jsonArray = new JSONArray(json);
	    			List<String> list = new ArrayList<String>();

	    			for (int i = 0; i < jsonArray.length(); i++) {
	    			    list.add(jsonArray.getString(i));
	    			}
	    			Log.i("ha",list.get(0));
	    			if(list.get(0).equals("Nothing") || list.get(0).equals(null)){
	    				
	    				

	    			}
	    			else{
	    				//tvUser.setText("GOT iMAGE");
	    				String imageString = list.get(0);
	    				byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);		
	    				if(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)==null || BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length).equals(null)){
	    					  profilepic.setImageResource(R.drawable.nopic);
		    				  profilepic.getLayoutParams().height = 150;
			    	            profilepic.getLayoutParams().width = 150;
	    				}
	    				else{
	    			    profilepic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
	    			    
	    	            profilepic.getLayoutParams().height = 200;
	    	            profilepic.getLayoutParams().width = 150;
	    	            profilepic.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								startActivity(pic);
								
								
							}});
	    				}
	    			
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
	       
	       
	       btnProfile = (Button)findViewById(R.id.btnProfile);
	       btnProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + Phone));
				startActivity(intent);
				
			}});
	       btnMedi = (Button)findViewById(R.id.btnMedicine);

	       btnMedi.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(p);
			}});
	       
	       
	       
	       btnMedication = (Button)findViewById(R.id.btnCalendar);
	       btnMedication.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			     startActivity(o);
				
			}});
	       
	       btncoordinates= (Button)findViewById(R.id.btnLocation);
	       btncoordinates.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getApplicationContext(),coordinates, Toast.LENGTH_LONG).show();
				
				startActivity(myIntent);
			}});
	       
	       btnEmergency = (Button)findViewById(R.id.btnEmergency);
	       btnEmergency.setOnClickListener(new OnClickListener() {
	    	   
	 		  @Override
	 		  public void onClick(View arg0) {
	  
	 			// custom dialog
	 			final Dialog dialog = new Dialog(SelectedElder.this);
	 			dialog.setContentView(R.layout.selectedemergencydialog);
	 			dialog.setTitle("Emergency Information");
	  
	 			// set the custom dialog components - text, image and button
	 			TextView textename = (TextView) dialog.findViewById(R.id.tvEmergencyName);
	 			textename.setText(emergencyName);
	 			TextView textephone = (TextView) dialog.findViewById(R.id.tvEmergencyPhone);
	 			textephone.setText(emergencyPhone);
	 			TextView texteaddress = (TextView) dialog.findViewById(R.id.tvEmergencyAddress);
	 			texteaddress.setText(emergencyAddress);
	 			ImageView image = (ImageView) dialog.findViewById(R.id.imageEmergency);
	 			image.setImageResource(R.drawable.emergency);
	 			Button callButton = (Button) dialog.findViewById(R.id.btnCall);
	 			callButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + emergencyPhone));
						startActivity(intent);
					}});
	 			
	 			
	 			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
	 			// if button is clicked, close the custom dialog
	 			dialogButton.setOnClickListener(new OnClickListener() {
	 				@Override
	 				public void onClick(View v) {
	 					dialog.dismiss();
	 				}
	 			});
	  
	 			dialog.show();
	 		  }
	 		});
	       
	       
	       
	 	}
	
	
	
	
	public void onBackPressed(){
		Intent i = new Intent(this, testing.class);
	     startActivity(i);
	}

}
