package auction.impl;

import java.rmi.RemoteException;

import auction.IAuthAuctionServer;
import auction.IAuctionListener;
import auction.IAuthAuctionListener;

public aspect AuthClient {
	declare parents: StandardClientImpl implements IAuthAuctionListener;

	declare precedence: AuthClient, Logging; //before AuthClient, before Logging, after Logging, after AuthClient

	String StandardClientImpl.m_password;
	
	public String StandardClientImpl.getPass() {
		return m_password;
	}
	
	public boolean StandardClientImpl.initialize(String sName, String sPass) {
		m_password = sPass;
		return initialize(sName);
	}
	
    before (IAuthAuctionServer c, IAuctionListener observer) throws RemoteException:
    	Logging.attachToServer(c, observer) {
    	c.login((IAuthAuctionListener) observer);
    }
}
