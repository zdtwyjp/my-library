package org.dp.create.abstractfactory;

public class SenderFactoryTest {
	
	public static void main(String[] args) {
		Provider mail = new MailSenderFactory(); 
		Sender sen = mail.produce();
		sen.send();
	}
}
