package com.supermap;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

public class RequestControl {
	public static ExecutorService pool=null;
	public static ImgDatabase myDb = null;
	public static HashMap<String,Object> keyMap = new HashMap<String,Object>();
	public static String dir=null;
	public static String dbName = null;//"China_256X256_PNG.mbtiles";//"World_256X256_PNG_T.mbtiles";
	public static boolean isInit = false;
	public static String storageType = "File";
	public static Activity act = null;
	//public static JSONObject config = null;
	public static ExecutorService getPool(){
		if(pool==null){
			pool = Executors.newFixedThreadPool(5);
		}
		return pool;
	}
	
	public static void initdb(Context context,String dbn,String storageType){
		if(storageType.equals("db")){
        	dbName = dbn+".mbtiles";
        	RequestControl.myDb = new ImgDatabase(context);
        }
	}
	
	public static void init(Activity activity) throws JSONException{
		if(!isInit){
			isInit =  true;

			File SDCardRoot = Environment.getExternalStorageDirectory();
	        String strDir = SDCardRoot.getAbsolutePath();
	        strDir = (new StringBuilder(String.valueOf(strDir))).append("/SuperMap/").toString();
	        File dirFile = new File(strDir);
	        if(!dirFile.exists())
	            dirFile.mkdirs();
	        dirFile = null;
	        dir = strDir;
	        
	        
	        //ShotScreen.act = activity;
	        act = activity;
//	        if(storageType.equals("db")){
//	        	dbName = dbn+".mbtiles";
//				DBOpenHelp dbOpenHelper = new DBOpenHelp(context,strDir+dbName);
//				System.out.println("querykeyMap");
//				SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//				db.execSQL("create table IF NOT EXISTS tiles (id integer primary key autoincrement, tile_column varchar(20), tile_row varchar(20), zoom_level varchar(20), tile_data blob)");
//				db.execSQL("create table IF NOT EXISTS metadata (id integer primary key autoincrement, name varchar(20), value text)");
//				String sql = "select tile_column,tile_row,zoom_level from tiles";
//				Cursor cursor = db.rawQuery(sql, new String[]{});
//				if(cursor.moveToFirst()){
//					String x = cursor.getString(0);
//					String y = cursor.getString(1);
//					String z = cursor.getString(2);
//					String key = x+"_"+y+"_"+z;
//					RequestControl.keyMap.put(key, 1);
//					while(cursor.moveToNext()){
//						x = cursor.getString(0);
//						y = cursor.getString(1);
//						z = cursor.getString(2);
//						key = x+"_"+y+"_"+z;
//						RequestControl.keyMap.put(key, 1);
//					}
//				}
//				cursor.close();
//				
//				db.close();
//				dbOpenHelper.close();
				
//				RequestControl.myDb = new ImgDatabase(context);
//	        }
		}
	}
	
//	public static void testIserverMbtile(Context context) throws IOException{
//		DBOpenHelp dbOpenHelper = new DBOpenHelp(context,dir+"World_256X256_PNG_T.mbtiles");
//		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		
//		byte[] imgdata = null;
//		//SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//		String sql = "select tile_data,tile_column,tile_row,zoom_level from tiles";
//		Cursor cursor = db.rawQuery(sql, new String[]{});
//		if(cursor.moveToFirst()){
//			int count = 0;
//			while(cursor.moveToNext()){
//				count++;
//				if(count>10){
//					break;
//				}
//				imgdata = cursor.getBlob(0);
//				String x = cursor.getString(1);
//				String y = cursor.getString(2);
//				String z = cursor.getString(3);
//				
//				File file3 = new File(dir+"testIserverMbtile"+x+"_"+y+"_"+z+".png");
//				FileOutputStream fileOutput = new FileOutputStream(file3);
//				fileOutput.write(imgdata);
//		        fileOutput.flush();
//		        fileOutput.close();
//			}
//		}
//		cursor.close();
//		db.close();
//		dbOpenHelper.close();
//	}
	
	public static boolean isKeyExist(String key){
		boolean isExist = false;
		if(keyMap.containsKey(key)){
			isExist = true;
		}
		return isExist;
	}
}
