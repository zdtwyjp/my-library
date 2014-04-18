package org.dp.create.prototype;

public class Prototype implements Cloneable {
	
	public Object clone() throws CloneNotSupportedException {
		Prototype pro = (Prototype)super.clone();
		return pro;
	}
}
