import java.util.ArrayList;

public interface InventoryFunc<T extends MusicalInstrument> {

	// Add all the String Instruments from one arrayList to another
	public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super StringInstrument> des);

	// Add all the Wind Instruments from one arrayList to another
	public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> src,
			ArrayList<? super WindInstrument> des);

	// Sorts arrayList of Instruments by brand and price
	public void SortByBrandAndPrice(ArrayList<T> array);

	// Binary search in an arrayList of Instruments by brand and price
	public int binnarySearchByBrandAndPrice(ArrayList<T> array, String Brand, Number Price);

	// Add an instrument to an arrayList
	public void addInstrument(ArrayList<T> array, T instrument);

	// Remove an instrument from an arrayList of instruments
	public boolean removeInstrument(ArrayList<T> array, T instrument);

	// Remove all instruments from an arrayList of instruments
	public boolean removeAll(ArrayList<T> array);

}
