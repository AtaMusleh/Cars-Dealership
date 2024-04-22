
public class brandNode {
	private Brand brand;
	private brandNode next, pre;
	private carLinkedList cars;

	public carLinkedList getCars() {
		return cars;
	}

	public void setCars(carLinkedList cars) {
		this.cars = cars;
	}

	public brandNode(Brand brand) {
		this(brand, null, null, null);
	}

	public brandNode(Brand brand, carLinkedList cars, brandNode next, brandNode pre) {
		this.brand = brand;
		this.cars = cars;
		this.next = next;
		this.pre = pre;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public brandNode getNext() {
		return next;
	}

	public void setNext(brandNode next) {
		this.next = next;
	}

	public brandNode getPre() {
		return pre;
	}

	public void setPre(brandNode pre) {
		this.pre = pre;
	}

}
