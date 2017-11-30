package auction.ui;


public aspect AuthClientUserInterface {
	static String ClientUserInterface.clientPass;
	
	pointcut ClientUserInterfaceMain(String[] args):
    	args(args) && execution(void ClientUserInterface.main(String[]));
	
	before(String[] args): ClientUserInterfaceMain(args) {
		if (args.length >= 2)
			ClientUserInterface.clientPass = args[1];
	}
	
	pointcut clientInitialize(ClientUserInterface c, String clientName):
    	this(c) && args(clientName) && call(boolean *.initialize(String));
	
	boolean around(ClientUserInterface c, String clientName): clientInitialize(c, clientName) {
		return c.m_client.initialize(ClientUserInterface.clientName, ClientUserInterface.clientPass);
	}
	
}
