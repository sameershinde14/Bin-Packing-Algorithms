
public class NextFit extends Packing {
	
	protected final boolean shouldDecrease = false;
	
	public NextFit(double[] items) {
		super(items);
	}

	@Override
	protected void pack(double item) {
		int binSize = this.bins.size();
		boolean canFit = binSize > 0 && this.bins.get(binSize - 1).remainingCapacity >= item;
		int binIndex = binSize - (canFit ? 1 : 0);
		this.addItem(item, binIndex);  
	}

	@Override
	protected boolean shouldDecrease() {
		return this.shouldDecrease;
	}

	
}
