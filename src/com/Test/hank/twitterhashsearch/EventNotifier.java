package com.Test.hank.twitterhashsearch;

public class EventNotifier {
	private CallbackEvent cbe;
	private boolean flag;
	
	public EventNotifier(CallbackEvent event) {
		cbe = event;
		flag = false;
	}
	
	public void alert() {
		if(flag) {
			cbe.callbackEvent();
		}
	}
}
