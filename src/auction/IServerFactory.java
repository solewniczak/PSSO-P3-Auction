package auction;

import java.rmi.RemoteException;

//Abstract Factory
public interface IServerFactory {
	public IAuctionServer createServer() throws RemoteException;
}
