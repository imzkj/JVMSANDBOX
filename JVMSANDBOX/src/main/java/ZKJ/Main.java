package ZKJ;

public class Main {
	public static void main(String[] args) throws InterruptedException {
//		new Thread() {
//			@Override
//			public void run() {
//				Clock normalClock = new Clock.NormalClock();
//				try {
//					normalClock.loopReport();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();

		new Thread() {
			@Override
			public void run() {
				Clock brokenClock = new Clock.BrokenClock();
				try {
					brokenClock.loopReport();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();



	}
}
