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
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class editProfileFragment extends Fragment {
	
	 SessionManager session;
	
	 @Override
	   @SuppressWarnings("static-access")
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
	           Bundle savedInstanceState) {
	
	LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.fragment_editprofile, container, false);
	
	 String name = "";
     String Phone = "";
     String NRIC = "";
     String Email = "";
     
     Context context = getActivity().getApplicationContext();
     session = new SessionManager(context);  
     HashMap<String, String> user = session.getUserDetails();
     name = user.get(SessionManager.KEY_NAME);
     Phone = user.get(SessionManager.KEY_PHONE);
     NRIC = user.get(SessionManager.KEY_NRIC);
     Email = user.get(SessionManager.KEY_EMAIL);
     //email = user.get(SessionManager.KEY_EMAIL);
     final EditText etxtName;
     final EditText etxtEmail;
     final EditText etxtPhone;
     final TextView txtNRICProfile;
     etxtName = (EditText)rootView.findViewById(R.id.etxtName);
     etxtEmail = (EditText)rootView.findViewById(R.id.etxtEmail);
     etxtPhone = (EditText)rootView.findViewById(R.id.etxtPhone);
     txtNRICProfile = (TextView)rootView.findViewById(R.id.txtNRICProfile);
     
     etxtName.setText(name);
     etxtEmail.setText(Email);
     etxtPhone.setText(Phone);
     txtNRICProfile.setText(NRIC);
     Button btnEdit;
     btnEdit = (Button)rootView.findViewById(R.id.btnEditPic);
     
     btnEdit.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			String updateName;
			String updateEmail;
			String updatePhone;
			String updateNric;
			updateNric = (String)txtNRICProfile.getText().toString();
			updateName = (String)etxtName.getText().toString();
			updateEmail =(String)etxtEmail.getText().toString();
			updatePhone = (String)etxtPhone.getText().toString();
			DefaultHttpClient hc=new DefaultHttpClient();
        	ResponseHandler <String> res=new BasicResponseHandler();
        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/SuperTestUpdateProfileServlet");
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        	nameValuePairs.add(new BasicNameValuePair("NRIC",updateNric));  
        	nameValuePairs.add(new BasicNameValuePair("Email",updateEmail ));
        	nameValuePairs.add(new BasicNameValuePair("Phone",updatePhone ));
        	nameValuePairs.add(new BasicNameValuePair("Name",updateName ));
        	try {
    			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
	    			
        	try {
				HttpResponse response=hc.execute(postMethod);
				session.createLoginSession(updateName,updatePhone,updateNric,updateEmail);
				 Fragment newFragment = new HomeFragment(); 
				 FragmentTransaction transaction = getFragmentManager().beginTransaction();
				    transaction.replace(R.id.frame_container, newFragment);
				    transaction.addToBackStack(null);
				    transaction.commit(); 
				/*Scanner scanner = new Scanner(response.getEntity().getContent());
    			String json = scanner.nextLine() ;
    			JSONArray jsonArray = new JSONArray(json);
    			List<String> list = new ArrayList<String>();
    			for (int i = 0; i < jsonArray.length(); i++) {
    			    list.add(jsonArray.getString(i));
    			}*/
				
				

				
				
				
				
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
					
			
		}});
     
	
	
	
	
	return rootView;
}
}
