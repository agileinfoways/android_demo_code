package com.agileinfoways.notification;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.agileinfoways.notification.utils.NotificationCompat2;

public class NotificationLikeWhatsApp {
/*
	 NotificationManager mgr;
	   
	   Notification myNotification =null;
		
	   int NOTIFICATION_COUNT=1;
	   
	   ArrayList<String> mNotificationItems = new ArrayList<String>();
	   
	   
	
	public NotificationLikeWhatsApp()
	{

	}
	
	
	public void notificationLikeWhatsApp()
	{

		mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		String messageWhatsApp = "Me : Hello Hi "+NOTIFICATION_COUNT;


		NotificationCompat2.Builder mBuilder= new NotificationCompat2.Builder(NotificationMainActivity.this);				
					
		           
					mBuilder.setContentIntent(getPendingIntent(bundle, uniqueInteger));
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
	
	

    private PendingIntent getPendingIntent(Bundle bundle, int rc) {
    	    	
        Intent notificationIntent = new Intent(mContext, XMPPMainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtras(bundle);

		int requestID = (int) System.currentTimeMillis();		
        
        return PendingIntent.getActivity(this, requestID, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }*/
}
