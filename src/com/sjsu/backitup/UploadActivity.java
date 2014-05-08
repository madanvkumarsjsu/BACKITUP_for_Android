package com.sjsu.backitup;

import java.io.File;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;

import android.os.AsyncTask;
import android.util.Log;


public class UploadActivity extends AsyncTask<Void, Void, Void> {

	AWSCredentials credentials = null;
	Bucket buc;
	AmazonS3Client conn = null;
	List<S3ObjectSummary> list = null;
	String uploadPath;
	String strResponse;
	TransferManager manager;
	public UploadActivity(String path){
		manager = new TransferManager(credentials);
		conn = new AmazonS3Client(credentials);
		uploadPath = path;
		//dialog = new ProgressDialog(getApplicationContext());
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
		//System.out.println(ur.getBucketName());
		//System.out.println(upload.isDone());
		if(upload.isDone()){
			bSuccess = true;
		}
		return bSuccess;
	}
	@Override
	protected Void doInBackground(Void... params) {

		try{
			File file = new File(uploadPath);
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(int i = 0;i<file.length();i++){
					boolean bsuccess = UploadFile(buc.getName(), files[i]);
					if(!bsuccess){
						strResponse = "Some files not uploaded properly";
					}
				}
			}
			//     Download myDownload = manager.download(strUserBucket,"/Downloads/sampletest.txt", file);
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
