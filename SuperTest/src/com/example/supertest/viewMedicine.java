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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.ListView;

public class viewMedicine extends Activity {
	ElderSession session1;
	
	Intent myIntent;
	Intent iredirect;	    ListView list;
	    LazyAdapter7 adapter;
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
		setContentView(R.layout.eldermedi);
		iredirect = new Intent(this,viewMedicine.class);
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
		     	   map.put(KEY_TITLE, "No medicine available,please contact caregiver!");
		            map.put(KEY_ARTIST, "");//this would be status and dosage
		            map.put(KEY_DURATION,"");//this would date time taken
		            map.put(KEY_THUMB_URL, "");//this would be the image
		     	   songsList.add(map);
		     	   adapter=new LazyAdapter7(this, songsList);
		            list.setAdapter(adapter);
		        }else{
		        // Getting adapter by passing xml data ArrayList
		        adapter=new LazyAdapter7(this, songsList);
		        list.setAdapter(adapter);
		 
		        // Click event for single list row
		    	
		
		
		
		
		
		
		
		        }
		
}
	static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
	 public void onBackPressed(){
			Intent i = new Intent(this, elder_main.class);
		     startActivity(i);
		}



}
