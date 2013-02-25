package com.Test.hank.twitterhashsearch;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class UserInfoListItem {
	private String userName, tweet, user_profile_image_url;
	private Bitmap user_profile_image;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public Bitmap getUser_profile_image() {
		return user_profile_image;
	}
	public void setUser_profile_image(String user_profile_image_url) {
		//this.user_profile_image = user_profile_image;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		setUser_profile_image_url(user_profile_image_url);
		user_profile_image = LoadImage(user_profile_image_url, bmOptions);
	}
	
	private Bitmap LoadImage(String URL, BitmapFactory.Options options) {       
	    Bitmap bitmap = null;
	    InputStream in = null;       
	    try {
	        in = OpenHttpConnection(URL);
	        bitmap = BitmapFactory.decodeStream(in, null, options);
	        in.close();
	    } catch (IOException e1) {}
	    
	    return bitmap;               
    }
	
	private InputStream OpenHttpConnection(String strURL) throws IOException {
		 InputStream inputStream = null;
		 URL url = new URL(strURL);
		 URLConnection conn = url.openConnection();

		 try{
			 HttpURLConnection httpConn = (HttpURLConnection)conn;
			 httpConn.setRequestMethod("GET");
			 httpConn.connect();

			 if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				 inputStream = httpConn.getInputStream();
			 }
		 }
		 catch (Exception ex){}
		 
		 return inputStream;
	}
	
	public String getUser_profile_image_url() {
		return user_profile_image_url;
	}
	
	public void setUser_profile_image_url(String user_profile_image_url) {
		this.user_profile_image_url = user_profile_image_url;
	}
	
}
