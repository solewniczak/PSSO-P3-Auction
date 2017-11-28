package auction.support;

import java.io.Serializable;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sko
 */
public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ownerName;
	private String itemName;
	private String itemDesc;
	private double currentBid;
	private int auctionTime;

	public String currentBidderName;

	public Item(String ownerName, String itemName, String itemDesc, double currentBid, int auctionTime) {
		this.ownerName = ownerName;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.currentBid = currentBid;
		this.auctionTime = auctionTime;
	}

	public void bid(String bidderName, double bid) throws RemoteException {
		if (bid < currentBid) {
			throw new RemoteException("bid must be greater than currentBid");
		}
		currentBidderName = bidderName;
		currentBid = bid;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public double getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(double currentBid) {
		this.currentBid = currentBid;
	}

	public int getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(int auctionTime) {
		this.auctionTime = auctionTime;
	}

	public String getCurrentBidderName() {
		return currentBidderName;
	}

	public void setCurrentBidderName(String currentBidderName) {
		this.currentBidderName = currentBidderName;
	}

}
