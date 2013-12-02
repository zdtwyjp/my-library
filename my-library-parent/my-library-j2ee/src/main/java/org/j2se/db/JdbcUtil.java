package org.j2se.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;


public class JdbcUtil {
	private static Logger log = Logger.getLogger(JdbcUtil.class);
	private static Properties info = new Properties();
	static {
		try {
			InputStream in = JdbcUtil.class.getResourceAsStream("/config/properties/jdbc.properties");
			info.load(in);
			in.close();
			log.debug("Config file loading success");
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private JdbcUtil(){}
	/**
	 * Getter connection
	 * It's thread secure
	 */
	private static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	public static Connection getConnection() throws Exception {
//		Connection conn = tl.get();
		Connection conn = null;
//		if (conn == null) {
			Class.forName(info.getProperty("driver"));
			
			String name = info.getProperty("name");
			String password = info.getProperty("password");
//			String pd = "root";
//			try {
//			    if (name != null) {
//			    	name = AESEncryptUtil.decrypt(name, pd);
//			    }
//			    if (password != null) {
//			    	password = AESEncryptUtil.decrypt(password, pd);
//			    }
			conn = DriverManager.getConnection(info.getProperty("url"),name, password);
			tl.set(conn);
//		}
		return conn;
	}
	/**
	 * release
	 * 1 arguments only is Connection Object
	 * 2 arguments only is Statement and Connection
	 * 3 arguments only is ResultSet and Statement,Connection
	 */
	public static void release(Object... args) {
		if (args.length == 1) {
			if (args[0] instanceof Connection) {
				Connection conn = (Connection) args[0];
				releaseInner(null, null, conn);
			} else if (args[0] instanceof ResultSet){
				ResultSet rs = (ResultSet) args[0];
				releaseInner(rs,null,null);
			} else if (args[0] instanceof Statement) {
				Statement stm = (Statement) args[0];
				releaseInner(null, stm, null);
			} else throw new RuntimeException("Type error !");
		} else if (args.length == 2) {
			ResultSet rs = null;
			Statement stm = null;
			Connection conn = null;
			for (Object arg : args) {
				if (arg instanceof Statement) stm = (Statement) arg;
				if (arg instanceof Connection) conn = (Connection) arg;
				if (arg instanceof ResultSet) rs = (ResultSet) arg;
			}
			if (rs != null && stm != null) {
				releaseInner(rs, stm, null);
			} else if (stm != null && conn != null) {
				releaseInner(null, stm, conn);
			}			
		} else if (args.length == 3) {
			ResultSet rs = null;
			Statement stm = null;
			Connection conn = null;
			for (Object arg : args) {
				if (arg instanceof ResultSet) rs = (ResultSet) arg;
				if (arg instanceof Statement) stm = (Statement) arg;
				if (arg instanceof Connection) conn = (Connection) arg;
			}
			if (rs == null && stm != null && conn != null) {
				releaseInner(rs, stm, null);
			} else if (rs != null && stm != null && conn != null) {
				releaseInner(rs, stm, conn);
			}
		} else throw new RuntimeException("Parameter not less 3 !");
	}
	private static void releaseInner(ResultSet rs, Statement stm, Connection conn) {
		if (rs != null) try { rs.close(); log.debug("ResulstSet release success"); } catch (Exception e) { log.error(e); }
		if (stm != null) try { stm.close(); log.debug("Statement release success"); } catch (Exception e) { log.error(e); }
		if (conn != null) try { conn.close(); log.debug("Connection release success"); } catch (Exception e) { log.error(e); }
	}
/*	*//**
	 * setting config file path
	 * @param path
	 *//*
	public static void setFilePath(String path) {
		filePath = path;
	}
	private static String getFilePath() {
		if (filePath != null) {
			return filePath;
		} else {
			return "/config.properties";
		}
	}*/
}
