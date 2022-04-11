// Vatsal Shah 10474245

import java.util.*;

public class Treap<E extends Comparable<E>> {
	//	Constructor Starts
	private static class Node<E extends Comparable<E>>{
		public E data;
		public int priority;
		public Node <E> left;
		public Node <E> right;
		
		//	Initialize data
		public Node(E data, int priority) {
			if(data == null) {
				throw new NoSuchElementException("Data cannot be null!");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		//	Initialization ends
		
		//	Right Rotation 	
		Node<E> rotateRight(){
			Node<E> x = new Node<E>(this.data, this.priority);
			if(this.left != null) {
				if(this.right != null) {
					x.right = this.right;
				}
				if(this.left.right != null ) {
					x.left = this.left.right;
				}
				this.priority = this.left.priority;
				this.data = this.left.data;
				this.right = x;
				if(this.left.left == null) {
					this.left = null;
				} else {
					this.left = this.left.left;
				}
			}
			return x;
		}
		
		//	Left Rotation 	
		Node<E> rotateLeft(){
			Node<E> x = new Node<E>(this.data, this.priority);
			if(this.right != null) {
				if(this.left != null) {
					x.left = this.left;
				}
				if(this.right.left != null ) {
					x.right = this.right.left;
				}
				this.priority = this.right.priority;
				this.data = this.right.data;
				this.left = x;
				if(this.right.right == null) {
				this.right = null;
				} else {
					this.right = this.right.right;
				}
			}
		return x;
		}
		
		//	toString function
		@Override
		public String toString() {
			return data.toString();
		}
	}
	//	Constructor Ends
	
	private Random priorityGenerator;
	private Node<E> root;
	
	//	Constructor that initializes random prioirtyGenerator variable
	public Treap() {
		priorityGenerator = new Random();
		root = null;
	}
	
	public Treap(long seed) {
		priorityGenerator = new Random(seed);
		root = null;
	}
	
	//	Add function with a key and random priority
	public boolean add(E key) {
		return add(key, priorityGenerator.nextInt());
	}
	
	//	Add function with a key and a priority
	public boolean add(E key, int priority) {
		Node<E> r = root;
		Stack<Node<E>> stack= new Stack<Node<E>>();
		if (root != null) {
			while(r != null) {
				stack.push(r);
				if(r.data.compareTo(key) == 0) {
					return false;
				}
				if(r.data.compareTo(key) > 0) {
					r = r.left;
				} else {
					r = r.right;
				}
			}
			r = stack.peek();
			if(r.data.compareTo(key) > 0) {
				Node<E> a = new Node<E>(key, priority);
				r.left = a;
				stack.push(a);
			} else {
				Node<E> a = new Node<E>(key, priority);
				r.right = a;
				stack.push(a);
			}
			
			Node<E> a = stack.pop();
			reheap(stack, a);
			return true;
		}
		root = new Node<E>(key, priority);
		return true;
	}
	
	private void reheap(Stack<Node<E>> stack, Node<E> a) {
		Node<E> x = stack.pop();
		while(x != null && x.priority < a.priority) {
			if(a.data.compareTo(x.data) > 0) {
				x.rotateLeft();
				if(stack.isEmpty()) {
					return;
				}
				x = stack.pop();
			} else {
				x.rotateRight();
				if(stack.isEmpty()) {
					return;
				}
				x = stack.pop();
			}
		} 
	}
	
	//	Delete function Starts
	public boolean delete(E key) {
		if(find(key) == true) {
			Node<E> del = root;
			Node<E> n = null;
			
			while(del.data.compareTo(key) != 0) {
				n = del;
				if(del.data.compareTo(key) > 0) {
					del = del.left;
				} else {
					del = del.right;
				}
			}
			
			while(!(del.left == null && del.right == null)) {
				n = del;
				if(del.left == null) {
					del = del.rotateLeft();
				} else if(del.right == null) {
					del = del.rotateRight();
				} else if(del.left.priority > del.right.priority) {
					del = del.rotateRight();
				} else {
					del = del.rotateLeft();
				}
			}
			if(root.data.compareTo(key) == 0 && root.left == null && root.right == null) {
				root = null;
			} else if(n.left != null && n.left.data.compareTo(key) == 0) {
				n.left = null;
			} else {
				n.right = null;
			}
			return true;
		}
		return false;
	}
	
	public boolean find(E key) {
		if(key == null) {
			throw new NoSuchElementException("Key cannot be null!");
		}
		return find(root, key);
	}
	
	//	Find function with key and root and key
	public boolean find(Node<E> root, E key) {
		if(root == null) {
			return false;
		}
		if(key.compareTo(root.data) == 0) {
			return true;
		}
		if(key.compareTo(root.data) < 0) {
			return find(root.left, key);
		}
		return find(root.right, key);
	}
	
	public String toString() {
		StringBuilder S = new StringBuilder();
		preOrderTraversal(root,S,1);
		return S.toString();
	}
	
	public void preOrderTraversal(Node<E> curr, StringBuilder S, int n) {
		int i = 0;
		while(i < n) {
			S.append(" ");
			i++;
		}
		if(curr != null) {
			S.append("(key="+curr.toString()+", priority="+curr.priority+")\n");
			preOrderTraversal(curr.left, S, n+1);
			preOrderTraversal(curr.right, S, n+1);
		} else {
			S.append("null\n");
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Treap<Integer> t=new Treap<Integer>();
		t.add(4,19);
		t.add(2,31);
		t.add(6,70);
		t.add(1,84);
		t.add(3,12);
		t.add(5,83);
		t.add(7);
		System.out.println("Tree :\n"+t.toString());
		System.out.println("Find result="+t.find(7));
		System.out.println(t.delete(7));
		System.out.println(t.delete(5)+"\n");
		System.out.println("Tree:\n"+t.toString());
	}
}























