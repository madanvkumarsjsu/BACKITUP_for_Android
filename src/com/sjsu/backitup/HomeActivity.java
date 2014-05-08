package com.sjsu.backitup;

import com.sjsu.backitup.util.DirectoryChooser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class HomeActivity extends ActionBarActivity {

	int count;
	String m_chosenDir = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Debug.startMethodTracing("Testing_home");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		count = 2;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		Spinner spinner = (Spinner) findViewById(R.id.interval_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.interval_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
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
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}
	//onClick() Method for First Browse Button
	public void addDirectory1(View view){
		control(1);
	}

	//onClick() Method for Second Browse Button
	public void addDirectory2(View view){
		control(2);
	}

	//onClick() Method for Third Browse Button
	public void addDirectory3(View view){
		control(3);
	}

	//onClick() Method for Fourth Browse Button
	public void addDirectory4(View view){
		control(4);
	}

	//onClick() Method for Fifth Browse Button
	public void addDirectory5(View view){
		control(5);
	}

	//onClick() Method for add Directory Widgets Button
	public void addDirectory(View view){
		if(count <= 5){
			switch(count){
			case 2:{
				LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout2);
				layout.setVisibility(View.VISIBLE);
				break;
			}
			case 3:{
				LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout3);
				layout.setVisibility(View.VISIBLE);
				break;
			}
			case 4:{
				LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout4);
				layout.setVisibility(View.VISIBLE);
				break;
			}
			case 5:{
				LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout5);
				layout.setVisibility(View.VISIBLE);
				break;
			}
			}
		}
		else{
			Toast.makeText(
					HomeActivity.this, "Maximum Number of Directories Chosen!", Toast.LENGTH_LONG).show();
		}

		count++;
	}
	public void control(final int pathNo){
		boolean m_newFolderEnabled = true;
		// Create DirectoryChooser and register a callback 
		DirectoryChooser DirectoryChooser = 
				new DirectoryChooser(HomeActivity.this, 
						new DirectoryChooser.ChosenDirectoryListener() 
				{
					@Override
					public void onChosenDir(String chosenDir) 
					{
						m_chosenDir = chosenDir;
						Toast.makeText(
								HomeActivity.this, "Chosen directory: " + 
										chosenDir, Toast.LENGTH_LONG).show();

						switch(pathNo){
						case 1:{
							EditText filePath = (EditText) findViewById(R.id.edit_path1);
							filePath.setText(chosenDir);
							break;
						}
						case 2:{
							EditText filePath = (EditText) findViewById(R.id.edit_path2);
							filePath.setText(chosenDir);
							break;
						}
						case 3:{
							EditText filePath = (EditText) findViewById(R.id.edit_path3);
							filePath.setText(chosenDir);
							break;
						}
						case 4:{
							EditText filePath = (EditText) findViewById(R.id.edit_path4);
							filePath.setText(chosenDir);
							break;
						}
						case 5:{
							EditText filePath = (EditText) findViewById(R.id.edit_path5);
							filePath.setText(chosenDir);
							break;
						}
						}
					}
				}); 
		// Toggle new folder button enabling
		DirectoryChooser.setNewFolderEnabled(m_newFolderEnabled);
		// Load directory chooser dialog for initial 'm_chosenDir' directory.
		// The registered callback will be called upon final directory selection.
		DirectoryChooser.chooseDirectory();
		m_newFolderEnabled = ! m_newFolderEnabled;

	}
	public void savePreferences(View view){
		Spinner intervalSpinner = (Spinner) findViewById(R.id.interval_spinner);
		EditText filePath1 = (EditText) findViewById(R.id.edit_path1);
		EditText filePath2 = (EditText) findViewById(R.id.edit_path2);
		EditText filePath3 = (EditText) findViewById(R.id.edit_path3);
		EditText filePath4 = (EditText) findViewById(R.id.edit_path4);
		EditText filePath5 = (EditText) findViewById(R.id.edit_path5);
		String interval = intervalSpinner.getSelectedItem().toString();
		String strPath1 = filePath1.getText().toString();
		String strPath2 = filePath2.getText().toString();
		String strPath3 = filePath3.getText().toString();
		String strPath4 = filePath4.getText().toString();
		String strPath5 = filePath5.getText().toString();

		SharedPreferences.Editor editor = getSharedPreferences("BACKITUP", MODE_PRIVATE).edit();
		editor.putString("interval", interval);
		editor.putString("path1", strPath1);
		editor.putString("path2", strPath2);
		editor.putString("path3", strPath3);
		editor.putString("path4", strPath4);
		editor.putString("path5", strPath5);
		editor.commit();

		Intent intent = new Intent(this, ListContentsActivity.class);
		startActivity(intent);

		UploadActivity uploadActivity; 
		Debug.startMethodTracing();

		/*while(true){
			for(int i = 0; i <5; i++){
				if(!"".equalsIgnoreCase("strPath"+i)){
					uploadActivity = new UploadActivity("strPath"+i);
					uploadActivity.execute();
				}
			}
			try {
				Thread.sleep(Long.parseLong(interval));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}

}
