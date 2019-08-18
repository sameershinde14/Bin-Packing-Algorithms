

public class Bin {

	public double capacity;
	public double remainingCapacity;
	public int index;
	
	public Bin(int index) {
		this(index, 1.0);
	}
	
	public Bin(int index, double capacity) {
		this.index = index;
		this.capacity = Utils.format(capacity);
		this.remainingCapacity = Utils.format(capacity);
	}

	
	public void addItem(double item) {
		this.remainingCapacity -= item;
		this.remainingCapacity = Utils.format(this.remainingCapacity);
	}
	
	@Override
	public String toString() {
		return "Bin #" + Integer.toString(index) 
		+ " | CAP: " + Double.toString(capacity) 
		+ " | RCAP: " + Double.toString(remainingCapacity);
	}
}
