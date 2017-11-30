package auction.impl;

import java.rmi.RemoteException;

import auction.IAuctionListener;
import auction.support.FileLogger;

public aspect Logging {
	FileLogger StandardServerImpl.fileLogger = new FileLogger("server.log");
    
	/**
     * Client attached to server
     */   
    pointcut attachToServer(StandardServerImpl c, IAuctionListener observer):
    	target(c) && args(observer) && execution(void *.attach(IAuctionListener));
    
	/**
     * Client detached from server
     */   
    pointcut detachToServer(StandardServerImpl c, IAuctionListener observer):
    	target(c) && args(observer) && execution(void *.detach(IAuctionListener));
    
    /**
     * Log client attach
     * @throws RemoteException 
     */
    after (StandardServerImpl c, IAuctionListener observer) throws RemoteException:
    	attachToServer(c, observer) {
    	
    	String msg = observer.getClient() + " attached";
		System.err.println(msg);
		c.fileLogger.log(msg);
    }
    
    /**
     * Log client detach
     * @throws RemoteException 
     */
    after (StandardServerImpl c, IAuctionListener observer) throws RemoteException:
    	detachToServer(c, observer) {
    	
    	String msg = observer.getClient() + " detached";
		System.err.println(msg);
		c.fileLogger.log(msg);
    }
	
}
