package auction.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import auction.ui.ClientUserInterface;

/**
 *
 * @author sko
 */

import auction.IAuctionListener;
import auction.IAuctionServer;
import auction.IBiddingStrategy;
import auction.support.Item;

public class StandardClientImpl implements IAuctionListener {

	// Remote server
	protected IAuctionServer m_remote;

	// Client GUI
	protected ClientUserInterface m_ui;

	// Client name
	protected String m_client;

	// Client strategies
	protected Hashtable<String, IBiddingStrategy> strategies = new Hashtable<String, IBiddingStrategy>();

	public StandardClientImpl(ClientUserInterface clientInterface) {
		m_ui = clientInterface;
	}

	public String getClient() throws RemoteException {
		return m_client;
	}

	public boolean initialize(String sName) {

		try {
			// Define service name
			String name = "AuctionServer";

			// Locate rmi registry
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");

			// Attach to remote object
			m_remote = (IAuctionServer) registry.lookup(name);

			m_client = sName;

			// Export remote methods
			UnicastRemoteObject.exportObject(this, 0);

			m_remote.attach(this);

			return true;

		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Chat server not available!");
			System.exit(-1);
			return false;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}

	}

	@Override
	public void update() throws RemoteException {
		Item[] items = m_remote.getItems();
		// Run client strategies
		for (Item i : items) {
			IBiddingStrategy strategy = strategies.get(i.getItemName());
			if (strategy != null) {
				strategy.doAlgorithm(this, i);
			}
		}
		m_ui.updateItems(items);
	}

	@Override
	public void placeItemForBid(String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {
		m_remote.placeItemForBid(m_client, itemName, itemDesc, startBid, auctionTime);
	}

	@Override
	public Item[] getItems() throws RemoteException {
		return m_remote.getItems();
	}

	@Override
	public void detach() throws RemoteException {
		m_remote.detach(this);
	}

	@Override
	public void bidOnItem(String itemName, double bid) throws RemoteException {
		m_remote.bidOnItem(m_client, itemName, bid);
	}

	@Override
	public void setStrategy(String itemName, IBiddingStrategy s) throws RemoteException {
		strategies.put(itemName, s);
		update();
	}

	@Override
	public void removeStrategy(String itemName) throws RemoteException {
		strategies.remove(itemName);
	}

}
