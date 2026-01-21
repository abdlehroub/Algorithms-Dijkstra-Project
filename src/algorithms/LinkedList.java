package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LinkedList<T extends Comparable<T>> implements Iterable<T> {
	private DNode<T> head;

	public LinkedList() { // To Create dummy head
		head = new DNode<T>(null);
		head.setNext(head);
		head.setPrev(head);
	}

	public boolean isEmpty() { // By check if any node linked with the head
		return head.getNext() == head;
	}

	public boolean contains(T data) {
		if (!isEmpty()) {
			DNode<T> curr = head.getNext();
			while (curr != head) {
				if (curr.getData().equals(data)) {
					return true;
				}
				curr = curr.getNext();
			}
		}
		return false;
	}

	public int size() { // Return the number of Nodes in the list
		DNode<T> curr = head.getNext();
		int size = 0;
		while (curr != head) {
			size++;
			curr = curr.getNext();
		}
		return size;
	}

	public void addFirst(T data) { // For Unsorted Linked-List
		DNode<T> node = new DNode<T>(data);
		if (isEmpty()) {
			node.setNext(head);
			head.setPrev(node);
		} else {
			node.setNext(head.getNext());
			head.getNext().setPrev(node);

		}
		node.setPrev(head);
		head.setNext(node);
	}

	public void addAll(LinkedList<T> added) {
		if (!(added == null || added.isEmpty())) {
			DNode<T> thisLast = head.getPrev();
			DNode<T> otherFirst = added.head.getNext();
			DNode<T> otherLast = added.head.getPrev();
			thisLast.setNext(otherFirst);
			otherFirst.setPrev(thisLast);
			otherLast.setNext(this.head);
			this.head.setPrev(otherLast);
		}
	}

	public void addAll(Collection<T> list) {
		for (T t : list) {
			addFirst(t);
		}
	}

	public void insertSorted(T data) { // For Sorted Linked-List
		if (isEmpty()) { // Case 1 -> the list is empty then add the node in the first.
			addFirst(data);
		} else {
			DNode<T> node = new DNode<T>(data);
			DNode<T> curr = head.getNext();
			while (curr.getNext() != head) {
				if (curr.getData().compareTo(data) >= 0)
					break;
				else
					curr = curr.getNext();
			}
			if (curr == head.getNext()) { // Case 2 -> the new node is less than all nodes in the list then add it first
											// too.
				addFirst(data);
			} else if (curr.getNext() == head && curr.getData().compareTo(data) < 0) { // Case 3 -> the current pointer
																						// stopped on the last node
				node.setNext(head);
				node.setPrev(curr);
				curr.setNext(node);
				head.setPrev(node);

			} else { // Case 4 -> if the pointer stopped on node in the middle of the list
				node.setNext(curr);
				node.setPrev(curr.getPrev());
				curr.getPrev().setNext(node);
				curr.setPrev(node);

			}
		}
	}

	public void addLast(T data) { // Add Node as the last node in the list
		DNode<T> node = new DNode<T>(data);
		node.setNext(head);
		node.setPrev(head.getNext());
		head.getPrev().setNext(node);
		head.setPrev(node);
	}

	public void insert(int index, T data) { // Insert node in the list with specific index
		if (index <= size() && index >= 0) {
			DNode<T> curr = head.getNext();
			DNode<T> node = new DNode<T>(data);
			while (index-- > 0) {
				curr = curr.getNext();
			}
			node.setNext(curr);
			node.setPrev(curr.getPrev());
			curr.getPrev().setNext(node);
			curr.setPrev(node);
		} else {
			System.out.println("Invalid index!");
		}

	}

	public T removeFirst() { // Remove the first node in the list
		T data = head.getNext().getData();
		head.setNext(head.getNext().getNext());
		return data;
	}

	public T remove(int index) { // Remove Node by index
		if (isEmpty()) {
			System.out.println("The list is empty!");
		} else if (index < size() && index > 0) {
			DNode<T> curr = head.getNext();
			while (index-- > 0) {
				curr = curr.getNext();
			}
			T data = curr.getData();
			curr.getPrev().setNext(curr.getNext());
			curr.getNext().setPrev(curr.getPrev());
			return data;
		} else {
			System.out.println("Index Out Of Bound!!");
		}
		return null;
	}

	public boolean remove(T data) { // Remove the first Node have the data in arguments
		if (isEmpty()) {
			System.out.println("The list is empty!");
		} else {
			DNode<T> curr = head.getNext();
			while (curr != head) {
				if (curr.getData() == data) {
					curr.getPrev().setNext(curr.getNext());
					curr.getNext().setPrev(curr.getPrev());
					return true;
				}
				curr = curr.getNext();
			}
		}
		return false;
	}

	public T removeLast() { // Remove the last node in the list
		if (isEmpty()) {
			System.out.println("The list is empty!!");
		} else {
			T data = head.getPrev().getData();
			head.getPrev().getPrev().setNext(head);
			head.setPrev(head.getPrev().getPrev());
			return data;
		}
		return null;
	}

	public void removeDuplicate() { // Remove the duplicated nodes recursively
		if (head.getNext() != head)
			removeDuplicate(head.getNext());
	}

	private void removeDuplicate(DNode<T> curr) { // Helper method to remove duplicated nodes recursively
		if (curr.getNext() == head) { // Base Case when the current on the last node in the list
			return;
		} else {
			if (curr.getNext().getData().compareTo(curr.getData()) == 0) {
				curr.setNext(curr.getNext().getNext());
				curr.getNext().setPrev(curr);
				removeDuplicate(curr);
			} else {
				removeDuplicate(curr.getNext());
			}
		}
	}

	public void clear() { // Clear all elements in the list
		head.setNext(head);
		head.setPrev(head);
	}

	public T get(int index) {
		if (head.getNext() != head) {
			DNode<T> curr = head.getNext();
			while (index-- > 0) {
				curr = curr.getNext();
			}
			return curr.getData();
		}
		return null;
	}

	public T getFirst() {
		if (!isEmpty())
			return head.getNext().getData();
		return null;
	}

	public T getLast() {
		if (!isEmpty())
			return head.getPrev().getData();
		return null;
	}

	private DNode<T> getNode(T data) {
		if (head.getNext() != head) {
			DNode<T> curr = head.getNext();
			while (curr != head) {
				if (curr.getData() == data)
					return curr;
				curr = curr.getNext();
			}
		}
		return head;

	}

	public T getNext(T curr) {
		return getNode(curr).getNext() != head ? getNode(curr).getNext().getData() : head.getNext().getData();
	}

	public T getPrev(T curr) {
		return getNode(curr).getPrev() != head ? getNode(curr).getPrev().getData() : head.getPrev().getData();
	}

	public ArrayList<T> toArrayList() {
		ArrayList<T> list = new ArrayList<T>();
		DNode<T> curr = head.getNext();
		while (curr != head) {
			list.add(curr.getData());
			curr = curr.getNext();
		}
		return list;
	}

	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<T> {
		private DNode<T> curr = head.getNext();

		public boolean hasNext() {
			return curr != head;
		}

		public T next() {
			T t = curr.getData();
			curr = curr.getNext();
			return t;
		}
	}

	public Iterable<T> reverseIterable() {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					private DNode<T> curr = head.getPrev();

					public boolean hasNext() {
						return curr != head;
					}

					public T next() {
						T data = curr.getData();
						curr = curr.getPrev();
						return data;
					}
				};
			}
		};
	}

	public T getMiddle() {
		if (head.getNext() != head)
			return getMiddle(head.getNext()).getData();
		else
			return null;
	}

	private DNode<T> getMiddle(DNode<T> start) {
		DNode<T> fast = start;
		DNode<T> slow = start;

		while (fast != head && fast.getNext() != head) {
			fast = fast.getNext().getNext();
			slow = slow.getNext();
		}
		return slow;
	}

	@Override
	public String toString() {
		String s = "Head->";
		DNode<T> curr = head.getNext();
		while (curr != head) {
			s += curr;
			curr = curr.getNext();
		}
		return s + "Head";
	}

}

class DNode<T extends Comparable<T>> {
	private T data;
	private DNode<T> next;
	private DNode<T> prev;

	public DNode(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DNode<T> getNext() {
		return this.next;
	}

	public void setNext(DNode<T> next) {
		this.next = next;
	}

	public DNode<T> getPrev() {
		return this.prev;
	}

	public void setPrev(DNode<T> prev) {
		this.prev = prev;
	}

	public String toString() {
		return "<-[" + this.data + "]->";
	}

}
