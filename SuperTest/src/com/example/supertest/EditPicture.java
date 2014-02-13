package com.example.supertest;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
import org.json.JSONException;
import org.json.JSONObject;

import sessionManager.SessionManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditPicture extends Activity {
	   SessionManager session;
	   Button chgPic;
	   private Uri mImageUrl;
		ImageView mImageView;
		private static final int CAMERA_REQUEST=1888;
		String name = "";
	       String Phone = "";
	       String NRIC = "";
	       String Email = "";
	       TextView tvUser;
	       TextView tvPhone;
	       TextView tvNRIC;
	       TextView tvEmail;Intent myIntent;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caregivereditpic);
	    
	       Button update;
	      
	      
	       Button chgpic;
	       Context context = getApplicationContext();
	       session = new SessionManager(context);  
	       HashMap<String, String> user = session.getUserDetails();
	       name = user.get(SessionManager.KEY_NAME);
	       Phone = user.get(SessionManager.KEY_PHONE);
	       NRIC = user.get(SessionManager.KEY_NRIC);
	       Email = user.get(SessionManager.KEY_EMAIL);
	       tvUser=(TextView)findViewById(R.id.textpicName);
	       tvPhone=(TextView)findViewById(R.id.textView3);
	       tvNRIC=(TextView)findViewById(R.id.txtNRICProfile);
	       tvEmail=(TextView)findViewById(R.id.textView2);
	       tvUser.setText(name);
	       tvPhone.setText(Phone);
	       tvNRIC.setText(NRIC);
	       tvEmail.setText(Email);
	       mImageView = (ImageView) findViewById(R.id.imgEditProfile);
	       myIntent= new Intent(this, elder_main.class);
			
			
	       
	       
	       chgPic=(Button)findViewById(R.id.btnEditPic);
	       chgPic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		        startActivityForResult(cameraIntent, CAMERA_REQUEST);

				
			}});
	     /*  Intent intent = getIntent();
			String imageString = intent.getStringExtra("image");
	if (imageString != null && !imageString.equals("")) {
		byte[] imageAsBytes = Base64.decode(imageString, Base64.DEFAULT);			  
	    mImageView.setImageBitmap(
	            BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
	    );
	}     */
		
}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	        Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	        mImageView.setImageBitmap(photo);
	        Uri currImageURI = data.getData();
	        mImageView.setImageURI(currImageURI);
	        String imageString;
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	      //this will convert image to byte[] 
	        byte[] byteArrayImage = baos.toByteArray(); 

	        imageString = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
	        JSONObject jsonUrl = new JSONObject();
	        try {
				jsonUrl.put("Image", imageString);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
			ThreadPolicy tp = ThreadPolicy.LAX;
			StrictMode.setThreadPolicy(tp);
			DefaultHttpClient hc=new DefaultHttpClient();
        	ResponseHandler <String> res=new BasicResponseHandler();
        	HttpPost postMethod=new HttpPost("http://1.mediapp101.appspot.com/StoreImageServlet");
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        	nameValuePairs.add(new BasicNameValuePair("NRIC",NRIC));  
        	nameValuePairs.add(new BasicNameValuePair("Email",Email ));
        	nameValuePairs.add(new BasicNameValuePair("Phone",Phone ));
        	nameValuePairs.add(new BasicNameValuePair("Name",name ));
        	nameValuePairs.add(new BasicNameValuePair("Image",imageString));
        	try {
    			postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
				try {
					HttpResponse response=hc.execute(postMethod);
					
					
					
					
					
					
					//tvNRIC.setText(imageString);
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//session.createImageSession(imageString);
				//Intent myIntent= new Intent(this, elder_main.class);
				//startActivity(myIntent);
				mImageView.setImageURI(currImageURI);
		
		
			
			
			//Set the image view's image by using imageUri
			//mImageView.setImageURI(currImageURI);
			
		}
	        
		/*try {
			//handle result from gallary select
			if (requestCode == 987) {
				 currImageURI = data.getData();
				this.mImageUrl = currImageURI;
				//Set the image view's image by using imageUri
				mImageView.setImageURI(currImageURI);
				saveTodo();
			}
		} catch (Exception ex) {
			Log.e("TodoDetailsActivity", "Error in onActivityResult: " + ex.getMessage());
		}*/
	
	
	
		}
	
	public void onBackPressed(){
		Intent i = new Intent(this, testing.class);
	     startActivity(i);
	}
}