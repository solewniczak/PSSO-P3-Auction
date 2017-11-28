package auction.ui;

import java.rmi.RemoteException;

import auction.IServerFactory;
import auction.impl.StandardServerFactory;

public class ServerUserInterface {
	public static void main(String[] args) {
		//Abstract Factory
		IServerFactory sFactory = new StandardServerFactory();
		try {
			sFactory.createServer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
