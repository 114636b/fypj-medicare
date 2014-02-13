package com.example.supertest;

import java.io.IOException;
import java.io.InputStream;
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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sessionManager.ElderSession;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectedMed extends Activity {
	ElderSession session1;
	Button addMed;
	Intent myIntent;
	Intent iredirect;	    ListView list;
	    LazyAdapter4 adapter;
	    String stringid;
	   // All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";
    List<String> list1 = new ArrayList<String>();
    String Phone,name,NRIC;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		session1 = new ElderSession(getApplicationContext());  
		setContentView(R.layout.selectedmed);
		iredirect = new Intent(this,SelectedMed.class);
		final Context context = getApplicationContext();
		 session1 = new ElderSession(context);  
	       HashMap<String, String> user1 = session1.getSelectedDetails();
	       double id = 1;
	       name = user1.get(ElderSession.KEY_ENAME);
	      Phone = user1.get(ElderSession.KEY_EPHONE);
	       NRIC = user1.get(ElderSession.KEY_ENRIC);
	       DefaultHttpClient hc=new DefaultHttpClient();
	   	ResponseHandler <String> res=new BasicResponseHandler();
	   	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveMedicine");
	   	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	   	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
	   	nameValuePairs.add(new BasicNameValuePair("elderName", name)); 
	   	InputStream inputStream = null;	     
	   	
	   	double d = 0;
	   	try {
	   		postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	   	} catch (UnsupportedEncodingException e) {
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	   	}
	   	HttpResponse response;
		
			try {
				response = hc.execute(postMethod);
				Scanner scanner = new Scanner(response.getEntity().getContent());
				String json = scanner.nextLine() ;
						    			
				JSONArray jsonArray = new JSONArray(json);
				
				d = Double.parseDouble(jsonArray.getString(0));
				double totalrecords = 1+(d*3);
				
				for (int i = 0; i < totalrecords; i++) {
				    list1.add(jsonArray.getString(i));
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
			
			 ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
			 
		        XMLParser parser = new XMLParser();
		        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		        Document doc = parser.getDomElement(xml); // getting DOM element
		 
		        NodeList nl = doc.getElementsByTagName(KEY_SONG);
		        // looping through all song nodes &lt;song&gt;
		        for (int i = 0; i < d; i++) {
		            // creating new HashMap
		            HashMap<String, String> map = new HashMap<String, String>();
		            //Element e = (Element) nl.item(i);
		            stringid=Double.toString(id);
		            // adding each child node to HashMap key =&gt; value
		            map.put(KEY_ID, stringid);
		            map.put(KEY_TITLE, list1.get((int) (1+(i*3) )));//this would be med name
		            map.put(KEY_ARTIST, list1.get((int) (2+(i*3))));//this would be REMARKS
		            map.put(KEY_DURATION, "");//this would date time taken
		            map.put(KEY_THUMB_URL, list1.get((int) (3+(i*3))));//this would be the image
		 
		            // adding HashList to ArrayList
		            songsList.add(map);
		            id++;
		        }
		 
		        list=(ListView)findViewById(R.id.list2);
		        if(songsList.isEmpty()){
		     	   HashMap<String, String> map = new HashMap<String, String>();
		     	   map.put(KEY_ID, stringid);
		     	   map.put(KEY_TITLE, "No available Medicine, Please add one");
		            map.put(KEY_ARTIST, "");//this would be status and dosage
		            map.put(KEY_DURATION,"");//this would date time taken
		            map.put(KEY_THUMB_URL, "");//this would be the image
		     	   songsList.add(map);
		     	   adapter=new LazyAdapter4(this, songsList);
		            list.setAdapter(adapter);
		        }else{
		        // Getting adapter by passing xml data ArrayList
		        adapter=new LazyAdapter4(this, songsList);
		        list.setAdapter(adapter);
		 
		        // Click event for single list row
		        list.setOnItemClickListener(new OnItemClickListener() {
		 
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                    final int position, long id) {
		            	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(SelectedMed.this);
		            	 helpBuilder.setTitle("Confirmation");
		            	 helpBuilder.setMessage("Are you sure you want to delete?");
		            	 helpBuilder.setNegativeButton("No",
		            			   new DialogInterface.OnClickListener() {

		            			    public void onClick(DialogInterface dialog, int which) {
		            			     // Do nothing but close the dialog
		            			    }
		            			   });

		            	 helpBuilder.setPositiveButton("Yes",
		            			   new DialogInterface.OnClickListener() {

		            			    public void onClick(DialogInterface dialog, int which) {
		            			     // Do nothing but close the dialog
		            			    	String medName = list1.get((int) (1+((position)*3) ));
		        			        	String medRemarks =list1.get((int) (2+((position)*3)));
		        			        	  DefaultHttpClient hc=new DefaultHttpClient();
		        			    	   	ResponseHandler <String> res=new BasicResponseHandler();
		        			    	   	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/DeleteMedicineServlet");
		        			    	   	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		        			    	   	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
		        			    	   	nameValuePairs.add(new BasicNameValuePair("elderName", name)); 
		        			    	   	nameValuePairs.add(new BasicNameValuePair("medName", medName));
		        			    	   	nameValuePairs.add(new BasicNameValuePair("medRemarks", medRemarks));
		        			    	   	try {
											postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
		        			    	   	try {
											HttpResponse response=hc.execute(postMethod);
											startActivity(iredirect);
										} catch (ClientProtocolException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										
		            			    	
		            			    }
		            			   });
		            	 
		            	 
		            	 AlertDialog helpDialog = helpBuilder.create();
		            	 helpDialog.show();
		            	
			    	   
							
						
						
						
							
        	      
        	        }
		        	        	
		            
		            	
		            	
		            	
		 
		            
		        });	
		
		
		
		
		
		
		
		        }
		
		
		
		
		
		
		
		
		myIntent = new Intent(this,addMedicine.class);
	 addMed=(Button)findViewById(R.id.addMed);
	        addMed.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(myIntent);
					
					
					
					
				}});
}
	static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
	 public void onBackPressed(){
			Intent i = new Intent(this, SelectedElder.class);
		     startActivity(i);
		}



}
