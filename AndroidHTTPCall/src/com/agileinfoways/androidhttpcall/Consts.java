package com.agileinfoways.androidhttpcall;

public class Consts {


	/** GET SharedPref from Device
	 *  run-as com.example.androidhttpcall cat /data/data/com.example.androidhttpcall/shared_prefs/File.xml >/sdcard/othername.xml   */


	/** get database from Device*/
   /**  run-as com.example.androidhttpcall cat /data/data/com.example.androidhttpcall/databases/name >/sdcard/othername.db   */
	
	
	/** remove emulator's log from logcat
	 *  ^(?!.*(nativeGetEnabledTags)).*$
	 */

	public static final String TAG ="AndroidHttpCall App";

	/**
	 * If True then Log files are Visible.
	 * If False Not visible.
	 */
	public static final boolean  IS_DEBUG = true;
	
	public static final String PREFRENCES_FILE_NAME="httpcall_prefs";
	
	
}
