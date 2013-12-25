// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 2012/6/21 11:00:00
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LocalStoragePlugin.java

package com.supermap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class LocalStoragePlugin extends Plugin {

	public LocalStoragePlugin() {
	}

	public PluginResult execute(String action, JSONArray data, String callbackId) {
		PluginResult result = null;
		if ("getImg".equals(action))
			try {
				String fileurl = data.getString(0);
				String strLayer = data.getString(1);
				String x = data.getString(2);
				String y = data.getString(3);
				String z = data.getString(4);
				String methodName = data.getString(5);
				JSONObject fileInfo = getImg(fileurl, strLayer, x, y, z,
						methodName);
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("saveurl".equals(action))
			try {
				String fileurl = data.getString(0);
				String strLayer = data.getString(1);
				String x = data.getString(2);
				String y = data.getString(3);
				String z = data.getString(4);
				String methodName = data.getString(5);
				JSONObject fileInfo = saveurl(fileurl, strLayer, x, y, z,
						methodName);
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("savedb".equals(action))
			try {
				String fileurl = data.getString(0);
				String strLayer = data.getString(1);
				String x = data.getString(2);
				String y = data.getString(3);
				String z = data.getString(4);
				String methodName = data.getString(5);
				JSONObject fileInfo = savedb(fileurl, strLayer, x, y, z,
						methodName);
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("isexist".equals(action))
			try {
				String strLayer = data.getString(0);
				String x = data.getString(1);
				String y = data.getString(2);
				String z = data.getString(3);
				JSONObject fileInfo = new JSONObject();
				fileInfo.put("filename", isFileExist(strLayer, x, y, z));
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("getsdcard".equals(action))
			try {
				JSONObject fileInfo = new JSONObject();
				fileInfo.put("sdcard", getSDCARDPath());
				return new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("savaconfig".equals(action))
			try {
				JSONObject fileInfo = new JSONObject();
				String strLayer = data.getString(0);
				String strResult = data.getString(1);
				fileInfo.put("json", saveConfig(strLayer, strResult));
				return new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		else if ("getconfig".equals(action)) {
			try {
				JSONObject fileInfo = new JSONObject();
				String strLayer = data.getString(0);
				String storageType = data.getString(1);
				fileInfo.put("json", getConfig(strLayer, storageType));
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.OK, fileInfo);
			} catch (JSONException jsonEx) {
				result = new PluginResult(
						org.apache.cordova.api.PluginResult.Status.JSON_EXCEPTION);
			}
		} else {
			result = new PluginResult(
					org.apache.cordova.api.PluginResult.Status.INVALID_ACTION);
			Log.d("DirectoryListPlugin",
					(new StringBuilder("Invalid action : ")).append(action)
							.append(" passed").toString());
		}
		return result;
	}

	// private synchronized JSONObject savedb1(String fileurl, String
	// strLayerName, String x, String y, String z,String methodName)
	// throws JSONException, UnsupportedEncodingException
	// {
	// if(RequestControl.myDb == null){
	// RequestControl.myDb = new ImgDatabase(this.webView.getContext());
	// }
	// String key = x+"_"+y+"_"+z;
	// JSONObject fileInfo = new JSONObject();
	// fileInfo.put("x", x);
	// fileInfo.put("y", y);
	// fileInfo.put("z", z);
	// fileInfo.put("layername", strLayerName);
	// if(RequestControl.isKeyExist(key)){
	// //RequestControl.myDb.queryImg(x+"_"+y+"_"+z);
	// String imgdata = RequestControl.myDb.queryImgBase64(x,y,z);
	// fileInfo.put("data", imgdata);
	// this.webView.loadUrl("javascript:"+methodName+"("+fileInfo.toString()+")");
	// }
	// else{
	// try
	// {
	// URL url = new URL(fileurl);
	// HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	// connection.setDoInput(true);
	// connection.connect();
	// InputStream input = connection.getInputStream();
	// //byte[] data = null;
	// //data = new byte[input.available()];
	// //input.read(data);
	//
	// Bitmap myBitmap = BitmapFactory.decodeStream(input);
	// input.close();
	// // ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();
	// //myBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100,
	// jpeg_data);
	//
	// String imgdata = RequestControl.myDb.saveMap(myBitmap, x, y, z)(myBitmap,
	// key);
	// imgdata = imgdata.replaceAll("\n", "");
	// fileInfo.put("data", imgdata);
	// this.webView.loadUrl("javascript:"+methodName+"("+fileInfo.toString()+")");
	// //RequestControl.myDb.saveByte(x+"_"+y+"_"+z, data);//(myBitmap,
	// x+"_"+y+"_"+z);
	// }
	// catch(MalformedURLException e)
	// {
	// e.printStackTrace();
	// }
	// catch(IOException e1)
	// {
	// e1.printStackTrace();
	// }
	// }
	// return fileInfo;
	// }
	private JSONObject saveurl(String fileurl, String strLayerName, String x,
			String y, String z, String methodName) {
		// Thread t = new
		// ImgThread(fileurl,strLayerName,x,y,z,methodName,this.webView);
		// 将线程放入池中进行执行
		// ExecutorService pool = RequestControl.getPool();
		// pool.execute(t);
		return new JSONObject();
	}

	private JSONObject savedb(String fileurl, String strLayerName, String x,
			String y, String z, String methodName) {
		// Thread t = new
		// ImgDataThread(fileurl,strLayerName,x,y,z,methodName,this.webView);
		// 将线程放入池中进行执行
		// ExecutorService pool = RequestControl.getPool();
		// pool.execute(t);
		return new JSONObject();
	}

	private JSONObject getImg(String fileurl, String strLayerName, String x,
			String y, String z, String methodName) {
		Thread t = new GetImgThread(fileurl, strLayerName, x, y, z, methodName,
				this.webView);
		// 将线程放入池中进行执行
		ExecutorService pool = RequestControl.getPool();
		pool.execute(t);
		return new JSONObject();
	}

	// private JSONObject saveurl1(String fileurl, String strLayerName, String
	// x, String y, String z)
	// throws JSONException, InterruptedException
	// {
	// JSONObject fileInfo = new JSONObject();
	// String fileUrl = fileurl;
	// File file3 = null;
	// byte buffer[] = (byte[])null;
	// File SDCardRoot = Environment.getExternalStorageDirectory();
	// String strDir = SDCardRoot.getAbsolutePath();
	// strDir = (new
	// StringBuilder(String.valueOf(strDir))).append("/SuperMap/").append(strLayerName).append("/").append(z).toString();
	// File dirFile = new File(strDir);
	// if(!dirFile.exists())
	// dirFile.mkdirs();
	// dirFile = null;
	// String filename = (new
	// StringBuilder(String.valueOf(strDir))).append("/").append(x).append("_").append(y).append(".png").toString();
	// fileInfo.put("x", x);
	// fileInfo.put("y", y);
	// fileInfo.put("z", z);
	// fileInfo.put("layername", strLayerName);
	// file3 = new File(filename);
	// if(!file3.exists()||(file3.exists()&&file3.length()<1L))
	// try
	// {
	// URL url = new URL(fileUrl);
	// HttpURLConnection urlConnection =
	// (HttpURLConnection)url.openConnection();
	// urlConnection.setConnectTimeout(6000);
	// FileOutputStream fileOutput = new FileOutputStream(file3);
	// InputStream inputStream = urlConnection.getInputStream();
	// buffer = new byte[1024];
	// for(int bufferLength = 0; (bufferLength = inputStream.read(buffer)) > 0;)
	// fileOutput.write(buffer, 0, bufferLength);
	//
	// buffer = (byte[])null;
	// fileOutput.close();
	// if(file3.length() < 1L)
	// file3.delete();
	// if(file3.exists())
	// fileInfo.put("filename", filename);
	// else
	// fileInfo.put("filename", "null");
	// }
	// catch(MalformedURLException e)
	// {
	// fileInfo.put("filename", "url");
	// buffer = (byte[])null;
	// file3.delete();
	// e.printStackTrace();
	// }
	// catch(IOException e1)
	// {
	// fileInfo.put("filename", "io");
	// buffer = (byte[])null;
	// file3.delete();
	// e1.printStackTrace();
	// }
	// file3 = null;
	// buffer = (byte[])null;
	// int time = (int) Math.round(Math.random()*4000+1000);
	// Thread.sleep(time);
	// return fileInfo;
	// }

	public String isFileExist(String strLayerName, String x, String y, String z) {
		File SDCardRoot = Environment.getExternalStorageDirectory();
		String strDir = SDCardRoot.getAbsolutePath();
		strDir = (new StringBuilder(String.valueOf(strDir)))
				.append("/SuperMap/").append(strLayerName).append("/")
				.append(z).toString();
		String filename = (new StringBuilder(String.valueOf(strDir)))
				.append("/").append(x).append("_").append(y).append(".png")
				.toString();
		File file3 = new File(filename);
		if (file3.exists())
			return "true";
		else
			return "false";
	}

	public String getSDCARDPath() {
		File SDCardRoot = Environment.getExternalStorageDirectory();
		String strDir = SDCardRoot.getAbsolutePath();
		return strDir;
	}

	public boolean isSynch(String action) {
		return action.equals("getsdcard") || action.equals("saveconfig")
				|| action.equals("isexist") || action.equals("saveurl")
				|| action.equals("savedb") || action.equals("getImg");
	}

	public String saveConfig(String strName, String strResult) {
		try {
			File SDCardRoot = Environment.getExternalStorageDirectory();
			String strDir = (new StringBuilder(String.valueOf(SDCardRoot
					.getAbsolutePath()))).append("/SuperMap").toString();
			File dirFile = new File(strDir);
			if (!dirFile.exists())
				dirFile.mkdirs();
			strDir = (new StringBuilder(String.valueOf(SDCardRoot
					.getAbsolutePath()))).append("/SuperMap/").append(strName)
					.append(".config").toString();
			File file = new File(strDir);
			FileOutputStream fos = null;
			if (!file.exists())
				file.createNewFile();
			fos = new FileOutputStream(file);
			byte bytes[] = new byte[1024];
			bytes = strResult.getBytes();
			int b = strResult.length();
			fos.write(bytes, 0, b);
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "ok";
	}

	public String getConfig(String fileName, String storageType)
			throws JSONException {
		String jsonString = "false";
		RequestControl.storageType = storageType;
		RequestControl.initdb(this.ctx.getContext(), fileName, storageType);
		if (storageType.equals("db")) {
			try {
				jsonString = DatabaseConfig.getConfig(this.ctx.getContext())
						.toString();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			File file;
			BufferedReader reader = null;

			File SDCardRoot = Environment.getExternalStorageDirectory();
			String strDir = (new StringBuilder(String.valueOf(SDCardRoot
					.getAbsolutePath()))).append("/SuperMap").toString();
			File dirFile = new File(strDir);
			if (!dirFile.exists())
				dirFile.mkdirs();
			strDir = (new StringBuilder(String.valueOf(SDCardRoot
					.getAbsolutePath()))).append("/SuperMap/").append(fileName)
					.append(".config").toString();
			file = new File(strDir);
			if (!file.exists())
				reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				int line = 1;
				jsonString = "";
				for (String tempString = null; (tempString = reader.readLine()) != null;) {
					jsonString = (new StringBuilder(String.valueOf(jsonString)))
							.append(tempString).toString();
					line++;
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException ioexception) {
				}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException ioexception1) {
				}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException ioexception2) {
				}
		}

		return jsonString;
	}

	// public static final String ACTION = "saveurl";
	// public static final String ACTION2 = "isexist";
	// public static final String ACTION3 = "getsdcard";
	// public static final String ACTION4 = "savaconfig";
	// public static final String ACTION5 = "getconfig";
	// public static final String ACTION6 = "savedb";
	// public static final String ACTION7 = "shotscreen";
	// public static final String ACTION8 = "getImg";
}