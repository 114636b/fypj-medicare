package sessionManager;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ElderSession {
	 SharedPreferences elder;
	 
	 // Editor for Shared preferences
	    Editor editor;
	     
	    // Context
	    Context _context;
	     
	    // Shared pref mode
	    int PRIVATE_MODE = 0;
	     
	    // Sharedpref file name
	    private static final String PREF_NAME = "testing1";
	    public static final String IS_LOGIN = "IsLoggedIn";
	    public static final String KEY_NAME = "username";
	    public static final String KEY_PHONE = "99999999";
	    public static final String KEY_NRIC = "S9999999I";
	    public static final String KEY_EMAIL = "-";
	    public static final String KEY_ENAME = "uASDASDe";
	    public static final String KEY_EPHONE = "999ASDASD99";
	    public static final String KEY_ENRIC = "S9ASDASDI";
	    public static final String KEY_ElderEMAIL = "1-";
	    public static final String KEY_cgName = "-6";
	    public static final String KEY_cgEmail = "-5";
	    public static final String KEY_cgPhone = "-4";
	    public static final String KEY_COORDINATES = "-3";
	    public static final String KEY_LATITUDE = "-2";
	    public static final String KEY_LONGITUDE = "-1";
	    public static final String KEY_LASTLOGIN = "1123123";
	    public static final String KEY_LATITUDE2 = "-22";
	    public static final String KEY_LONGITUDE2 = "-12";
	    public static final String KEY_LATITUDE3 = "-23";
	    public static final String KEY_LONGITUDE3 = "-13";
	    public static final String KEY_LATITUDE4 = "-24rrrr";
	    public static final String KEY_LONGITUDE4 = "-14rrr";
	    public static final String KEY_LATITUDE5 = "-25";
	    public static final String KEY_LONGITUDE5 = "-15";
	    public static final String KEY_EMERGENCYNAME = "EMERGENCY NAME";
	    public static final String KEY_EMERGENCYPHONE = "EMERGENCY PHONE";
	    public static final String KEY_EMERGENCYADDRESS = "EMERGENCY ADDRESS";
	    // Constructor
	    public ElderSession(Context context){
	        this._context = context;
	        elder = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = elder.edit();
	    }
	    public void selectElderSession(String Name,String Phone,String Nric,String Email,String coordinates,String latitude,String longitude,String date,String latitude2,String longitude2,String latitude3,String longitude3,String latitude4,String longitude4,String latitude5,String longitude5,String ENAME,String EPHONE,String EADDRESS){
	        // Storing login value as TRUE
	        editor.putBoolean(IS_LOGIN, true);
	         
	        // Storing name in ELDER
	        editor.putString(KEY_ENAME, Name);
	        editor.putString(KEY_EPHONE, Phone);
	        editor.putString(KEY_ENRIC, Nric);
	        editor.putString(KEY_ElderEMAIL, Email);
	        editor.putString(KEY_COORDINATES, coordinates);
	        editor.putString(KEY_LATITUDE, latitude);
	        editor.putString(KEY_LONGITUDE, longitude);
	        editor.putString(KEY_LASTLOGIN, date);
	        editor.putString(KEY_LATITUDE2, latitude2);
	        editor.putString(KEY_LONGITUDE2, longitude2);
	        editor.putString(KEY_LATITUDE3, latitude3);
	        editor.putString(KEY_LONGITUDE3, longitude3);
	        editor.putString(KEY_LATITUDE4, latitude4);
	        editor.putString(KEY_LONGITUDE4, longitude4);
	        editor.putString(KEY_LATITUDE5, latitude5);
	        editor.putString(KEY_LONGITUDE5, longitude5);
	        editor.putString(KEY_EMERGENCYNAME, ENAME);
	        editor.putString(KEY_EMERGENCYPHONE, EPHONE);
	        editor.putString(KEY_EMERGENCYADDRESS, EADDRESS);
	         
	        // commit changes
	        editor.commit();
	    }   
	    public HashMap<String, String> getSelectedDetails(){
	        HashMap<String, String> user = new HashMap<String, String>();
	        // user info
	        user.put(KEY_ENAME, elder.getString(KEY_ENAME, null));
	        user.put(KEY_EPHONE, elder.getString(KEY_EPHONE, null));
	        user.put(KEY_ENRIC, elder.getString(KEY_ENRIC, null));
	        user.put(KEY_ElderEMAIL, elder.getString(KEY_ElderEMAIL, null));	 
	        
	        //coordinates
	        user.put(KEY_COORDINATES, elder.getString(KEY_COORDINATES, null));
	        user.put(KEY_LATITUDE, elder.getString(KEY_LATITUDE, null));
	        user.put(KEY_LONGITUDE, elder.getString(KEY_LONGITUDE, null));
	        user.put(KEY_LASTLOGIN, elder.getString(KEY_LASTLOGIN, null));
	        user.put(KEY_LATITUDE2, elder.getString(KEY_LATITUDE2, null));
	        user.put(KEY_LONGITUDE2, elder.getString(KEY_LONGITUDE2, null));
	        user.put(KEY_LATITUDE3, elder.getString(KEY_LATITUDE3, null));
	        user.put(KEY_LONGITUDE3, elder.getString(KEY_LONGITUDE3, null));
	        user.put(KEY_LATITUDE4, elder.getString(KEY_LATITUDE4, null));
	        user.put(KEY_LONGITUDE4, elder.getString(KEY_LONGITUDE4, null));
	        user.put(KEY_LATITUDE5, elder.getString(KEY_LATITUDE5, null));
	        user.put(KEY_LONGITUDE5, elder.getString(KEY_LONGITUDE5, null));
	        
	        //user emergencyinfo
	        user.put(KEY_EMERGENCYNAME, elder.getString(KEY_EMERGENCYNAME, null));
	        user.put(KEY_EMERGENCYPHONE, elder.getString(KEY_EMERGENCYPHONE, null));
	        user.put(KEY_EMERGENCYADDRESS, elder.getString(KEY_EMERGENCYADDRESS, null));
	         //user.put(KEY_ORGANISATION,pref.getString(KEY_ORGANISATION, null));
	     
	         
	        // return user
	        return user;
	    }
	 
}
