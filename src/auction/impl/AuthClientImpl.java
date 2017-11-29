package auction.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import auction.IAuthAuctionListener;
import auction.IAuthAuctionServer;
import auction.ui.ClientUserInterface;

public class AuthClientImpl extends StandardClientImpl implements IAuthAuctionListener {
	
	// Client Password
	protected String m_password;

	public AuthClientImpl(ClientUserInterface clientInterface) {
		super(clientInterface);
	}
	
	@Override
	public String getPass() {
		return m_password;
	}
	
	@Override
	public boolean initialize(String sName) {
		return false;
	}

	public boolean initialize(String sName, String sPass) {

		try {
			// Define service name
			String name = "AuctionServer";

			// Locate rmi registry
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");

			// Attach to remote object
			m_remote = (IAuthAuctionServer) registry.lookup(name);

			m_client = sName;
			m_password = sPass;

			// Export remote methods
			UnicastRemoteObject.exportObject(this, 0);
			
			((IAuthAuctionServer)m_remote).login(this);

			m_remote.attach(this);

			return true;

		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Chat server not available!");
			System.exit(-1);
			return false;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}

	}
}
