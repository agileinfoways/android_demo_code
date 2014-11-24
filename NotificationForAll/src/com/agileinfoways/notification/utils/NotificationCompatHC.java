package com.agileinfoways.notification.utils;

import android.app.Notification;

import static android.app.Notification.FLAG_AUTO_CANCEL;
import static android.app.Notification.FLAG_ONGOING_EVENT;
import static android.app.Notification.FLAG_ONLY_ALERT_ONCE;

class NotificationCompatHC implements NotificationCompat2.NotificationCompatImpl {
    static Notification.Builder createBuilder(NotificationCompat2.Builder b) {
        final Notification n = b.mNotification;
        return new Notification.Builder(b.mContext)
                .setWhen(n.when)
                .setSmallIcon(n.icon, n.iconLevel)
                .setContent(n.contentView)
                .setTicker(n.tickerText, b.mTickerView)
                .setSound(n.sound, n.audioStreamType)
                .setVibrate(n.vibrate)
                .setLights(n.ledARGB, n.ledOnMS, n.ledOffMS)
                .setOngoing((n.flags & FLAG_ONGOING_EVENT) != 0)
                .setOnlyAlertOnce((n.flags & FLAG_ONLY_ALERT_ONCE) != 0)
                .setAutoCancel((n.flags & FLAG_AUTO_CANCEL) != 0)
                .setDefaults(n.defaults)
                .setContentTitle(b.mContentTitle)
                .setContentText(b.mContentText)
                .setContentInfo(b.mContentInfo)
                .setContentIntent(b.mContentIntent)
                .setDeleteIntent(n.deleteIntent)
                .setFullScreenIntent(b.mFullScreenIntent,
                    (n.flags & Notification.FLAG_HIGH_PRIORITY) != 0)
                .setLargeIcon(b.mLargeIcon)
                .setNumber(b.mNumber);
    }

    public Notification build(NotificationCompat2.Builder b) {
        return createBuilder(b).getNotification();
    }
}
