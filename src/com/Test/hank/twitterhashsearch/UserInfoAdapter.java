package com.Test.hank.twitterhashsearch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoAdapter extends ArrayAdapter<UserInfoListItem>{

	ArrayList<UserInfoListItem> items;
	Activity context;
	
	public UserInfoAdapter(Context context, int textViewResourceId, ArrayList<UserInfoListItem> items) {
		super(context, textViewResourceId, items);
		// TODO Auto-generated constructor stub
		this.items = items;
		this.context = (Activity) context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if(v == null) {
			
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row, null);
		}
		
        UserInfoListItem uili = items.get(position);
        if (uili != null) {
        		
                TextView userName = (TextView) v.findViewById(R.id.username);
                TextView tweet = (TextView) v.findViewById(R.id.tweet);
                ImageView image = (ImageView) v.findViewById(R.id.userIcon);
                
                userName.setText(uili.getUserName());
                tweet.setText(uili.getTweet());
                image.setImageBitmap(uili.getUser_profile_image());
        }
		return v;
	}
}
