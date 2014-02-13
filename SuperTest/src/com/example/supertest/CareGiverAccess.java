package com.example.supertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import sessionManager.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CareGiverAccess extends Activity {
	Button btnLogin;
	Button btnSignup;
	Intent myIntent;
	Intent toMenu;
	EditText txtNric;
	EditText txtPassword;
	TextView Try1;
			TextView Try2;
			TextView Try3;
			 SessionManager session;
			 Intent mainpage;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		session = new SessionManager(getApplicationContext());  
		
		Try1 =(TextView)findViewById(R.id.txtNRICProfile);
		
		setContentView(R.layout.caregiveraccess);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnSignup=(Button)findViewById(R.id.btnSignup);
		myIntent= new Intent(this, CareGiverSignUp.class);
		toMenu = new Intent(this,  testing.class);
		mainpage = new Intent(this,  MainPage.class);
		btnLogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Try1 =(TextView)findViewById(R.id.txtNRICProfile);
				Try2=(TextView)findViewById(R.id.textView2);
				Try3 =(TextView)findViewById(R.id.textView3);
txtNric = (EditText) findViewById(R.id.txtNric);
txtPassword = (EditText)findViewById(R.id.txtPassword);
String NRIC = txtNric.getText().toString();
String Pin = txtPassword.getText().toString();

//Try3.setVisibility(View.INVISIBLE);
	        	DefaultHttpClient hc=new DefaultHttpClient();
	        	ResponseHandler <String> res=new BasicResponseHandler();
	        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/SuperTestRetrieveServlet");
	        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	        	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
	        	nameValuePairs.add(new BasicNameValuePair("Pin", Pin)); 
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
	        	try{
		    			HttpResponse response=hc.execute(postMethod);
	    			 // 9. receive response as inputStream
		            //inputStream = response.getEntity().getContent();
		    			Scanner scanner = new Scanner(response.getEntity().getContent());
		    			String json = scanner.nextLine() ;
		    					    			
		    			JSONArray jsonArray = new JSONArray(json);
		    			List<String> list = new ArrayList<String>();

		    			for (int i = 0; i < jsonArray.length(); i++) {
		    			    list.add(jsonArray.getString(i));
		    			}
		    			retrievedName = (String)list.get(0);
		    			retrievedPass = (String)list.get(1);
		    			retrievedPhone = (String)list.get(2);
		    			retrievedNRIC = (String)list.get(3);
		    			
		    			try{
		    				retrievedEmail =(String)list.get(4);
		    				
		    			
		    			session.createLoginSession(retrievedName,retrievedPhone,retrievedNRIC,retrievedEmail);
		    			
		    			
		    			
		    			
		    			
		    			
		    			startActivity(toMenu);}
		    			catch(Exception e){
		    				Try3.setVisibility(View.VISIBLE);
		    			Try3.setText(retrievedPass);
		    			}
		    			 
		    			
		    				 
		    				 
		    			 }
		    			
		    			
		    			 
		    			 
		    			
		    						 
		    						 
		    					 
		    				 
		    				 
		    				 
		    			
		    			 
		    			 
		    			 
		    			 
		    			
		           
	    		 catch (ClientProtocolException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		//startActivity(myIntent);
 catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
				
				
				
				
				
			}
		});
		
		btnSignup.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				startActivity(myIntent);
			}
		});
	}
	public void onBackPressed(){
		
	     startActivity(mainpage);
	}
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }   
	static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
}
