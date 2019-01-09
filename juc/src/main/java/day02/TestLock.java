package day02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * һ�����ڽ�����̰߳�ȫ����ķ�ʽ��
 * 
 * synchronized:��ʽ��
 * 1. ͬ�������
 * 
 * 2. ͬ������
 * 
 * jdk 1.5 ��
 * 3. ͬ���� Lock
 * ע�⣺��һ����ʾ������Ҫͨ�� lock() ��������������ͨ�� unlock() ���������ͷ���
 */
public class TestLock {
	
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(ticket, "1�Ŵ���").start();
		new Thread(ticket, "2�Ŵ���").start();
		new Thread(ticket, "3�Ŵ���").start();
	}

}

class Ticket implements Runnable{
	
	private volatile int tick = 100;

	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		while(true){
			
			lock.lock(); //����
			
			try{
				if(tick > 0){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}

					System.out.println(Thread.currentThread().getName() + " �����Ʊ����ƱΪ��" + --tick);
				}
			}finally{
				lock.unlock(); //�ͷ���
			}
		}
	}
	
}