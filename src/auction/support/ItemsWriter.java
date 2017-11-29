package auction.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemsWriter {
	protected String fileName;
	
	public ItemsWriter(String fileName) {
		this.fileName = fileName;
	}
	
	public void save(List<Item> items) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(items);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Item> load() {
		try {
			//default data
			File f = new File(fileName);
			if(!f.exists()) {
				List<Item> items = new ArrayList<>();
				items.add(new Item("Ada", "rower", "rower gorski", 500.0, 60 * 60));
				items.add(new Item("Ada", "radio", "radio tranzystorowe", 100.0, 60 * 60));
				items.add(new Item("Beata", "laptop", "Inter i7", 2000.0, 60));
				this.save(items);
				return items;
			}
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			@SuppressWarnings("unchecked")
			List<Item> items = (List<Item>) ois.readObject();
			ois.close();
			
			return items;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
