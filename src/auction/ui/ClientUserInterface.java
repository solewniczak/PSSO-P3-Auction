package auction.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import auction.IBiddingStrategy;

/**
 *
 * @author sko
 */

import auction.impl.StandardClientImpl;
import auction.impl.StrategyLastMinute;
import auction.impl.StrategyUpToMax;
import auction.support.Item;

public class ClientUserInterface {

	// Client name argument
	static String clientName;

	// Auction client instance
	protected StandardClientImpl m_client;

	// Columns
	public static final int COLUMN_OWNER_NAME = 0;
	public static final int COLUMN_ITEM_NAME = 1;
	public static final int COLUMN_ITEM_DESC = 2;
	public static final int COLUMN_CURRENT_BIDDER_NAME = 3;
	public static final int COLUMN_CURRENT_BID = 4;
	public static final int COLUMN_AUCTION_TIME = 5;

	// User interface variables
	protected JFrame frame;
	protected DefaultTableModel tableModel;
	protected JTextField itemNameTextField;
	protected JTextField itemDescTextField;
	protected JTextField itemStartBidTextField;
	protected JTextField itemTimeField;

	protected JTextField bidRateTextField;

	protected JTextField strategyUpToMaxMaxTextField;

	/**
	 * Create the application.
	 * 
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public ClientUserInterface() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);

		String columnNames[] = { "Owner name ", "Item name", "Item desc", "Current bider", "Current bid",
				"Auction time" };
		tableModel = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		JScrollPane scrollPane = new JScrollPane(table);

		JPanel formsPane = new JPanel();
		formsPane.setLayout(new BoxLayout(formsPane, BoxLayout.PAGE_AXIS));

		JPanel formPane = new JPanel();
		formPane.setBorder(BorderFactory.createTitledBorder("Place new item"));

		JLabel lblName = new JLabel("Item Name");
		formPane.add(lblName);
		itemNameTextField = new JTextField();
		formPane.add(itemNameTextField);
		itemNameTextField.setColumns(10);

		JLabel lblDesc = new JLabel("Item Desc");
		formPane.add(lblDesc);
		itemDescTextField = new JTextField();
		formPane.add(itemDescTextField);
		itemDescTextField.setColumns(10);

		JLabel lblStartBid = new JLabel("Start bid");
		formPane.add(lblStartBid);
		itemStartBidTextField = new JTextField();
		formPane.add(itemStartBidTextField);
		itemStartBidTextField.setColumns(5);

		JLabel lblTime = new JLabel("Time");
		formPane.add(lblTime);
		itemTimeField = new JTextField();
		formPane.add(itemTimeField);
		itemTimeField.setColumns(5);

		JButton btnSubmit = new JButton("Place item");
		formPane.add(btnSubmit);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (itemNameTextField.getText().isEmpty()) {
						throw new Exception("item name is required");
					}
					if (itemDescTextField.getText().isEmpty()) {
						throw new Exception("item desc is required");
					}
					if (itemStartBidTextField.getText().isEmpty()) {
						throw new Exception("start bid is required");
					}
					if (itemTimeField.getText().isEmpty()) {
						throw new Exception("time is required");
					}
					m_client.placeItemForBid(itemNameTextField.getText(), itemDescTextField.getText(),
							Double.parseDouble(itemStartBidTextField.getText()),
							Integer.parseInt(itemTimeField.getText()));
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, e.getCause().getMessage(), "RemoteException",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ValidationException",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JPanel bidPane = new JPanel();
		bidPane.setBorder(BorderFactory.createTitledBorder("Bid an item"));

		JLabel lblBidRate = new JLabel("Bid rate");
		bidPane.add(lblBidRate);
		bidRateTextField = new JTextField();
		bidPane.add(bidRateTextField);
		bidRateTextField.setColumns(5);

		JButton btnBid = new JButton("Bid");
		bidPane.add(btnBid);

		btnBid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						if (bidRateTextField.getText().isEmpty()) {
							throw new Exception("bid rate is required");
						}
						String itemName = (String) tableModel.getValueAt(row, COLUMN_ITEM_NAME);
						double bid = Double.parseDouble(bidRateTextField.getText());
						m_client.bidOnItem(itemName, bid);
					} else {
						throw new Exception("Select an item to bid");
					}

				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, e.getCause().getMessage(), "RemoteException",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ValidationException",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JPanel strategyUpToMaxPane = new JPanel();
		strategyUpToMaxPane.setBorder(BorderFactory.createTitledBorder("Strategy: Up to max"));

		JLabel lblStrategyUpToMaxMax = new JLabel("Max rate");
		strategyUpToMaxPane.add(lblStrategyUpToMaxMax);
		strategyUpToMaxMaxTextField = new JTextField();
		strategyUpToMaxPane.add(strategyUpToMaxMaxTextField);
		strategyUpToMaxMaxTextField.setColumns(5);

		JButton btnStrategyUpToMax = new JButton("Start");
		strategyUpToMaxPane.add(btnStrategyUpToMax);

		btnStrategyUpToMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						if (strategyUpToMaxMaxTextField.getText().isEmpty()) {
							throw new Exception("max rate is required");
						}
						String itemName = (String) tableModel.getValueAt(row, COLUMN_ITEM_NAME);
						double maxBid = Double.parseDouble(strategyUpToMaxMaxTextField.getText());
						IBiddingStrategy strategy = new StrategyUpToMax(maxBid);
						m_client.setStrategy(itemName, strategy);
					} else {
						throw new Exception("Select an item to set strategy");
					}

				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, e.getCause().getMessage(), "RemoteException",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ValidationException",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JPanel strategyLastMinutPane = new JPanel();
		strategyLastMinutPane.setBorder(BorderFactory.createTitledBorder("Strategy: Last minute"));

		JButton btnStrategyLastMinut = new JButton("Start");
		strategyLastMinutPane.add(btnStrategyLastMinut);

		btnStrategyLastMinut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String itemName = (String) tableModel.getValueAt(row, COLUMN_ITEM_NAME);
						IBiddingStrategy strategy = new StrategyLastMinute();
						m_client.setStrategy(itemName, strategy);
					} else {
						throw new Exception("Select an item to set strategy");
					}

				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, e.getCause().getMessage(), "RemoteException",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ValidationException",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		formsPane.add(formPane);
		formsPane.add(bidPane);
		formsPane.add(strategyUpToMaxPane);
		formsPane.add(strategyLastMinutPane);

		// Create a split pane with the two scroll panes in it.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, formsPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		frame.add(splitPane);
	}

	public boolean initialize() {
		try {
			// Create client object
			m_client = new StandardClientImpl(this);
			if (m_client.initialize(clientName)) {
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Parse arguments
		if (args.length >= 1)
			clientName = args[0];

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

	protected int rowByItemName(String itemName) {
		if (tableModel.getRowCount() > 0) {
			for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
				String rowItemName = (String) tableModel.getValueAt(i, COLUMN_ITEM_NAME);
				if (rowItemName.equals(itemName)) {
					return i;
				}
			}
		}
		return -1;
	}

	// https://stackoverflow.com/questions/356807/java-double-comparison-epsilon
	protected boolean areEqualDouble(double a, double b, int precision) {
		return Math.abs(a - b) <= Math.pow(10, -precision);
	}

	public void updateItems(Item[] items) {
		for (Item i : items) {
			int row = rowByItemName(i.getItemName());
			if (row == -1) {
				Object[] objs = { i.getOwnerName(), i.getItemName(), i.getItemDesc(), i.getCurrentBidderName(),
						i.getCurrentBid(), i.getAuctionTime() };
				tableModel.addRow(objs);

				continue;
			}
			// update Current bidder name
			tableModel.setValueAt(i.getCurrentBidderName(), row, COLUMN_CURRENT_BIDDER_NAME);
			// update current bid
			tableModel.setValueAt(i.getCurrentBid(), row, COLUMN_CURRENT_BID);
			// update auction time
			tableModel.setValueAt(i.getAuctionTime(), row, COLUMN_AUCTION_TIME);
		}
	}

}
