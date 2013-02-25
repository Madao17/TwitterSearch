package com.Test.hank.twitterhashsearch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity {

	ImageView user_profile_image;
	TextView Username, Tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		Bundle bundle = this.getIntent().getExtras();
		
		Username = (TextView) findViewById(R.id.UserInfoUsername);
		Tweet = (TextView) findViewById(R.id.UserInfoTweet);
		user_profile_image = (ImageView) findViewById(R.id.UserInfoProfileImage);
		
		// get image
		UserInfoListItem uii = new UserInfoListItem();
		uii.setUser_profile_image(bundle.getString("profile_img"));
		
		Username.setText(bundle.getString("username"));
		Tweet.setText(bundle.getString("tweet"));
		user_profile_image.setImageBitmap(uii.getUser_profile_image());
	}


}
