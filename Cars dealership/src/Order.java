import java.time.LocalDate;

public class Order {
	private String name;
	private int number;
	private String brand;
	private String model;
	private int year;
	private String color;
	private String price;
	private LocalDate date;

	public Order(String name, int number, String brand, String model, int year, String color, String price,
			LocalDate date) {
		this.name = name;
		this.number = number;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.color = color;
		this.price = price;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order{" + "name='" + name + '\'' + ", number=" + number + ", brand='" + brand + '\'' + ", model='"
				+ model + '\'' + ", year=" + year + ", color='" + color + '\'' + ", price='" + price + '\'' + ", date="
				+ date + '}';
	}

}
