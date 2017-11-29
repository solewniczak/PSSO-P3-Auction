package auction.ui;

import java.rmi.RemoteException;

import auction.IServerFactory;
import auction.impl.AuthServerFactory;

public class AuthServerUserInterface {
	public static void main(String[] args) {
		//Abstract Factory
		IServerFactory sFactory = new AuthServerFactory();
		try {
			sFactory.createServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
