package org.dp.create.factorymethod;

public class MailSender implements Sender{

	@Override
	public void send() {
		System.out.println("This is a mailSender!");
	}
	
}
