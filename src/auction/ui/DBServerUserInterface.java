package auction.ui;

import java.rmi.RemoteException;

import auction.IServerFactory;
import auction.impl.DBServerFactory;
import auction.impl.DBServerImpl;

public class DBServerUserInterface extends AuthServerUserInterface {
	public static void main(String[] args) {
		//Abstract Factory
		IServerFactory sFactory = new DBServerFactory();
		try {
			DBServerImpl server = (DBServerImpl) sFactory.createServer();
			server.loadItems();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
