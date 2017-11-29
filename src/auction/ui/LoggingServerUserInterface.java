package auction.ui;

import java.rmi.RemoteException;

import auction.IServerFactory;
import auction.impl.LoggingServerFactory;

public class LoggingServerUserInterface extends ServerUserInterface {
	public static void main(String[] args) {
		//Abstract Factory
		IServerFactory sFactory = new LoggingServerFactory();
		try {
			sFactory.createServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
