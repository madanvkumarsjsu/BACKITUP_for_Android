package com.sjsu.backitup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;


public class UploadActivity extends AsyncTask<Void, Void, Void> {

	AWSCredentials credentials = null;
	Bucket buc;
	AmazonS3Client conn = null;
	List<S3ObjectSummary> list = null;
	String uploadPath;
	String strResponse;
	String strBucketName = "mybucket";
	String strUserName = "";
	TransferManager manager;
	int intervalCount = 0;
	ArrayList<String> alPath = new ArrayList<String>();
	public UploadActivity(String strPath1, String strPath2, String strPath3, String strPath4, String strPath5, String userName, int intervalCount){

		try {
			credentials = new PropertiesCredentials(AwsConsoleApp.class.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alPath.add(strPath1);
		alPath.add(strPath2);
		alPath.add(strPath3);
		alPath.add(strPath4);
		alPath.add(strPath5);
		this.intervalCount = intervalCount; 
		manager = new TransferManager(credentials);
		conn = new AmazonS3Client(credentials);
		this.strUserName = userName;
		try{
			Debug.startMethodTracing();
			//execute();
			Timer timer = new Timer();

			TimerTask tt = new TimerTask() {

				@Override
				public void run() {
					Log.i("timer#########################", "taskexecuted");
					execute();
				}
			};
			timer.scheduleAtFixedRate(tt, 0, intervalCount*60*1000);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public boolean UploadFile(String bucName, File file){
		boolean bSuccess = false;
		String fName = file.getName();
		Upload upload = manager.upload(bucName, fName, file);
		UploadResult ur = null;
		try {
			ur = upload.waitForUploadResult();
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(upload.isDone()){
			bSuccess = true;
		}
		return bSuccess;
	}
	@Override
	protected Void doInBackground(Void... params) {

		try {
			conn = new AmazonS3Client(credentials);
			String strUserBucket = (strBucketName + strUserName)
					.toLowerCase();
			if (!conn.doesBucketExist(strUserBucket)) {
				buc = conn.createBucket(strUserBucket);
				System.out.println(buc.getName());
			} else {
				buc = new Bucket(strUserBucket);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Message#####" + ex.getMessage());
		}
		try{
			File file = null;
			for(int i = 0; i<5;i++){
				String strPath = (String) alPath.get(i);
				file = new File(strPath);				

				if(file.isDirectory()){
					File[] files = file.listFiles();
					for(int j = 0;j<files.length;j++){
						if(!files[j].isDirectory()){
							boolean bsuccess = UploadFile(buc.getName(), files[j]);
							if(!bsuccess){
								strResponse = "Some files not uploaded properly";
							}
						}
					}
				}
			}
		}
		catch(Exception ex){
			Log.e("Exception",ex.toString());
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void Result) {
		//			if (dialog.isShowing()) {
		//				dialog.dismiss();
		//			}
	}

	@Override
	protected void onPreExecute() {
		//			dialog.setMessage("Uploading Content, please wait.");
		//			dialog.show();
	}

	@Override
	protected void onProgressUpdate(Void... values) {

	}
}
