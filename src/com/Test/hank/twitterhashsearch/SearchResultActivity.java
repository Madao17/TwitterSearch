package com.Test.hank.twitterhashsearch;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultActivity extends ListActivity {

	private ArrayList<UserInfoListItem> Users = null;
	private ArrayAdapter<UserInfoListItem> Adapter = null;
	String jsonString;
	int tweetCount = 0, currentTweet = 0;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		Users = new ArrayList<UserInfoListItem>();
		Adapter = new UserInfoAdapter(SearchResultActivity.this, R.layout.row, Users);
		
		// dummy object
		/*
		UserInfoListItem ui = new UserInfoListItem();
		ui.setTweet("Fuck it I'm hungry.");
		ui.setUser_profile_image("http://www.777icons.com/libs/db/user_profile-icon.gif");
		ui.setUserName("Hank");
		Users.add(ui);
		*/
		
		bundle = getIntent().getExtras();
		jsonString = bundle.getString("inJson");
		
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray twitterSearchResult =  json.getJSONArray("results");
			
			//Log.d("JSONArrayLength", twitterSearchResult.length()+"");
			
			tweetCount = twitterSearchResult.length();
			/*
			for(int i = 0; i < tweetCount; i++) {
				UserInfoListItem ui = new UserInfoListItem();
				JSONObject user = twitterSearchResult.getJSONObject(i);
				
				ui.setUserName(user.getString("from_user"));
				ui.setTweet(user.getString("text"));
				ui.setUser_profile_image(user.getString("profile_image_url"));
				Users.add(ui);
			}
			*/
			new TwitterResult().execute(twitterSearchResult);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
	
	public class TwitterResult extends AsyncTask<JSONArray, Void, ArrayList<UserInfoListItem>> {

		@Override
		protected ArrayList<UserInfoListItem> doInBackground(JSONArray... params) {
			// TODO Auto-generated method stub
			try {
				//Log.d("tweetCount", tweetCount+"");
				//Log.d("params", params.length+"");
				if(params.length == 1) {
					for(int i = 0; i < tweetCount; i++) {
						UserInfoListItem ui = new UserInfoListItem();
						JSONObject user = params[0].getJSONObject(i);
						
						ui.setUserName(user.getString("from_user"));
						ui.setTweet(user.getString("text"));
						ui.setUser_profile_image(user.getString("profile_image_url"));
						Users.add(ui);
					}
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			return Users;
		}

		@Override
		protected void onPostExecute(ArrayList<UserInfoListItem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Adapter = new UserInfoAdapter(SearchResultActivity.this, R.layout.row, result);
			setListAdapter(Adapter);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//Log.d("id", id+"");
		//Log.d("position", position+"");
		
		UserInfoListItem uii =(UserInfoListItem) l.getItemAtPosition(position);
		
		Bundle bundle = new Bundle();
		bundle.putString("username", uii.getUserName());
		bundle.putString("tweet", uii.getTweet());
		bundle.putString("profile_img", uii.getUser_profile_image_url());
		
		Intent intent = new Intent(this, UserInfoActivity.class);
		intent.putExtras(bundle);
		
		startActivity(intent);
	}
	
	

}
