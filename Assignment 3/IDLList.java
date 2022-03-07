// Vatsal Shah

import java.util.*;

public class IDLList<E> {
	
	// PRIVATE NODE CLASS STARTS //
	private class Node<E> {
		E Data;
		Node<E> next;
		Node<E> prev;
		Node (E elem){
			Data = elem;
		}
		Node(E elem, Node<E> prev, Node<E> next) {
			Data = elem;
			this.next = next;
			this.prev = prev;
		}
	}
	// PRIVATE NODE CLASS ENDS //
	
	private Node<E> head;
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	public IDLList() {
		indices = new ArrayList<Node<E>>();
		head = null;
		tail= null;
	}

	//To add the element at the given index
	public boolean add (int index, E elem) {
		if(index < 0) throw new IllegalArgumentException("Cannot have index less than 0");
		if(index > size) throw new IllegalArgumentException("Cannot have index more than the size");

		Node<E> newData = new Node<E> (elem);
		if(head == null) { //Empty DLL
			head = newData;
			tail = newData;
			size++;
			indices.add(index, newData);
			return true;
		} else if (index == 0) { //Insert at head
			return this.add(elem);
		} else if (index == size) { //Insert at tail
			return this.append(elem);
		} else { //Insert anywhere
			Node<E> nodeAtIndex = indices.get(index);
			nodeAtIndex.prev.next = newData;
			newData.prev = nodeAtIndex.prev;
			nodeAtIndex.prev = newData;
			newData.next = nodeAtIndex;
			size++;
			indices.add(index, nodeAtIndex);
			return true;
		}
	}

	//To add the element at the head
	public boolean add (E elem) {
		Node<E> newData = new Node<E> (elem, null, head);
		newData.next = head;
		newData.prev = null;
		if(head != null) {
			head.prev = newData;
		}
		if(tail == null) {
			tail = newData;
		}
		head = newData;
		size++;
		indices.add(0, newData);
		return true;
	}
	
	//To add the element at the tail
	public boolean append(E elem) {
		Node<E> newData = new Node<E> (elem, tail, null);
		newData.prev = tail;
		newData.next = null;
		if(tail != null) {
			tail.next = newData;
		}
		if(head == null) {
			head = newData;
		}
		tail = newData;
		size++;
		indices.add(size - 1, newData);
		return true;
	}

	//To get the element at the given index
	public E get (int index) {
		if(index < 0) throw new IllegalArgumentException("Cannot have index less than 0");
		if(index > size) throw new IllegalArgumentException("Cannot have index more than the size");

		return indices.get(index).Data;
	}

	//To get the element at the head
	public E getHead () {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return indices.get(0).Data;
	}

	//To get the element at the tail
	public E getLast () {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return indices.get(size - 1).Data;
	}

	//To return the size
	public int size() {
		return size;
	}

	//To remove the element at the head
	public E remove() {
		Node<E> delData = head;
		head = head.next;
		head.prev = null;
		size--;
		indices.remove(0);
		return delData.Data;
	}

	//To remove the element at the tail
	public E removeLast() {
		Node<E> delData = tail;
		tail = tail.prev;
		tail.next = null;
		size--;
		indices.remove(size - 1);
		return delData.Data;
	}

	//To remove the element at the given index
	public E removeAt (int index) {
		if(index < 0) throw new IllegalArgumentException("Cannot have index less than 0");
		if(index > size) throw new IllegalArgumentException("Cannot have index more than the size");
		if (size == 0) {
			throw new NoSuchElementException();
		}
		if(index == 0) { // Remove at head
			return this.remove();
		} else if(index == size - 1) { // Remove at tail
			return this.removeLast();
		} else { // Remove anywhere
			Node<E> delData = indices.get(index);
			delData.prev.next = delData.next;
			delData.next.prev = delData.prev;
			size--;
			indices.remove(index);
			return delData.Data;
		}	
	}

	//To remove the element if it is present in the list 
	public boolean remove(E elem) {
		if(head == null) throw new NoSuchElementException();
		Node<E> pointer = head;
		int index = 0;
		while(pointer != null) {
			if(pointer.Data.equals(elem)) {
				this.removeAt(index);
				return true;
			}
			pointer = pointer.next;
			index++;
		}	
		return false;
	}

	//To convert the list to string format
	public String toString() {
		String result = "List: ";
		for (Node<E> i : indices) result += i.Data + " ";
		return result;
	}
}
