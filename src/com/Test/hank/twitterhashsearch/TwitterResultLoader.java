package com.Test.hank.twitterhashsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TwitterResultLoader extends AsyncTask<String, Void, String> implements CallbackEvent{

	Activity parent;
	Class<SearchResultActivity> child;
	String result = "";
	
	public TwitterResultLoader(Activity parent, Class<SearchResultActivity> child) {
		this.parent = parent;
		this.child = child;
	}

	@Override
	protected String doInBackground(String... url) {
		
		URL twitterURL;
		BufferedReader in = null;
		String JsonString = "";
		
		try {
			twitterURL = new URL(url[0]);
			HttpURLConnection urlConnect = (HttpURLConnection) twitterURL.openConnection();
			in = new BufferedReader(new InputStreamReader(urlConnect.getInputStream()));
			JsonString = in.readLine();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JsonString;
	}
	
	protected void onPostExecute(String result) {
		// start new activity
		/*
		Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("inJson", result);
		
		intent.putExtras(bundle);
		
		//progressbar.setVisibility(View.INVISIBLE);
		startActivityForResult(intent, 0);
		progressbar.setVisibility(View.INVISIBLE);
		*/
		this.result = result;
		callbackEvent();
    }

	@Override
	public void callbackEvent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(parent, child);
		Bundle bundle = new Bundle();
		bundle.putString("inJson", result);
		intent.putExtras(bundle);
		parent.startActivityForResult(intent, 0);
	}
	
}