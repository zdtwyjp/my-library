package com.app.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import com.sys.common.util.DataSourcePropertiesEncryptFactoryBean;
import com.sys.core.IdGenerator;

public class ConsultantcountJob {

	public static int init = 0;
	public static String url = "";
	public static String name = "";
	public static String password = "";

	public void work() {
		if (init == 0) {
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
				init = 1;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Connection connection = null;
		PreparedStatement psmt = null;
		PreparedStatement insertPsmt = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(url, name, password);
			psmt = connection
					.prepareStatement("select sn,mobilePhone,loginedCount from consultant");
			insertPsmt = connection
			.prepareStatement("insert into consultantcount values(?,?,?,?,?,?)");
			rs = psmt.executeQuery();
			Calendar c = Calendar.getInstance();
			String year = "" + c.get(Calendar.YEAR);
			String date = c.get(Calendar.MONTH)+1+"-"+c.get(Calendar.DATE);
			int i = 0;
			while (rs.next()) {
				if(i >=50 ){
					insertPsmt.executeBatch();
					i=0;
					insertPsmt.clearBatch();
				}
				insertPsmt.setLong(1, IdGenerator.generateId());
				insertPsmt.setLong(2, rs.getLong(1));
				insertPsmt.setString(3, date);
				insertPsmt.setString(4, year);
				insertPsmt.setInt(5, rs.getInt(3));
				insertPsmt.setString(6, rs.getString(2));
				insertPsmt.addBatch();
				i++;
			}
			if(i > 0){
				insertPsmt.executeBatch();
			}
			psmt.execute("update consultant set loginedCount=0");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				psmt.close();
				insertPsmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
