/*
 * Programmer: Fabian Martinez
 * Date: 10/28/19
 * 
 * This program was written with extreme guidance from
 * https://eddmann.com/posts/parallel-summation-in-java/
 * 
 * Without the source, not sure how I could better understand this.
 * 
 * Due to creating a separate class extending Threads,
 * thread objects were not needed to be constructed as the CPU
 * can understand the need for adopting more cores based on the amount allowed.
 * 
 * Timings can also be increased regardless of cores depending on background tasks
 * as the threads follow concurrency. CPU-intensive tasks like photo-/video-editing
 * in the background will dramatically increase the timings.
 * 
 */


import java.util.Random;

public class Test {
	public static void main(String[] args) {
		Random rand = new Random();
		Sum.setThreads(2);
		
		// Initializing and populating array with random numbers between 1 and 10 inclusive
		int[] arr = new int[200000000];
		for (int i = 0; i < arr.length; i++)
			arr[i] = rand.nextInt(11) + 1;
		
		
		// Single-Thread Summation
		Sum.setThreads(1);
		long startTime = System.currentTimeMillis();
		int single = Sum.sum(arr);
		System.out.println("1-Threaded Sum: " + (System.currentTimeMillis() - startTime));
		System.out.println("\t\tSum: " + single);
		
		
		// Parallel-Threaded Summation
		Sum.setThreads(2);
		startTime = System.currentTimeMillis();
		int parallel2 = Sum.parallelSum(arr);
		System.out.println("2-Threaded Sum: " + (System.currentTimeMillis() - startTime));
		System.out.println("\t\tSum: " + parallel2);
		
		Sum.setThreads(3);
		startTime = System.currentTimeMillis();
		int parallel3 = Sum.parallelSum(arr);
		System.out.println("3-Threaded Sum: " + (System.currentTimeMillis() - startTime));
		System.out.println("\t\tSum: " + parallel3);
		
		Sum.setThreads(4);
		startTime = System.currentTimeMillis();
		int parallel4 = Sum.parallelSum(arr);
		System.out.println("4-Threaded Sum: " + (System.currentTimeMillis() - startTime));
		System.out.println("\t\tSum: " + parallel4);
		
		// Most computers are quad-core (8 threads - 2 threads per core)
		// however if you are running more than that then by default,
		// setting threads to <= 0 will use all available cores per my class definition
		Sum.setThreads(0);
		startTime = System.currentTimeMillis();
		int parallel = Sum.parallelSum(arr);
		System.out.println(Runtime.getRuntime().availableProcessors()+ "-Threaded Sum: " + (System.currentTimeMillis() - startTime));
		System.out.println("\t\tSum: " + parallel);
	}

}
