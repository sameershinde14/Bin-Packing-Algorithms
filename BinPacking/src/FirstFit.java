
public class FirstFit extends Packing {
	
	protected final boolean shouldDecrease;
	protected AVLTree tree;
	private int binSize = 0;
	
	public FirstFit(double[] items, boolean shouldDecrease) {
		super(items);
		this.shouldDecrease = shouldDecrease;
		if(this.shouldDecrease()) Utils.makeItDecrease(this.items);
		this.tree = new AVLTree();
	}

	@Override
	protected void pack(double item) throws Exception {
	
		int index = this.tree.find(item);
		
		if(index == Integer.MAX_VALUE) {
			this.addItem(item, binSize);
			this.tree.add(binSize, this.bins.get(binSize).remainingCapacity);
			this.binSize++;
			return;
		}
		
		Bin bin = this.bins.get(index);
		this.tree.delete(bin.index, bin.remainingCapacity);
		bin.addItem(item);
		this.tree.add(bin.index, bin.remainingCapacity);
		this.bins.set(bin.index, bin);
	}
	
	@Override
	protected boolean shouldDecrease() {
		return this.shouldDecrease;
	}
	
	

	static class Node {
		public double value;
		public int minIndex, height, index;
		public Node left, right;
		
		public Node(double value, int index) {
			this.index = index;
			this.minIndex = index;
			this.value = value;
		}
		
		public static void copyTo(Node fromNode, Node toNode) {
			if(fromNode == null || toNode == null) return;
			toNode.value = fromNode.value;
			toNode.index = fromNode.index;
		}
			
		@Override
		public String toString() {
			return "Bin #" + Integer.toString(index) + ": " + Double.toString(value);
		}
	}
	
	static class AVLTree {
		public Node root;
		
		public void printInorder(Node node) {
			if(node == null) return;
			printInorder(node.left);
			System.out.println(node.toString());
			printInorder(node.right);
		}
		
	    int height(Node N) { 
	        if (N == null) 
	            return 0; 
	  
	        return N.height; 
	    } 
	    
	    public int find(double item) {
	    		int index = find(this.root, item);
	    		return index;
	    }
	    
	    
		private int find(Node node, double item) {
			if(node == null) return Integer.MAX_VALUE;
			if(node.value < item) return find(node.right, item);
			if(node.value > item) {
				int rightMinIndex = node.right == null ? Integer.MAX_VALUE : node.right.minIndex;
				int minIndex = Math.min(node.index, rightMinIndex);
				minIndex = Math.min(minIndex, find(node.left, item));
				return minIndex;
			}
			
			int minIndex = Math.min(find(node.left, item), find(node.right, item));
			minIndex = Math.min(minIndex, node.index);
			return minIndex;		
		}
		
		public void add(int index, double item) {
			this.root = add(this.root, index, item);
			//adjustMinIndex(this.root, true);
		}
		
		private Node add(Node node, int index, double item) { 
			  
	        if (node == null) return new Node(item, index); 
	  
	        if (item <= node.value) node.left = add(node.left, index, item); 
	        else node.right = add(node.right, index, item); 

	        node.height = 1 + Math.min(height(node.left), height(node.right)); 

	        int balance = getBalance(node); 

	        if (balance > 1 && item < node.left.value) return rightRotate(node); 
	  
	        if (balance < -1 && item > node.right.value) return leftRotate(node); 

	        if (balance > 1 && item > node.left.value) { 
	            node.left = leftRotate(node.left); 
	            return rightRotate(node); 
	        } 
	  
	        if (balance < -1 && item < node.right.value) { 
	            node.right = rightRotate(node.right); 
	            return leftRotate(node); 
	        } 

	        adjustMinIndex(node);
	        return node; 
	    } 
		
		
	    private Node minValueNode(Node node) {  
	        Node current = node;  

	        while (current.left != null)  
	        current = current.left;  
	  
	        return current;  
	    }  
	
	    
	    public void delete(int index, double item) {
	    		this.root = this.delete(this.root, index, item);
	    		//adjustMinIndex(this.root, true);
	    }
	    
	    
		private Node delete(Node node, int index, double item) {   
	        if (node == null) return node;  
	        
	        if (item < node.value)  node.left = delete(node.left, index, item);  
	        else if (item > node.value) node.right = delete(node.right, index, item); 
	        else if (index != node.index) {
	        		node.left = delete(node.left, index, item);
	        		node.right = delete(node.right, index, item);
	        }
	        else
	        {  
	  
	            if ((node.left == null) || (node.right == null))  
	            {  
	                Node temp = null;  
	                if (temp == node.left)  
	                    temp = node.right;  
	                else
	                    temp = node.left;  
	  
	                if (temp == null)  
	                {  
	                    temp = node;  
	                    node = null;  
	                }  
	                else node = temp; 
	                            
	            }  
	            else
	            {  
	                Node temp = minValueNode(node.right);  

	                Node.copyTo(temp, node);
	                node.right = delete(node.right, temp.index, temp.value);  
	            }  
	        }  
	  
	        if (node == null) return node;  
	  
	        node.height = Math.max(height(node.left), height(node.right)) + 1;  

	        int balance = getBalance(node);  

	        if (balance > 1 && getBalance(node) >= 0) return rightRotate(node);  

	        if (balance > 1 && getBalance(node.left) < 0) {  
	            node.left = leftRotate(node.left);  
	            return rightRotate(node);  
	        }  
	  
	        if (balance < -1 && getBalance(node.right) <= 0) return leftRotate(node);  
	  
	        if (balance < -1 && getBalance(node.right) > 0) {  
	        		node.right = rightRotate(node.right);  
	            return leftRotate(node);  
	        }  
	  
	        adjustMinIndex(node);
	        return node;  
	    }  
	    

	    Node rightRotate(Node y) { 
	        Node x = y.left; 
	        
	        if(x == null) {
        			return y;
	        }
	        
	        Node T2 = x.right; 

	        x.right = y; 
	        y.left = T2; 
	  
	        y.height = Math.max(height(y.left), height(y.right)) + 1; 
	        x.height = Math.max(height(x.left), height(x.right)) + 1; 

	        adjustMinIndex(y);
	        adjustMinIndex(x);
	        
	        return x; 
	    } 
	  
	    Node leftRotate(Node x) { 
	        Node y = x.right; 
	        
	        if(y == null) {
	        		return x;
	        }
	        
	        Node T2 = y.left; 
	  
	        y.left = x; 
	        x.right = T2; 

	        x.height = Math.max(height(x.left), height(x.right)) + 1; 
	        y.height = Math.max(height(y.left), height(y.right)) + 1; 
	        
	        adjustMinIndex(x);
	        adjustMinIndex(y);
	        
	        return y; 
	    } 

	    int getBalance(Node N) { 
	        if (N == null) 
	            return 0; 
	  
	        return height(N.left) - height(N.right); 
	    }
	    
	    private int adjustMinIndex(Node node) {
	    		if(node == null) return Integer.MAX_VALUE;
	    		int minIndex = node.index;
	    		int leftMinIndex = node.left == null ? Integer.MAX_VALUE : node.left.minIndex;
	    		int rightMinIndex = node.right == null ? Integer.MAX_VALUE : node.right.minIndex;
	    		minIndex = Math.min(minIndex, leftMinIndex);
	    		minIndex = Math.min(minIndex, rightMinIndex);
	    		node.minIndex = minIndex;
	    		return minIndex;
	    }
	}
	
}
