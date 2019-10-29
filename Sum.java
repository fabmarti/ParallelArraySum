
public class Sum extends Thread {
	private int[] arr;
	private int min;
	private int max;
	private int total;
	private static int threads;
	
	public Sum(int[] arr, int min, int max) {
		this.arr = arr;
		this.min = min;
		this.max = Math.min(max, arr.length);
	}
	
	public static void setThreads(int threads) {
		if(threads <= 0)
			Sum.threads = Runtime.getRuntime().availableProcessors();
		else {
			try {
				Sum.threads = threads;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("You do not have that many threads.");
			}
		}
			
	}
	
	public int getTotal() {
		return total;
	}
	
	public void run() {
		total = sum(arr, min, max);
	}
	
	
	/* SINGLE SUM
	 * Method Overloading helps break up the array and relate to the constructor
	*/
	public static int sum(int[] arr) {
		return sum(arr, 0, arr.length);
	}
	
	public static int sum(int[] arr, int min, int max)
    {
        int result = 0;

        for (int i = min; i < max; i++) {
            result += arr[i];
        }

        return result;
    }
	
	/* PARALLEL SUM
	 * Method Overloading helps break up the array and relate to the constructor
	 */
	public static int parallelSum(int[] arr)
    {
        return parallelSum(arr, threads);
    }
	
    public static int parallelSum(int[] arr, int threads)
    {
    	// Sets the "range" for each increment of summations per thread
        int size = (int) Math.ceil(arr.length * 1.0 / threads);
        
        // Creates an array of Class Sum objects,
        // each with length = to number of threads
        Sum[] sums = new Sum[threads];

        // Each thread is "initialized"
        for (int i = 0; i < threads; i++) {
            sums[i] = new Sum(arr, i * size, (i + 1) * size);
            sums[i].start();
        }

        // Joins the separate threads back into the main thread for accurate counting
        try {
            for (Sum sum : sums) {
                sum.join();
            }
        } catch (InterruptedException e) { }

        int result = 0;
        for (Sum sum : sums) {
            result += sum.getTotal();
        }

        return result;
    }

}
