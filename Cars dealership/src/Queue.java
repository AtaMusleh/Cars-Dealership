
public class Queue {
	orderNode front, rear;
	int size;

	public Queue() {
		front = rear = null;
		size = 0;
	}

	public orderNode getFront() {
		return front;
	}

	public void setFront(orderNode front) {
		this.front = front;
	}

	public orderNode getRear() {
		return rear;
	}

	public void setRear(orderNode rear) {
		this.rear = rear;
	}

	public void enQueue(Order order) {
		orderNode node = new orderNode(order);
		if (isEmpty()) {
			front = node;
			rear = node;
		} else {
			rear.setNext(node);
			rear = node;

		}
		size++;

	}


	public Order deQueue() {
		if (isEmpty()) {
			return null;
		} else {
			Order element = front.getOrder();
			front = front.getNext();
			if (front == null) {
				rear = null;
			}
			size--;
			return element;

		}
	}

	public void checkAndRemove(Order order) {
		orderNode current = front;
		orderNode previous = null;

		while (current != null) {
			if (current.getOrder().equals(order)) {
				if (current.getOrder().getDate().isBefore(order.getDate())) {
					// The existing order has a newer date, so remove it
					if (previous == null) {
						// Removing the front node
						front = current.getNext();
					} else {
						previous.setNext(current.getNext());
					}
					size--;
					break;
				}
			}

			previous = current;
			current = current.getNext();
		}

		// Add the new order to the queue
		enQueue(order);
	}

	public boolean contains(Order order) {
		orderNode current = front;

		while (current != null) {
			if (current.getOrder().getBrand().equals(order.getBrand())
					&& current.getOrder().getModel().equals(order.getModel())
					&& current.getOrder().getYear() == order.getYear()
					&& current.getOrder().getColor().equals(order.getColor())) {
				return true;
			}
			current = current.getNext();
		}

		return false;
	}

	public Order front() {
		if (isEmpty()) {
		}
		return front.getOrder();
	}

	public void clear() {
		front = rear = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (front == null && rear == null);
	}

	public int getSize() {
		return size;
	}

}
