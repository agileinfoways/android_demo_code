package com.agileinfoways.androidhttpcall.asynctask;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.entity.mime.content.ByteArrayBody;

import android.content.Context;
import android.os.AsyncTask;

import com.agileinfoways.androidhttpcall.Consts;
import com.agileinfoways.androidhttpcall.http.RestClient;
import com.agileinfoways.androidhttpcall.utils.NetworkUtils;


/**
 * 
 *  This Class extends {@link android.os.AsyncTask} & it will Handle All Network calls
 *  So, no need to make other {@link android.os.AsyncTask} class.
 *  
 *  
 *  <br> <br>The Constructor Parameters 
 *  
 *   <br> CommonAsyncTask mCommonTask =new CommonAsyncTask(context,this,key,Request_id);  
 *    
 *  @param params1 Context  				: Pass Context. 
 *  @param params2 Interface Reference  	: Pass here Interface reference  
 *                                            1) If this use in Activity then implements CommonAsyncTask.onAsynctaskCompletedListener<T>  
 *                                                then pass this (AsyncTask run & pass on activity's implemented Method).
 *                                            2) If not then pass null(AsyncTask run as separately not dependent on activity). 
 *  @param params3 Key                      : Pass key as String if not then pass blank : ""
 *  @param params4 Request_id               : If more than one AsyncTask are use in same activity for Result Response makes separate Execution.
 *  
 *  
 *  AsyncTask has three Params as TypeCast <Params, Progress, Result>
 *  
 *  1) Params Type as Object : you can pass any type of Object for send HttpCall & 
 *                             it Will Pass directly on doInBackGround(Object... params) Method.
 *  
 *  	
 *  <br> <br>   You have to Pass Params Like this way
	
<br>	  CommonAsyncTask mCommonTask =new CommonAsyncTask(context,this,key,Request_id);   
<br>	   mCommonTask.execute(Params1 ,Params2 , Params3, Params4);

	 <br> <br>
	 
	   @param params1 = URL (String)                                // Pass url.
	   @param params2 = Params (LinkedHasmap<String,String>)        // Pass Params.
	   @param params3 = Request Method (String Like POST,GET,PUT)   // Request Method.
	   @param params4 = Image ByteData (ByteArrayBody)              // Image ByteArray.
	   @param params5 = AuthenticateParams HasMap       (LinkedHasmap<String,String>)   // AuthenticateParams LinkedHasMap<String ,String>
	   @param params6 = HeaderParams HasMap             (LinkedHasmap<String,String>)   // HeaderParams LinkedHasMap<String ,String>
	
 *
 */

 public class CommonAsyncTask extends AsyncTask<Object, Integer, String>
 {
	// If more than one AsyncTask are use in same activity for Result Response makes separate Execution.
	protected int mRequestId;
	
	// Listener for pass result on Current Running Activity.
	public onAsynctaskCompletedListener<String> CallBack;
	
	// Context 
	Context mActivityContext;
	
	// Pass value to AsyncTask & use for Feature 	
	String mKey;
		
	/**
	 * This is interface that take care of Asynctask Completed 
	 * & if Completed this will Send Result in Implemented Method of Activity
	 * @param <T>
	 */
	public interface onAsynctaskCompletedListener<T>
	{
		public abstract void onTaskCompleted(T result, int Request_id);
	}
	
	/**
	 * This is constructor of CommonAsyncTask that take care of 
	 * 
	 * 1) Context 
	 * 2) Asynctask callback Register if not then pass null 
	 * 2) String Key if needed other Wise pass null
	 * 4) If more than one AsyncTask are use in same activity for Result Response makes
	 *       separate Execution.	
	 */
	public CommonAsyncTask(Context context,onAsynctaskCompletedListener<String> callback,String key,int Request_id)
	{
		mActivityContext = context;
		CallBack         = callback;
		mKey             = key;
		setRequestId(Request_id);
	}

	
	/** Setter Getter Methods for Request Id */
   
	 public void setRequestId (int requestId)
	 {
		 this.mRequestId = requestId;
	 }
	 
	 public int getRequestId ()
	 {  
		 return mRequestId;
	 }
	

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	/**
	 * This is doInBackGround(Object... params) method of CommonAsyncTask that take care of Network Call
	 * 
	 * You have to Pass Params Like this way <br> 
	 * <br>
	 *  <b>CommonAsyncTask mCommonTask =new CommonAsyncTask(context,this,key); </b>  <br>
	 *  <b>mCommonTask.execute(Params1 ,Params2 , Params3, Params4);</b>
	 * 
	 *  @param params = URL                             (String)
	 *  @param params = Params                          (LinkedHasmap<String,String>)
	 *  @param params = Request Method                  (String Like POST,GET,PUT)
	 *  @param params = Image ByteData                  (ByteArrayBody)
	 *  @param params = AuthenticateParams HasMap       (LinkedHasmap<String,String>)
	 *  @param params = HeaderParams HasMap             (LinkedHasmap<String,String>)
	 */
	
	@Override
	public String doInBackground(Object... params) {
		// TODO Auto-generated method stub

		ByteArrayBody mBody=null;		
		File myFile=null;
		String Responce = "";
		String URL = "";
		String REQUEST_METHOD = "POST";
		LinkedHashMap<String, String> mParamsHasMap = new LinkedHashMap<String, String>();
		
		LinkedHashMap<String , String> myHeadersHasMap = new LinkedHashMap<String, String>();

		LinkedHashMap<String , String> myAuthenticateHasMap = new LinkedHashMap<String, String>();


		// First Param String Url
		try {
			URL = (String) params[0];
		} catch (Exception e) {
		}

		// Second Param LinkedHasMap Parameters
		try {
			mParamsHasMap = (LinkedHashMap<String, String>) params[1];
		} catch (Exception e) {
		}

		// Third Param String Request Method
		try {
			REQUEST_METHOD = (String) params[2];
		} catch (Exception e) {
		}

		// Fourth Param ByteArrayBody ImageByteArray
		try {
			mBody = (ByteArrayBody) params[3];
		} catch (Exception e) {
		}
		
		
		// Fifth Params LinkedHasMap As Authenticate
		try {
			myAuthenticateHasMap = (LinkedHashMap<String, String>) params[4];
		} catch (Exception e) {
		}
		
		// Six Params LinkedHasMap As Headers
		try {
			myHeadersHasMap = (LinkedHashMap<String, String>) params[5];
		} catch (Exception e) {
		}
		
		
		// Seven Params File As file
		try {
			myFile = (File) params[6];
		} catch (Exception e) {
		}
		
		
		
		try {
			if (NetworkUtils.isNetworkAvailable(mActivityContext) )
			{
				RestClient mClient = new RestClient(URL);
								
				// If Body is null then Use simple Network Operation 
				if(mBody == null)
				{
					
					if(myFile == null)
					{
					
						// Add Params in RestClient Object
						if (mParamsHasMap != null && mParamsHasMap.size() > 0) {
							
							for (Map.Entry<String, String> entry : mParamsHasMap.entrySet()) 
							{
								mClient.addParam(entry.getKey(), entry.getValue());
							}
						}
						
						
						// Add Authentication in RestClient Object
						if (myAuthenticateHasMap != null && myAuthenticateHasMap.size() > 0) {
							
							for(Map.Entry<String, String> entry : myAuthenticateHasMap.entrySet())
							{
								mClient.addBasicAuthentication(entry.getKey(), entry.getValue());
							}
						}
						
						
						// Add HeadersHasMap in RestClient Object
						if (myHeadersHasMap != null && myHeadersHasMap.size() > 0) {
							
							for(Map.Entry<String, String> entry : myHeadersHasMap.entrySet())
							{
								mClient.addHeader(entry.getKey(), entry.getValue());
							}
						}
						
						if (REQUEST_METHOD.compareTo("GET") == 0) {
							Responce = mClient.execute(RestClient.RequestMethod.GET);
						} else if (REQUEST_METHOD.compareTo("POST") == 0) {
							Responce = mClient.execute(RestClient.RequestMethod.POST);
						} else if (REQUEST_METHOD.compareTo("PUT") == 0) {
							Responce = mClient.execute(RestClient.RequestMethod.PUT);
						} else if (REQUEST_METHOD.compareTo("DELETE") == 0) {
							Responce = mClient.execute(RestClient.RequestMethod.DELETE);
						}
					
					}
					else
					{
						// If Body is there then Use Post Image With Params Network Operation 
						if (REQUEST_METHOD.compareTo("POST") == 0) {
							Responce = mClient.postFile(myFile, mParamsHasMap);
						} 
					}
				}
				else
				{
					// If Body is there then Use Post Image With Params Network Operation 
					if (REQUEST_METHOD.compareTo("POST") == 0) {
						Responce = mClient.postImage(mBody, mParamsHasMap);
					} 
				}
			}

		} catch (Exception e) 
		{
			if(Consts.IS_DEBUG)
			{ 	e.printStackTrace();
			}
		}
		return Responce;
	}
	

	@Override
	public void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
				
		if(CallBack != null)
		{
			CallBack.onTaskCompleted(result,getRequestId());
		}
				
	}


	@Override
	public void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
}
