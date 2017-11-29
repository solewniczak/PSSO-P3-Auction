package auction.impl;

import java.rmi.RemoteException;
import java.util.Map;

import auction.IAuthAuctionListener;
import auction.IAuthAuctionServer;
import auction.support.CredentialsLoader;

public class AuthServerImpl extends LoggingServerImpl implements IAuthAuctionServer {
		
	protected static Map<String, String> credentials = CredentialsLoader.getCredentials();
	
	@Override
	public void login(IAuthAuctionListener observer) throws RemoteException {
		if (!credentials.get(observer.getClient()).equals(observer.getPass())) {
			throw new RemoteException("wrong password for " + observer.getClient());
		}
	}
}
