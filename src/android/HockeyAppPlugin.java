package org.nypr.cordova.hockeyappplugin;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.FeedbackManager;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.lang.RuntimeException;

import android.util.Log;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class HockeyAppPlugin extends CordovaPlugin {
	protected static final String LOG_TAG = "HockeyAppPlugin";
	protected static String hockeyAppId = "";
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		try {
			ApplicationInfo app = cordova.getActivity().getPackageManager().getApplicationInfo(cordova.getActivity().getPackageName(),PackageManager.GET_META_DATA);
	        Bundle bundle = app.metaData;
	    	hockeyAppId = bundle.getString("HOCKEY_APP_KEY");
	    }	catch (NameNotFoundException e) {
	    		Log.e(LOG_TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
	    }
		super.initialize(cordova, webView);
	  	_checkForCrashes();
	  	//_checkForUpdates();
		Log.d(LOG_TAG, "HockeyApp Plugin initialized");
	}
	
	@Override
	public void onResume(boolean multitasking) {
		Log.d(LOG_TAG, "HockeyApp Plugin resuming");
	  //_checkForUpdates();
	  _checkForCrashes();
		super.onResume(multitasking);
	}

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    boolean ret=true;
    if(action.equalsIgnoreCase("forcecrash")){
    	cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
  					Calendar c = Calendar.getInstance();
      				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      				throw new RuntimeException("Test crash at " + df.format(c.getTime())); 
                    //callbackContext.success(); // Thread-safe.
                }
            });
    } else if (action.equals("feedback")) {
    	this.feedback();
    	ret=true;
    } else if(action.equals("start")) {
    	//Not required for Android
    	ret=true;
    } else{
      callbackContext.error(LOG_TAG + " error: invalid action (" + action + ")");
      ret=false;
    }
    return ret;
  }
		
	@Override
	public void onDestroy() {
		Log.d(LOG_TAG, "HockeyApp Plugin destroying");
		super.onDestroy();
	}

	@Override
	public void onReset() {
		Log.d(LOG_TAG, "HockeyApp Plugin onReset--WebView has navigated to new page or refreshed.");
		super.onReset();
	}

	private void feedback() {
		//String hockeyAppId="9bc60ec1849d56499f209e326b3544f4";
  		FeedbackManager.register(cordova.getActivity(), hockeyAppId, null);
  		FeedbackManager.showFeedbackActivity(cordova.getActivity());
	}
	
	protected void _checkForCrashes() {
		Log.d(LOG_TAG, "HockeyApp Plugin checking for crashes");
		//String hockeyAppId="9bc60ec1849d56499f209e326b3544f4"; // replaced by build script. better to pull from a a config file?
		if(hockeyAppId!=null && !hockeyAppId.equals("") && !hockeyAppId.contains("hockeyAppId")){
			CrashManager.register(cordova.getActivity(), hockeyAppId);
		}
	}

	protected void _checkForUpdates() {
		// Remove this for store builds!
		//__HOCKEY_APP_UPDATE_ACTIVE_START__
		Log.d(LOG_TAG, "HockeyApp Plugin checking for updates");
		//String hockeyAppId="9bc60ec1849d56499f209e326b3544f4";
		if(hockeyAppId!=null && !hockeyAppId.equals("") && !hockeyAppId.contains("hockeyAppId")){		
			UpdateManager.register(cordova.getActivity(), hockeyAppId);
		}
		//__HOCKEY_APP_UPDATE_ACTIVE_END__
	}
}
