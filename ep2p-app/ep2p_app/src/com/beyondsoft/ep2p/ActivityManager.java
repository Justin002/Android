package com.beyondsoft.ep2p;

import java.util.Stack;

import android.app.Activity;

/**
 * Description: 管理Activity
 * 
 * @author Ivan.Lu
 */
public class ActivityManager {
	private static ActivityManager instance;
	private Stack<Activity> activityStack;
	private Stack<Activity> appActivityStack;
	private ActivityManager() {
		
	}

	public static ActivityManager getInstance() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	public Activity getCurrentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	public void pushActivity2(Activity activity) {
		if (appActivityStack == null) {
			appActivityStack = new Stack<Activity>();
		}
		appActivityStack.add(activity);
	}

	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	public void popAllActivity() {
		if (activityStack != null) {
			while (activityStack.size() > 0) {
				Activity activity = activityStack.lastElement();
				if (activity == null)
					break;
				activity.finish();
				activityStack.remove(activity);
				activity = null;
			}
		}
	}
	
	public void exitApp() {
		if (appActivityStack != null) {
			while (appActivityStack.size() > 0) {
				Activity activity = appActivityStack.lastElement();
				if (activity == null)
					break;
				activity.finish();
				appActivityStack.remove(activity);
				activity = null;
			}
		}
	}
}
