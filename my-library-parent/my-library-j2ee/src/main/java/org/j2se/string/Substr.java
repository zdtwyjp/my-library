package org.j2se.string;

public class Substr {

	public static void main(String[] args) {
		String t = "web-inf/key/xzaj.key";
		int x = t.lastIndexOf("/");
		System.out.println(t.substring(0, x + 1));
		System.out.println(t.substring(x+1));
	}

}
