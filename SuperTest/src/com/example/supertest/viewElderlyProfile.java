package com.example.supertest;


import info.androidhive.slidingmenu.model.NavDrawerItem;

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

import sessionManager.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class viewElderlyProfile extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    SessionManager session;

    private ActionBarDrawerToggle mDrawerToggle;
 Intent myIntent;
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    TextView tvUser;
    TextView tvPhone;
    TextView tvNRIC;
TextView tvEmail;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elderlyprofile);

        //retrieve session
        session = new SessionManager(getApplicationContext());  

        String name = "";
        String Phone = "";
        String NRIC = "";
        String Email = "";
        HashMap<String, String> user = session.getElderDetails();
       
        name = user.get(SessionManager.KEY_ENAME);
        Phone = user.get(SessionManager.KEY_EPHONE);
        NRIC = user.get(SessionManager.KEY_ENRIC);
        imageView = (ImageView)findViewById(R.id.imageView1);
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
		    				
		    				if(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)==null || BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length).equals(null)){
		    					  imageView.setImageResource(R.drawable.nopic);
			    				  imageView.getLayoutParams().height = 300;
				    	            imageView.getLayoutParams().width = 300;
		    				}
		    				else{
		    			    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		    			    imageView.getLayoutParams().height = 400;
		    	            imageView.getLayoutParams().width = 400;
		    				}
	    
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
					
					
				
 
        
        
        
        
        
        
        
        
        
        
        
        
        
        tvUser=(TextView)findViewById(R.id.txtLoginName);
        tvPhone=(TextView)findViewById(R.id.textView6);
        tvNRIC=(TextView)findViewById(R.id.textView8);
        tvEmail=(TextView)findViewById(R.id.textView4);
       // tvUser.setText(name);
        //tvPhone.setText(Phone);
        //tvNRIC.setText(NRIC);
        tvUser.setText(name);
        tvPhone.setText(Phone);
        tvNRIC.setText(NRIC);
        tvEmail.setText(Email);
        
        
        
        
        
    }
    
}