package auction.ui;

import java.awt.EventQueue;

public aspect AuthClientUserInterface {
	static String ClientUserInterface.clientPass;
	
	/**
	 * Launch the application.
	 */
	public static void ClientUserInterface.main(String[] args) {
		// Parse arguments
		if (args.length >= 1)
			clientName = args[0];
		if (args.length >= 2)
			clientPass = args[1];
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ClientUserInterface window = new ClientUserInterface();

					window.frame.setTitle(clientName);

					window.initialize();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	pointcut clientInitialize(ClientUserInterface c, String clientName):
    	this(c) && args(clientName) && call(boolean *.initialize(String));
	
	boolean around(ClientUserInterface c, String clientName): clientInitialize(c, clientName) {
		return c.m_client.initialize(ClientUserInterface.clientName, ClientUserInterface.clientPass);
	}
	
}
