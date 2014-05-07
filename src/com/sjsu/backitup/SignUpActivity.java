package com.sjsu.backitup;

import java.sql.Connection;
import java.sql.Statement;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends ActionBarActivity {

	DataBaseConnection dbConn;
	Connection conn;
	Statement stmt;

	String username;
	String password1;
	String password2;
	String query;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Debug.startMethodTracing("Testing_SignUP");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_sign_up,
					container, false);
			return rootView;
		}
	}
	public void launchHome(View view){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
	//onClick Method for Sign Up
	public void signUp(View view){
		new ConnectToDatabaseTask(this).execute();
	}
	public void feedback(String strStatus){
		TextView hiddenText = (TextView) findViewById(R.id.hidden);
		Debug.stopMethodTracing();
		if("Success".equalsIgnoreCase(strStatus)){
			//Intent intent = new Intent(this, LoginActivity.class);
			SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
			editor.putString("username", "testUser");
			editor.commit();
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		}
		else{
			hiddenText.setText(strStatus);
		}
		
	}
	class ConnectToDatabaseTask extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;
		boolean bSuccess = false;
		ConnectToDatabaseTask(SignUpActivity activity){
			dialog = new ProgressDialog(activity);	
		}

		@Override
		protected void onPostExecute(Void result) {

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if(bSuccess){
				feedback("Success");
			}
			else{
				feedback("Login Failed. Try again...");
			}
			
		}
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Signing up, please wait.");
			dialog.show();
		}


		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			dbConn = new DataBaseConnection();
			conn = dbConn.getDataBaseConnection();
			EditText usernameEntry = (EditText) findViewById(R.id.username);
			username = usernameEntry.getText().toString();

			EditText password1Entry = (EditText) findViewById(R.id.password1);
			password1 = password1Entry.getText().toString();

			EditText password2Entry = (EditText) findViewById(R.id.password2);
			password2 = password2Entry.getText().toString();

			//Check That the two Typed Passwords Match
			if(password1.equals(password2)){
				query = "INSERT INTO USER (USERNAME, PASSWORD) VALUES(\'"+username+"\',\'"+password1+"\')";
				try {
					if(conn != null){
						stmt = conn.createStatement();
						stmt.execute(query);
						bSuccess = true;
					}
					
				} 
				catch (Exception e) {
					System.out.println("Error in execute query::"+e.getMessage());
					e.printStackTrace();
				}
			}
			return null;
		}
	}

}
