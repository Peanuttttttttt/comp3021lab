package lab9;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };
	int max1,max2,max3;
	Thread thread1 = new Thread(new Runnable() {
		public void run() {
			max1 = findMax(0,29);
		}
	});
	Thread thread2 = new Thread(new Runnable() {
		public void run() {
			max2 = findMax(30,59);
		}
	});
	Thread thread3 = new Thread(new Runnable() {
		public void run() {
			max3 = findMax(60,89);
		}
	});
	public void newfindMax() {
		thread1.start();
		thread2.start();
		thread3.start();
		int max;
		while(thread1.isAlive()||thread2.isAlive()||thread3.isAlive()) {
			
		}
		if(max1>=max2) {
			max = max1;
		}
		else {
			max = max2;
		}
		if(max3>=max) {
			max = max3;
		}
		System.out.println("the max value is " + max);
	}

	public static void main(String[] args) {
		//new FindMax().printMax();
		new FindMax().newfindMax();
	}

	public void printMax() {
		// this is a single threaded version
		int max = findMax(0, array.length - 1);
		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}

