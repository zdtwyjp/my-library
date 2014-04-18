package org.dp.create.factorymethod;

public class SmsSender implements Sender{

	@Override
	public void send() {
		System.out.println("This is a smsSender!");
	}
	
}
