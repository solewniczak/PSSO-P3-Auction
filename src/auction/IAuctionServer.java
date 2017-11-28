package auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

import auction.support.Item;

/**
 *
 * @author sko
 */
public interface IAuctionServer extends Remote {

	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException;

	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException;

	public Item[] getItems() throws RemoteException;

	public void attach(IAuctionListener observer) throws RemoteException;

	public void detach(IAuctionListener observer) throws RemoteException;

	public void notifyAllObservers() throws RemoteException;

}
