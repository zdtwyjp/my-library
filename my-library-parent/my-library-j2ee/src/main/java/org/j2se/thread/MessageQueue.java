package org.j2se.thread;
public class MessageQueue {
	
	private String[] messages;
	private int index;
	
	public MessageQueue(int size){
		if(size <0){
			size = 1;
		}
		messages = new String[size];
		index = 0;
	}
	
	public synchronized void put(String message){
		while(index == messages.length){
			System.out.println("消息已满！");
			try {
				wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		messages[index] = message;
		index++;
		System.out.println("生产者 " + Thread.currentThread().getName() + " 生产了一条消息: " + message);  
		notifyAll();
	}
	
	public synchronized String get(){
		while(index == 0){
			System.out.println("消息已完！");
			try {
				wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		String message = messages[index - 1];
		index--;
		System.out.println("消费者 " + Thread.currentThread().getName() + " 消费了一条消息: " + message);  
        // 消费后，对生产者进行唤醒  
        notifyAll();  
        return message; 
	}
	
}
