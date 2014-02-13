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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sessionManager.ElderSession;
import sessionManager.SessionManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FindPeopleFragment extends Fragment{

	   static final String KEY_SONG = "song"; // parent node
	    static final String KEY_ID = "id";
	    static final String KEY_TITLE = "title";
	    static final String KEY_ARTIST = "artist";
	    static final String KEY_DURATION = "duration";
	    static final String KEY_THUMB_URL = "thumb_url";
	    LazyAdapter3 adapter;
	    String stringid;
	String[] items={"MR LEE","MR CHIT " ,"MR Boon","mr wazzup","???","mr wazzup","???","mr wazzup","???"};
	ArrayAdapter<CharSequence> adapter1;
	ListView lv;
	   public static final String EXTRA_TITLE = "com.devspark.sidenavigation.sample.extra.MTGOBJECT";
	    public static final String EXTRA_RESOURCE_ID = "com.devspark.sidenavigation.sample.extra.RESOURCE_ID";
	    public static final String EXTRA_MODE = "com.devspark.sidenavigation.sample.extra.MODE";
	    SessionManager session;  
	   ElderSession session1;
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    Intent myIntent;
    CharSequence mTitle;
    private String[] navMenuTitles;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	 private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	    ImageView btnAdd;
	    String name = "";
	       String Phone = "";
	       String NRIC = "";
	       String Email = "";
	        List<String> list1 = new ArrayList<String>();
	     ListView ListView;
TextView txtVName;
int deleteposition;
   public FindPeopleFragment(){}
    
   @Override
   @SuppressWarnings("static-access")
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
 
	   LinearLayout rootView = (LinearLayout)inflater.inflate(R.layout.caregiverhome, container, false);
    // get the listview
       ListView = (ListView)rootView.findViewById(R.id.listElder);

       final Context context = getActivity().getApplicationContext();
       session = new SessionManager(context);  session1 = new ElderSession(context);  
       HashMap<String, String> user = session.getUserDetails();
       name = user.get(SessionManager.KEY_NAME);
       Phone = user.get(SessionManager.KEY_PHONE);
       NRIC = user.get(SessionManager.KEY_NRIC);
       Email = user.get(SessionManager.KEY_EMAIL);
      // prepareListData();
       txtVName=(TextView)rootView.findViewById(R.id.txtNRICProfile);
       txtVName.setText(name + "/ "+ NRIC);
       
       // preparing list data

       
       DefaultHttpClient hc=new DefaultHttpClient();
   	ResponseHandler <String> res=new BasicResponseHandler();
   	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveElderServlet");
   	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
   	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
   	try {
		postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	try {
		HttpResponse response=hc.execute(postMethod);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		//scanner = new Scanner(response.getEntity().getContent());
		String json = reader.readLine() ;
		ArrayList<HashMap<String, String>> elderList = new ArrayList<HashMap<String, String>>();	    			
		JSONArray jsonArray = new JSONArray(json);
		 List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
		    list1.add(jsonArray.getString(i));
		    Log.i("things in array",jsonArray.getString(i));
		}
		try{
			String Valid = (String)list1.get(0);
			  // looping through all song nodes &lt;song&gt;
		    for (int i = 0; i < (jsonArray.length()/2); i++) {
		    	
		        // creating new HashMap
		        HashMap<String, String> map = new HashMap<String, String>();
		        //Element e = (Element) nl.item(i);
		      stringid =  String.valueOf(i+1) ;
		        // adding each child node to HashMap key =&gt; value
		        map.put(KEY_ID, stringid);
		        map.put(KEY_TITLE, list1.get((2*i)));//this would be med name
		        map.put(KEY_ARTIST,"");//this would be status and dosage
		        map.put(KEY_DURATION, "");//this would date time taken
		        map.put(KEY_THUMB_URL, list1.get((2*i)+1));//this would be the image

		        // adding HashList to ArrayList
		        elderList.add(map);
		        
		    }
		    adapter=new LazyAdapter3(getActivity().getApplicationContext(), elderList);
	           ListView.setAdapter(adapter);
			
	           
	           ListView.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					deleteposition = arg2;
					new AlertDialog.Builder(getActivity()).setTitle("Delete Elder")
					.setMessage("Are you sure you want to delete?")
					    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					        	
					        	String eldername = list1.get(deleteposition*2);
								//String eldername = (String) parent.getItemAtPosition(position);
						
									    
					               DefaultHttpClient hc=new DefaultHttpClient();
						        	ResponseHandler <String> res=new BasicResponseHandler();
						        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/DeleteElderServlet");
						        	List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();  
						        	nameValuePairs1.add(new BasicNameValuePair("NRIC",NRIC));  
						        	nameValuePairs1.add(new BasicNameValuePair("elderName", eldername)); 
						        	InputStream inputStream = null;	     
						        	try {
										postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
						        	
										try {
											HttpResponse response=hc.execute(postMethod);
											Fragment newFragment = new FindPeopleFragment(); 
											 FragmentTransaction transaction = getFragmentManager().beginTransaction();
											    transaction.replace(R.id.frame_container, newFragment);
											    transaction.addToBackStack(null);
											    transaction.commit(); 
										} catch (ClientProtocolException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
					      
					        	
					        	
					        	
					        	
					        }}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
					        
						        }})
							     .show();
					      
							     
								;	
							
					
					
					
					
					
					
					return false;
				}});
	           
	           
	           
	           
	           ListView.setOnItemClickListener(new OnItemClickListener() {
	        	   
	              

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
	                       int position, long id) {
					String eldername = list1.get(position*2);
					//String eldername = (String) parent.getItemAtPosition(position);
			Log.i("s",eldername );
						    
		               DefaultHttpClient hc=new DefaultHttpClient();
			        	ResponseHandler <String> res=new BasicResponseHandler();
			        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveOneElderServlet");
			        	List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();  
			        	nameValuePairs1.add(new BasicNameValuePair("NRIC",NRIC));  
			        	nameValuePairs1.add(new BasicNameValuePair("elderName", eldername)); 
			        	InputStream inputStream = null;	     
			        	try {
							postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	try {
							HttpResponse response=hc.execute(postMethod);
							BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
							//scanner = new Scanner(response.getEntity().getContent());
							String json = reader.readLine() ;
			    					    			
			    			JSONArray jsonArray = new JSONArray(json);
			    			List<String> list = new ArrayList<String>();

			    			for (int i = 0; i < jsonArray.length(); i++) {
			    			    list.add(jsonArray.getString(i));
			    			}
			    			  String retrievedName = (String)list.get(0);
			    			  String retrievedNRIC=(String)list.get(1);
			    				String retrievedEmail = (String)list.get(2);
			    				String retrievedPhone = (String)list.get(3);
			    				String retrievedCGEmail = (String)list.get(4);
			    				String retrievedCGName = (String)list.get(5);
			    				String retrievedCGPhone = (String)list.get(6);
			    				String retrievedCoordinates = (String)list.get(7);
			    				String retrievedLatitude = (String)list.get(8);
			    				String retrievedLongitude = (String)list.get(9);
			    			String retrievedDate = (String)list.get(10);
			    			String retrievedLatitude2 = (String)list.get(11);
		    				String retrievedLongitude2 = (String)list.get(12);
		    				String retrievedLatitude3 = (String)list.get(13);
		    				String retrievedLongitude3 = (String)list.get(14);
		    				String retrievedLatitude4 = (String)list.get(15);
		    				String retrievedLongitude4 = (String)list.get(16);
		    				String retrievedLatitude5 = (String)list.get(17);
		    				String retrievedLongitude5 = (String)list.get(18);
		    				String retrievedEmergencyName = (String)list.get(19);
		    				String retrievedEmergencyPhone =(String)list.get(20);
		String retrievedEmergencyAddress = (String)list.get(21);
		    				
			    				session1.selectElderSession(retrievedName,retrievedPhone,retrievedNRIC,retrievedEmail,retrievedCoordinates,retrievedLatitude,retrievedLongitude,retrievedDate,retrievedLatitude2,retrievedLongitude2,
			    						retrievedLatitude3,retrievedLongitude3,retrievedLatitude4,retrievedLongitude4,retrievedLatitude5,retrievedLongitude5,retrievedEmergencyName,retrievedEmergencyPhone,retrievedEmergencyAddress);
			    				
			    				
			    				
			    				Intent i = new Intent(getActivity(), SelectedElder.class);         
			    				getActivity().startActivity(i);
			    			
							
							
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
			        	
			        	
			        	
		               return;
		           }
		       });

			

		}catch(Exception e){
			e.printStackTrace();
			//list1.add("You are not caring for any elder.");
			 HashMap<String, String> map = new HashMap<String, String>();
		        //Element e = (Element) nl.item(i);
		      
		        // adding each child node to HashMap key =&gt; value
		        map.put(KEY_ID, "1");
		        map.put(KEY_TITLE, "You are not caring for any elder");//this would be med name
		        map.put(KEY_ARTIST,"");//this would be status and dosage
		        map.put(KEY_DURATION, "");//this would date time taken
		        map.put(KEY_THUMB_URL, "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");//this would be the image
		        elderList.add(map);
		        adapter=new LazyAdapter3(getActivity().getApplicationContext(), elderList);
		         ListView.setAdapter(adapter);
		         ListView.setSelector(android.R.color.transparent); 
		        
		        
		        ListView.setLongClickable( false );
		        ListView.setClickable( false );
		        
		}
		 
		
