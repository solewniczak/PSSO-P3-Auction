package auction;

import java.rmi.Remote;
import java.rmi.RemoteException;

import auction.support.Item;

/**
 *
 * @author sko
 */
public interface IAuctionListener extends Remote {

	public void update() throws RemoteException;

	public void placeItemForBid(String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException;

	public Item[] getItems() throws RemoteException;

	public void detach() throws RemoteException;

	public void bidOnItem(String itemName, double bid) throws RemoteException;

	public void setStrategy(String itemName, IBiddingStrategy s) throws RemoteException;

	public void removeStrategy(String itemName) throws RemoteException;

	public String getClient() throws RemoteException;

}
