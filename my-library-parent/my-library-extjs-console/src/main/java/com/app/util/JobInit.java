package com.app.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sys.common.util.DataSourcePropertiesEncryptFactoryBean;

public class JobInit {
	private static String url;
	private static String name;
	private static String password;

	public void init() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream(
					"/config/properties/jdbc.properties"));
			DataSourcePropertiesEncryptFactoryBean dp = new DataSourcePropertiesEncryptFactoryBean();
			Properties props = new Properties();
			props.put("user", prop.get("jdbc.username"));
			props.put("password", prop.getProperty("jdbc.password"));
			props.put("pd", "fxpgy");
			dp.setProperties(props);
			try {
				props = (Properties) dp.getObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = prop.getProperty("jdbc.url");
			name = props.getProperty("user");
			password = props.getProperty("password");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void doWork() {
		Connection connection = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(
					url, name,
					password);
			psmt = connection
					.prepareStatement("select distinct name from project where projectState=1");
			rs = psmt.executeQuery();
			JSONArray array = new JSONArray();
			while (rs.next()) {
				JSONObject temp = new JSONObject();
				temp.put("name", rs.getString(1));
				array.add(temp);
			}
			ParameterUtil.setSearchWords(array);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				psmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
