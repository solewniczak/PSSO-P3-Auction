package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionServer;
import auction.IServerFactory;

public class LoggingServerFactory  implements IServerFactory {
	// Singleton
	protected static IAuctionServer uniqueInstance = new LoggingServerImpl();
	
	@Override
	public IAuctionServer createServer() throws RemoteException {
		return uniqueInstance;
	}
}
