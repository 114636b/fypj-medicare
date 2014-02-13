package com.example.supertest;

import java.io.IOException;
import java.io.InputStream;
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
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ElderlyAccess extends Activity {
	Button btnLogin;
	Button btnSignup;
	Intent myIntent;
	Intent toMenu;
	EditText txtNric;
	EditText txtPassword;
	TextView Try1;
			TextView Try2;
			TextView Try3;
			 SessionManager session;Double latitude;
			 Double longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		session = new SessionManager(getApplicationContext());  
		setContentView(R.layout.elderlyaccess);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		
		 final Intent viewE = new Intent(this, ViewEmergency.class);
		toMenu = new Intent(this, elder_main.class);
		 final GPSTracker tracker = new GPSTracker(this);
		 
			btnLogin.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{if (tracker.canGetLocation() == false) {
			        tracker.showSettingsAlert();
			    } else {
			        latitude = tracker.getLatitude();
			        longitude = tracker.getLongitude();
			    }
			 String testing = new Double(latitude).toString();
			 String testing2 = new Double(longitude).toString();
			 final String coordinates = (testing + "," + testing2);
				
txtNric = (EditText) findViewById(R.id.txtNric);
txtPassword = (EditText)findViewById(R.id.txtPassword);
String NRIC = txtNric.getText().toString();
String Pin = txtPassword.getText().toString();
Try1 =(TextView)findViewById(R.id.txtNRICProfile);
Try2=(TextView)findViewById(R.id.textView2);
Try3 =(TextView)findViewById(R.id.textView3);
Try3.setVisibility(View.INVISIBLE);
DefaultHttpClient hc=new DefaultHttpClient();
ResponseHandler <String> res=new BasicResponseHandler();
HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/ElderLoginServlet");
List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
nameValuePairs.add(new BasicNameValuePair("Pin", Pin)); 
nameValuePairs.add(new BasicNameValuePair("coordinates", coordinates)); 
nameValuePairs.add(new BasicNameValuePair("latitude", latitude.toString())); 
nameValuePairs.add(new BasicNameValuePair("longitude", longitude.toString()));
InputStream inputStream = null;	
String retrievedName = "";
String retrievedPass = "";
String retrievedPhone = "";
String retrievedEmail="";
String retrievedNRIC="";
String trySuccess;
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
		retrievedNRIC = (String)list.get(1);
		retrievedEmail = (String)list.get(2);
		retrievedPhone = (String)list.get(3);
		
		try{
			String cgretrievedEmail =(String)list.get(4);
			String cgretrievedName =(String)list.get(5);
			String cgretrievedPhone =(String)list.get(6);
		String retrieveename=(String)list.get(7);
		String retrieveephone =(String)list.get(8);
		String retrieveeadd=(String)list.get(9);
		
		session.createElderLoginSession(retrievedName,retrievedPhone,retrievedNRIC,retrievedEmail,cgretrievedEmail,cgretrievedName,cgretrievedPhone,coordinates);
		session.storeEmergencyInfo(retrieveename, retrieveephone,retrieveeadd);
		Toast.makeText(getApplicationContext(),coordinates, Toast.LENGTH_LONG).show();
		startActivity(toMenu);}
		catch(Exception e){
			Try3.setVisibility(View.VISIBLE);
		Try3.setText(retrievedNRIC);
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
		
	
	}

	static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
	
	public void onBackPressed(){
		Intent i = new Intent(this, MainPage.class);
	     startActivity(i);
	}
}
