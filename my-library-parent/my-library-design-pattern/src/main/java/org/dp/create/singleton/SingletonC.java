package org.dp.create.singleton;

/** 此方法比SingletonA和SingletonB性能要优 */
public class SingletonC {
	private SingletonC() {}

	private static class SingletonFactory {
		public static SingletonC single = new SingletonC();
	}

	public static SingletonC getInstance() {
		return SingletonFactory.single;
	}
}
