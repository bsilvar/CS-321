import java.util.NoSuchElementException;

public class Cache<T> {

	private int count, size;
	private LinearNode<T> head, tail;

	public Cache(int cacheSize) {
		size = cacheSize;
		count = 0;
		head = tail = null;
	}

	public T getObject() {
		return first();
	}

	// adds object to the front
	public void addObject(T element) {
		LinearNode<T> node = new LinearNode<T>(element);
		if(size() == 0) {
			head = tail = node;
		} else {
			node.setNext(head);
			head = node;
		}
		count++;
	}
	
	public T removeObject(T target) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		LinearNode<T> current = head;
		LinearNode<T> previous = null;
		boolean found = false;
		while(current != null && !found) {
			if(current.getElement().equals(target)) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		if(!found) {
			throw new NoSuchElementException();
		}
		if(size() == 1) {
			head = tail = null;
		} else if(current.equals(head)) {
			head = head.getNext();
		} else if(current.equals(tail)) {
			tail = previous;
			tail.setNext(null);
		} else {
			previous.setNext(current.getNext());
		}
		count--;
		return current.getElement();
	}
	
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return removeObject(last());
	}
	
	private T first() {
		return head.getElement();
	}
	
	private T last() {
		return tail.getElement();
	}
	
	public boolean contains(T target) {
		LinearNode<T> current = head;
		while(current != null) {
			if(target.equals(current.getElement())) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	public void clearCache() {
		count = 0;
		head = tail = null;
	}
	
	private boolean isEmpty() {
		return (size() == 0);
	}

	public boolean isFull() {
		return (count == size);
	}
	
	private int size() {
		return count;
	}
	
	public String toString() {
		String result = "[ ";
		LinearNode<T> current = head;
		while(current != tail) {
			result += current.getElement().toString() + ", ";
			current = current.getNext();
		}
		result += current.getElement().toString() + " ]";
		return result;
	}

}

