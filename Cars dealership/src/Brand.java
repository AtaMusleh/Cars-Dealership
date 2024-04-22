
public class Brand implements Comparable<Brand> {
	private String brand;
	private carLinkedList cars;

	public Brand(String brand) {
		this.brand = brand;
		this.cars = new carLinkedList();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public carLinkedList getCars() {
		return cars;
	}

	public void setCars(carLinkedList cars) {
		this.cars = cars;
	}

	@Override
	public int compareTo(Brand o) {
		return brand.compareToIgnoreCase(o.getBrand());
	}

}
