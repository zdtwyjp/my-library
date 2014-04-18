package org.dp.create.singleton;

/** 懒汉式 */
public class SingletonB {
	private SingletonB() {}

	public static SingletonB single = null;

	/** 
	 * 此处synchronized加在整个方法上，效率比较低。
	 * */
	public synchronized static SingletonB getInstance() {
		if(single == null) {
			single = new SingletonB();
		}
		return single;
	}
	
	/** 
	 * 此处synchronized加在new上，效率比加在方法上要高一些。
	 * 但是，这种写法会存在问题。AB线程同时到达synchronized处，A执行完后
	 * single不为空，B执行则直接返回。如果同步未执行完，则对象未被初始化则出现异常。
	 * */
	public static SingletonB getInstance2() {
		if(single == null) {
			synchronized(single){
				if(single == null){
					single = new SingletonB();
				}
			}
		}
		return single;
	}
}
