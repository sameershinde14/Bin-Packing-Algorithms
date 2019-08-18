import java.util.Arrays;
import java.util.Random;

public class Utils {

	public static final String[] LABELS = {"NEXT_FIT", "BEST_FIT", "BEST_FIT_DECR", "FIRST_FIT", "FIRST_FIT_DECR"};
	public static final int precision = 7;
	public static double[] generateItems(double min, double max, int size) {
		
		Random random = new Random(System.currentTimeMillis());
		double[] items = new double[size];
		while(size > 0) {
			size--;
			items[size] = getRandomNumber(random, min, max);
		} 

		return items;
	}
	
	public static void makeItDecrease(double[] items) {
		int size = items.length;
		for(int i = 0; i < size; i++) items[i] = format(items[i] * -1);
		Arrays.sort(items);
		for(int i = 0; i < size; i++) items[i] = format(items[i] * -1);
	}
	
	public static double getRandomNumber(final Random random, double min, double max) {
		double range = max - min;
		double value = format(random.nextDouble() * range + min);
		return format(value);
	}
	
	public static double format(double value) {
		value *= Math.pow(10, precision);
		value = (int)(value);
		value /= Math.pow(10,precision);
		return value;
	}
}
