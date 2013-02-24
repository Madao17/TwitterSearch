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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button searchButton;
	EditText searchText;
	ProgressBar progressbar;
	TextView tweetCountText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        searchButton = (Button) findViewById(R.id.SearchButton);
        searchText = (EditText) findViewById(R.id.hashtagTextField);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        tweetCountText = (TextView) findViewById(R.id.tweetCount);
        
        progressbar.setVisibility(View.INVISIBLE);
        
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressbar.setVisibility(View.VISIBLE);
				new TwitterResultLoader().execute("http://search.twitter.com/search.json?q=" + searchText.getText());
				
				
			}
        	
        });
    }
    
    public class TwitterResultLoader extends AsyncTask<String, Void, String> {

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
			Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("inJson", result);
			
			intent.putExtras(bundle);
			
			//progressbar.setVisibility(View.INVISIBLE);
			startActivityForResult(intent, 0);
			progressbar.setVisibility(View.INVISIBLE);
	    }
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO handle here. 
        
        //Log.d("requestCode", requestCode+"");
        
        if(requestCode == 0) {
        	if(resultCode == RESULT_OK) {
        		
        		Bundle bundle = data.getExtras();
        		int tweetCount = bundle.getInt("tweetCount");
        		
        		Log.d("tweetCount", tweetCount+"");
        		
        		tweetCountText.setText(tweetCount+"");
        	}
        }
    }
}
