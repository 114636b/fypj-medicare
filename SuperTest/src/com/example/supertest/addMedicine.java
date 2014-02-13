package com.example.supertest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import sessionManager.ElderSession;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class addMedicine extends Activity {
 ImageView btnAdd;
 TextView testing;
 Button addMed;
 EditText medName;
 EditText medRemarks;
 ElderSession session1;
 int position;
 String MEDIMAGE;
 Intent myIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmed);
        session1 = new ElderSession(getApplicationContext());  
        final Context context = getApplicationContext();
        session1 = new ElderSession(context);  
	       HashMap<String, String> user1 = session1.getSelectedDetails();
	       final String name = user1.get(ElderSession.KEY_ENAME);
	      final String NRIC = user1.get(ElderSession.KEY_ENRIC);
	    
        
        final Intent p = new Intent(this,AddMedImage.class);
        MEDIMAGE="http://files.softicons.com/download/system-icons/phuzion-icons-by-kyo-tux/png/256/Stop.png";
        testing=(TextView)findViewById(R.id.textView1);
        medName = (EditText)findViewById(R.id.etMedName);
        medRemarks=(EditText)findViewById(R.id.etMedFunc);
        try{
     // get intent data
        Intent i = getIntent();
 
        // Selected image id
        
        position = i.getExtras().getInt("id");
        if(position == 0){
        	MEDIMAGE="http://files.softicons.com/download/system-icons/phuzion-icons-by-kyo-tux/png/256/Stop.png";	
        	
        }
        else if(position == 1){
        	MEDIMAGE="http://files.softicons.com/download/web-icons/bees-help-icons-by-artbees/png/128x128/Capsule.png";	
        	
        }
        else if(position == 2){
        	MEDIMAGE="http://png-3.findicons.com/files/icons/734/phuzion/128/antivirus.png";	
        	
        }
        else if(position == 3){
        	MEDIMAGE="http://files.softicons.com/download/web-icons/bees-help-icons-by-artbees/png/128x128/Green-Capsule.png";	
        	
        } else if(position == 4){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-grey-icons-by-turbomilk/png/32x32/pill.png";	
        	
        }
        else if(position == 5){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-black-icons-by-turbomilk/png/32x32/pill.png";	
        	
        } else if(position == 6){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-green-icons-by-turbomilk/png/32x32/pill.png";	
        	
        } else if(position == 7){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-yellow-icons-by-turbomilk/png/32x32/pill.png";	
        	
        } else if(position == 8){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-light-green-icons-by-turbomilk/png/32/pill.png";	
        	
        } else if(position == 9){
        	MEDIMAGE="http://files.softicons.com/download/application-icons/toolbar-icon-set-by-anna-shlyapnikova/png/32/pill.png";	
        	
        } else if(position == 10){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-light-blue-icons-by-turbomilk/png/32/pill.png";	
        	
        } else if(position == 11){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-red-icons-by-turbomilk/png/32/pill.png";	
        	
        }else if(position == 12){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/fatcow-hosting-icons-by-fatcow/png/32/pill.png";	
        	
        }else if(position == 13){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-blue-icons-by-turbomilk/png/32/pill.png";	
        	
        }else if(position == 14){
        	MEDIMAGE="http://files.softicons.com/download/toolbar-icons/iconza-dark-purple-icons-by-turbomilk/png/32/pill.png";	
        	
        };
        ImageAdapter imageAdapter = new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.medPic);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
        }catch(Exception e){}
        btnAdd=(ImageView)findViewById(R.id.medPic);
        btnAdd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(p);
				
			}});
        addMed=(Button)findViewById(R.id.addMed);
        addMed.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
			String MEDINAME =(String) medName.getText().toString();
			String MEDIREMARKS = (String) medRemarks.getText().toString();
				
			DefaultHttpClient hc=new DefaultHttpClient();
        	ResponseHandler <String> res=new BasicResponseHandler();
        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/AddMedicineServlet");
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        	nameValuePairs.add(new BasicNameValuePair("nric", NRIC));  
        	nameValuePairs.add(new BasicNameValuePair("name", name)); 
        	nameValuePairs.add(new BasicNameValuePair("medName", MEDINAME)); 
        	nameValuePairs.add(new BasicNameValuePair("medRemarks", MEDIREMARKS)); 
        	nameValuePairs.add(new BasicNameValuePair("medImage",MEDIMAGE)); 
        	try {
				postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
				try {
					HttpResponse response=hc.execute(postMethod);
					
					showOk();
					
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			}});
        
        
        
        
        
        
        
    }
    public void onBackPressed(){
		Intent i = new Intent(this, SelectedMed.class);
	     startActivity(i);
	}
    
    
    public void showOk(){
    	
    	myIntent= new Intent(this, SelectedMed.class);
    	new AlertDialog.Builder(this).setTitle("Successful")
    	.setMessage("Added successfully")
    	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int which) {
    	        	
    	        	
    	        	startActivity(myIntent);
    	      
    	        	
    	        	
    	        	
    	        	
    	        }})
    	      
    			     .show();
    				;	
    			}

    
    
}