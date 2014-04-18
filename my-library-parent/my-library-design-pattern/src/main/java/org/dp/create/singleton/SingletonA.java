package org.dp.create.singleton;

/** 饿汉式 */
public class SingletonA {
	private SingletonA() {}

	public static SingletonA single = new SingletonA();

	public static SingletonA getInstance() {
		return single;
	}
}
