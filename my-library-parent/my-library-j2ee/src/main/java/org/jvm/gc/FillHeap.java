
package org.jvm.gc;

import java.util.ArrayList;
import java.util.List;

public class FillHeap {
	
	static class OOMObject{
		public byte[] placeholder = new byte[64 * 1024];
	}
	
	public static void fillHeap(int num) throws InterruptedException{
		List<OOMObject> list = new ArrayList<FillHeap.OOMObject>();
		for(int i = 0; i < num; i++){
			System.out.println(i);
			Thread.sleep(100);
			list.add(new OOMObject());
			if(i % 100 == 0){
				System.gc();
			}
		}
		System.gc();
		Thread.sleep(5000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		fillHeap(1000);
	}
}
