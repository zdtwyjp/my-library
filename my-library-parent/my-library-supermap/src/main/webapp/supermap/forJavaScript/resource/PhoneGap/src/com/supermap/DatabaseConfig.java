package com.supermap;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConfig {

	public DatabaseConfig() {
		// TODO Auto-generated constructor stub

	}

	public static JSONObject getConfig(Context ctx) throws JSONException {
		JSONObject config = new JSONObject();

		try {
			HashMap<String, Object> map = queryConfig(ctx);
			config = prepareConfig(map);
		} catch (Exception e) {
			System.out.println("there is a error in DatabaseConfig.getConfig :"
					+ e.getMessage());
		}

		return config;
	}

	public static HashMap<String, Object> queryConfig(Context ctx) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			DBOpenHelp dbOpenHelper = new DBOpenHelp(ctx, RequestControl.dir
					+ RequestControl.dbName);
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

			String sql = "select name,value from metadata";
			Cursor cursor = db.rawQuery(sql, new String[] {});
			if (cursor.moveToFirst()) {
				do {
					String name = cursor.getString(0);
					String value = cursor.getString(1);
					map.put(name, value);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
			dbOpenHelper.close();
		} catch (Exception e) {
			System.out.println("there is a error in DatabaseConfig.getConfig :"
					+ e.getMessage());
		}

		return map;
	}

	public static JSONObject prepareConfig(HashMap<String, Object> map)
			throws JSONException {
		JSONObject config = new JSONObject();

		JSONArray resolutions = StringToArray((String) map.get("resolutions"));
		JSONArray scales = StringToArray((String) map.get("scales"));
		String referResolution = resolutions.getString(0);
		String referScale = scales.getString(0);
		String dpi = getDPI(referScale, referResolution);
		JSONObject bounds = getBounds((String) map.get("bounds"));
		String unit = (String) map.get("unit");

		config.put("scales", scales);
		config.put("dpi", dpi);
		config.put("unit", unit);
		config.put("bounds", bounds);

		return config;
	}

	public static JSONArray StringToArray(String str) throws JSONException {
		JSONArray arr = new JSONArray();

		String[] strs = str.split(",");
		for (int i = 0; i < strs.length; i++) {
			String a = strs[i];
			double b = Double.parseDouble(a);
			arr.put(b);
		}

		return arr;
	}

	public static JSONObject getBounds(String str) throws JSONException {
		JSONObject bounds = new JSONObject();

		String[] strs = str.split(",");
		bounds.put("left", Double.parseDouble(strs[0]));
		bounds.put("bottom", Double.parseDouble(strs[1]));
		bounds.put("right", Double.parseDouble(strs[2]));
		bounds.put("top", Double.parseDouble(strs[3]));

		return bounds;
	}

	public static String getDPI(String scale, String resolution) {
		return Double.toString(0.0254 / Double.parseDouble(scale)
				/ Double.parseDouble(resolution));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
