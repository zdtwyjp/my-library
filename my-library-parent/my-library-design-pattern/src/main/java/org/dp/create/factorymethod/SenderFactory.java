package org.dp.create.factorymethod;

public class SenderFactory {
	
	public static MailSender getMailSenderInstance() {
		return new MailSender();
	}

	public static SmsSender getSmsSenderInstance() {
		return new SmsSender();
	}
}
