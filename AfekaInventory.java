import java.util.ArrayList;
import java.util.Collections;

// class that manages the inventory of the store
public class AfekaInventory<T extends MusicalInstrument> implements InventoryFunc<T> {

	private ArrayList<T> array = new ArrayList<T>();
	private double sumOfPrice;
	private boolean isSort;

	public AfekaInventory() {
	}

	public AfekaInventory(ArrayList<T> array) {
		setArray(array);
	}

	// ----------------------------------------
	public ArrayList<T> getArray() {
		return array;
	}

	public void setArray(ArrayList<T> array) {
		this.array = array;
	}

	public double getSumOfPrice() {
		return sumOfPrice;
	}

	public void setSumofprice(double sumOfPrice2) {
		this.sumOfPrice = sumOfPrice2;
	}

	public boolean getIsSort() {
		return isSort;
	}

	public void setIsSort(boolean isSort) {
		this.isSort = isSort;
	}

	@Override // add all string instruments
	public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super StringInstrument> des) {
		for (int i = 0; i < src.size(); i++) {
			if (src.get(i) instanceof StringInstrument) {
				des.add((StringInstrument) src.get(i));
				sumOfPrice += src.get(i).getPrice().doubleValue();
			}
		}
		setIsSort(false);
	}

	@Override // add all wind instruments
	public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super WindInstrument> des) {
		for (int i = 0; i < src.size(); i++) {
			if (src.get(i) instanceof WindInstrument) {
				des.add((WindInstrument) src.get(i));
				sumOfPrice += src.get(i).getPrice().doubleValue();
			}
		}
		setIsSort(false);
	}

	@Override // sort by brand and price
	public void SortByBrandAndPrice(ArrayList<T> array) {
		Collections.sort(array);// sort by compareTo
		if (array.size() != 0)
			setIsSort(true);
	}

	@Override // binary search by brand and price
	public int binnarySearchByBrandAndPrice(ArrayList<T> array, String Brand, Number Price) {
		if (!isSort) {
			SortByBrandAndPrice(array);
		}
		int low = 0, high = array.size() - 1;
		int mid = 0;

		while (low <= high) {
			mid = (low + high) / 2;

			if (array.get(mid).getBrand().compareToIgnoreCase(Brand) < 0) {
				low = mid + 1;
			} else if (array.get(mid).getBrand().compareToIgnoreCase(Brand) > 0) {
				high = mid - 1;
			} else if (array.get(mid).getBrand().compareToIgnoreCase(Brand) == 0) {
				if ((array.get(mid).getPrice().doubleValue() == Price.doubleValue())) {
					return mid;
				} else if (array.get(mid).getPrice().doubleValue() > Price.doubleValue())
					high = mid - 1;
				else if (array.get(mid).getPrice().doubleValue() < Price.doubleValue())
					low = mid + 1;
			}
		}

		return -1 - low;
	}

	@Override // add instrument
	public void addInstrument(ArrayList<T> array, T m) {
		array.add(m);
		SortByBrandAndPrice(array);
	}

	@Override // remove instrument
	public boolean removeInstrument(ArrayList<T> array, T instrument) {
		boolean isRemoved = array.remove(instrument);
		if (isRemoved)
			sumOfPrice -= instrument.getPrice().doubleValue();
		return isRemoved;
	}

	@Override // remove all
	public boolean removeAll(ArrayList<T> array) {
		if (array.isEmpty())
			return false;
		else {
			array.removeAll(array);
			setIsSort(false);
			setSumofprice(0);
			return true;
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < array.size(); i++) {
			s += array.get(i).toString();
			s += "\n";
		}

		s += String.format("Total Price: %8.2f \tSorted: %b", getSumOfPrice(), getIsSort());
		return s;

	}
}