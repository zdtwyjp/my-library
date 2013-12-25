
package org.j2se.classloader;

import org.junit.Test;

public class LauncherTest {
	@Test
	public void getClassLoader(){
		 System.out.println("the Launcher's classloader is "+sun.misc.Launcher.getLauncher().getClass().getClassLoader());
	}
}
