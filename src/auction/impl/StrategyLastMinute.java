package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionListener;
import auction.IBiddingStrategy;
import auction.support.Item;

//Strategy
public class StrategyLastMinute implements IBiddingStrategy {

	@Override
	public void doAlgorithm(IAuctionListener c, Item i) throws RemoteException {
		String currentBidder = i.getCurrentBidderName();
		String client = c.getClient();
		double currentBid = i.getCurrentBid();
		if (i.getAuctionTime() == 60 && !client.equals(currentBidder)) {
			c.bidOnItem(i.getItemName(), currentBid * 2);
		}
	}

}
