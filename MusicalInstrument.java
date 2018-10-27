
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc {
	private Number price;
	private String brand;

	// Constructor
	public MusicalInstrument(String brand, Number price) {
		setBrand(brand);
		setPrice(price);
	}

	// Scanner Constructor
	public MusicalInstrument(Scanner scanner) {
		Number price;
		String brand;

		try {
			price = scanner.nextInt();
			setPrice(price);
		} catch (InputMismatchException ex) {
			try {
				price = scanner.nextDouble();
				setPrice(price);
			} catch (InputMismatchException ex2) {
				throw new NumberFormatException("Price not a number!");
			}

		}

		scanner.nextLine();
		brand = scanner.nextLine();
		setBrand(brand);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		if (price.doubleValue() > 0)
			this.price = price;
		else
			throw new IllegalArgumentException("Price must be a positive number!");

	}

	protected boolean isValidType(String[] typeArr, String material) {
		for (int i = 0; i < typeArr.length; i++) {
			if (material.equals(typeArr[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof MusicalInstrument))
			return false;

		MusicalInstrument otherInstrument = (MusicalInstrument) o;

		return getPrice().doubleValue() == otherInstrument.getPrice().doubleValue()
				&& getBrand().equals(otherInstrument.getBrand());
	}

	@Override
	public String toString() {
		return String.format("%-8s %-9s| Price: " + (getPrice() instanceof Integer ? "%7d" : "%7.1f"), getBrand(),
				getClass().getCanonicalName(), getPrice());
	}

	@Override
	public int compareTo(MusicalInstrument other) {
		if (this.getBrand().compareToIgnoreCase(other.getBrand()) == 0) {
			return (new Double(this.getPrice().doubleValue())).compareTo(new Double(other.getPrice().doubleValue()));
		}
		return this.getBrand().compareToIgnoreCase(other.getBrand());
	}
}
