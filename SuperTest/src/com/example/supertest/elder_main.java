package com.example.supertest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class elder_main extends Activity {
	private Button button;
	
	Menu myMenu = null;
	private String haha;
	static int RQS_1 = 1;
	ArrayAdapter<CharSequence> adapter;
	TextView selection;
	ListView myList;
	Context myContext;
	SessionManager session;
	String nric;
	String name;
	TextView nricdisplay;
	Intent myIntent;
	ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elder_main);
        session = new SessionManager(getApplicationContext()); 
        HashMap<String, String> user = session.getElderDetails();
        nric = user.get(SessionManager.KEY_ENRIC);
        name = user.get(SessionManager.KEY_ENAME);
        nricdisplay = (TextView)findViewById(R.id.txtNRICProfile);
        nricdisplay.setText(name + " / "+nric);
        imageView = (ImageView)findViewById(R.id.imageView1);
      //serveltf or image
        DefaultHttpClient hc1= new DefaultHttpClient();
        ResponseHandler <String> res1 =new BasicResponseHandler();
		HttpPost postMethod1=new HttpPost("http://1.mediapp101.appspot.com/ElderImageServlet");
		List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
		nameValuePairs1.add(new BasicNameValuePair("NRIC",nric));  
		nameValuePairs1.add(new BasicNameValuePair("Name", name));
		try{
			postMethod1.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		HttpResponse response1 = null;
		
		Scanner scanner1;
		try{
			response1 = hc1.execute(postMethod1);
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		BufferedReader reader1;
		
		try{
			reader1 = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
			String json1;
			
			json1 = reader1.readLine();
			JSONArray jsonArray1 = new JSONArray(json1);
			List<String> list1 = new ArrayList<String>();
			
			for(int i = 0; i <jsonArray1.length(); i++){
				list1.add(jsonArray1.getString(i));}
			String imageString = list1.get(0);
			byte[]imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);
			if(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)==null || BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length).equals(null)){
				  imageView.setImageResource(R.drawable.nopic);
				  imageView.getLayoutParams().height = 100;
  	            imageView.getLayoutParams().width = 100;
			}
			else{
		    imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
		    imageView.getLayoutParams().height = 200;
          imageView.getLayoutParams().width = 200;
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
			
		

        
        //end of imageservlet
        
//       //get alarm
        DefaultHttpClient hc=new DefaultHttpClient();
    	ResponseHandler <String> res=new BasicResponseHandler();
    	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveAlarmServlet");
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
    	nameValuePairs.add(new BasicNameValuePair("NRIC", nric));  
    	nameValuePairs.add(new BasicNameValuePair("elderName", name)); 
    	List<String> list1 = new ArrayList<String>();
    	double d = 0;
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
			
			d = Double.parseDouble(jsonArray.getString(0));
			double totalrecords = 1+(d*10);
			
			for (int i = 0; i < totalrecords; i++) {
			    list1.add(jsonArray.getString(i));
			}
			Calendar cal=Calendar.getInstance();
			   
		if(totalrecords != 1){
			for(int i = 0; i < d; i++ ){
				
				//cal.set(Calendar.MONTH,Integer.parseInt(list1.get(i*7)));
				cal.set(Calendar.YEAR,Integer.parseInt(list1.get(8+(i*10))));
				cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(list1.get(6+(i*10))));
				   cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(list1.get(10+(i*10))));
				   cal.set(Calendar.MINUTE,Integer.parseInt(list1.get(9+(i*10))));
				   String MedicineName = list1.get(1+(i*10));
				   String MedicineRemarks = list1.get(2+(i*10));
				   String MedDosage = list1.get(4+(i*10));
				
				   Intent intent = new Intent(getBaseContext(), AlarmManagerBroadcastReceiver.class);
				   intent.putExtra("MN", MedicineName);
				   intent.putExtra("MR", MedicineRemarks);
				   intent.putExtra("MD", MedDosage);
				   PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
				   AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				   alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);   
				   NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				   
				   
				   
				   RQS_1++;
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
    }


   

    
    public void viewProfile(View v){
    	
    	button = (Button) findViewById(R.id.btnProfile);
    	Intent i = new Intent(this, viewElderlyProfile.class);
		
		startActivity(i);
    	
    }
 public void viewEmergency(View v){
    	
    	button = (Button) findViewById(R.id.btnEmergency);
    	Intent i = new Intent(this, ViewEmergency.class);
		
		startActivity(i);
    	
    }
 public void SMSReceiverActivity(View v)
 {
	 
	 button = (Button) findViewById(R.id.btnJournalLog);
	 Intent sms = new Intent(this, SMSReceiverActivity.class);
	 startActivity(sms);
	 
 }
    public void viewMedicine(View v){
    	
    	button =(Button) findViewById(R.id.btnMedicine);
    	Intent med =  new Intent(this,  viewMedicine.class);
    	
    	startActivity(med);
    }
    public void MedicineAlarm(View v)
    {
    	button = (Button) findViewById(R.id.btnCalendar);
    	Intent cal = new Intent(this, ElderMedication.class);
    	
    	startActivity(cal);
    	
    }
    
    
    
    public void onBackPressed(){
		myIntent= new Intent(this,ElderlyAccess.class);
    	new AlertDialog.Builder(this).setTitle("Confirmation")
		.setMessage("Are you sure you want to log out?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	//Check and inside database code here
		        	
		        	
		        	
		        	startActivity(myIntent);
		        	
		        	
		        	
		        	
		        }})
		        .setNegativeButton("No", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        	
		        
			        }})
				     .show();
					;	
				}

    
}
