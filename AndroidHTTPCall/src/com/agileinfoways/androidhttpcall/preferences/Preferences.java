package com.agileinfoways.androidhttpcall.preferences;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

import com.agileinfoways.androidhttpcall.Consts;

/**
 * This is static Wrapper class of Preference before use this make sure you have created 
 *             PreferencesKeys class which define all keys which will use throughout Application. 
 * 
 *<br><br> Use this Way : 
 * 
 *  <br> 1) for Set Value to Preferences 
 *   
 *   	<br>	Preferences.setPreference(context, key, val);            // set Boolean type
		<br>	Preferences.setPreference(context, key, val);            // set String type
		<br>	Preferences.setPreference_float(context, key, val);	     // set Float type		
		<br>	Preferences.setPreference_int(context, key, val);        // set Integer type
		<br>	Preferences.setPreference_long(context, key, val);       // set Long type
		<br>	Preferences.setPreference_ArrayList(context, key, val);  // setArrayList type
			
   <br><br>   2) for get value from Preferences
      
        <br> 	Preferences.getAllPreference(context);                   // get AllPreferences with comma seperated
		<br>	Preferences.getPreference(context, key);                 // get Preferences String 
		<br>	Preferences.getPreference_boolean(context, key);         // get Boolean 
		<br>	Preferences.getPreference_int(context, key);             // get Integer
		<br>	Preferences.getPreference_long(context, key);            // get Long
		<br>	Preferences.getPreferenceArray(mContext, key);           // get Array
 *   
 *   
 *  
 */


public class Preferences {

	/**
	 * getSharedPrefrences Instance
	 *  
     * @param context - pass context
     * @return SharedPreferences
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Consts.PREFRENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     *  Add String value in preferences
     *  
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - String value to be stored
     */
    public static void setPreference(Context context, String key, String val) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, val);
        editor.commit();
    }

    /**
     * Add float value in preferences
     * 
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - float value to be stored
     */
    public static void setPreference_float(Context context, String key, float val) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, val);
        editor.commit();
    }
    
    /**
     * Add boolean value in preferences
     * 
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - boolean value to be stored
     */
    public static void setPreference(Context context, String key, boolean val) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    /**
     * Add int value in preferences
     * 
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - int value to be stored
     */
    public static void setPreference_int(Context context, String key, int val) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, val);
        editor.commit();
    }


    /**
     * Add Long value in preferences
     *
     * @param context - context
     * @param key     - Constant key, will be used for accessing the stored value
     * @param val     - long value to be stored
     */
    public static void setPreference_long(Context context, String key, long val) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, val);
        editor.commit();
    }
    

    /**
     * Add Array<String> value in preferences 
     *
     * @param mContext             - context
     * @param key                  - Constant key, will be used for accessing the stored value
     * @param array<String> val    - ArrayList<String> value to be stored, mostly used to store FB Session value
     */
    
    public static boolean setPreferenceArray( Context mContext , String key, ArrayList<String> array) {   
        SharedPreferences prefs = Preferences.getSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();  
        editor.putInt(key +"_size", array.size());  
        for(int i=0;i<array.size();i++)  
            editor.putString(key + "_" + i, array.get(i));  
        return editor.commit();  
    } 
    
    
    /**
     * Clear preference Array from Key
     *
     * @param context - context
     * @param key     - the key which you stored before
     */    
	public static void clearPreferenceArray(Context c, String key) {
		SharedPreferences settings = Preferences.getSharedPreferences(c);

		if (getPreferenceArray(c, key) != null
				&& getPreferenceArray(c, key).size() > 0) {
			for (String element : getPreferenceArray(c, key)) {
				if (findPrefrenceKey(c, element) != null
						&& settings.contains(findPrefrenceKey(c, element))) {
					SharedPreferences.Editor editor = settings.edit();
					editor.remove(findPrefrenceKey(c, element));
					editor.commit();
				}
			}
		}
	}
    
    
	/**
     * get preference key from  preference value
     *
     * @param context - context
     * @param value   - the value which you stored before
     */
	public static String findPrefrenceKey(Context con, String value) {
		SharedPreferences settings = Preferences.getSharedPreferences(con);
		Map<String, ?> editor = settings.getAll();
		for (Entry<String, ?> entry : editor.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null; // not found
	}

    /**
     * Remove preference key
     *
     * @param context - context
     * @param key     - the key which you stored before
     */
    public static void removePreference(Context context, String key) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Get preference String value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return        - String value
     */
    public static String getPreference(Context context, String key) {
        SharedPreferences prefs = Preferences.getSharedPreferences(context);
        return prefs.getString(key, "");
    }
    
    /**
     * Get preference ArrayList<String> value by passing related key
     *
     * @param mContext - context
     * @param key      - key value used when adding preference
     * @return         - ArrayList<String> value
     */    
    public static ArrayList<String> getPreferenceArray(Context mContext,String key) {  
        SharedPreferences prefs = Preferences.getSharedPreferences(mContext); 
        int size = prefs.getInt( key + "_size", 0);  
        ArrayList<String> array= new ArrayList<String>(size);  
        for(int i=0;i<size;i++)  
        	array.add(prefs.getString(key + "_" + i, null));  
        return array;  
    }  
    
    
    /**
     * Get preference Long value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return        - long value
     */
    public static long getPreference_long(Context context, String key) {
        SharedPreferences prefs = Preferences.getSharedPreferences(context);
        return prefs.getLong(key, 0);
    }


    /**
     * Get preference Boolean value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return        - Boolean value
     */
    
    public static boolean getPreference_boolean(Context context, String key) {
        SharedPreferences prefs = Preferences.getSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }

    /**
     * Get preference Integer value by passing related key
     *
     * @param context - context
     * @param key     - key value used when adding preference
     * @return        - int value
     */
    public static int getPreference_int(Context context, String key) {
        SharedPreferences prefs = Preferences.getSharedPreferences(context);
        return prefs.getInt(key, 0);
    }

    /**
     * Clear all stored  preferences
     *
     * @param context - context
     */
    public static void removeAllPreference(Context context) {
        SharedPreferences settings = Preferences.getSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
    
    /**
     * get all stored  preferences as String
     *
     * @param context - context
     */
	public static String getAllPreference(Context context) {
		SharedPreferences settings = Preferences.getSharedPreferences(context);
		Map<String, ?> editor = settings.getAll();
		String text = "";
		try {
			for (Entry<String, ?> entry : editor.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				// do stuff
				text += "\t" + key + " = " + value + "\t";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return text;
	}	
}
