package auction.impl;

import java.rmi.RemoteException;
import java.util.Map;

import auction.IAuthAuctionListener;
import auction.support.CredentialsLoader;

import auction.IAuthAuctionServer;

public aspect AuthServer {
	declare parents: StandardServerImpl implements IAuthAuctionServer;

	static Map<String, String> StandardServerImpl.credentials = CredentialsLoader.getCredentials();
	
	public void StandardServerImpl.login(IAuthAuctionListener observer) throws RemoteException {
		if (!credentials.get(observer.getClient()).equals(observer.getPass())) {
			throw new RemoteException("wrong password for " + observer.getClient());
		}
	}
}
