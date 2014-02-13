package com.example.supertest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment  {
    
   public HomeFragment(){}
   SessionManager session;
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
 
       View rootView = inflater.inflate(R.layout.fragment_home, container, false);
       String name = "";
       String Phone = "";
       String NRIC = "";
       String Email = "";
       Button update;
       TextView tvUser;
       TextView tvPhone;
       TextView tvNRIC;
       TextView tvEmail;
       ImageView profilepic;
       Button chgpic;
       Context context = getActivity().getApplicationContext();
       session = new SessionManager(context);  
       HashMap<String, String> user = session.getUserDetails();
       name = user.get(SessionManager.KEY_NAME);
       Phone = user.get(SessionManager.KEY_PHONE);
       NRIC = user.get(SessionManager.KEY_NRIC);
       Email = user.get(SessionManager.KEY_EMAIL);
       tvUser=(TextView)rootView.findViewById(R.id.txtLoginName);
       tvPhone=(TextView)rootView.findViewById(R.id.textView6);
       tvNRIC=(TextView)rootView.findViewById(R.id.textView8);
       tvEmail=(TextView)rootView.findViewById(R.id.textView4);
       profilepic = (ImageView)rootView.findViewById(R.id.imageView1);
      tvUser.setText(name);
       tvPhone.setText(Phone);
       tvNRIC.setText(NRIC);
       tvEmail.setText(Email);
   	DefaultHttpClient hc=new DefaultHttpClient();
	ResponseHandler <String> res=new BasicResponseHandler();
	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/ImageServlet");
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
	nameValuePairs.add(new BasicNameValuePair("Name", name)); 
	InputStream inputStream = null;	
	String retrievedName = "";
	String retrievedPass = "";
	String retrievedPhone = "";
	String retrievedEmail="";
	String retrievedNRIC="";
	String trySuccess;
	String pic = "";
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
    			if(list.get(0).equals("Nothing")){
    				
    				//tvUser.setText("No image");
    			}
    			else{
    				//tvUser.setText("GOT iMAGE");
    				String imageString = list.get(0);
    				byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);			  
    			    profilepic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
    			    
    	            profilepic.getLayoutParams().height = 400;
    	            profilepic.getLayoutParams().width = 400;
    	            
    			
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
		 // 9. receive response as inputStream
        //inputStream = response.getEntity().getContent();
			Scanner scanner;
			
			
			
       
       chgpic=(Button)rootView.findViewById(R.id.button2);
       chgpic.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getActivity(), EditPicture.class);         
			getActivity().startActivity(i);
			
		}});
       
       update = (Button)rootView.findViewById(R.id.btnUpdate);
      update.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Fragment newFragment = new editProfileFragment();
			 FragmentTransaction transaction = getFragmentManager().beginTransaction();
			 transaction.replace(R.id.frame_container, newFragment);
			    transaction.addToBackStack(null);
			    transaction.commit(); 
			
			
		}
    	  
    	  
    	  
      });
       
       
       
       
       return rootView;
       
       
   }
}