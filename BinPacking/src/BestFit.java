import java.util.Stack;
import java.util.TreeMap;

public class BestFit extends Packing {

	protected final boolean shouldDecrease; 
	private TreeMap<Double, Stack<ExtendedBin>> treeMap;
	private int binSize = 0;
	
	public BestFit(double[] items, boolean shouldDecrease) {
		super(items);
		this.treeMap = new TreeMap<>();
		this.shouldDecrease = shouldDecrease;
		if(this.shouldDecrease()) Utils.makeItDecrease(this.items);
	}

	@Override
	protected void pack(double item) {
		Double eBinKey = treeMap.ceilingKey(item);
		
		Bin bin = null;
		if(eBinKey == null || treeMap.get(eBinKey).isEmpty()) {
			bin = new Bin(this.binSize++);
			this.bins.add(bin);
		}
		else {
			Stack<ExtendedBin> eBinsStack = treeMap.get(eBinKey);
			bin = eBinsStack.pop().bin;
			if(eBinsStack.isEmpty()) treeMap.remove(eBinKey);
			else treeMap.put(eBinKey, eBinsStack);
		}
		
		bin.addItem(item);
		Stack<ExtendedBin> eBinsStack = treeMap.getOrDefault(bin.remainingCapacity, new Stack<>());
		eBinsStack.push(new ExtendedBin(bin));
		treeMap.put(bin.remainingCapacity, eBinsStack);
		this.bins.set(bin.index, bin);
	}
	
	@Override
	protected boolean shouldDecrease() {
		return this.shouldDecrease;
	}
	
	static class ExtendedBin implements Comparable<ExtendedBin> {

		public Bin bin;
		
		public ExtendedBin(final Bin bin) {
			this.bin = bin;
		}
		
		@Override
		public int compareTo(ExtendedBin eb) {
			return Double.compare(this.bin.remainingCapacity, eb.bin.remainingCapacity);
		}
		
	}
	
}
