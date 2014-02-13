package com.example.supertest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import sessionManager.ElderSession;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.maps.SupportMapFragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;



public class ElderLocation extends Activity implements OnMapClickListener{
	 GoogleMap map;
	 ElderSession session1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cgelderlocation);
        session1 = new ElderSession(getApplicationContext());  
        Context context = getApplicationContext();
		 session1 = new ElderSession(context);  
	       HashMap<String, String> user1 = session1.getSelectedDetails();
	       String name = user1.get(ElderSession.KEY_ENAME);
	      String Phone = user1.get(ElderSession.KEY_EPHONE);
	      String NRIC = user1.get(ElderSession.KEY_ENRIC);
	      String Email = user1.get(ElderSession.KEY_ElderEMAIL);
	      String coordinates= user1.get(ElderSession.KEY_COORDINATES);
	      String latitude= user1.get(ElderSession.KEY_LATITUDE);
	     String longitude =  user1.get(ElderSession.KEY_LONGITUDE);
	     String latitude2= user1.get(ElderSession.KEY_LATITUDE2);
	     String longitude2 =  user1.get(ElderSession.KEY_LONGITUDE2);
	     String latitude3= user1.get(ElderSession.KEY_LATITUDE3);
	     String longitude3 =  user1.get(ElderSession.KEY_LONGITUDE3);
	     String latitude4= user1.get(ElderSession.KEY_LATITUDE4);
	     String longitude4 =  user1.get(ElderSession.KEY_LONGITUDE4);
	     String latitude5= user1.get(ElderSession.KEY_LATITUDE5);
	     String longitude5 =  user1.get(ElderSession.KEY_LONGITUDE5);
	     String lastlogin = user1.get(ElderSession.KEY_LASTLOGIN);
	     
	 	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
	 	Double ltt2 = null;
	 	Double lgt2 = null;
	 	Double ltt3 = null;
	 	Double lgt3 = null;
	 	Double ltt4 = null;
	 	Double lgt4 = null;
	 	Double ltt5 = null;
	 	Double lgt5 = null;
	 	Date currentLocalTime = cal.getTime();
	 	 DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");   
	 	 date.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
	 	 String localTime = date.format(currentLocalTime); 
	 	Date d1 = null;
		Date d2 = null;
		try {
			d2 = date.parse(localTime);
			d1 = date.parse(lastlogin);
 
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
 /*
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");*/
			
		
 

	     
       Double ltt = 1.37847255;
       Double lgt = 103.85032865;
       ltt = Double.parseDouble(latitude);
       lgt = Double.parseDouble(longitude);
        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment 
         = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        double totalradius = 0;
        double daysradius = 0;
        if(diffDays <5){
        	daysradius = 43200*diffDays;
        }
        double hoursradius=1800*diffHours;
        double minutesradius = 30*diffMinutes;
        totalradius = daysradius+hoursradius+minutesradius;
        
        
        
        
        //MAKING OF CIRCLES
        CircleOptions circleOptions = new CircleOptions()
	    .center(new LatLng(ltt, lgt))
	    .radius(totalradius).strokeColor(Color.RED); // In meters
        map = myMapFragment.getMap();
        // Sets the map type to be "hybrid"
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.addMarker(new MarkerOptions().position(new LatLng(ltt, lgt)).title("Elder was last here at").snippet(diffDays + " days, " +diffHours + " hours, "+diffMinutes + " minutes ago.").icon(BitmapDescriptorFactory.fromResource(R.drawable.number1)));
        
        
        
        //map.addMarker(new MarkerOptions().position(new LatLng(1.37847255, 103.85032865)).title("Last Location"));
        //LatLng latlng= new LatLng(1.37847255,103.85032865);
        LatLng latlng= new LatLng(ltt,lgt);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
       //map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
       //map.setOnMapClickListener(this);
        map.getUiSettings().setZoomControlsEnabled(true);
       map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
       // map.setMyLocationEnabled(true);
        Toast.makeText(getApplicationContext(),"Estimated travelled distance "+totalradius + "m", Toast.LENGTH_LONG).show();
        try{
        	  ltt2 = 1.37847255;
              lgt2 = 103.85032865;
             ltt2 = Double.parseDouble(latitude2);
             lgt2 = Double.parseDouble(longitude2);
             map.addMarker(new MarkerOptions().position(new LatLng(ltt2, lgt2)).title("2nd last location").icon(BitmapDescriptorFactory.fromResource(R.drawable.number2)));

             map.addPolyline(new PolylineOptions().geodesic(true).color(Color.BLUE)
                     .add(new LatLng(ltt, lgt))  
                     .add(new LatLng(ltt2, lgt2)));  
                    
             Log.d("filter", Double.toString(ltt2+lgt2));
             try{
           	  ltt3 = 1.37847255;
                 lgt3 = 103.85032865;
                ltt3 = Double.parseDouble(latitude3);
                lgt3 = Double.parseDouble(longitude3);
                map.addMarker(new MarkerOptions().position(new LatLng(ltt3, lgt3)).title("3rd last location").icon(BitmapDescriptorFactory.fromResource(R.drawable.number3)));
                map.addPolyline(new PolylineOptions().geodesic(true).color(Color.BLUE)
                        .add(new LatLng(ltt, lgt))  
                        .add(new LatLng(ltt2, lgt2))
                        .add(new LatLng(ltt3, lgt3)));  
                
                Log.d("filter", Double.toString(ltt3+lgt3));
                try{
                 	  ltt4 = 1.37847255;
                       lgt4 = 103.85032865;
                      ltt4 = Double.parseDouble(latitude4);
                      lgt4 = Double.parseDouble(longitude4);
                      map.addMarker(new MarkerOptions().position(new LatLng(ltt4, lgt4)).title("4th last location").icon(BitmapDescriptorFactory.fromResource(R.drawable.number4)));
                      map.addPolyline(new PolylineOptions().geodesic(true).color(Color.BLUE)
                              .add(new LatLng(ltt, lgt))  
                              .add(new LatLng(ltt2, lgt2))
                              .add(new LatLng(ltt3, lgt3))
                              .add(new LatLng(ltt4, lgt4)));  
                      Log.d("filter", Double.toString(ltt4+lgt4));
                      try{
                       	  ltt5 = 1.37847255;
                             lgt5 = 103.85032865;
                            ltt5 = Double.parseDouble(latitude5);
                            lgt5 = Double.parseDouble(longitude5);
                            map.addPolyline(new PolylineOptions().geodesic(true).color(Color.BLUE)
                                    .add(new LatLng(ltt, lgt))  
                                    .add(new LatLng(ltt2, lgt2))
                                    .add(new LatLng(ltt3, lgt3))
                                    .add(new LatLng(ltt4, lgt4))
                                    .add(new LatLng(ltt5, lgt5)));  
                            map.addMarker(new MarkerOptions().position(new LatLng(ltt5, lgt5)).title("5th last location").icon(BitmapDescriptorFactory.fromResource(R.drawable.number5)));
                            map.addPolyline(new PolylineOptions().geodesic(true).color(Color.BLUE)
                                    .add(new LatLng(ltt, lgt))  
                                    .add(new LatLng(ltt2, lgt2))
                                    .add(new LatLng(ltt3, lgt3))
                                    .add(new LatLng(ltt4, lgt4))
                                    .add(new LatLng(ltt5, lgt5)));  
                            Log.d("filter", Double.toString(ltt5+lgt5));
                            
                       }catch(Exception e){
                       	Log.d("filter", Double.toString(ltt5+lgt5)); 
                       }
                 }catch(Exception e){
                 	Log.d("filter", Double.toString(ltt4+lgt4)); 
                 }
           }catch(Exception e){
           	Log.d("filter", Double.toString(ltt3+lgt3)); 
           }
        }catch(Exception e){
        	Log.d("filter", Double.toString(ltt2+lgt2)); 
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.addMarker(new MarkerOptions().position(new LatLng(ltt, lgt)).title("Elder was last here at").snippet(diffDays + " days, " +diffHours + " hours, "+diffMinutes + " minutes ago.").icon(BitmapDescriptorFactory.fromResource(R.drawable.number1)));
       

	// Get back the mutable Circle
	Circle circle = map.addCircle(circleOptions);
     }catch (Exception e) {
		e.printStackTrace();
	}}
    @Override
    public void onMapClick(LatLng point) {
    	LatLng latlng= new LatLng(1.37847255,103.85032865);
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        
     
    }
}