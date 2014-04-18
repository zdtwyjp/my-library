package org.dp.create.abstractfactory;

public class SmsSender implements Sender{

	@Override
	public void send() {
		System.out.println("This is a smsSender!");
	}
	
}
