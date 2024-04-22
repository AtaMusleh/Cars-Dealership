
public class Car implements Comparable<Car> {
	private String model;
	private int year;
	private String color;
	private String price;

	public Car(String model, int year, String color, String price) {
		this.model = model;
		this.year = year;
		this.color = color;
		this.price = price;

	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public int compareTo(Car o) {
		return Integer.compare(year, o.year);
	}
	@Override
	public String toString() {
		return model+" "+year+" "+color+" "+price;
	}

}
