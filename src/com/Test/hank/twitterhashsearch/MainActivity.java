package com.Test.hank.twitterhashsearch;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        
        // check connectivity
        if(isOnline()) {
        	searchButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					progressbar.setVisibility(View.VISIBLE);
					//new TwitterResultLoader(MainActivity.this, SearchResultActivity.class).execute("http://search.twitter.com/search.json?q=" + searchText.getText());
					
					if(!searchText.getText().toString().contains(" ") && searchText.getText().toString().compareTo("") != 0) {
						new TwitterResultLoader(MainActivity.this, 
								SearchResultActivity.class).execute("http://search.twitter.com/search.json?q="+searchString(searchText.getText().toString()));
					}
					else {
						
					}
				}
	        	
	        });
        }
        else {
        	tweetCountText.setText("No Internet Connection.");
        }
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO handle here. 
        progressbar.setVisibility(View.INVISIBLE);
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
    
    // check connectivity
    private boolean isOnline() {
    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
    		return true;
    	else
    		return false;
    }
    
    // hashtag analysis
    private String searchString(String str) {
    	
    	// if str contains no hashtag
    	if(!str.contains("#")) {
    		return str;
    	}
    	// if str contains hashtag(s)
    	
    	return str.replaceAll("#", "%23");
    }
}
