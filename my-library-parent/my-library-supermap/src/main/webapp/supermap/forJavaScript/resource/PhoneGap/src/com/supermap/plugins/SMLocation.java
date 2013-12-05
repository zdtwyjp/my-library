package com.supermap.plugins;


import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;



public class SMLocation extends Plugin
{
	String errorString = ""; 
    public SMLocation()
    {
    }

    public PluginResult execute(String action, JSONArray data, String callbackId) 
    {
        PluginResult result = null;
        if("Location".equals(action)){
        	try
            {
        		JSONObject fileInfo = new JSONObject();
                int TimeOut = Integer.parseInt(data.getString(0));
                fileInfo = locationInfo(TimeOut);                	
                result = new PluginResult(org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
            }
            catch(Exception e)
            {
            	e.printStackTrace();
                result = new PluginResult(org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
            }  
        }
        return result;
    }
    
    private String errorFun(String str){
    	if(errorString == ""){
    		errorString = errorString + str;
    	}else{
    		errorString = errorString + ";" + str;
    	}
    	return errorString;
    }
    
    public JSONObject locationInfo (int time) throws Exception{
    	JSONObject fileInfo = new JSONObject();
		Location locationInfo = null;
		double lon = 0;
		double lat = 0;
        if(GpsLocation.isGpsActive()){
        	locationInfo = GpsLocation.gpsLocation(time);
        	if(locationInfo != null){
        		lon = locationInfo.getLongitude();
        		lat = locationInfo.getLatitude();
            }else{
            	String GPSerrorString = "GPS定位失败";
            	errorString = errorFun(GPSerrorString);
            }
        }else{
        	String GPSerrorString = "GPS未打开";
        	errorString = errorFun(GPSerrorString);
        }
        
        if(locationInfo == null){
        	JSONObject locationWifiInfo = new JSONObject();
        	if(WiFiLocation.isWiFiActive()){	
            	locationWifiInfo = WiFiLocation.wifiLocation();
            	if(locationWifiInfo == null){
            		String WifierrorString = "wifi定位失败";
            		errorString = errorFun(WifierrorString);
            	}else{
					lat = locationWifiInfo.getDouble("latitude");
					lon = locationWifiInfo.getDouble("longitude");
            	}
            }else{
            	String WifierrorString = "未连接网络";
            	errorString = errorFun(WifierrorString);
            }
        }
        fileInfo.put("lon", lon);
    	fileInfo.put("lat", lat);
    	fileInfo.put("errorString",errorString);
        return fileInfo;
    }
    public static final String ACTION = "Location";
}
