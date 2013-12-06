package com.supermap.plugins;



import java.util.Timer;
import java.util.TimerTask;

import com.supermap.RequestControl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

@SuppressLint("ParserError")
public class GpsLocation {
	public static Activity act = RequestControl.act;
	private static boolean TIME_OUT = false;
	private static long TIME_DURATION = 5000;
	private static LocationManager locationManager;
	public GpsLocation()
	{
	}
	
	public static Location gpsLocation(int timeOut) {
		TIME_DURATION = timeOut;
		Location location = null;
	    String context = Context.LOCATION_SERVICE;
	    locationManager = (LocationManager)act.getSystemService(context);
	    boolean GPS_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if(GPS_status){
		    String provider = LocationManager.GPS_PROVIDER;
		    location = locationManager.getLastKnownLocation(provider);
		    TimeExecution();
		    location = updateWithNewLocation(location);
	    }
	    
	    return location;
	}
	
	public static void TimeExecution(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				TIME_OUT = true;
			}
		}, TIME_DURATION);
	}
	 private static Location updateWithNewLocation(Location location) {
		Location locationInfo = null;
	    while(!TIME_OUT){	    	
	    	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    }
	    if(location != null){
	    	locationInfo = location;
	    }
	    return locationInfo;
	}
	 
	 public static boolean isGpsActive(){
		 String context = Context.LOCATION_SERVICE;
		 locationManager = (LocationManager)act.getSystemService(context);
		 boolean GPS_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		 if(GPS_status){
			 return true;
		 }
		 return false;
	 }
}
