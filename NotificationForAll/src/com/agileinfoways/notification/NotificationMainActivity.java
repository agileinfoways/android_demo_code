
package com.agileinfoways.notification;


import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.agileinfoways.notification.utils.NotificationCompat2.PRIORITY_HIGH;
import static com.agileinfoways.notification.utils.NotificationCompat2.PRIORITY_LOW;
import static com.agileinfoways.notification.utils.NotificationCompat2.PRIORITY_MAX;
import static com.agileinfoways.notification.utils.NotificationCompat2.PRIORITY_MIN;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.agileinfoways.notification.utils.NotificationCompat2;



/**
 *  This is Implementation of Notification Compatible for All Android Flavours. 
 * 
 *  This is Activity where all notification Example are shown & choose any one as you want 
 *    
 *  For More Detail Please Refer 1) Notification UI : {@link http://developer.android.com/guide/topics/ui/notifiers/notifications.html }
 *								 2) Notification Design : {@link http://developer.android.com/guide/topics/ui/notifiers/notifications.html }
 * 								 3) Notification Methods : {@link http://developer.android.com/reference/android/app/Notification.html }
 */


public class NotificationMainActivity extends Activity 
 {
	
    public static final String TAG = "NotificationMainActivity";
    
	/**
	 *  NotificationManager & Notification that takes care all operation regarding notifications.
	 *  
	 *  {@link http://developer.android.com/reference/android/app/NotificationManager.html }
	 */
	   NotificationManager mgr;
	   
	   Notification myNotification =null;
		
	   int NOTIFICATION_COUNT=1;
	   
	   ArrayList<String> mNotificationItems = new ArrayList<String>();
	   
	   AsyncTask<Void, Integer, Void> myNotificationAsyncTask;
	
	   
	   @Override
	  protected void onCreate(Bundle savedInstanceState) 
	 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		
		/** -------------------------------------------------------------------------------------------------------------------------
		 *            This Method is for Simple notification
		 */
				
		((Button) findViewById(R.id.simple)).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View view) 
			{


				NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
				
							mBuilder.setSmallIcon(R.drawable.icon);
							mBuilder.setTicker("Ticker: " + "Simple Notification Ticker");
							mBuilder.setContentTitle("Title: " + "Simple Notification Title");
							mBuilder.setContentText("Simple Notification Content Text");
							mBuilder.setContentIntent(getPendingIntent());
							
							/**
							 *  If this method pass true Notification gone away from notificationBar
							 */
							mBuilder.setAutoCancel(true);
							
							/**
							 *  This method used for when to notify time in long
							 */
							mBuilder.setWhen(System.currentTimeMillis());
				
							/**
							 *  Set Vibration When Notify notification
							 *  Dont forget to add vibrate permission on manifest file
							 */
						
							// Start without a delay
							// Vibrate for 100 milliseconds
							// Sleep for 1000 milliseconds
							long[] pattern = {0, 1000, 1000};

							
							// Vibration for Notification
							mBuilder.setVibrate(pattern);
							
							/**
							 * Set the large number at the right-hand side of the notification
							 */
							mBuilder.setNumber(2);
							
				
			    myNotification = mBuilder.build();
						
			    /**
			     * Notification Flags
			     * 
			     * Notification.DEFAULT_ALL      : Default settings (DEFAULT_LIGHTS | DEFAULT_SOUND | DEFAULT_VIBRATE)
			     * 
			     * Notification.FLAG_AUTO_CANCEL : Bit to be bitwise-ored into the flags field that should be set if the 
			     *                                 notification should be canceled when it is clicked by the user.
			     *                                 
			     * Notification.FLAG_NO_CLEAR    : That should be set if the notification should not be canceled
			     *                                   when the user clicks the Clear all button.
			     *                                   
			     * Notification.FLAG_SHOW_LIGHTS : That should be set if you want the LED on for this notification.
			     * 
			     */
			    
			    
			    
		    //    myNotification.flags =Notification.DEFAULT_ALL|Notification.FLAG_AUTO_CANCEL;
			    
			    /**
			     * Notify notification 
			     * 
			     * @params notificationId : if it same it will override notification But it is different it will create new every time.
			     * @params Notification : Instance of notification. 
			     */
				mgr.notify(R.id.simple, myNotification);

				
				/**
				 * This is badge display only for Samsung devices.  
				 */
				setBadge(getApplicationContext(), 2);		
			}
		});
		
			
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for BigPicture notification 
		 * 
		 */
		
		((Button) findViewById(R.id.big_picture)).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View view)
			{
				BigPictureNotification();
			}
		});
		
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for BigText Notification 
		 * 
		 */	
		((Button) findViewById(R.id.big_text)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view) {
							
				BigTextNotification();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Inbox (Line By Line Like Gmail) Notification 
		 * 
		 */		
		((Button) findViewById(R.id.inbox)).setOnClickListener(new OnClickListener() 
		{			
			@Override 
			public void onClick(View view) 
			{
				InboxStyleNotification();
			}
		});
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Give different Action in one Notification 
		 * 
		 */
		((Button) findViewById(R.id.actions)).setOnClickListener(new OnClickListener() 
		{			
			@Override 
			public void onClick(View view) {
			 
				 ActionNotification();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Show Progress in one Notification 
		 * 
		 */
		((Button) findViewById(R.id.progress)).setOnClickListener(new OnClickListener()
		{
			@Override 
			public void onClick(final View view)
			{
							
				ProgressBarNotification(view);
		    }
		});

		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Big Content Custom View (RemoteViews) in one Notification 
		 * 
		 */
		((Button) findViewById(R.id.big_content_view)).setOnClickListener(new OnClickListener() 
		{
			@Override 
			public void onClick(View view) 
			{
				CustomRemoteViewNotifications();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for HighPriority  Notification 
		 * 
		 */
		((Button) findViewById(R.id.high_priority)).setOnClickListener(new OnClickListener()
		{
			@Override 
           public void onClick(View view) {
								
				HighPriorityNotification();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for LowPriority Notification 
		 * 
		 */
		((Button) findViewById(R.id.low_priority)).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View view) {
								
               LowPriorityNotification();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Max Priority Notification 
		 * 
		 */
		((Button) findViewById(R.id.max_priority)).setOnClickListener(new OnClickListener()
		 {
			@Override 
			 public void onClick(View view)
			{
					MaxPriorityNotification();
			}
		});
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is for Min Priority Notification 
		 * 
		 */
		((Button) findViewById(R.id.min_priority)).setOnClickListener(new OnClickListener() 
		{
			@Override 
			public void onClick(View view)
			{
					MinPriorityNotification();
			}
		});
		
		
		/**------------------------------------------------------------------------------------------------------------------------
		 * This Method is Display Like Whats App Notification 
		 * 
		 */
		((Button) findViewById(R.id.Nofication_Like_WhatsApp)).setOnClickListener(new OnClickListener() 
		{
			@Override 
			public void onClick(View view)
			{
				String messageWhatsApp = "Me : Hello Hi "+NOTIFICATION_COUNT;


				NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);				
							
				           
							mBuilder.setContentIntent(getPendingIntent());
							mNotificationItems.add(messageWhatsApp);

							mBuilder.setAutoCancel(false);
							mBuilder.setWhen(System.currentTimeMillis());

							mBuilder.setSmallIcon(R.drawable.rockaway_sunset);	

							mBuilder.setNumber(NOTIFICATION_COUNT);		
							
							// If count is one then display message Like this way & count ++ then display different way
							if(NOTIFICATION_COUNT==1)
							{
								mBuilder.setTicker("Message From Me");
								mBuilder.setContentTitle("Me");	
								mBuilder.setContentText(messageWhatsApp);							
								
								myNotification = mBuilder.build();								
							}
							else
							{			
								mBuilder.setTicker("Message From Agile");
								mBuilder.setContentTitle("WhatsApp");	
								mBuilder.setContentText(NOTIFICATION_COUNT+" new message.");
																						
								NotificationCompat2.InboxStyle  mInboxStyle= new NotificationCompat2.InboxStyle(mBuilder);
								
											// setBig Text
								            mInboxStyle.setBigContentTitle("WhatsApp");
								            
								            for(String message:mNotificationItems)
								            {
								            	 mInboxStyle.addLine(message);
								            }
								            
								            mInboxStyle.setSummaryText(mNotificationItems.size() + " Conversions from 1 recipient.");
								          
								 
								myNotification = mInboxStyle.build();
													
						}
					
 
				mgr.notify(100, myNotification);

				setBadge(getApplicationContext(), NOTIFICATION_COUNT);	
					
				NOTIFICATION_COUNT++;
			}
		});
		
		
		
		//------------------------------------------------------------------------------------------------------------------------
		
		
		((Button) findViewById(R.id.clearAll)).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View view) 
			{
				
				// If Asynctask is runing when activity goes in background it will  cancel that Asynctask.
				if(myNotificationAsyncTask != null && myNotificationAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
				{
					myNotificationAsyncTask.cancel(true);
					
					myNotificationAsyncTask= null;
				}
				
				// Reset Contdown
				NOTIFICATION_COUNT = 1;
				mNotificationItems.clear();
				
				// Clear All Notification
				mgr.cancelAll();

				
				
				/**
				 * This is badge display only for Samsung devices.  
				 *  & it is reset
				 */
				setBadge(getApplicationContext(), 0);
			}
		});
	}


	   
	   /**
	    *  Pending Intent {@link http://developer.android.com/reference/android/app/PendingIntent.html }
	    *  
	    *  FLAG_CANCEL_CURRENT :  Flag indicating that if the described PendingIntent 
	    *                          already exists, the current one should be cancelled before generating a new one.
	    *                            
	    *  FLAG_NO_CREATE      :  Flag indicating that if the described PendingIntent already exists, then simply return null instead of creating it.
	    *  
	    *  FLAG_ONE_SHOT       :  Flag indicating that this PendingIntent can be used only once.
	    *  
	    *  FLAG_UPDATE_CURRENT :  Flag indicating that if the described PendingIntent already exists, 
	    *                          then keep it but replace its extra data with what is in this new Intent.
	    * @return
	    */
	private PendingIntent getPendingIntent() {
		Intent i = new Intent(this, NotificationMainActivity.class);
		
		// Clear top Activity
		i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
		return PendingIntent.getActivity(this, 0, i, 0);
	}
	
	
	
	/**
	 * Play Default notification Sound
	 * @param con
	 */
	
	public void playDefaultNotificationSound(Context con) {
		// TODO Auto-generated method stub
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(con, notification);
		if (!r.isPlaying())
			r.play();

	}

	/**
	 * Play Raw File Sound
	 * @param con
	 */
	public void playSound(int id, Context mContext) {
		try {
			
			MediaPlayer mediaPlayer = MediaPlayer.create(mContext, id);

			mediaPlayer.start();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * This MEthod is For Running Only Samsung Devices(Which using TouchWiz UI)
	 *  bcoz they have implemented this feature for own devices.
	 *  
	 * @param context
	 * @param count
	 */
	public static void setBadge(Context context, int count) {
		String launcherClassName = getLauncherClassName(context);
		if (launcherClassName == null) {
			return;
		}
		Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent.putExtra("badge_count", count);
		intent.putExtra("badge_count_package_name", context.getPackageName());
		intent.putExtra("badge_count_class_name", launcherClassName);
		context.sendBroadcast(intent);
	}

	
	public static String getLauncherClassName(Context context) {

		PackageManager pm = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
		for (ResolveInfo resolveInfo : resolveInfos) {
			String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
			if (pkgName.equalsIgnoreCase(context.getPackageName())) {
				String className = resolveInfo.activityInfo.name;
				return className;
			}
		}
		return null;
	}
	
		
	
	public void BigPictureNotification()
	{

		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "BigPicture Notification Ticker");
					mBuilder.setContentTitle("Title: " + "BigPicture Notification Title");
					mBuilder.setContentText("BigPicture Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(2);
					
		
		/**
		 * Big Picture Style Compatible Method for Notification.
		 */
		
		NotificationCompat2.BigPictureStyle  mBigPicStyle= new NotificationCompat2.BigPictureStyle(mBuilder);
		
					// setBig Picture Bitmap
		            mBigPicStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.rockaway_sunset));
		            mBigPicStyle.setBigContentTitle("BigPicture Content Title");	
		            mBigPicStyle.setSummaryText("http://www.google.com");
		
		
		            
		
		 myNotification = mBigPicStyle.build();
					
		
		
		 /**
		  * Notify notification 
		  * 
		  * @params notificationId : if it same it will override notification But it is different it will create new every time.
		  * @params Notification : Instance of notification. 
		  */
		 mgr.notify(R.id.big_picture, myNotification);


		 /**
		  * This is badge display only for Samsung devices.  
		  */
		 setBadge(getApplicationContext(), 2);
	
	}
	
	
	public void BigTextNotification()
	{
		
		
		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Big Text Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Big Text Notification Title");
					mBuilder.setContentText("Big Text Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
					
					
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(3);
					
		
		/**
		 * Big Text Style Compatible Method for Notification.
		 * 
		 */
		
		NotificationCompat2.BigTextStyle  mBigTextStyle= new NotificationCompat2.BigTextStyle(mBuilder);
		
					// setBig Text
		            mBigTextStyle.setBigContentTitle("BigText Content Title");
		            mBigTextStyle.bigText(""
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. "
							+ "This is a very long piece of text. ");
		
		
		            
		
		 myNotification = mBigTextStyle.build();
					
		
		
		 /**
		  * Notify notification 
		  * 
		  * @params notificationId : if it same it will override notification But it is different it will create new every time.
		  * @params Notification : Instance of notification. 
		  */
		 mgr.notify(R.id.big_text, myNotification);


		 /**
		  * This is badge display only for Samsung devices.  
		  */
		 setBadge(getApplicationContext(), 3);
		
	}
	
	
	public void InboxStyleNotification()
	{

		
		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Inbox Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Inbox Notification Title");
					mBuilder.setContentText("Inbox Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(3);
					
		
		/**
		 * Inbox Style Compatible Method for Notification.
		 * 
		 */
		
		NotificationCompat2.InboxStyle  mInboxStyle= new NotificationCompat2.InboxStyle(mBuilder);
		
					// setBig Text
		            mInboxStyle.setBigContentTitle("Inbox Content Title");
		            mInboxStyle.addLine("Line One");
		            mInboxStyle.addLine("Line Two");
					mInboxStyle.addLine("Line Three");
					mInboxStyle.addLine("Line Four");
					mInboxStyle.addLine("Line Five");
		
		
		            
		
		 myNotification = mInboxStyle.build();
					
		
		
		 /**
		  * Notify notification 
		  * 
		  * @params notificationId : if it same it will override notification But it is different it will create new every time.
		  * @params Notification : Instance of notification. 
		  */
		 mgr.notify(R.id.inbox, myNotification);


		 /**
		  * This is badge display only for Samsung devices.  
		  */
		 setBadge(getApplicationContext(), 4);
	}
	
	
	public void ActionNotification()
	{

		
		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Actions Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Actions Notification Title");
					mBuilder.setContentText("Actions Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
					
					
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(3);
					
					
					mBuilder.addAction(R.drawable.icon, "One", getPendingIntent());
					mBuilder.addAction(android.R.drawable.sym_def_app_icon, "Two", getPendingIntent());
					mBuilder.addAction(android.R.drawable.ic_menu_revert, "Three", getPendingIntent());
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.actions, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(),5);
	}
	
	
	public void ProgressBarNotification(final View view)
	{

		final NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Progress Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Progress Notification Title");
					
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(3);
					
							
					 mBuilder.setProgress(0, 0, false);     
					
					 myNotification = mBuilder.build();
					    
					    /**
					     * Notify notification 
					     * 
					     * @params notificationId : if it same it will override notification But it is different it will create new every time.
					     * @params Notification : Instance of notification. 
					     */
					 mgr.notify(R.id.progress, myNotification);
	
		
		// Start a lengthy operation in a background thread
				
		myNotificationAsyncTask =new AsyncTask<Void, Integer, Void>()
		{
			// Run on Ui thread
			@Override
			protected void onPreExecute() {
								
				super.onPreExecute();
										
				if(((Button)view) != null)
				 ((Button)view).setEnabled(false);
										   
			}
			
			
			@Override
			protected void onCancelled() {
				// TODO Auto-generated method stub
				super.onCancelled();
				
				if(((Button)view) != null)
					((Button)view).setEnabled(true);
			}
			
			

			// Run in background Thread
			@Override
			protected Void doInBackground(Void... arg0) {
				
				
				 try
				  {
				
				   
				   int incr;            
				   // Do the "lengthy" operation 20 times 
				   
						   for (incr = 0; incr <= 100; incr+=5) 
						   {   
							 //  If you call cancel(true), an interrupt will be sent to the background thread, 
							//   which may help interruptible tasks. Otherwise, you should simply make sure to check isCancelled()
							//   regularly in your doInBackground() method.
							   if (isCancelled()) 
								   break;
							   
							   
							   // Sets the progress indicator to a max value, the   
							   // current completion percentage, and "determinate" 
							   // state                 
							   mBuilder.setProgress(100, incr, false);     
							   
							   // Displays the progress bar for the first time.  
							  								
							   mBuilder.setContentText("Downloading file : "+incr+"/"+100 );
							   
										    
							    /**
							     * Notify notification 
							     * 
							     * @params notificationId : if it same it will override notification But it is different it will create new every time.
							     * @params Notification : Instance of notification. 
							     */
								mgr.notify(R.id.progress, mBuilder.build());

								
							   // Sleeps the thread, simulating an operation  
							   // that takes time               
							   
							   try 
							   {               
								   // Sleep for 5 seconds       
								   Thread.sleep(1000);     
								} catch (InterruptedException e) 
								{                 
									Log.d(TAG, "sleep failure"); 
								}         
							}            
				   
				   // When the loop is finished, updates the notification 
				   mBuilder.setContentText("Download complete")  
				   
				   // Removes the progress bar   
				   .setProgress(0,0,false);    
				   
				   	
				    /**
				     * Notify notification 
				     * 
				     * @params notificationId : if it same it will override notification But it is different it will create new every time.
				     * @params Notification : Instance of notification. 
				     */
					mgr.notify(R.id.progress, mBuilder.build());

				
				   }
					catch (Exception e) {
						// TODO: handle exception
					}
				 
				return null;
			}
			
			
			
			// Run on Ui thread
			@Override
			protected void onPostExecute(Void result) {
			
				super.onPostExecute(result);
				
				if(((Button)view) != null)
				((Button)view).setEnabled(true);
				
			}
			
		};
		
		
		// If Asynctask is not runing then execute asynctask.
		if(myNotificationAsyncTask != null && myNotificationAsyncTask.getStatus() != AsyncTask.Status.RUNNING)
		{
								
			myNotificationAsyncTask.execute();
			
		}
		
					
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(),5);
	}
	
	public void CustomRemoteViewNotifications()
	{

		
		RemoteViews mExpandedView = new RemoteViews(getPackageName(), R.layout.big_content_view);
		
		// SetTextView Text on RemoteView. 
		mExpandedView.setTextViewText(R.id.text, "Big Content View");
						
		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Big Content View Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Big Content View Notification Title");
					mBuilder.setContentText("Big Content View Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					// SetRemoteView
					mBuilder.setBigContentView(mExpandedView);
					
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(3);
					
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.big_content_view, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(),7);
	}
	
	
	public void HighPriorityNotification()
	{

		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
		            mBuilder.setPriority(PRIORITY_HIGH);
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "High Priority Notification Ticker");
					mBuilder.setContentTitle("Title: " + "High Priority Notification Title");
					mBuilder.setContentText("High Priority Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(2);
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.high_priority, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(), 2);
	}
	
	
	public void LowPriorityNotification()
	{

		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
		            mBuilder.setPriority(PRIORITY_LOW);
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Low Priority Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Low Priority Notification Title");
					mBuilder.setContentText("Low Priority Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(2);
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.low_priority, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(), 2);
	}
	
	public void MaxPriorityNotification()
	{


		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
		            mBuilder.setPriority(PRIORITY_MAX);
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Max Priority Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Max Priority Notification Title");
					mBuilder.setContentText("Max Priority Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(2);
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.max_priority, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(), 2);
	}
	
	public void MinPriorityNotification()
	{
		
		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);
		
		            mBuilder.setPriority(PRIORITY_MIN);
					mBuilder.setSmallIcon(R.drawable.icon);
					mBuilder.setTicker("Ticker: " + "Min Priority Notification Ticker");
					mBuilder.setContentTitle("Title: " + "Min Priority Notification Title");
					mBuilder.setContentText("Min Priority Notification Content Text");
					mBuilder.setContentIntent(getPendingIntent());
					
					/**
					 *  If this method pass true Notification gone away from notificationBar
					 */
					mBuilder.setAutoCancel(false);
					
					/**
					 *  This method used for when to notify time in long
					 */
					mBuilder.setWhen(System.currentTimeMillis());
		
										
					/**
					 * Set the large number at the right-hand side of the notification
					 */
					mBuilder.setNumber(2);
					
		
	    myNotification = mBuilder.build();
				
	    
	    /**
	     * Notify notification 
	     * 
	     * @params notificationId : if it same it will override notification But it is different it will create new every time.
	     * @params Notification : Instance of notification. 
	     */
		mgr.notify(R.id.min_priority, myNotification);

		
		/**
		 * This is badge display only for Samsung devices.  
		 */
		setBadge(getApplicationContext(), 2);
	}
	
	@Override
	protected void onPause() {
	
		super.onPause();
		
		// If Asynctask is runing when activity goes in background it will  cancel that Asynctask.
		if(myNotificationAsyncTask != null && myNotificationAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
		{
			myNotificationAsyncTask.cancel(true);
			
			myNotificationAsyncTask= null;
		}
	}
}
