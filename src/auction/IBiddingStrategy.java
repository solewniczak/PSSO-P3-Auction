package auction;

import java.rmi.RemoteException;

import auction.support.Item;

// Strategy
public interface IBiddingStrategy {
	public void doAlgorithm(IAuctionListener c, Item i) throws RemoteException;
}
