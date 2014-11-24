package com.agileinfoways.androidhttpcall;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;

import org.apache.http.entity.mime.content.ByteArrayBody;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.agileinfoways.androidhttpcall.asynctask.CommonAsyncTask;
import com.agileinfoways.androidhttpcall.utils.NetworkUtils;
import com.agileinfoways.androidhttpcall.utils.ToastUtils;

/**
 *  This is PostImageActivity Which is used for post image with HttpNetworkCall
 * 
 *  for that we have to implement interface when result back from AsyncTask to Activity.
 *  
 *  use One AsyncTask during activity So, it will manage things efficiently.
 */

public class PostImageActivity extends Activity implements 
                   CommonAsyncTask.onAsynctaskCompletedListener<String>,OnClickListener{
	
	/**
	 * AsyncTask Instance 
	 */
	CommonAsyncTask commonAsyncTask;

	/**
	 * Use ProgressDialog
	 */
    private ProgressDialog mProgressDialog;
	
    /**
     * AsyncTask Request Id for identificatioin
     */
	public static final int REQUEST_ID =12; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_image);
								
		((Button)findViewById(R.id.PostImageButton)).setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http_call_main, menu);
		return true;
	}

	/**
	 *  Result back this method when AsyncTask completed.
	 */
	@Override
	public void onTaskCompleted(String result, int Request_id) {
		
		try
		{	   		
		   	 if (mProgressDialog != null)
		     {
		    	 if(mProgressDialog.isShowing())
		    	 {
		    		 mProgressDialog.dismiss();
		    	 }
		   		 
		      }  		    
		   	 
			// Compare Response request id Match with fireRequested Id if same then execute code as per get
		 if(Request_id == REQUEST_ID)
		 {
			Toast.makeText(getApplicationContext(), "Responce : "+result, Toast.LENGTH_SHORT).show();
										
		 }		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.PostImageButton)
		{			
			 ToastUtils	mToasty =new ToastUtils(getApplicationContext());
		
			
				if (NetworkUtils.isNetworkAvailable(getApplicationContext()))
				{


					mProgressDialog =new ProgressDialog(new ContextThemeWrapper(
							PostImageActivity.this, R.style.AppTheme));					 
					mProgressDialog.setCancelable(false);
					mProgressDialog.setMessage("Loading...");
					mProgressDialog.setTitle("");
					mProgressDialog.setCanceledOnTouchOutside(false);
					mProgressDialog.show();
   
					
					commonAsyncTask =new CommonAsyncTask(getApplicationContext(), this, "key",REQUEST_ID);
					  
					   /**
						 * HttpRequest Parameter
						 */
					  LinkedHashMap<String , String> myParamsHasMap = new LinkedHashMap<String, String>();
						
					  myParamsHasMap.put("parmsKey", "paramsValue");
					  
						
					  commonAsyncTask.execute("http://www.google.com/",myParamsHasMap,"POST", getBitmapByteArrayBody());
				} 
				else 
				{
					mToasty.showToast("internet not available");
				}			
	     }	
		
	}
	
	public ByteArrayBody getBitmapByteArrayBody()
	{
		try
		{

		    ByteArrayBody imagebyArrayBody =null;
		    
			((ImageView) findViewById(R.id.postImageView)).buildDrawingCache();
			Bitmap bmap = ((ImageView) findViewById(R.id.postImageView)).getDrawingCache();
			
			
			if(bmap != null)
			{
				// Converting to ByteArrayBody
				ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
				bmap.compress(Bitmap.CompressFormat.PNG, 80, bao1);
				byte[] bitmapdata = bao1.toByteArray();
				String name = Long.toString(System.currentTimeMillis());
				imagebyArrayBody = new ByteArrayBody(bitmapdata, name + ".png");
			}		
			
		return imagebyArrayBody;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
  	@Override
   	protected void onPause() {
   		// TODO Auto-generated method stub
   		super.onPause();
   		
   	 if (mProgressDialog != null)
     {
    	 if(mProgressDialog.isShowing())
    	 {
    		 mProgressDialog.dismiss();
    	 }
   		 
      }  
    
   	}
  	
  	
  	
  	@Override
  	public void onBackPressed() {
  		// TODO Auto-generated method stub
  		super.onBackPressed();
  		
  		 if (commonAsyncTask != null && commonAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
  	     {
  			commonAsyncTask.cancel(true);
  	     }
  	}
  
}
