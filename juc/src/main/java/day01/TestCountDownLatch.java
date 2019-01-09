package day01;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch �������������ĳЩ�����ǣ�ֻ�����������̵߳�����ȫ����ɣ���ǰ����ż���ִ��
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(50);
		LatchDemo ld = new LatchDemo(latch);

		long start = System.currentTimeMillis();

		for (int i = 0; i < 50; i++) {
			new Thread(ld).start();
		}

		try {
			System.out.println("�ȴ�");
			latch.await();
		} catch (InterruptedException e) {
		}

		long end = System.currentTimeMillis();

		System.out.println("�ķ�ʱ��Ϊ��" + (end - start));
	}

}

class LatchDemo implements Runnable {

	private CountDownLatch latch;

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {

		try {
			for (int i = 0; i < 50000; i++) {
				if (i % 2 == 0) {
//					System.out.println(i);
				}
			}
		} finally {
			latch.countDown();
		}

	}

}