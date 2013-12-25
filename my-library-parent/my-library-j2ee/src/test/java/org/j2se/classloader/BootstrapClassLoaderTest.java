package org.j2se.classloader;


import java.net.URL;

import org.junit.Test;

public class BootstrapClassLoaderTest {
	/**
	 * 获取BootStrap ClassLoader 加载对象
	 */
	@Test
	public void getBootStrapClassLoader() {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(int i = 0; i < urls.length; i++) {
			URL url = urls[i];
			System.out.println(url.getFile());
		}
	}
}
