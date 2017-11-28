package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionServer;
import auction.IServerFactory;

//Abstract Factory
public class StandardServerFactory implements IServerFactory {

	// Singleton
	protected static IAuctionServer uniqueInstance = new StandardServerImpl();

	@Override
	public IAuctionServer createServer() throws RemoteException {
		return uniqueInstance;
	}

}
