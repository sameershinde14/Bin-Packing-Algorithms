import java.util.ArrayList;
import java.util.List;

public abstract class Packing {
	
	protected double binCapacity = 1.0; 
	protected List<Bin> bins;
	protected int nItems;
	protected double[] items;
	
	public Packing(double[] items) {
		this.bins = new ArrayList<>();
		cloneItems(items);
	}
	
	private void cloneItems(double[] items) {
		this.nItems = items.length;
		this.items = new double[this.nItems];
		for(int i = 0; i < this.nItems; i++) this.items[i] = Utils.format(items[i]);
	}
	
	protected double packBins() throws Exception {
		for(double item : this.items) {
			item = Utils.format(item);
			pack(item);
		}
		
		double totalWastage = 0;
		for(Bin bin : this.bins) totalWastage += bin.remainingCapacity;
		return totalWastage;
	}
	
	protected void addItem(double item, int binIndex) {
		int binSize = this.bins.size();
		if(binIndex < 0 || binIndex >= binSize) {
			binIndex = binSize;
			this.bins.add(new Bin(binIndex));
		}
		
		this.bins.get(binIndex).addItem(item);
	} 
	
	protected void printBins() {
		System.out.println("\nPrinting Bins..");
		for(Bin bin : this.bins) {
			System.out.println("\t" + bin.toString());
		}
	}
	
	
	protected abstract boolean shouldDecrease();
	
	protected abstract void pack(double item) throws Exception;
	
}
