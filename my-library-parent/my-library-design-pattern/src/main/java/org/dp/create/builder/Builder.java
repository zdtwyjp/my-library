package org.dp.create.builder;

import java.util.ArrayList;
import java.util.List;

/** 
 * 建造者模式
 * */
public class Builder {
	private static List<Sender> list = new ArrayList<Sender>();

	public static void getMailSenderInstance(int size) {
		for(int i = 0; i < size; i++) {
			list.add(new MailSender());
		}
	}

	public static void getSmsSenderInstance(int size) {
		for(int i = 0; i < size; i++) {
			list.add(new SmsSender());
		}
	}
}
