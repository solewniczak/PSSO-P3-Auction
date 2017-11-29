package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionServer;
import auction.IServerFactory;

public class AuthServerFactory implements IServerFactory {
	// Singleton
	protected static IAuctionServer uniqueInstance = new AuthServerImpl();
	
	@Override
	public IAuctionServer createServer() throws RemoteException {
		return uniqueInstance;
	}

}
