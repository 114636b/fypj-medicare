package sessionManager;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	 SharedPreferences pref;
    
	    // Editor for Shared preferences
	    Editor editor;
	     
	    // Context
	    Context _context;
	     
	    // Shared pref mode
	    int PRIVATE_MODE = 0;
	     
	    // Sharedpref file name
	    private static final String PREF_NAME = "testing";
	    public static final String IS_LOGIN = "IsLoggedIn";
	    public static final String KEY_NAME = "username";
	    public static final String KEY_PHONE = "99999999";
	    public static final String KEY_NRIC = "S9999999I";
	    public static final String KEY_EMAIL = "-";
	    public static final String KEY_ENAME = "uASDASDe";
	    public static final String KEY_EPHONE = "999AS2DASD99";
	    public static final String KEY_ENRIC = "S9ASDASDI";
	    public static final String KEY_ElderEMAIL = "-";
	    public static final String KEY_cgName = "-1";
	    public static final String KEY_cgEmail = "-A";
	    public static final String KEY_cgPhone = "-123";
	    public static final String KEY_coordinates = "-441";
	    public static final String KEY_LATITUDE = "-445";
	    public static final String KEY_LONGITUDE = "-4433";
	    public static final String KEY_EEADDRESS = "Somewhere in Singapore";
	    public static final String KEY_EEPHONE = "EMERGENCY PHONE";
	    public static final String KEY_EENAME = "EMERGENCY CONTACT NAME";
	    // Constructor
	    public SessionManager(Context context){
	        this._context = context;
	        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = pref.edit();
	    }


	    public void storeEmergencyInfo(String updateName, String updatePhone , String updateAddress)
	    {
	    	editor.putBoolean(IS_LOGIN, true);
	         
	        // Storing name in pref
	        editor.putString(KEY_EENAME, updateName);
	        editor.putString(KEY_EEPHONE, updatePhone);
	        editor.putString(KEY_EEADDRESS, updateAddress);
	        
	         
	        // Storing email in pref
	        //editor.putString(KEY_EMAIL, email);
	         
	        // commit changes
	        editor.commit();
	    }
	    
	    //method to put value to variable created here
	    public void createLoginSession(String Name,String Phone, String Nric,String Email){
	        // Storing login value as TRUE
	        editor.putBoolean(IS_LOGIN, true);
	         
	        // Storing name in pref
	        editor.putString(KEY_NAME, Name);
	        editor.putString(KEY_PHONE, Phone);
	        editor.putString(KEY_NRIC, Nric);
	        editor.putString(KEY_EMAIL,Email);
	         
	        // Storing email in pref
	        //editor.putString(KEY_EMAIL, email);
	         
	        // commit changes
	        editor.commit();
	    }   
	    public void selectElderSession(String Name,String Phone, String Nric,String Email,String coordinates,String latitude,String longitude){
	        // Storing login value as TRUE
	        editor.putBoolean(IS_LOGIN, true);
	         
	        // Storing name in pref
	        editor.putString(KEY_ENAME, Name);
	        editor.putString(KEY_EPHONE, Phone);
	        editor.putString(KEY_ENRIC, Nric);
	        //editor.putString(KEY_ElderEMAIL,Email);
	        editor.putString(KEY_coordinates,coordinates);
	        editor.putString(KEY_LATITUDE,latitude);
	        editor.putString(KEY_LONGITUDE,longitude);
	      
	         
	        // commit changes
	        editor.commit();
	    }   
	    
	    
	    public void createElderLoginSession(String Name,String Phone, String Nric,String Email,String cgEmail,String cgName,String cgPhone,String coordinates){
	        // Storing login value as TRUE
	        editor.putBoolean(IS_LOGIN, true);
	         
	        // Storing name in pref
	        editor.putString(KEY_ENAME, Name);
	        editor.putString(KEY_EPHONE, Phone);
	        editor.putString(KEY_ENRIC, Nric);
	        editor.putString(KEY_ElderEMAIL,Email);
	        editor.putString(KEY_cgEmail,cgEmail);
	        editor.putString(KEY_cgName,cgName);
	        editor.putString(KEY_cgPhone,cgPhone);
	        editor.putString(KEY_coordinates,coordinates);
	        // Storing email in pref
	        //editor.putString(KEY_EMAIL, email);
	         
	        // commit changes
	        editor.commit();
	    }   
	    
	    
	    /**
	     * Get stored session data
	     * */
	    public HashMap<String, String> getUserDetails(){
	        HashMap<String, String> user = new HashMap<String, String>();
	        // user name
	        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
	        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
	        user.put(KEY_NRIC, pref.getString(KEY_NRIC, null));
	        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
	        
	         //user.put(KEY_ORGANISATION,pref.getString(KEY_ORGANISATION, null));
	     
	         
	        // return user
	        return user;
	    }
	    public HashMap<String, String> getElderDetails(){
	        HashMap<String, String> user = new HashMap<String, String>();
	        // user name
	        user.put(KEY_ENAME, pref.getString(KEY_ENAME, null));
	        user.put(KEY_EPHONE, pref.getString(KEY_EPHONE, null));
	        user.put(KEY_ENRIC, pref.getString(KEY_ENRIC, null));
	        user.put(KEY_ElderEMAIL, pref.getString(KEY_ElderEMAIL, null));
	        user.put(KEY_cgEmail, pref.getString(KEY_cgEmail, null));
	        user.put(KEY_cgName, pref.getString(KEY_cgName, null));
	        user.put(KEY_cgPhone, pref.getString(KEY_cgPhone, null));
	        user.put(KEY_coordinates, pref.getString(KEY_coordinates, null));
	         //user.put(KEY_ORGANISATION,pref.getString(KEY_ORGANISATION, null));
	     
	         
	        // return user
	        return user;
	    }
	   
	    public HashMap<String, String> getEInfoDetails(){
	        HashMap<String, String> user = new HashMap<String, String>();
	        // user name
	        user.put(KEY_EENAME, pref.getString(KEY_EENAME, null));
	        user.put(KEY_EEPHONE, pref.getString(KEY_EEPHONE, null));
	        user.put(KEY_EEADDRESS, pref.getString(KEY_EEADDRESS, null));
	        editor.commit();
	         
	     
	         
	        // return user
	        return user;


}

}