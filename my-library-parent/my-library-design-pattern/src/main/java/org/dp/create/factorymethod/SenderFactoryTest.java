package org.dp.create.factorymethod;

public class SenderFactoryTest {
	
	public static void main(String[] args) {
		MailSender ms = SenderFactory.getMailSenderInstance();
		ms.send();
		
		SmsSender ss = SenderFactory.getSmsSenderInstance();
		ss.send();
	}
}
