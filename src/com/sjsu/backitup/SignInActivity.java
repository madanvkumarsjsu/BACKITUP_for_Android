package com.sjsu.backitup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class SignInActivity extends ActionBarActivity {

	public EditText  username=null;
	public EditText  password=null;
	public String usrnm, psswd;
	public String usrnmLogged, psswdLogged;
	boolean resultMatch= false;
	boolean loggedIn= false;
	Editor toEdit;

	DataBaseConnection dbc;
	Connection connLogin;   
	Statement stmt;
	SharedPreferences sh_Pref;

	boolean flag_on_Create = false;
	boolean flag_on_Resume = false;
	boolean flag_on_Start = false;
	private static final String MY_PREFERENCES = "Login Credentials";  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}

		sh_Pref = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE); 
		boolean secondCreate;
		secondCreate = sh_Pref.getBoolean("first", loggedIn);
		if (secondCreate == true)
		{
			flag_on_Create = true;
			Intent intent = new Intent(getApplicationContext(), ListContentsActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_sign_in,
					container, false);
			return rootView;
		}
	}

	public void login(View view){

		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);  	
		usrnm = username.getText().toString();
		psswd = password.getText().toString();
		new ConnectToDatabaseTask().execute();
	}

	public void sharedPrefernces() 
	{ 
		sh_Pref = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE); 
		toEdit = sh_Pref.edit(); 
		toEdit.putString("usrnmLogged", usrnmLogged); 
		toEdit.putString("psswdLogged", psswdLogged); 
		toEdit.putBoolean("first",loggedIn);
		toEdit.commit(); 

	}

	public void logged()
	{
		loggedIn= true;
		Toast.makeText(getApplicationContext(), "Redirecting...", 
				Toast.LENGTH_SHORT).show();
		sharedPrefernces();
		Intent intent = new Intent(getApplicationContext(), ListContentsActivity.class);
		startActivity(intent);
	}

	public void notLogged()
	{

		Toast.makeText(getApplicationContext(), "Wrong Credentials",
				Toast.LENGTH_SHORT).show();

	}

	class ConnectToDatabaseTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			if (resultMatch == true)  	 			
				logged();
			else
				notLogged();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			dbc = new DataBaseConnection();

			connLogin = dbc.getDataBaseConnection();


			if(usrnm != null && psswd != null){
				String query = "select * from USER where USERNAME= '"+usrnm+"' AND PASSWORD= '"+psswd+"'";
				try {
					if(connLogin != null){
						stmt = connLogin.createStatement();
						stmt.executeQuery(query);
						ResultSet rs = stmt.executeQuery(query);

						if (rs.next()){
							usrnmLogged = rs.getString("USERNAME");
							psswdLogged = rs.getString("PASSWORD");
							resultMatch = true;
						}		
					}
				}
				catch (Exception e) {
					System.out.println("Error in execute query::"+e.getMessage());
					e.printStackTrace();
				}	}		 
			return null;
		}
	}

}
