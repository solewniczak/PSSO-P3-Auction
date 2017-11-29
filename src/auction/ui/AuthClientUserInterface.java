package auction.ui;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import auction.impl.AuthClientImpl;
import auction.support.Item;

public class AuthClientUserInterface extends ClientUserInterface {
	
	protected static String clientPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Parse arguments
		if (args.length >= 1)
			clientName = args[0];
		if (args.length >= 2)
			clientPass = args[1];
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					AuthClientUserInterface window = new AuthClientUserInterface();

					window.frame.setTitle(clientName);

					window.initialize();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean initialize() {
		try {
			// Create client object
			m_client = new AuthClientImpl(this);
			if (((AuthClientImpl)m_client).initialize(clientName, clientPass)) {
				frame.setVisible(true);
				Item[] items = m_client.getItems();
				for (Item i : items) {
					Object[] objs = { i.getOwnerName(), i.getItemName(), i.getItemDesc(), i.getCurrentBidderName(),
							i.getCurrentBid(), i.getAuctionTime() };
					tableModel.addRow(objs);
				}

				frame.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						try {
							m_client.detach();
							System.out.println("Closed");
							e.getWindow().dispose();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

				return true;
			}
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
