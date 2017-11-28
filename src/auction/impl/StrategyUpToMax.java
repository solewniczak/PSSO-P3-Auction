package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionListener;
import auction.IBiddingStrategy;
import auction.support.Item;

//Strategy
public class StrategyUpToMax implements IBiddingStrategy {

	protected double max;

	public StrategyUpToMax(double max) {
		this.max = max;
	}

	@Override
	public void doAlgorithm(IAuctionListener c, Item i) throws RemoteException {
		String currentBidder = i.getCurrentBidderName();
		String client = c.getClient();
		double currentBid = i.getCurrentBid();
		if (!client.equals(currentBidder) && currentBid < max) {
			c.bidOnItem(i.getItemName(), currentBid + 1);
		}
	}

}
