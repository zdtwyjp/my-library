/**
 * 
 */
package com.supermap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * @author liuhong
 *
 */
public class ImgControl {

	/**
	 * 
	 */
	public ImgControl() {
		// TODO Auto-generated constructor stub
	}
	
	public static void downloadToFile(String fileUrl,File file3,JSONObject fileInfo,String filename) throws JSONException, IOException{
		InputStream inputStream = null;
        FileOutputStream fileOutput = null;
        byte buffer[] = (byte[])null;
		try
        {
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(6000);
            fileOutput = new FileOutputStream(file3);
            inputStream = urlConnection.getInputStream();
            //buffer = new byte[1024];
            //for(int bufferLength = 0; (bufferLength = inputStream.read(buffer)) > 0;)
            //    fileOutput.write(buffer, 0, bufferLength);	
            //buffer = (byte[])null;
            
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    		byte[] imgdata = baos.toByteArray();
    		String dataStr = Base64.encodeToString(imgdata, Base64.DEFAULT);
    		dataStr = dataStr.replaceAll("\n", "");
    		
    		fileOutput.write(imgdata);
            inputStream.close();
            fileOutput.flush();
            fileOutput.close();
            if(file3.length() < 1L)
                file3.delete();
            if(file3.exists()){
            	fileInfo.put("filename", filename);
            	fileInfo.put("data", dataStr);
            }
            else{
            	fileInfo.put("filename", "null");
            }
        }
        catch(Exception e)//MalformedURLException
        {
            fileInfo.put("filename", "url");
            buffer = (byte[])null;
            if(inputStream!=null)inputStream.close();
            if(fileOutput!=null){
            	fileOutput.flush();
                fileOutput.close();
            }
            file3.delete();
            e.printStackTrace();
        }
		buffer = (byte[])null;
	}
	
	public static Bitmap downloadToBitmap(String fileurl){
		Bitmap map = null;
		try
        {
            URL url = new URL(fileurl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            
            map = BitmapFactory.decodeStream(input);
            input.close();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e1)
        {
            e1.printStackTrace();
        }
        return map;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
