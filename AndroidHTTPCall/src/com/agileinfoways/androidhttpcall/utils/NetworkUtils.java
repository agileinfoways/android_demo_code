package com.agileinfoways.androidhttpcall.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtils {


	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
	
	
   /**
	 * This Method is Checked that network is Available or Not If available
	 * Result Will be True If not available Result Will be False
	 */
	public static boolean isNetworkAvailable(Context mContext) {

		/* getting systems Service connectivity manager */
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (mConnectivityManager != null) {
			NetworkInfo[] mNetworkInfos = mConnectivityManager
					.getAllNetworkInfo();
			if (mNetworkInfos != null) {
				for (int i = 0; i < mNetworkInfos.length; i++) {
					if (mNetworkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		return false;
	}
	
	/**
	 * This Method is Checked that Internet Connectivity or Not If available
	 * Result Will be True If not available Result Will be False
	 */
	public static boolean isInternetConnectivityReachable(Context mContext,String link)
	{
		try
		{
			long startnow=0;
			long endnow=0;
			
			startnow = android.os.SystemClock.uptimeMillis();
						
			final int CONN_WAIT_TIME = 3000;
			final int CONN_DATA_WAIT_TIME = 2000;

			HttpParams httpParams = new BasicHttpParams();      
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_WAIT_TIME);
			HttpConnectionParams.setSoTimeout(httpParams, CONN_DATA_WAIT_TIME);

			DefaultHttpClient postClient = new DefaultHttpClient(httpParams);
			
			HttpResponse mResponse = postClient.execute(new HttpGet(link));
			
			HttpEntity mEntity = mResponse.getEntity();			
			InputStream is = mEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String data = null;
			String webPage = "";
			while ((data = reader.readLine()) != null){
				webPage += data + "\n";
			}
			
			endnow = android.os.SystemClock.uptimeMillis();
				
			if(webPage.compareTo("") != 0)
			{
				Log.i("", "Internet Connection : true  Time : "+(endnow-startnow)+" ms");
				return true;
			}
			else
			{   Log.i("", "Internet Connection : false Time : "+(endnow-startnow)/1000+" s");
				return false;
			}
		}catch(Exception e)
		{
			 Log.i("", "Internet Connection : false "+Log.getStackTraceString(e));
			return false;
			
		}
	}

	   /**
		 * This Method is Checked that Which network is connected 
		 *   1 : Wifi 
		 *   2 : mobile network 
		 *   0 : no Connection
		 */
	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	
	public static String getConnectivityStatusString(Context context) {
		int conn = getConnectivityStatus(context);
		String status = null;
		if (conn == TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}
}
