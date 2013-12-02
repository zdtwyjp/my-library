package org.j2se.string;

public class TestReplace {
	public static void main(String[] args) {
	
		String t = "A0001A0001";
//		t = t.replace("A0001", "A0002");
		int x = t.indexOf("A0001");
		String xx = "A0002" + t.substring("A0001".length());
		System.err.println(xx);
	}
}
