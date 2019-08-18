import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) throws Exception {
		
		double lowestValue = 1.0 / Math.pow(10, Utils.precision);
		int ITER = 10;
		int N_LIMIT = 10001;

		List<List<Double>> data = new ArrayList<>();
		for(int i = 0; i < 5; i++) data.add(new ArrayList<>());
		
	
		for(int N = 1; N <= N_LIMIT; N++) {
			double[] items = Utils.generateItems(lowestValue, 0.8, N);
			double[] scores = run(items, ITER);
			for(int i = 0; i < scores.length; i++) data.get(i).add(scores[i]);
			System.out.println("Done for N:" + N);
		}
		
		for(int i = 0; i < data.size(); i++) saveToFile(data.get(i), Utils.LABELS[i]);
	}
	
	private static void saveToFile(List<Double> data, String fileName) {
		fileName = fileName + ".csv";
		StringBuilder builder = new StringBuilder();
		
		for (Double d : data) {
			if (builder.length() > 0) builder.append("\n");
			builder.append(d);
		}

		save(fileName, builder.toString());
		System.out.println("File saved - " + fileName);
	}



	private static void save(String fileName, String data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(data);
			writer.close();
		} catch (IOException e) { }
	}
	
	
	private static double[] run(double[] items, int ITER) throws Exception {
		Packing packing = null;
		double[] scores = new double[5];
		
		for(int i = 0; i < ITER; i++) {
		
			packing = new NextFit(items);
			scores[0] += packing.packBins();
			
			packing = new BestFit(items, false);
			scores[1] += packing.packBins();
			
			packing = new BestFit(items, true);
			scores[2] += packing.packBins();
			
			packing = new FirstFit(items, false);
			scores[3] += packing.packBins();
			
			packing = new FirstFit(items, true);
			scores[4] += packing.packBins();
		}

		
		for(int i = 0; i < 5; i++) scores[i] /= ITER;
		return scores;
		
	}

}
