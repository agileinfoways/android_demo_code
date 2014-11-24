package com.agileinfoways.androidhttpcall;

import java.util.LinkedHashMap;

import org.apache.http.Header;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.agileinfoways.androidhttpcall.asynctask.CommonAsyncTask;
import com.agileinfoways.androidhttpcall.http.RestClient;
import com.agileinfoways.androidhttpcall.utils.CommonUtils;
import com.agileinfoways.androidhttpcall.utils.NetworkUtils;
import com.agileinfoways.androidhttpcall.utils.ToastUtils;

/**
 *  This is HttpCallMainActivity Which is used for fire HttpNetworkCall
 * 
 *  for that we have to implement interface when result back from AsyncTask to Activity.
 *  
 *  use One AsyncTask during activity So, it will manage things efficiently.
 */

public class HttpCallMainActivity extends Activity implements 
                   CommonAsyncTask.onAsynctaskCompletedListener<String>,OnClickListener{

	/**
	 *  Request Methods
	 */
	public static final String[] REQUEST_METHOD_ARRAY =new String[]
	  {       "GET",
		      "POST",
		      "PUT",
		      "DELETE"	
	     };
	
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
	public static final int REQUEST_ID =10; 
	
	/**
	 * HttpRequest Parameter
	 */
	public LinkedHashMap<String , String> myParamsHasMap = new LinkedHashMap<String, String>();
	
	/**
	 * HttpRequest Header Parameter
	 */
	public LinkedHashMap<String , String> myHeadersHasMap = new LinkedHashMap<String, String>();

	/**
	 * HttpRequest Authentication Parameter
	 */
	public LinkedHashMap<String , String> myAuthenticateHasMap = new LinkedHashMap<String, String>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_call_main);
		
		SpinnerAdapter mSpinnerAdapter =new SpinnerAdapter(getApplicationContext(), R.layout.myspinner, REQUEST_METHOD_ARRAY);		
		((Spinner)findViewById(R.id.methodSpinner)).setAdapter(mSpinnerAdapter);
		((Spinner)findViewById(R.id.methodSpinner)).setSelection(0);
  		
		
		((Button)findViewById(R.id.launchRequestBtn)).setOnClickListener(this);
		((Button)findViewById(R.id.AddHeaderBtn)).setOnClickListener(this);
		((Button)findViewById(R.id.AddParamsBtn)).setOnClickListener(this);
		((Button)findViewById(R.id.AuthenticationBtn)).setOnClickListener(this);
		((Button)findViewById(R.id.ViewRequestBtn)).setOnClickListener(this);
		((Button)findViewById(R.id.ViewResponceBtn)).setOnClickListener(this);		 		
		((Button)findViewById(R.id.PostImageBtn)).setOnClickListener(this);
		
		
		((EditText)findViewById(R.id.UrlEditText)).setText("");
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
			Header[] mHeaderList =  RestClient.getHttpResponse().getAllHeaders();
			
			((TextView)findViewById(R.id.ResponceHeadersTextView)).setText( "\nHeaders : \n "+CommonUtils.arrayToString(mHeaderList) + "\n\n");
			
			((TextView)findViewById(R.id.ResponceTextView)).setText( "\nResponse : \n "+result);
			
			if(RestClient.getHttpResponseTime() > 1000)
			{
				((TextView)findViewById(R.id.ExecutionTimeTextView)).setText("Time :" +RestClient.getHttpResponseTime()/1000+" sec");
			}
			else
			{
				((TextView)findViewById(R.id.ExecutionTimeTextView)).setText("Time :"+ RestClient.getHttpResponseTime()+" msec");
			}
			
		
			
			((TextView)findViewById(R.id.responceCodeTextView)).setText("Responce code : "+RestClient.getHttpResponse().getStatusLine().getStatusCode());
			
			
			((TextView)findViewById(R.id.urlTextView)).setText( ((Spinner)findViewById(R.id.methodSpinner)).getSelectedItem()  +" :  \n" +
					"                    "+ ((EditText)findViewById(R.id.UrlEditText)).getText().toString()+"\n\n");
			
										
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
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		if(v.getId() == R.id.AddHeaderBtn)
		{
			try {
				LinearLayout mainLayout = (LinearLayout) findViewById(R.id.AddHeaderLayout);

			    View mView = inflater
								.inflate(R.layout.authentication_row, null, false);

						EditText field1EditText = (EditText) mView
								.findViewById(R.id.field1EditText);

						EditText field2EditText = (EditText) mView
								.findViewById(R.id.field2EditText);
						
						field1EditText.setHint("name");						
						field2EditText.setHint("value");						
						
						Button removeRowButton = (Button) mView.findViewById(R.id.cencelrowBtn);
						
						removeRowButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								LinearLayout mView = (LinearLayout) v.getParent();								
								
								LinearLayout mParentView = (LinearLayout) mView.getParent();
								
								mParentView.removeView(mView);
								
							}
						});

						mainLayout.addView(mView);
							     
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		else if(v.getId() == R.id.AddParamsBtn)
		{
			try {

				LinearLayout mainLayout = (LinearLayout) findViewById(R.id.AddParamsLayout);

			    View mView = inflater
								.inflate(R.layout.authentication_row, null, false);

						EditText field1EditText = (EditText) mView
								.findViewById(R.id.field1EditText);

						EditText field2EditText = (EditText) mView
								.findViewById(R.id.field2EditText);
						
						field1EditText.setHint("name");						
						field2EditText.setHint("value");						
						
						Button removeRowButton = (Button) mView.findViewById(R.id.cencelrowBtn);
						
						removeRowButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								LinearLayout mView = (LinearLayout) v.getParent();								
								
								LinearLayout mParentView = (LinearLayout) mView.getParent();
								
								mParentView.removeView(mView);
								
							}
						});

						mainLayout.addView(mView);
							     
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		else if(v.getId() == R.id.AuthenticationBtn)
		{
			try {

				LinearLayout mainLayout = (LinearLayout) findViewById(R.id.AuthenticationLayout);

			    View mView = inflater
								.inflate(R.layout.authentication_row, null, false);

						EditText field1EditText = (EditText) mView
								.findViewById(R.id.field1EditText);

						EditText field2EditText = (EditText) mView
								.findViewById(R.id.field2EditText);
						
						field1EditText.setHint("username");						
						field2EditText.setHint("password");						
						
						Button removeRowButton = (Button) mView.findViewById(R.id.cencelrowBtn);
						
						removeRowButton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								LinearLayout mView = (LinearLayout) v.getParent();								
								
								LinearLayout mParentView = (LinearLayout) mView.getParent();
								
								mParentView.removeView(mView);
								
							}
						});

						mainLayout.addView(mView);
							     
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		else if(v.getId() == R.id.PostImageBtn)
		{
			Intent mIntent =new Intent(HttpCallMainActivity.this, PostImageActivity.class);
			startActivity(mIntent);
					
		}
		else if(v.getId() == R.id.launchRequestBtn)
		{
			
			
			 ToastUtils	mToasty =new ToastUtils(getApplicationContext());
			
						
			if(((EditText)findViewById(R.id.UrlEditText)).getText().toString().trim().compareTo("") !=0)
			{
				
				// Clear all data
				
				((TextView)findViewById(R.id.ResponceHeadersTextView)).setText("");				
				((TextView)findViewById(R.id.ResponceTextView)).setText("");
				((TextView)findViewById(R.id.ExecutionTimeTextView)).setText("");
				((TextView)findViewById(R.id.responceCodeTextView)).setText("");						
				((TextView)findViewById(R.id.urlTextView)).setText("");	
						
				
				myParamsHasMap.clear();
				myHeadersHasMap.clear();
				myAuthenticateHasMap.clear();
				
				getAllAuthentications();
				getAllHeaders();
				getAllParameters();
				
			
				if (NetworkUtils.isNetworkAvailable(getApplicationContext()))
				{
					 mProgressDialog =new ProgressDialog(new ContextThemeWrapper(
			                                  HttpCallMainActivity.this, R.style.AppTheme));					 
				     mProgressDialog.setCancelable(false);
				     mProgressDialog.setMessage("Loading...");
				     mProgressDialog.setTitle("");
				 	mProgressDialog.setCanceledOnTouchOutside(false);
				     mProgressDialog.show();
				    					
					 commonAsyncTask =new CommonAsyncTask(getApplicationContext(), this, "key",REQUEST_ID);
					   
					 commonAsyncTask.execute(((EditText)findViewById(R.id.UrlEditText)).getText().toString().trim()
							     ,myParamsHasMap,REQUEST_METHOD_ARRAY[((Spinner)findViewById(R.id.methodSpinner)).getSelectedItemPosition()], null ,myAuthenticateHasMap, myHeadersHasMap);
				} 
				else 
				{
					mToasty.showToast("internet not available");
				}

			}
			else {
				
				   mToasty.showToast("url is not valid");
				}
			
		}		
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
  		  		
  	}
  	
	/**
	 * get All HttpCall Params from Dynamic layout
	 */
	public void getAllParameters()
	{
		LinearLayout AddParamsLayout = (LinearLayout) findViewById(R.id.AddParamsLayout);

		for(int i=0; i< AddParamsLayout.getChildCount(); i++)
		{
			    LinearLayout mChildLayout =(LinearLayout) AddParamsLayout.getChildAt(i);
				    
			    if(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString().compareTo("") != 0)
			    {
			    myParamsHasMap.put(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString(),
			    		  ((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString());
			    }
		}
	}
	
	/**
	 * get All HttpCall Headers from Dynamic layout
	 */
	public void getAllHeaders()
	{
		LinearLayout AddHeaderLayout = (LinearLayout) findViewById(R.id.AddHeaderLayout);

		for(int i=0; i< AddHeaderLayout.getChildCount(); i++)
		{
			    LinearLayout mChildLayout =(LinearLayout) AddHeaderLayout.getChildAt(i);
				    
			    if(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString().compareTo("") != 0)
			    {
			    myHeadersHasMap.put(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString(),
			    		  ((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString());
			    }
		}
	}
	
	/**
	 * get All HttpCall Authentications from Dynamic layout
	 */
	public void getAllAuthentications()
	{
		LinearLayout AuthenticationLayout = (LinearLayout) findViewById(R.id.AuthenticationLayout);

		for(int i=0; i< AuthenticationLayout.getChildCount(); i++)
		{
			    LinearLayout mChildLayout =(LinearLayout) AuthenticationLayout.getChildAt(i);
				    
			    if(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString().compareTo("") != 0)
			    {
			    	myAuthenticateHasMap.put(((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString(),
			    		  ((EditText) mChildLayout.findViewById(R.id.field1EditText)).getText().toString());
			    }
		}
	}
	
	// Custom Simple Spinner Adapter 	   
	public class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		 String[]  items = new String[]{};

		public SpinnerAdapter(final Context context, final int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
			this.context = context;
		}
		

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						R.layout.myspinner, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						R.layout.myspinner, parent, false);
			}

			// android.R.id.text1 is default text view in resource of the android.
			// android.R.layout.simple_spinner_item is default layout in resources of android.

			TextView tv = (TextView) convertView
			.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			
			return convertView;
		}
	}

}
