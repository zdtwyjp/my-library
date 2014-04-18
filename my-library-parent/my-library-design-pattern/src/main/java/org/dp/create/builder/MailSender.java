package org.dp.create.builder;

public class MailSender implements Sender{

	@Override
	public void send() {
		System.out.println("This is a mailSender!");
	}
	
}
