package com.Test.hank.twitterhashsearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;

public class SearchResultActivity extends ListActivity {

	private ArrayList<UserInfoListItem> Users = null;
	private ArrayAdapter<UserInfoListItem> Adapter = null;
	
	int tweetCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		Users = new ArrayList<UserInfoListItem>();
		String jsonString;
		// dummy object
		/*
		UserInfoListItem ui = new UserInfoListItem();
		ui.setTweet("Fuck it I'm hungry.");
		ui.setUser_profile_image("http://www.777icons.com/libs/db/user_profile-icon.gif");
		ui.setUserName("Hank");
		Users.add(ui);
		*/
		Bundle bundle = getIntent().getExtras();
		jsonString = bundle.getString("inJson");
		
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray twitterSearchResult =  json.getJSONArray("results");
			
			//Log.d("JSONArrayLength", twitterSearchResult.length()+"");
			
			tweetCount = twitterSearchResult.length();
			
			for(int i = 0; i < tweetCount; i++) {
				UserInfoListItem ui = new UserInfoListItem();
				JSONObject user = twitterSearchResult.getJSONObject(i);
				
				ui.setUserName(user.getString("from_user"));
				ui.setTweet(user.getString("text"));
				ui.setUser_profile_image(user.getString("profile_image_url"));
				Users.add(ui);
				
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Adapter = new UserInfoAdapter(this, R.layout.row, Users);
		setListAdapter(Adapter);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

	    	Bundle bundle = new Bundle();
	        bundle.putInt("tweetCount", tweetCount);
	        
	        Intent intent = new Intent();
	        intent.putExtras(bundle);
	    	
	        setResult(Activity.RESULT_OK, intent);
	        
	        finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

}
