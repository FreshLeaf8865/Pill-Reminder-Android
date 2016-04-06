package com.medidoz.medidoz_pro_english;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.DroidGap;
import org.apache.cordova.IceCreamCordovaWebViewClient;
import org.apache.cordova.api.CordovaInterface;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.ads.*;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class MyPhoneGapActivity extends DroidGap {
	
//	private Handler mHandler = new Handler();
//	private AdView adView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.loadUrl("file:///android_asset/www/index.html");
		
		//doAdMob();
		
//		mHandler.postDelayed(new Runnable() {
//            public void run() {
//                doAdMob();
//            }
//        }, 5000);     
		
//		 LinearLayout layout = super.root;
//	     AdView adView = new AdView(this, AdSize.BANNER, "a150f91868e87bb");
//	     layout.addView(adView);
//	     layout.setHorizontalGravity(android.view.Gravity.CENTER_HORIZONTAL);
//	     AdRequest request = new AdRequest();
//	     //request.addTestDevice(AdRequest.TEST_EMULATOR);
//	     adView.loadAd(request);
	}
	
//	private void doAdMob() {
//        // Create the adView
//        adView = new AdView(this, AdSize.BANNER, "a150f91868e87bb");
//        // Lookup your LinearLayout - get the super.root
//        LinearLayout layout = super.root;
//        // Add the adView to it
//        layout.addView(adView);
//        // This centers the ads in landscape mode.        
//        layout.setHorizontalGravity(android.view.Gravity.CENTER_HORIZONTAL);
//        // Initiate a generic request to load it with an ad
//        AdRequest request = new AdRequest();
//        //request.addTestDevice(AdRequest.TEST_EMULATOR);
//        // and finally... 
//        adView.loadAd(request);                    
//    }
	
	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    GoogleAnalyticsTracker tracker = com.google.android.apps.analytics.GoogleAnalyticsTracker.getInstance();
	    tracker.dispatch();
	    tracker.stopSession();
	}
	
	@Override
	public void init() {
	    Log.e(TAG, "init()");
	    CordovaWebView webView = new CordovaWebView(MyPhoneGapActivity.this);
	    CordovaWebViewClient webViewClient;
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
	    {
	        webViewClient = new CordovaWebViewClient(this, webView);
	    }
	    else
	    {
	        webViewClient = new IceCreamCordovaWebViewClient(this, webView);
	    }
	    this.init(webView, webViewClient, new MyCordovaChromeClient(this, webView));
	}

	private class MyCordovaChromeClient extends CordovaChromeClient{
	    private CordovaInterface cordova;
	    public MyCordovaChromeClient(CordovaInterface ctx, CordovaWebView app) {
	        super(ctx, app);
	        this.cordova = ctx;
	    }


	    @Override
	    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
	        if(cordova.getActivity().isFinishing()){
	            Log.w(TAG, "Trying to alert while activity is finishing!! -> ignore");
	            result.cancel();
	            return true;
	        }

	        return super.onJsAlert(view, url, message, result);
	    }
	}
}
