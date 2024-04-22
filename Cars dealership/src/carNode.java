
public class carNode {
	private Car car;
	private carNode next;

	public carNode(Car car) {
		this(car, null);
	}

	public carNode(Car car, carNode next) {
		this.car = car;
		this.next = next;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public carNode getNext() {
		return next;
	}

	public void setNext(carNode next) {
		this.next = next;
	}

}
