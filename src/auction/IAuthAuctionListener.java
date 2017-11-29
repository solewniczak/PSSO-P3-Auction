package auction;

import java.rmi.RemoteException;

public interface IAuthAuctionListener extends IAuctionListener {
	public String getPass() throws RemoteException;
}
