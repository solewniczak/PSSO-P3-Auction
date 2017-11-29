package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionListener;
import auction.support.FileLogger;

public class LoggingServerImpl extends StandardServerImpl {
	
	protected static FileLogger fileLogger = new FileLogger("server.log");
	
	@Override
	public void attach(IAuctionListener observer) throws RemoteException {
		super.attach(observer);
		String msg = observer.getClient() + " attached";
		System.err.println(msg);
		fileLogger.log(msg);
	}

	@Override
	public void detach(IAuctionListener observer) throws RemoteException {
		super.detach(observer);
		String msg = observer.getClient() + " detached";
		System.err.println(msg);
		fileLogger.log(msg);
	}
}
