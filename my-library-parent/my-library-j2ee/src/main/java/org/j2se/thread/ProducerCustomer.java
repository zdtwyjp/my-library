package org.j2se.thread;

public class ProducerCustomer {
	public static void main(String[] args) {
		final MessageQueue mq = new MessageQueue(10);
		for(int i = 0; i < 3; i++) {
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true) {
						mq.put("消息来了！");
						try {
							Thread.currentThread().sleep(1000);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "Producer " + i).start();
		}
		
		for(int j = 0; j < 3; j++) {
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						mq.get();
						try {
							Thread.currentThread().sleep(1000);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}, "Customer " + j).start();
		}
	}
}
