/**
 * 
 */
package com.supermap;

import java.io.File;

import org.json.JSONObject;

import android.webkit.WebView;

class GetImgThread extends Thread {
	private String fileurl = null;
	private String strLayerName = null;
	private String x = null;
	private String y = null;
	private String z = null;
	private String methodName = null;
	private WebView webview = null;
	public GetImgThread(String fileurl, String strLayerName, String x, String y, String z,String methodName,WebView webview){
		this.fileurl = fileurl;
    	this.strLayerName = strLayerName;
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	this.methodName = methodName;
    	this.webview = webview;
	}

	@Override
    public void run() {
		JSONObject fileInfo = new JSONObject();
    	try {
	    	
	        String fileUrl = fileurl;
	        //File file3 = null;
	        //File SDCardRoot = Environment.getExternalStorageDirectory();
	        //String strDir = SDCardRoot.getAbsolutePath();
	        //strDir = (new StringBuilder(String.valueOf(strDir))).append("/SuperMap/").append(strLayerName).append("/").append(z).toString();
	        //File dirFile = new File(strDir);
	        //if(!dirFile.exists())
	        //    dirFile.mkdirs();
	        //dirFile = null;
	        //String filename = (new StringBuilder(String.valueOf(strDir))).append("/").append(x).append("_").append(y).append(".png").toString();
			fileInfo.put("x", x);
	        fileInfo.put("y", y);
	        fileInfo.put("z", z);
	        fileInfo.put("layername", strLayerName);
	        String key = x+"_"+y+"_"+z;
	        if(RequestControl.storageType.equals("db")){
	        	//if(RequestControl.isKeyExist(key)){
        		//if(RequestControl.myDb.isExist(x,y,z)){
		        	System.out.println("key " + key + "get from db fileUrl " + fileUrl);
		        	//System.out.println("fileUrl " + key + "get from db");
	        		ImgDatabase queryDb = RequestControl.myDb;
	        		String imgdata = queryDb.queryImgBase64(x,y,z);
	        		
	        		fileInfo.put("data", imgdata);
	        		//this.webview.loadUrl("javascript:log.print(\"loadUrl:"+key+"\")");
	        		//this.webview.loadUrl("javascript:"+methodName+"("+fileInfo.toString()+")");
	        	//}
	        	//else{
	        		//fileInfo.put("src", fileurl);
	        		//fileInfo.put("data", "null");
	        		//this.webview.loadUrl("javascript:"+methodName+"("+fileInfo.toString()+")");
	        		
//	        		System.out.println("key " + key + "get from file fileUrl " + fileUrl);
//		        	String strDir = (new StringBuilder(String.valueOf(RequestControl.dir))).append(strLayerName).append("/").append(z).toString();
//		        	File dirFile = new File(strDir);
//			        if(!dirFile.exists())
//			            dirFile.mkdirs();
//			        dirFile = null;
//			        String filename = (new StringBuilder(String.valueOf(strDir))).append("/").append(x).append("_").append(y).append(".png").toString();
//			        File file3 = new File(filename);
//			        if(!file3.exists()||(file3.exists()&&file3.length()<1L)){
//			        	System.out.println("need down load");
//			        	ImgControl.downloadToFile(fileUrl, file3, fileInfo,filename);
//			        }
//			        file3 = null;
	        	//}
	        }
	        else{
//	        	Bitmap myBitmap = ImgControl.downloadToBitmap(fileUrl);
//                String imgdata = RequestControl.myDb.saveMap(myBitmap, x,y,z);
	        	System.out.println("key " + key + "get from file");
	        	String strDir = (new StringBuilder(String.valueOf(RequestControl.dir))).append(strLayerName).append("/").append(z).toString();
	        	File dirFile = new File(strDir);
		        if(!dirFile.exists())
		            dirFile.mkdirs();
		        dirFile = null;
		        String filename = (new StringBuilder(String.valueOf(strDir))).append("/").append(x).append("_").append(y).append(".png").toString();
		        File file3 = new File(filename);
		        if(!file3.exists()||(file3.exists()&&file3.length()<1L)){
		        	System.out.println("need down load");
		        	ImgControl.downloadToFile(fileUrl, file3, fileInfo,filename);
		        }
		        file3 = null;
		        //this.webview.loadUrl("javascript:"+this.methodName+"("+fileInfo.toString()+")");
	        }
	        //file3 = new File(filename);
	        //if(!file3.exists()||(file3.exists()&&file3.length()<1L))
	        	//ImgControl.downloadToFile(fileUrl, file3, fileInfo);
		        //file3 = null;
		        //this.webview.loadUrl("javascript:"+this.methodName+"("+fileInfo.toString()+")");
    	} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	try{
    		this.webview.loadUrl("javascript:"+methodName+"("+fileInfo.toString()+")");
    	}
    	catch(Exception e){
    		System.out.println("webview.loadUrl have a error:"+e.getMessage());
    	}
    	
    }
}
