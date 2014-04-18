package org.dp.create.builder;

public class SmsSender implements Sender{

	@Override
	public void send() {
		System.out.println("This is a smsSender!");
	}
	
}
