package com.sjsu.backitup;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class NotificationActivity extends ActionBarActivity {

	final int ID = 1;
	NotificationManager notificationmanager;
	Intent intent; 
	int icon;
	String tickerText;
	long when;
	boolean flag = false;
	Button button, button2;
	Notification notification;
	static final String TAG= "Tag";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		button = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		notificationmanager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		intent=new Intent(this, NotificationActivity.class);
		icon=R.drawable.ic_launcher;
		tickerText="Back it up";
		when=System.currentTimeMillis();
		notification=new Notification(icon, tickerText, when);

		PendingIntent pendingIntent=PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
		notification.setLatestEventInfo(NotificationActivity.this, "Back it up!", "upload..", pendingIntent);
		notificationmanager.notify(ID, notification);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_notification,
					container, false);
			return rootView;
		}
	}

	public void tenSeconds(View view){

		TimerTask task= new TimerTask(){
			@Override
			public void run(){


				PendingIntent pendingIntent=PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

				String contentTitle = "uploading";
				String contentText="Click for getting info";

				notification.setLatestEventInfo(NotificationActivity.this, contentTitle, contentText, pendingIntent);
				notificationmanager.notify(ID, notification);

			}

		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	public void service(View view){
		Intent intent = new Intent(NotificationActivity.this, Notifservice.class);
		startService(intent);
	}

}
