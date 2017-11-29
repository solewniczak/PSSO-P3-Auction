package auction;

import java.rmi.RemoteException;

public interface IAuthAuctionServer extends IAuctionServer {
	public void login(IAuthAuctionListener observer) throws RemoteException;
}
