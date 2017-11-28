package auction.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import auction.IAuctionListener;
import auction.IAuctionServer;
import auction.support.Item;

/**
 *
 * @author sko
 */
public class StandardServerImpl implements IAuctionServer {

	private List<Item> items = new ArrayList<Item>();
	private List<IAuctionListener> observers = new ArrayList<IAuctionListener>();

	private Registry m_registry;

	// Singleton
	private static StandardServerImpl uniqueInstance = new StandardServerImpl();
	public static StandardServerImpl instance() {
		return uniqueInstance;
	}

	private StandardServerImpl() {
		items.add(new Item("Ada", "rower", "rower gorski", 500.0, 60 * 60));
		items.add(new Item("Ada", "radio", "radio tranzystorowe", 100.0, 60 * 60));
		items.add(new Item("Beata", "laptop", "Inter i7", 2000.0, 60));

		try {
			// Define service name
			String name = "AuctionServer";

			// Export server object
			IAuctionServer stub = (IAuctionServer) UnicastRemoteObject.exportObject(this, 0);

			// Locate rmi registry
			m_registry = LocateRegistry.getRegistry();

			// Bind stub to service
			m_registry.rebind(name, stub);

			System.err.println("Server ready");
			// Count action time
			while (true) {
				TimeUnit.SECONDS.sleep(1);
				for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
					Item i = iterator.next();
					int time = i.getAuctionTime();
					if (time > 0) {
						i.setAuctionTime(time - 1);
					}
				}
				this.notifyAllObservers();
			}

		} catch (Exception e) {
			System.err.println("Server error: " + e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {
		// check if itemName is unique
		for (Item i : items) {
			if (i.getItemName().equals(itemName)) {
				throw new RemoteException("item exists");
			}
		}
		Item item = new Item(ownerName, itemName, itemDesc, startBid, auctionTime);
		items.add(item);
		System.err.println("Item placed: " + itemName);
		this.notifyAllObservers();
	}

	@Override
	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException {
		// lookup for item
		for (Item i : items) {
			if (i.getItemName().equals(itemName)) {
				if (i.getAuctionTime() == 0) {
					throw new RemoteException("auction is over");
				}
				i.bid(bidderName, bid);
			}
		}
		this.notifyAllObservers();
	}

	@Override
	public Item[] getItems() throws RemoteException {
		// return (Item [])items.toArray();
		Item[] items2 = new Item[this.items.size()];
		int counter = 0;
		for (Item i : this.items) {
			items2[counter] = i;
			counter++;
		}
		return items2;
	}

	@Override
	public void attach(IAuctionListener observer) throws RemoteException {
		for (IAuctionListener o : observers) {
			if (o.getClient().equals(observer.getClient())) {
				throw new RemoteException("client with name: " + o.getClient() + " alredy registered");
			}
		}
		observers.add(observer);
	}

	@Override
	public void detach(IAuctionListener observer) throws RemoteException {
		observers.remove(observer);
	}

	@Override
	public void notifyAllObservers() throws RemoteException {
		for (IAuctionListener observer : observers) {
			observer.update();
		}
	}
}
