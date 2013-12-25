package org.j2se.classloader;


import org.junit.Test;

public class SystemClassLoaderTest {
	@Test
	public void getClassLoader() {
		 System.out.println(System.getProperty("java.class.path"));
	}
}
