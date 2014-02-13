package com.example.supertest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	long time;
	SessionManager session;
	String nric,name;
	String medName,medRemarks,medImage,medDosage;
	List<String> list1 = new ArrayList<String>();
	 private static final int MY_NOTIFICATION_ID=1;
	 NotificationManager notificationManager;
	 Notification myNotification;
		static{
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
		StrictMode.setThreadPolicy(policy);  
		}	
	 @Override
	 public void onReceive(Context context, Intent intent) {
		 if (android.os.Build.VERSION.SDK_INT > 9) {
			    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
			} ThreadPolicy tp = ThreadPolicy.LAX;
			StrictMode.setThreadPolicy(tp);
		 session = new SessionManager(context); 
		    HashMap<String, String> user = session.getElderDetails();
		    nric = user.get(SessionManager.KEY_ENRIC);
		    name = user.get(SessionManager.KEY_ENAME);
		    
		    time = System.currentTimeMillis();
		    Calendar c = Calendar.getInstance(); 
		  //Set time in milliseconds
		    
		  c.setTimeInMillis(time);
		  int mYear = c.get(Calendar.YEAR);
		  int mMonth = c.get(Calendar.MONTH); 
		  int mDay = c.get(Calendar.DAY_OF_MONTH);
		  int hr = c.get(Calendar.HOUR_OF_DAY);
		  int min = c.get(Calendar.MINUTE);
		  
		  NumberFormat formatter = new DecimalFormat("00");  
         
         String monthstring = formatter.format(mMonth+1);
          String daystring = formatter.format(mDay);
          String minstring =formatter.format(min);
          String hourstring = formatter.format(hr);
          
		  String testing = daystring + monthstring+Integer.toString(mYear)+ hourstring+minstring;
		  DefaultHttpClient hc=new DefaultHttpClient();
	    	ResponseHandler <String> res=new BasicResponseHandler();
	    	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveMedAck");
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    	nameValuePairs.add(new BasicNameValuePair("NRIC", nric));  
	    	nameValuePairs.add(new BasicNameValuePair("elderName", name));    
	    	nameValuePairs.add(new BasicNameValuePair("day", daystring)); 
	    	nameValuePairs.add(new BasicNameValuePair("month", monthstring)); 
	    	nameValuePairs.add(new BasicNameValuePair("year", Integer.toString(mYear))); 
	    	nameValuePairs.add(new BasicNameValuePair("hour", hourstring)); 
	    	nameValuePairs.add(new BasicNameValuePair("min", minstring)); 
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
				
				for (int i = 0; i < jsonArray.length(); i++) {
				    list1.add(jsonArray.getString(i));
				}
				medName = list1.get(0);
				medRemarks = list1.get(1);
				medImage = list1.get(2);
				medDosage = list1.get(3);
				
				
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
	    	String content = "Please take "+medDosage+"-"+ medName +" Remarks:" + medRemarks;
		 
	  Toast.makeText(context, content, Toast.LENGTH_LONG).show();

	     Intent myIntent = new Intent(context, DoSomething.class);
	     myIntent.putExtra("time", System.currentTimeMillis());
	     myIntent.putExtra("day", daystring);
	     myIntent.putExtra("month", monthstring);
	     myIntent.putExtra("year", Integer.toString(mYear));
	     myIntent.putExtra("hr", hourstring);
	     myIntent.putExtra("min", minstring);
	     myIntent.putExtra("nric", nric);
	     myIntent.putExtra("name", name);
	     myIntent.putExtra("MN", medName);
	     myIntent.putExtra("MD", medDosage);
	     myIntent.putExtra("MR", medRemarks);
	     myIntent.putExtra("MI", medImage);
	     PendingIntent pendingIntent = PendingIntent.getActivity(
	            context, 
	            0, 
	            myIntent, 
	            Intent.FLAG_ACTIVITY_NEW_TASK);

	     myNotification = new NotificationCompat.Builder(context)
	       .setContentTitle("Time to Take Medicine")
	       .setContentText(content)
	       .setTicker("Medicine Alarm")
	       .setWhen(System.currentTimeMillis())
	       .setContentIntent(pendingIntent)
	       .setDefaults(Notification.DEFAULT_SOUND)
	       .setAutoCancel(true)
	       .setSmallIcon(R.drawable.background)
	       .build();
	     
	     notificationManager = 
	       (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	     notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
	     
	 }
	
	}