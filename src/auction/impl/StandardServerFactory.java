package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionServer;
import auction.IServerFactory;

//Abstract Factory
public class StandardServerFactory implements IServerFactory {

	@Override
	public IAuctionServer createServer() throws RemoteException {
		IAuctionServer aServer = StandardServerImpl.instance();
		return aServer;
	}

}
