import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

public class Stack {
	private orderNode top;
	private int size;

	public Stack() {
		top = null;
		size = 0;
	}

	public orderNode getTop() {
		return top;
	}

	public void setTop(orderNode top) {
		this.top = top;
	}

	public void push(Order order) {
		orderNode node = new orderNode(order);
		node.setNext(top);
		top = node;
		size++;
	}

	public Order pop() {
		if (!isEmpty()) {
			orderNode topC = top;
			top = top.getNext();
			size--;
			return topC.getOrder();
		} else {
			return null;
		}
	}

	public Order peek() {
		if (!isEmpty()) {
			return top.getOrder();
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return top == null;
	}

	public int getSize() {
		return size;
	}

	public void reportLastSoldCars(ObservableList<Order> carData) {
		orderNode currentNode = top;
		int count = 0;
		while (currentNode != null && count < 10) {
			Order order = currentNode.getOrder();
			carData.add(order);
			currentNode = currentNode.getNext();
			count++;
		}
	}

}
