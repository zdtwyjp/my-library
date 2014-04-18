package org.dp.create.prototype;

import java.io.IOException;

public class PrototypeBTest implements Cloneable {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		PrototypeB pr = new PrototypeB();
		System.out.println(pr.deepClone());
	}
}
