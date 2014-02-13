package com.example.supertest;


import java.util.ArrayList;
import java.util.HashMap;

import sessionManager.SessionManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SMSReceiverActivity extends Activity implements OnClickListener, OnItemClickListener
{
	SessionManager session1;
	Button btnSendSMS;
	Button btnCall;
	EditText txtPhoneNo;
	EditText txtMessage;
	String Phone;
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	super.onCreate(savedInstanceState);
    	session1 = new SessionManager(getApplicationContext());
        final Context context = (getApplicationContext());
        session1 = new SessionManager(context);
        HashMap<String, String> user = session1.getElderDetails();
        Phone = user.get(SessionManager.KEY_cgPhone);
        setTheme( android.R.style.Theme_Light );
        setContentView(R.layout.viewjournal);
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        btnCall = (Button) findViewById(R.id.btnCall);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtPhoneNo.setText(Phone);
        /**
         * You can also register your intent filter here.
         * And here is example how to do this.
         *
         * IntentFilter filter = new IntentFilter( "android.provider.Telephony.SMS_RECEIVED" );
         * filter.setPriority( IntentFilter.SYSTEM_HIGH_PRIORITY );
         * registerReceiver( new SmsReceiver(), filter );
        **/
        //start of smslist

		Uri uri = Uri.parse("content://sms/inbox/");
		String phoneNumber = Phone;
		String sms="address='"  +"+65"+phoneNumber+"'";
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query( uri,new String[]{"_id","body"},sms,null,null);
		System.out.println(cursor.getCount());
		

		//int indexBody = cursor.getColumnIndex( SmsReceiver.BODY );
		//int indexAddr = cursor.getColumnIndex( SmsReceiver.ADDRESS );
		
		//if ( indexBody < 0 || !cursor.moveToFirst() ) return;
		
		//smsList.clear();
		
		//do
		//{
		//	String str = "Sender: " + cursor.getString( indexAddr ) + "\n" + cursor.getString( indexBody );
		//	smsList.add( str );
		//}
		while( cursor.moveToNext() ){
			String strbody = cursor.getString(cursor.getColumnIndex("body"));
			smsList.add(strbody);
		}

		
		ListView smsListView = (ListView) findViewById( R.id.SMSList );
	
		smsListView.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, smsList) );
		smsListView.setOnItemClickListener( this );
	
        
        //
        btnCall.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent = new Intent(Intent.ACTION_CALL);
        		intent.setData(Uri.parse("tel:" + Phone));
        		startActivity(intent);
        	
        }});
        
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {            	
            	String phoneNo = txtPhoneNo.getText().toString();
            	String message = txtMessage.getText().toString();             	
                if (phoneNo.length()>0 && message.length()>0)                
                    sendSMS(phoneNo, message);                
                else
                	Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
        }); 
        //this.findViewById( R.id.UpdateList ).setOnClickListener( this );
    }

    ArrayList<String> smsList = new ArrayList<String>();
    
    private void sendSMS(String phoneNumber, String message)
    {      
    	/*
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, test.class), 0);                
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, pi, null);        
        */
    	
    	String SENT = "SMS_SENT";
    	String DELIVERED = "SMS_DELIVERED";
    	
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
        
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
    	
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS sent", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					    Toast.makeText(getBaseContext(), "Generic failure", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_NO_SERVICE:
					    Toast.makeText(getBaseContext(), "No service", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_NULL_PDU:
					    Toast.makeText(getBaseContext(), "Null PDU", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_RADIO_OFF:
					    Toast.makeText(getBaseContext(), "Radio off", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				}
			}
        }, new IntentFilter(SENT));
        
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS delivered", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case Activity.RESULT_CANCELED:
					    Toast.makeText(getBaseContext(), "SMS not delivered", 
					    		Toast.LENGTH_SHORT).show();
					    break;					    
				}
			}
        }, new IntentFilter(DELIVERED));        
    	
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);               
    } 
    
	public void onItemClick( AdapterView<?> parent, View view, int pos, long id ) 
	{
		try 
		{
		    	String[] splitted = smsList.get( pos ).split("\n"); 
			String sender = splitted[0];
			String encryptedData = "";
			for ( int i = 1; i < splitted.length; ++i )
			{
			    encryptedData += splitted[i];
			}
			String data = sender + "\n" + StringCryptor.decrypt( new String(SmsReceiver.PASSWORD), encryptedData );
			Toast.makeText( this, data, Toast.LENGTH_SHORT ).show();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	public void onClick( View v ) 
	{
		Uri uri = Uri.parse("content://sms/inbox/");
		String phoneNumber = Phone;
		String sms="address='"  +"+65"+phoneNumber+"'";
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query( uri,new String[]{"_id","body"},sms,null,null);
		System.out.println(cursor.getCount());
		

		//int indexBody = cursor.getColumnIndex( SmsReceiver.BODY );
		//int indexAddr = cursor.getColumnIndex( SmsReceiver.ADDRESS );
		
		//if ( indexBody < 0 || !cursor.moveToFirst() ) return;
		
		//smsList.clear();
		
		//do
		//{
		//	String str = "Sender: " + cursor.getString( indexAddr ) + "\n" + cursor.getString( indexBody );
		//	smsList.add( str );
		//}
		while( cursor.moveToNext() ){
			String strbody = cursor.getString(cursor.getColumnIndex("body"));
			smsList.add(strbody);
		}

		
		ListView smsListView = (ListView) findViewById( R.id.SMSList );
		smsListView.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, smsList) );
		smsListView.setOnItemClickListener( this );
	}
}