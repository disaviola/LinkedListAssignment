//Disa Röed Sahlström, disa7213

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyQueue<Type> implements ALDAQueue<Type> {

	private int capacity;
	private int count = 0;
	private Node<Type> head;
	private Node<Type> last;
	
	
	public MyQueue(int capacity){
		if (capacity == 0 || capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		head = new Node<Type>(null, null);
		last = new Node<Type>(null, null);
	}
	
	private static class Node<Type>{
		public Type data;
		public Node<Type> nextLink;
		
		public Node(Type data, Node<Type> nextLink) {
			this.data = data;
			this.nextLink = nextLink;
		}
	}

	@Override
	public Iterator<Type> iterator() {
		return new MyQueueIterator();
	}
	
	public class MyQueueIterator implements java.util.Iterator<Type>{
		private Node<Type> current = head;
		@Override
		public boolean hasNext() {
			 if (current.nextLink != null) {
				 return true;
			 }
			 return false;
		}

		@Override
		public Type next() {
			current = current.nextLink;
			if(current == null) {
				throw new NoSuchElementException();
			}
			Type nextItem = current.data;
			return nextItem;
		}
	}

	@Override
	public void add(Type element) {
		if(element == null) {
			throw new NullPointerException();
		}
		if (capacity == count) {
			throw new IllegalStateException();
		}
		Node<Type> previousLast = last;
		last = new Node<Type>(element, null);
		if (isEmpty()) {
			head.nextLink = last;
		} else{
			previousLast.nextLink = last;
		}
		count++;
		return;
		
	}

	@Override
	public void addAll(Collection<? extends Type> c) {
		for(Type t : c){
			add(t);
			count++;
		}
		return;
	}

	@Override
	public Type remove() {
		Node<Type> removed;
		removed = head.nextLink;
		if(removed == null) {
			throw new NoSuchElementException();
		}
		head.nextLink = head.nextLink.nextLink;
		count--;
		return removed.data;
	}

	@Override
	public Type peek() {
		if(head.nextLink == null) {
			return null;
		}
		return head.nextLink.data;
	}

	@Override
	public void clear() {
		head.nextLink = null;
		last = null;
		count = 0;
	}

	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (head.nextLink == null) {
		return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if(count == capacity) {
			return true;
		}
		return false;
	}

	@Override
	public int totalCapacity() {
		return capacity;
	}

	@Override
	public int currentCapacity() {
		return capacity - count;
	}

	@Override
	public int discriminate(Type e) {
		int moved = 0;
		MyQueue<Type> discriminated = new MyQueue<>(capacity);
		MyQueue<Type> nonDiscriminated = new MyQueue<>(capacity);
		if (e == null) {
			throw new NullPointerException();
		} else {
			for(Type t : this) {
				if(t.equals(e)){
					discriminated.add(e);
					moved++;
				} else {
					nonDiscriminated.add(t);
				}
			}
		} 
		clear();
		addAllQueue(nonDiscriminated);
		addAllQueue(discriminated);
		return moved;
	}
	
	private void addAllQueue(MyQueue<Type> q) {
		for (Type t : q) {
			add(t);
		}
		return;
	}
	
	@Override
	public String toString() {
		String print = "";
		for (Type t : this) {
			print = print + (t + ", ");
		}
		if (print == "") {
			return "[]";
		}
		print = print.substring(0, (print.length()-2));
		return "[" + print + "]";
	}
	
}