ImageView btnDelete = (ImageView)rootView.findViewById(R.id.imageView2);
		btnDelete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				new AlertDialog.Builder(getActivity()).setTitle("Delete Elder")
				.setMessage("To delete, hold elder's name in the list")
				    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				        	
				        	
				      
				        	
				        	
				        	
				        	
				        }})
				      
						     .show();
							;	
						
				
				
				
				
			}});
		
		
		
		
		   btnAdd=(ImageView)rootView.findViewById(R.id.btnAddElder);
	       btnAdd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Fragment fragment = null;
//		    	fragment = new FindPeopleFragment();
//		    	FragmentManager fragmentManager = getFragmentManager();
//		        fragmentManager.beginTransaction()
//		                .replace(R.id.frame_container, fragment).commit();
	//
//		        // update selected item and title, then close the drawer
//		        mDrawerList.setItemChecked(position, true);
//		        mDrawerList.setSelection(position);
//		        setTitle(navMenuTitles[position]);
//		        mDrawerLayout.closeDrawer(mDrawerList);
				 // Create new fragment and transaction
				Intent i = new Intent(getActivity(), addPeopleFragment.class);         
				getActivity().startActivity(i);
			   // Fragment newFragment = new addPeopleFragment(); 
			    // consider using Java coding conventions (upper char class names!!!)
			    //FragmentTransaction transaction = getFragmentManager().beginTransaction();

			    // Replace whatever is in the fragment_container view with this fragment,
			    // and add the transaction to the back stack
			   // transaction.replace(R.id.frame_container, newFragment);
			    //transaction.addToBackStack(null);

			    // Commit the transaction
			   // transaction.commit(); 
				//
				
			}
			});

		
	    	   

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
	return rootView;
       
       
   }
       
       
       
   
  

	static{
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
	StrictMode.setThreadPolicy(policy);  
	}	
public void setTitle(CharSequence title) {
    mTitle = title;
    getActivity().setTitle(mTitle);
}
 
}