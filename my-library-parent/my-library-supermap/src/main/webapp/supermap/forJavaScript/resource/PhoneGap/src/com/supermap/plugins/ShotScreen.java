package com.supermap.plugins;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import com.phonegap.api.Plugin;
import com.supermap.RequestControl;

public class ShotScreen extends Plugin {

	public PluginResult execute(String action, JSONArray data, String callbackId) {
		PluginResult result = null;
		if ("action".equals(action)) {
			try {
	    		String n = data.getString(0);
				String w = data.getString(1);
				String h = data.getString(2);
				int width = Integer.parseInt(w);
				int height = Integer.parseInt(h);
				System.out.println("shotscreen1");
	        	boolean isSucc = ShotScreen.shoot(n,width,height);
	        	if(isSucc)result = new PluginResult(org.apache.cordova.api.PluginResult.Status.OK, new JSONObject());
	        	else result = new PluginResult(org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			} catch (JSONException e) {
				e.printStackTrace();
				result = new PluginResult(org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		}
		return result;
	}
	
	 private static final String TAG="shotscreen"; 
	    public static Activity act = RequestControl.act;
	    public static String sdcardpath = null;
		@SuppressWarnings("unused") 
	    private static Bitmap takeScreenShot(Activity activity,int w,int h){
	    	Bitmap bitmap2 = null;
	    	try{
	    		View view =activity.getWindow().getDecorView(); 
	            view.setDrawingCacheEnabled(true); 
	            view.buildDrawingCache(); 
	            Bitmap bitmap = view.getDrawingCache(); 
	            bitmap2 = Bitmap.createBitmap(bitmap,0,0, w-5, h-5);
	            view.destroyDrawingCache();
	    	}
	    	catch(Exception e){
	    		System.out.println("a error happened at ShotScreen.takeScreenShot: "+e.getMessage());
	    	}
	        
	        return bitmap2; 
	    } 
	      
	    @SuppressWarnings("unused") 
	    private static boolean savePic(Bitmap bitmap,String filename){
	    	if(bitmap==null) return false;
	        FileOutputStream fileOutputStream = null; 
	        boolean isSucc = false;
	        try { 
	            fileOutputStream = new FileOutputStream(filename); 
	            if (fileOutputStream != null) { 
	                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream); 
	                fileOutputStream.flush(); 
	                fileOutputStream.close();
	                isSucc = true;
	            } 
	        } 
	        catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	        return isSucc;
	    } 
	      
	    public static boolean shoot(String n,int w,int h){ 
	    	//Activity a = act;
	    	boolean isSucc = false;
	    	if(sdcardpath==null){
	    		File SDCardRoot = Environment.getExternalStorageDirectory();
	    		String path = SDCardRoot.getAbsolutePath();
	    		sdcardpath = (new StringBuilder(String.valueOf(path))).append("/SuperMap/").toString();
	    	}
	    	Bitmap map = ShotScreen.takeScreenShot(act,w,h);
	    	isSucc = ShotScreen.savePic(map, sdcardpath+n);
	    	return isSucc;
	    }
}
