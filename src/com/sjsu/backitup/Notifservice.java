package com.sjsu.backitup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Notifservice extends Service {

	private static final int ID = 123;

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
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
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Toast.makeText(getApplicationContext(), "Service started...", 
			      Toast.LENGTH_SHORT).show();
		NotificationManager notificationmanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	    Intent notificationIntent=new Intent(this, NotificationActivity.class);
	    int icon = R.drawable.ic_launcher;
	    String tickerText = "Back it up";
	    long when = System.currentTimeMillis();
	   Notification notification = new Notification(icon, tickerText, when);
	  
	   PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, notificationIntent, 0);
	   notification.setLatestEventInfo(this, "Back it up!", "Service notification", pendingIntent);
	   notificationmanager.notify(ID, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
