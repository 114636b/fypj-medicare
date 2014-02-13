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
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class ProfilePic extends Activity {
	ElderSession session1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepic);
 
       
       
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        final Context context = getApplicationContext();
		 session1 = new ElderSession(context);  
	       HashMap<String, String> user1 = session1.getSelectedDetails();
	       String name = user1.get(ElderSession.KEY_ENAME);
	      
	      String NRIC = user1.get(ElderSession.KEY_ENRIC);
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
				
					Scanner scanner;
					try {
						response = hc.execute(postMethod);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					BufferedReader reader;
					try {
						reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						String json;
						
						json = reader.readLine();
						JSONArray jsonArray = new JSONArray(json);
		    			List<String> list = new ArrayList<String>();

		    			for (int i = 0; i < jsonArray.length(); i++) {
		    			    list.add(jsonArray.getString(i));}
		    			    String imageString = list.get(0);
		    				byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);	
		    			    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		    			    
		    			
	    
					} catch (IllegalStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//scanner = new Scanner(response.getEntity().getContent());
 catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				
 
}}