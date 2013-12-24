package org.j2se.classloader;


import org.junit.Test;

public class ExtensionClassLoaderTest {
	/**
	 * 获取ClassLoader 加载对象
	 */
	@Test
	public void getClassLoader() {
		System.out.println(System.getProperty("java.ext.dirs"));
		// extension classloader
		ClassLoader extensionClassloader=ClassLoader.getSystemClassLoader().getParent();
		System.out.println("the parent of extension classloader : "+extensionClassloader.getParent());
	}
}
