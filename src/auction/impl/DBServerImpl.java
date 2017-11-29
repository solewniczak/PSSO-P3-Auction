package auction.impl;

import java.rmi.RemoteException;

import auction.support.ItemsWriter;

public class DBServerImpl extends AuthServerImpl {
	
	protected static ItemsWriter itemsWriter = new ItemsWriter("items.db");
	protected boolean itemsLoaded = false;
	
	@Override
	public void loadItems() {
		//load items
		items = itemsWriter.load();
		System.err.println("items loaded from DB");
	}
	
	@Override
	public void notifyAllObservers() throws RemoteException {
		//save items to DB
		itemsWriter.save(items);
		
		super.notifyAllObservers();
	}
}
