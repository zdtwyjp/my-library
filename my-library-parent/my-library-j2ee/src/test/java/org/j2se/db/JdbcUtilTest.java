package org.j2se.db;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class JdbcUtilTest {
	private static int num = 0;
	
	@Test
	public void getConnection() {
		Connection con = null;
		try {
			con = JdbcUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(con);

	}
	
	@Test
	public void testInsertA02OnePeople(){
		try {
			insertA02(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertA02TwoPeople(){
		try {
			insertA02(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertA02(boolean isSingle) throws Exception{
		String xjsj = dateStr(new Date());
		for(int i=1; i<=12; i++) {
			String sql = "insert into A02 (a02_id, kh, ssbm, xm, wz, gzqy, ddsk, tlsc, sgwz, xjsj, ssk, dqsj, bz, yjtb) " +
					"values ('"+getId()+"', '3698', '工程实施', '014', '"+getWz(i)+"', '770段码头', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), '50分钟', '"+(getWz(i)-1)+"', to_date('"+xjsj+"', 'dd-mm-yyyy hh24:mi:ss'), '#A0001#E0001', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), null, 0)";
			System.out.println("sql >> " + sql);
			execute(sql);
			if(isSingle){
				sql = "insert into A02 (a02_id, kh, ssbm, xm, wz, gzqy, ddsk, tlsc, sgwz, xjsj, ssk, dqsj, bz, yjtb) " +
				"values ('"+getId()+"', '3698', '工程实施', '015', '"+getWz(i)+"', '770段码头', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), '50分钟', '"+(getWz(i)-1)+"', to_date('"+xjsj+"', 'dd-mm-yyyy hh24:mi:ss'), '#A0001#E0001', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), null, 0)";
				System.out.println("sql >> " + sql);
				execute(sql);
			}
			Thread.sleep(10000);
		}
		
		for(int i=12; i>0; i--) {
			String sql = "insert into A02 (a02_id, kh, ssbm, xm, wz, gzqy, ddsk, tlsc, sgwz, xjsj, ssk, dqsj, bz, yjtb) " +
					"values ('"+getId()+"', '3698', '工程实施', '014', '"+getWz(i)+"', '770段码头', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), '50分钟', '"+(getWz(i)+1)+"', to_date('"+xjsj+"', 'dd-mm-yyyy hh24:mi:ss'), '#A0001#E0001', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), null, 0)";
			System.out.println("sql >> " + sql);
			execute(sql);
			if(isSingle){
				sql = "insert into A02 (a02_id, kh, ssbm, xm, wz, gzqy, ddsk, tlsc, sgwz, xjsj, ssk, dqsj, bz, yjtb) " +
				"values ('"+getId()+"', '3698', '工程实施', '015', '"+getWz(i)+"', '770段码头', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), '50分钟', '"+(getWz(i)+1)+"', to_date('"+xjsj+"', 'dd-mm-yyyy hh24:mi:ss'), '#A0001#E0001', to_date('"+dateStr(new Date())+"', 'dd-mm-yyyy hh24:mi:ss'), null, 0)";
				System.out.println("sql >> " + sql);
				execute(sql);
			}
			Thread.sleep(10000);
		}
	}
	
	private String dateStr(Date date){
		String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(DATE_TIME_PATTERN);
		return FORMAT_DATE_TIME.format(date);
	}
	
	private int getWz(int num){
		return 3499 + num;
	}
	
	private int getId(){
		return num++;
	}
	
	private String[] getLonLat(int num){
		String[] result = new String[2];
		switch(num){
			case 1: {
				result[0] = "92.2023";
				result[1] = "29.2251";
			};
			case 2: {
				result[0] = "92.2027";
				result[1] = "29.2248";
			};
			case 3: {
				result[0] = "92.2035";
				result[1] = "29.2246";
			};
			case 4: {
				result[0] = "92.2055";
				result[1] = "29.2241";
			};
			case 5: {
				result[0] = "92.2083";
				result[1] = "29.2236";
			};
			case 6: {
				result[0] = "92.2116";
				result[1] = "29.2229";
			};
			case 7: {
				result[0] = "92.2163";
				result[1] = "29.2222";
			};
			case 8: {
				result[0] = "92.2182";
				result[1] = "29.2212";
			};
			case 9: {
				result[0] = "92.2148";
				result[1] = "29.2192";
			};
			case 10: {
				result[0] = "92.2123";
				result[1] = "29.2171";
			};
			case 11: {
				result[0] = "92.2096";
				result[1] = "29.2151";
			};
			case 12: {
				result[0] = "92.2089";
				result[1] = "29.2145";
			};
		}
		return result;
	}
	
	private void execute(String sql)throws Exception{
		Connection con = JdbcUtil.getConnection();
		Statement st = con.createStatement();
		st.execute(sql);
	}

}
