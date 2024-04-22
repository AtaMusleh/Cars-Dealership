
public class orderNode {
	private Order order;
	private orderNode next;

	public orderNode(Order order) {
		this(order, null);
	}

	public orderNode(Order order, orderNode next) {
		this.order = order;
		this.next = next;
	}

	public Order getOrder() {
		return order;
	}

	public void setCar(Order order) {
		this.order = order;
	}

	public orderNode getNext() {
		return next;
	}

	public void setNext(orderNode next) {
		this.next = next;
	}
}
