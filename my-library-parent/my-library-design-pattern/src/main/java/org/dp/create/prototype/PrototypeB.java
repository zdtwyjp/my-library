package org.dp.create.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PrototypeB implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	public Object clone() throws CloneNotSupportedException{
		PrototypeB pro = (PrototypeB)super.clone();
		return pro;
	}
	
	public Object deepClone() throws IOException, ClassNotFoundException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
}

class SerializableObject implements Serializable {
	private static final long serialVersionUID = 1L;
}
