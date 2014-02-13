package com.example.supertest;

import java.io.IOException;
import java.io.InputStream;
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

import sessionManager.ElderSession;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class addAlarm extends Activity 
{static final String KEY_ID = "id";
	   static final String KEY_TITLE = "title";
    Button btnSelectDate,btnSelectTime;
    ElderSession session1;
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;
    EditText etDate,etTime,etDosage,etNumber;
    String hourstring,minString,monthstring,yearstring,daystring;
        // variables to save user selected date and time
    public  int year,month,day,hour,minute;  
// declare  the variables to Show/Set the date and time when Time and  Date Picker Dialog first appears
    private int mYear, mMonth, mDay,mHour,mMinute; 
    final Calendar c = Calendar.getInstance();
    int maxYear = c.get(Calendar.YEAR) +100; // this year ( 2013 ) +100 =2113
   int maxMonth = c.get(Calendar.MONTH);
   int maxDay = c.get(Calendar.DAY_OF_MONTH);
   int minYear = c.get(Calendar.YEAR);
   int minMonth = c.get(Calendar.MONTH); // january
   int minDay = c.get(Calendar.DAY_OF_MONTH);
   int minHour= c.get(Calendar.HOUR_OF_DAY);
   int minMinute = c.get(Calendar.MINUTE);
   String stringid;
   boolean success = false;
   boolean success1 = false;
   boolean success2 = false;
   Button addReminder;
   Intent myIntent;
   Boolean succ = false;
   Spinner Items;
    // constructor
   String NRIC,name;
    public addAlarm()
    {
                // Assign current Date and Time Values to Variables
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.addalarm);
                myIntent= new Intent(this, SelectedElder.class);
                // get the references of buttons
                btnSelectDate=(Button)findViewById(R.id.buttonSelectDate);
                btnSelectTime=(Button)findViewById(R.id.buttonSelectTime);
                etDate=(EditText)findViewById(R.id.etDate);
                etTime=(EditText)findViewById(R.id.etTime);
                etDosage=(EditText)findViewById(R.id.etDosage);
                addReminder = (Button)findViewById(R.id.btnAddReminder);
                etNumber =(EditText)findViewById(R.id.etNumber);
                final Context context = getApplicationContext();
       		 session1 = new ElderSession(context);  
       	       HashMap<String, String> user1 = session1.getSelectedDetails();
       	       double id = 1;
       	       name = user1.get(ElderSession.KEY_ENAME);
       	       String Phone = user1.get(ElderSession.KEY_EPHONE);
NRIC = user1.get(ElderSession.KEY_ENRIC);
       	       DefaultHttpClient hc=new DefaultHttpClient();
       	   	ResponseHandler <String> res=new BasicResponseHandler();
       	   	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/RetrieveMedicine");
       	   	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
       	   	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
       	   	nameValuePairs.add(new BasicNameValuePair("elderName", name)); 
       	   	InputStream inputStream = null;	     
       	   	List<String> list1 = new ArrayList<String>();
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
       		if(d ==0){
       		 List<String> SpinnerArray =  new ArrayList<String>();
       	    SpinnerArray.add("No Medicine Available, Please add one");
addReminder.setClickable(false);
       	    ;

       	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
       	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       	    
       	    Items = (Spinner) findViewById(R.id.spinner1);
       	 Items.setEnabled(false);
       	    Items.setAdapter(adapter);
       		}
       		else{
       		 List<String> SpinnerArray =  new ArrayList<String>();
       		
       		 for (int i = 0; i < d; i++) {
		           
       			SpinnerArray.add(list1.get((int) (1+(i*3))));
		            id++;
		            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
		       	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		       	   Items= (Spinner) findViewById(R.id.spinner1);
		       	    Items.setAdapter(adapter);
		       	    success = true;
		        }
       			
       		}
       		
       		
       		//add reminder button
       		
       		addReminder.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					String dosage = etDosage.getText().toString();
					String number = etNumber.getText().toString();
					int times = Integer.parseInt(number);
					int timediff= 24/times;
					int hour = Integer.parseInt(hourstring);
					int day = Integer.parseInt(daystring);
					String daystringvalue;
					String hourstringvalue ;
					if(success ==true && success1==true && success2==true){
					String Text = Items.getSelectedItem().toString();
					// TODO Auto-generated method stub
					
					for(int i=0;i<times;i++){
					;
						
						 NumberFormat formatter = new DecimalFormat("00");  
						 hourstringvalue = formatter.format(hour); 
						 daystringvalue = formatter.format(day);
					DefaultHttpClient hc=new DefaultHttpClient();
		        	ResponseHandler <String> res=new BasicResponseHandler();
		        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/AddAlarmServlet");
		        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		        	nameValuePairs.add(new BasicNameValuePair("nric", NRIC));  
		        	nameValuePairs.add(new BasicNameValuePair("name", name)); 
		        	nameValuePairs.add(new BasicNameValuePair("dosage",dosage ));
		        	nameValuePairs.add(new BasicNameValuePair("medName", Text)); 
		        	nameValuePairs.add(new BasicNameValuePair("year", yearstring));
		        	nameValuePairs.add(new BasicNameValuePair("month", monthstring));
		        	nameValuePairs.add(new BasicNameValuePair("day", daystringvalue));
		        	nameValuePairs.add(new BasicNameValuePair("hour", hourstringvalue));
		        	nameValuePairs.add(new BasicNameValuePair("min", minString));
		        
		        	try {
						postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		        	
		        	HttpResponse response;
		        	
					
						try {
							response = hc.execute(postMethod);
							
					    	
							
							
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						hour = hour+timediff;
						if(hour>23){
							hour = hour-24;
							day = day+1;
						}
						succ = true;
					}
		        	
					}
					else{
						Toast.makeText(getApplicationContext(),
                                "Please fill in all details", 1).show();
					}
					if(succ==true){
						showOk();
					}
				}});
       		
       		
       		
       		
       		
       		
       		
       		
       		
       		
       		
       		
       		
                
                // Set ClickListener on btnSelectDate 

                btnSelectDate.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        // Show the DatePickerDialog
                         showDialog(DATE_DIALOG_ID);
                    }
                });
                
                // Set ClickListener on btnSelectTime
                btnSelectTime.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        // Show the TimePickerDialog
                         showDialog(TIME_DIALOG_ID);
                    }
                });
                
    }
    
    
    // Register  DatePickerDialog listener
     private DatePickerDialog.OnDateSetListener mDateSetListener =
                            new DatePickerDialog.OnDateSetListener() {
                        // the callback received when the user "sets" the Date in the DatePickerDialog
                                public void onDateSet(DatePicker view, int yearSelected,
                                                      int monthOfYear, int dayOfMonth) {
                                	
                                   year = yearSelected;
                                   month = monthOfYear+1;
                                   day = dayOfMonth;
                                   NumberFormat formatter = new DecimalFormat("00");  
                                   yearstring = formatter.format(year); 
                                   monthstring = formatter.format(month);
                                   daystring = formatter.format(day);

                                   if(year<minYear){
                                	   
                                	   Toast.makeText(getApplicationContext(),
                                               "Please choose future date", 1).show();
                                   }
                                   else if(month<minMonth){
                                	   Toast.makeText(getApplicationContext(),
                                               "Please choose future date", 1).show();
                                   }
                                   else if(day<minDay){
                                	   Toast.makeText(getApplicationContext(),
                                               "Please choose future date", 1).show();
                                   }
                                   // Set the Selected Date in Select date Button
                                   else{
                                	   success1=true;
                                   etDate.setText(day+"/"+month+"/"+year);}
                                   
                                }
                            };

       // Register  TimePickerDialog listener                 
                            private TimePickerDialog.OnTimeSetListener mTimeSetListener =
                                new TimePickerDialog.OnTimeSetListener() {
                         // the callback received when the user "sets" the TimePickerDialog in the dialog
                                    public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                                        hour = hourOfDay;
                                        minute = min;
                                        NumberFormat formatter = new DecimalFormat("00");  
                                       hourstring = formatter.format(hour); 
                                       minString = formatter.format(minute);
                                        if(hour<minHour){
                                     	   
                                     	   Toast.makeText(getApplicationContext(),
                                                    "Please choose future time", 1).show();
                                        }
                                        else{
                                        	/*if(success1 == true && minute<=minMinute ){
                                          	   Toast.makeText(getApplicationContext(),
                                                         "Please choose future time", 1).show();
                                             }else*/
                                        // Set the Selected Date in Select date Button
                                        etTime.setText(hourstring+"."+minString);
                                        	success2=true; }
                                      }
                                };


    // Method automatically gets Called when you call showDialog()  method
                            @Override
                            protected Dialog onCreateDialog(int id) {
                                switch (id) {
                                case DATE_DIALOG_ID:
                         // create a new DatePickerDialog with values you want to show 
                                    return new DatePickerDialog(this,
                                                mDateSetListener,
                                                mYear, mMonth, mDay);
                        // create a new TimePickerDialog with values you want to show 
                                case TIME_DIALOG_ID:
                                    return new TimePickerDialog(this,
                                            mTimeSetListener, mHour, mMinute, false);
                               
                                }
                                return null;
                            }
                            
                            
                            public void showOk(){
                            	
                            	myIntent= new Intent(this, SelectedElder.class);
                            	new AlertDialog.Builder(this).setTitle("Successful")
                            	.setMessage("Added successfully")
                            	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            	        public void onClick(DialogInterface dialog, int which) {
                            	        	
                            	        	
                            	        	startActivity(myIntent);
                            	      
                            	        	
                            	        	
                            	        	
                            	        	
                            	        }})
                            	      
                            			     .show();
                            				;	
                            			}             
                        	static{
                        	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
                        	StrictMode.setThreadPolicy(policy);  
                        	}	
                            
}