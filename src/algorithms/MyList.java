package algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyList<T> implements Iterable<T> {

	private T[] list;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public MyList() {
		list = (T[]) new Object[10];
	}

	@SuppressWarnings("unchecked")
	public MyList(int capacity) {
		list = (T[]) new Object[capacity];
	}

	/* ================== Core Methods ================== */

	public boolean add(T t) {
		ensureCapacity(size + 1);
		list[size++] = t;
		return true;
	}

	public void shrink() {
		if (size == list.length)
			return;
		T[] newList = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			newList[i] = list[i];
		}
		list = newList;
	}

	public void insert(int index, T t) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();

		ensureCapacity(size + 1);

		for (int i = size; i > index; i--) {
			list[i] = list[i - 1];
		}

		list[index] = t;
		size++;
	}

	public T get(int index) {
		checkIndex(index);
		return list[index];
	}

	public T set(int index, T t) {
		checkIndex(index);
		T old = list[index];
		list[index] = t;
		return old;
	}

	public T remove(int index) {
		checkIndex(index);
		T removed = list[index];

		for (int i = index; i < size - 1; i++) {
			list[i] = list[i + 1];
		}

		list[--size] = null;
		return removed;
	}

	public boolean remove(T o) {
		int index = indexOf(o);
		if (index == -1)
			return false;

		remove(index);
		return true;
	}

	/* ================== Utility ================== */

	public int indexOf(T o) {
		for (int i = 0; i < size; i++) {
			if (o == null ? list[i] == null : o.equals(list[i]))
				return i;
		}
		return -1;
	}

	public boolean contains(T o) {
		return indexOf(o) != -1;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void sort(Comparator<T> comp) {
		Arrays.sort(list, 0, size, comp);
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int minCapacity) {
		if (minCapacity <= list.length)
			return;

		int newCapacity = list.length * 2;
		T[] newArr = (T[]) new Object[newCapacity];

		for (int i = 0; i < size; i++) {
			newArr[i] = list[i];
		}

		list = newArr;
	}

	public ObservableList<T> asObservableList() {
		ObservableList<T> obs = FXCollections.observableArrayList();
		for (int i = 0; i < size; i++) {
			obs.add(list[i]);
		}
		return obs;
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
	}

	/* ================== Iterator ================== */

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public T next() {
				return list[index++];
			}
		};
	}
}
