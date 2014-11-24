/**
 * 
 */
package com.agileinfoways.androidhttpcall;

import java.io.File;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

/**
 * This is ImageUploadService service which upload multiple images (Using HttpMime Multipart) on server 
 * 
 *   <br>Call this way : <br>
 *  
 *   <br> Intent mIntent  =new Intent(Context,ImageUploadService.class);
 *   <br> mIntent.putExtra(ImageUploadService.IMAGE_FILE_PATH_KEY , "adcardimagepath");
 *   <br> startService(mIntent);
 * 
 *   Here used sdcardpath as u can configure your own.
 *   with this u can configure notification with progressbar as well.
 */

public class ImageUploadService extends Service
{		

	/** Flag indicating whether this service is already running. */
	private static boolean sIsRunning = false;
	
	
	public static final String IMAGE_FILE_PATH_KEY ="sdcardpath";


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		super.onStartCommand(intent, flags, startId);

		Bundle extras = intent.getExtras();
		String Path = extras.getString(IMAGE_FILE_PATH_KEY);


		final HttpClient  httpClient = new DefaultHttpClient();

		final HttpPost  postRequest = new HttpPost(API.IMAGE_UPLOAD_URL);

		final ResponseHandler<String>  responseHandler = new BasicResponseHandler();

		// Indicate that this information comes in parts (text and file)
		final MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		final File file = new File(Path);
		if(file.exists())
		{

			synchronized (this.getClass()) {		    
				if (!sIsRunning) {
					sIsRunning = true;

					new Thread() {

						public void run() 
						{
							
							String ServerResponse ="";
							
							// Lower thread priority a little. We're not the UI.
							Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

							String filename = file.getName();

							FileBody fileBody = new FileBody(file, "image/png");
							reqEntity.addPart("filecontent", fileBody);

							
							if (Consts.IS_DEBUG) 						
							Log.i(Consts.TAG, "UploadImage Thread Started");
													
							try {					
								// The rest of the data
								reqEntity.addPart("username", new StringBody(""));
								reqEntity.addPart("password", new StringBody(""));
								reqEntity.addPart("company", new StringBody(""));
								reqEntity.addPart("filename", new StringBody(filename));

								postRequest.setEntity(reqEntity);
								ServerResponse = httpClient.execute(postRequest, responseHandler);
								
								if (Consts.IS_DEBUG) 	
								Log.i(getPackageName(),"PostResponse=>"+ServerResponse+" ");

								if(ServerResponse.compareToIgnoreCase("") !=0)
								{
									JSONObject res = new JSONObject(ServerResponse);
									String StatusCode = res.getString("StatusCode");
									
									if (StatusCode.compareTo("200") == 0) 
									{
										if(file.exists())
										{
											Boolean deleted =file.delete();
											
											if (Consts.IS_DEBUG) 	
											Log.i(getPackageName(), "file deleted:"+deleted);
																						
										}
										else{
											if (Consts.IS_DEBUG) 	
											Log.i(getPackageName(), "Image not exist");
										}
										
										
									}
									else
									{										
										if (Consts.IS_DEBUG) 	
											Log.i(getPackageName(), "Failed Image Upload");
									}									
								
								}
							}catch(Exception e)
							{		if (Consts.IS_DEBUG) 
									Log.e(Consts.TAG, Log.getStackTraceString(e));								
							}
				 
						stopSelf();
						sIsRunning = false;
					}
				}
				.start();
			} else 
			{				
				if (Consts.IS_DEBUG) 
					Log.i(Consts.TAG, "UploadImage.onStartCommand(): Already running.");
			}
		}
	}
	else{		
		    if (Consts.IS_DEBUG) 
			Log.e(Consts.TAG, "Image file Not found");	
	}

	return START_STICKY;
}


}
