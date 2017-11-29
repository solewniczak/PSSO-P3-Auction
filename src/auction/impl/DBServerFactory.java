package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionServer;
import auction.IServerFactory;

public class DBServerFactory implements IServerFactory {
	// Singleton
	protected static DBServerImpl uniqueInstance = new DBServerImpl();
	
	@Override
	public IAuctionServer createServer() throws RemoteException {
		return uniqueInstance;
	}

}
